package org.primefaces.california.service.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.california.model.entity.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class AbcXyzAnalysis implements Analysis, Serializable {
    private AbcAnalysis.Result abcResult;
    private XyzAnalysis.Result xyzResult;

    @Override
    public String getName() {
        return "Совмещенный ABC-XYZ анализ";
    }

    @Override
    public Result process() {
        List<Result.Entry> entries = new ArrayList<>();
        for (Product product : abcResult.getEntries().stream().map(AbcAnalysis.Result.Entry::getProduct).collect(Collectors.toList())) {
            Optional<AbcAnalysis.Result.Entry> abcEntry = abcResult.getEntries().stream().filter(x -> x.getProduct().equals(product)).findFirst();
            if (abcEntry.isPresent()) {
                double shareInRevenue = abcEntry.get().getShareInRevenue();
                double shareInMargin = abcEntry.get().getShareInMargin();
                AbcAnalysis.Group abcGroup = abcEntry.get().getGroup();
                Optional<XyzAnalysis.Result.Entry> xyzEntry = xyzResult.getEntryList().stream().filter(x -> x.getProduct().equals(product)).findFirst();
                if (xyzEntry.isPresent()) {
                    double variation = xyzEntry.get().getVariation();
                    XyzAnalysis.Group xyzGroup = xyzEntry.get().getGroup();
                    Result.Entry entry = new Result.Entry(product, shareInRevenue, shareInMargin, abcGroup, variation, xyzGroup);
                    entries.add(entry);
                }
            }
        }
        return new Result(entries);
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Result implements Analysis.Result{
        private List<Entry> entries;

        @Data
        @AllArgsConstructor
        public static class Entry {
            private Product product;
            private double shareInRevenue;
            private double shareInMargin;
            private AbcAnalysis.Group abcGroup;
            private double variation;
            private XyzAnalysis.Group xyzGroup;

            public String getVariationString() {
                return String.format("%.2f %%", (variation * 100));
            }

            public String getShareInRevenueString() {
                return String.format("%.2f %%", (shareInRevenue * 100));
            }

            public String getShareInMarginString() {
                return String.format("%.2f %%", (shareInMargin * 100));
            }

            public String getGroupString() {
                return abcGroup.name() + xyzGroup.name();
            }
        }

        public String getName() {
            return "Результат ABC-XYZ анализа";
        }
    }
}
