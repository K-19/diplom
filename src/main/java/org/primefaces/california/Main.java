package org.primefaces.california;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.primefaces.california.model.dao.AssortmentDao;
import org.primefaces.california.model.dao.MarketDao;
import org.primefaces.california.model.dao.ProductDao;
import org.primefaces.california.model.dao.SalesDataDao;
import org.primefaces.california.model.entity.AssortmentData;
import org.primefaces.california.model.entity.Market;
import org.primefaces.california.model.entity.Product;
import org.primefaces.california.model.entity.SalesData;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

//        List<Market> markets = mapper.readValue(new File("src\\main\\resources\\markets.json"), new TypeReference<List<Market>>(){});
//        MarketDao marketDao = new MarketDao();
//        for (Market market : markets) {
//            marketDao.save(market);
//        }
//
        List<Product> products = mapper.readValue(new File("src\\main\\resources\\products.json"), new TypeReference<List<Product>>(){});
        ProductDao productDao = new ProductDao();
        for (Product product : products) {
            product.setCost(Math.random() * product.getPrice());
            productDao.save(product);
        }

        List<Market> markets = new MarketDao().findAll().stream().limit(20).collect(Collectors.toList());
        products = new ProductDao().findAll().stream().limit(100).collect(Collectors.toList());
        int amount;
        AssortmentDao assortmentDao = new AssortmentDao();
        for (Market market : markets) {
            List<AssortmentData> assortmentDataList = new ArrayList<>();
            for (Product product : products) {
                amount = ((Double)(Math.random() * 200)).intValue();
                AssortmentData assortmentData = new AssortmentData(market, product, amount);
                assortmentDataList.add(assortmentData);
            }
            assortmentDao.saveList(assortmentDataList);
        }

        markets = new MarketDao().findAll().stream().limit(20).collect(Collectors.toList());
        products = new ProductDao().findAll().stream().limit(100).collect(Collectors.toList());
//        AssortmentDao assortmentDao = new AssortmentDao();
        SalesDataDao salesDataDao = new SalesDataDao();
        for (Market market : markets) {
            List<SalesData> salesDataList = new ArrayList<>();
            for (Product product : products) {
                for (int year = 2021; year <= 2022; year++) {
                    for (int month = 1; month <= 12; month++) {
                        int maxAmount = assortmentDao.findByMarketAndProduct(market, product).getAmount();
                        amount = ((Double)(Math.random() * maxAmount)).intValue();
                        SalesData salesData = new SalesData(market, product, amount, year, month);
                        salesDataList.add(salesData);
                    }
                }
            }
            salesDataDao.saveList(salesDataList);
        }
    }
}
