package org.primefaces.california.beans;

import lombok.Data;
import org.primefaces.california.model.dao.AssortmentDao;
import org.primefaces.california.model.dao.ProductDao;
import org.primefaces.california.model.entity.AssortmentData;
import org.primefaces.california.model.entity.Market;
import org.primefaces.california.model.entity.Product;
import org.primefaces.california.model.entity.SalesData;
import org.primefaces.california.service.analysis.AbcAnalysis;
import org.primefaces.california.service.analysis.AbcXyzAnalysis;
import org.primefaces.california.service.analysis.Analysis;
import org.primefaces.california.service.analysis.XyzAnalysis;
import org.primefaces.california.service.entity.AssortmentService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Named
@SessionScoped
public class AssortmentBean implements Serializable {
    @Inject
    private GrowlView growlView;

    private Market market;
    private List<AssortmentData> assortment;
    private AssortmentService service = new AssortmentService();
    private List<String> analysesString;
    private AbcAnalysis.Result abcResult;
    private XyzAnalysis.Result xyzResult;
    private AbcXyzAnalysis.Result abcXyzResult;

    public List<AbcAnalysis.Result> getAbcResultAsList() {
        return new ArrayList<>(Collections.singletonList(abcResult));
    }

    public void setMarket(Market market) {
        this.market = market;
        assortment = null;
    }

    public String getAssortmentName() {
        return service.assortmentName(market);
    }

    public List<AssortmentData> getProducts() {
        if (assortment == null) {
            assortment = new AssortmentDao().findByMarket(market);
        }
        return assortment;
    }

    public void startAnalysis() {
        abcResult = null;
        xyzResult = null;
        List<Analysis> analyses = new ArrayList<>();
        for (String nameAnalysis : analysesString) {
            switch (nameAnalysis) {
                case "ABC": analyses.add(new AbcAnalysis(assortment));
                    break;
                case "XYZ": analyses.add(new XyzAnalysis(assortment));
                    break;
                default:
            }
        }
        if (analyses.size() > 0) {
            processAllAnalyses(analyses);
        }
        if (analysesString.contains("ABC") && analysesString.contains("XYZ")) {
            AbcXyzAnalysis analysis = new AbcXyzAnalysis(abcResult, xyzResult);
            analyses.add(analysis);
            abcXyzResult = analysis.process();
        }
    }

    private void processAllAnalyses(List<Analysis> analyses) {
        for (Analysis analysis : analyses) {
            Analysis.Result result = analysis.process();
            if (result instanceof AbcAnalysis.Result) {
                abcResult = (AbcAnalysis.Result) result;
            }
            else if (result instanceof XyzAnalysis.Result) {
                xyzResult = (XyzAnalysis.Result) result;
            }
            else if (result instanceof AbcXyzAnalysis.Result) {
                abcXyzResult = (AbcXyzAnalysis.Result) result;
            }
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("reportOfAnalysis.xhtml");
        } catch (IOException e) {
            growlView.showError("Что-то пошло не так :) Отчет не может быть загружен");
        }
    }

}
