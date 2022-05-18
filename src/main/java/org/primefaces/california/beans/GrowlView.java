package org.primefaces.california.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class GrowlView {
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo(String text) {
        addMessage(FacesMessage.SEVERITY_INFO, "Информация", text);
    }

    public void showWarn(String text) {
        addMessage(FacesMessage.SEVERITY_WARN, "Внимание", text);
    }

    public void showError(String text) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", text);
    }
}