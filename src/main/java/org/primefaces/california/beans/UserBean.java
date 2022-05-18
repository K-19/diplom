package org.primefaces.california.beans;

import lombok.Data;
import org.primefaces.california.model.dao.UserDao;
import org.primefaces.california.model.entity.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Named
@SessionScoped
public class UserBean implements Serializable {
    @Inject
    private GrowlView growlView;

    private User user;
    private String login = "";
    private String stringPassword = "";
    private boolean rememberMe;

    {
        user = new UserDao().findByLogin("admin");
    }

    public void signIn() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (validLoginForm()) {
                user = new UserDao().findByLoginAndPassword(login, stringPassword.hashCode());
                if (user != null) {
                    login = null;
                    stringPassword = null;
                    context.getExternalContext().redirect("dashboard.xhtml");
                } else
                    growlView.showError("Ошибка! Логин или пароль введены неверно");
            } else
                growlView.showError("Необходимо заполнить оба поля");
        } catch (IOException e) {
            growlView.showError("Хмм... Что-то пошло не так :(");
        }
    }

    private boolean validLoginForm() {
        return (stringPassword != null &&
                !stringPassword.isEmpty()
                && login != null && !login.isEmpty());
    }
}
