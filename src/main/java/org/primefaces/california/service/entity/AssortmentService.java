package org.primefaces.california.service.entity;

import org.primefaces.california.model.entity.Market;

public class AssortmentService {

    public String assortmentName(Market market) {
        return "Ассортимент товарной точки " + market.getCity() + " " + market.getAddress();
    }
}
