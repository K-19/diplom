package org.primefaces.california.service.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.primefaces.california.model.dao.SalesDataDao;
import org.primefaces.california.model.entity.AssortmentData;
import org.primefaces.california.model.entity.Market;
import org.primefaces.california.model.entity.Product;
import org.primefaces.california.model.entity.SalesData;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class XyzAnalysis implements Analysis{

    private List<AssortmentData> assortmentDataList;

    @Override
    public String getName() {
        return "XYZ-анализ";
    }

    @Override
    public Result process() {
        Map<Market, List<Product>> map = new HashMap<>();
        for (Market market : assortmentDataList.stream()
                .map(AssortmentData::getMarket)
                .distinct()
                .collect(Collectors.toList())) {
            List<Product> products = assortmentDataList.stream()
                    .filter(x -> x.getMarket().equals(market))
                    .map(AssortmentData::getProduct)
                    .collect(Collectors.toList());
            map.put(market, products);
        }
        List<Result.Entry> entries = new ArrayList<>();
        List<String> months = new ArrayList<>();
        for (Market market : map.keySet()) {
            for (Product product : map.get(market)) {
                List<SalesData> salesDataList = new SalesDataDao().findByMarketAndProduct(market, product);
                salesDataList.sort((x, y) -> {
                    int res = x.getYear() - y.getYear();
                    if (res == 0)
                        return x.getMonth() - y.getMonth();
                    return res;
                });
                double avgSales = salesDataList.stream().map(SalesData::getAmount).collect(Collectors.averagingDouble(Integer::doubleValue));
                int n = salesDataList.size();
                double tempValue = 0;
                for (SalesData salesData : salesDataList) {
                    tempValue = Math.pow((salesData.getAmount() - avgSales), 2);
                }
                double variation = (Math.sqrt(tempValue / n)) / avgSales;
                Group group = Group.defineGroup(variation);
                List<Integer> amounts = new ArrayList<>();
                for (SalesData salesData : salesDataList) {
                    if (months.size() < 5)
                        months.add(getMonthString(salesData));
                    amounts.add(salesData.getAmount());
                }
                Result.Entry entry = new Result.Entry(product, variation, group, amounts);
                entries.add(entry);
            }
        }
        return new Result(entries, months);
    }

    private String getMonthString(SalesData sales) {
        StringBuilder result = new StringBuilder();
        switch (sales.getMonth()) {
            case 1: result.append("Янв"); break;
            case 2: result.append("Фев"); break;
            case 3: result.append("Март"); break;
            case 4: result.append("Апр"); break;
            case 5: result.append("Май"); break;
            case 6: result.append("Июнь"); break;
            case 7: result.append("Июль"); break;
            case 8: result.append("Авг"); break;
            case 9: result.append("Сент"); break;
            case 10: result.append("Окт"); break;
            case 11: result.append("Нояб"); break;
            case 12: result.append("Дек"); break;
        }
        result.append(" ").append(sales.getYear());
        return result.toString();
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Result implements Analysis.Result {
        private List<Entry> entryList;
        private List<String> months;

        @Data
        @AllArgsConstructor
        public static class Entry {
            private Product product;
            private double variation;
            private Group group;
            private List<Integer> amountsSales;

            public String getVariationString() {
                return String.format("%.2f %%", (variation * 100));
            }
        }

        public String getName() {
            return "Результат XYZ-анализа";
        }
    }

    @AllArgsConstructor
    @Getter
    enum Group {
        X(0, 10),
        Y(10, 25),
        Z(25, 100);
        private final Integer min;
        private final Integer max;

        public static XyzAnalysis.Group defineGroup(double value) {
            value *= 100;
            for (XyzAnalysis.Group group : values()) {
                if (value > group.min && value <= group.max)
                    return group;
            }
            return null;
        }
    }
}
