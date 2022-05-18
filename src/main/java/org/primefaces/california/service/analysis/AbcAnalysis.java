package org.primefaces.california.service.analysis;

import lombok.*;
import org.primefaces.california.model.entity.AssortmentData;
import org.primefaces.california.model.entity.Product;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class AbcAnalysis implements Analysis, Serializable {

    private List<AssortmentData> assortmentDataList;
    static final String MONEY_FORMAT = "%.2f BYN";

    @Override
    public String getName() {
        return "ABC-анализ";
    }

    @Override
    public Result process() {
        Map<Product, Integer> amountOfProduct = new HashMap<>();
        for (AssortmentData data : assortmentDataList) {
            Product product = data.getProduct();
            if (amountOfProduct.containsKey(product))
                amountOfProduct.put(product, data.getAmount() + amountOfProduct.get(product));
            else
                amountOfProduct.put(product, data.getAmount());
        }
        if (amountOfProduct.size() == 0)
            return null;
        double allRevenue = 0d;
        double allCost = 0d;
        List<Result.Entry> entries = new ArrayList<>();
        for (Product product : amountOfProduct.keySet()) {
            int amount = amountOfProduct.get(product);
            Result.Entry entry = new Result.Entry();
            entry.setProduct(product);
            entry.setRevenue(product.getPrice() * amount);
            allRevenue += entry.getRevenue();
            entry.setSales(amount);
            entry.setCost(amount * product.getCost());
            allCost += amount * product.getCost();
            entry.setMargin(entry.getRevenue() - entry.getCost());
            entry.setProfitability(entry.getMargin() / entry.getRevenue());
            entries.add(entry);
        }
        double allMargin = allRevenue - allCost;
        double allProfitability = entries.stream().map(Result.Entry::getProfitability).collect(Collectors.averagingDouble(Double::doubleValue));
        Result result = new Result(entries, allRevenue, allCost, allMargin, allProfitability);
        result.calculate();
        return result;
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Result implements Analysis.Result{
        @EqualsAndHashCode.Exclude
        private List<Entry> entries;
        private double allRevenue;
        private double allCost;
        private double allMargin;
        private double allProfitability;

        public String getCostString() {
            return String.format(MONEY_FORMAT, allCost);
        }

        public String getMarginString() {
            return String.format(MONEY_FORMAT, allMargin);
        }

        public String getRevenueString() {
            return String.format(MONEY_FORMAT, allRevenue);
        }

        public String getProfitabilityString() {
            return String.format("%.2f %%", (allProfitability * 100));
        }

        @Data
        public static class Entry {
            private Product product;
            private int sales;
            private double cost;
            private double revenue;
            private double margin;
            private double profitability;
            private double shareInRevenue;
            private double shareInMargin;
            private Group group;

            public String getCostString() {
                return String.format(MONEY_FORMAT, cost);
            }

            public String getMarginString() {
                return String.format(MONEY_FORMAT, margin);
            }

            public String getRevenueString() {
                return String.format(MONEY_FORMAT, revenue);
            }

            public String getProfitabilityString() {
                return String.format("%.2f %%", (profitability * 100));
            }

            public String getShareInRevenueString() {
                return String.format("%.2f %%", (shareInRevenue * 100));
            }

            public String getShareInMarginString() {
                return String.format("%.2f %%", (shareInMargin * 100));
            }
        }

        public String getName() {
            return "Результат ABC-анализа";
        }

        private void calculate() {
            for (Entry entry : entries) {
                entry.setShareInRevenue(entry.revenue / allRevenue);
                entry.setShareInMargin(entry.margin / allMargin);
            }
            entries.sort(Comparator.comparingDouble(x -> x.shareInMargin));
            Collections.reverse(entries);
            double accumulation = 0d;
            for (Entry entry : entries) {
                accumulation += entry.getShareInMargin();
                entry.setGroup(Group.defineGroup(accumulation));
            }
        }
    }

    @AllArgsConstructor
    @Getter
    enum Group {
        A(0, 80),
        B(80, 95),
        C(95, 100);
        private final Integer min;
        private final Integer max;

        public static Group defineGroup(double value) {
            value *= 100;
            for (Group group : values()) {
                if (value > group.min && value <= group.max)
                    return group;
            }
            return null;
        }
    }
}
