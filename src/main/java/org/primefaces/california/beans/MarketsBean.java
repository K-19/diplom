package org.primefaces.california.beans;

import lombok.Data;
import org.primefaces.california.model.dao.MarketDao;
import org.primefaces.california.model.entity.Market;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Named
@SessionScoped
public class MarketsBean implements Serializable {

    @Inject
    private GrowlView growlView;
    @Inject
    private AssortmentBean assortmentBean;

    private String name = "Торговые точки";

    private Market selectedMarket;
    private List<Market> markets;
    {
        markets = new MarketDao().findAll().stream().limit(20).collect(Collectors.toList());
    }

    public void goToAssortment(Integer marketId) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (validMarketId(marketId)) {
                Optional<Market> market = markets.stream().filter(x -> x.getId() == marketId).findFirst();
                if (market.isPresent()) {
                    assortmentBean.setMarket(market.get());
                    context.getExternalContext().redirect("assortment.xhtml");
                } else
                    growlView.showError("Не найдена указанная торговая точка");
            } else
                growlView.showError("Что-то пошла не так (нажатой кнопки не существует)");
        } catch (IOException e) {
            growlView.showError("Хмм... Что-то пошло не так :(");
        }
    }

    private boolean validMarketId(Integer marketId) {
        return marketId != null && marketId > 0;
    }
}
