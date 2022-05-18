package org.primefaces.california.beans;

import lombok.Data;
import org.primefaces.california.model.dao.UserDao;
import org.primefaces.california.model.entity.User;
import org.primefaces.california.model.entity.UserType;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Data
@Named
@SessionScoped
public class AdminBean implements Serializable {
    @Inject
    private UserBean currentAdmin;
    @Inject
    private GrowlView growlView;

    private User registeredUser = new User();
    private String regUserPassword;
    private String regUserConfirmPassword;
    private String regUserType;

    public void registerNewUser() {
        if(registeredUserValidate()) {
            registeredUser.setPassword(regUserPassword.hashCode());
            registeredUser.setType(UserType.valueOf(regUserType));
            new UserDao().save(registeredUser);
            growlView.showInfo("Пользователь " +registeredUser.getFullName() + " успешно зарегистрирован");
            registeredUser = new User();
            regUserPassword = null;
            regUserConfirmPassword = null;
            regUserType = null;
        }
    }

    private boolean registeredUserValidate() {
        if (registeredUser == null) {
            growlView.showError("Хмм... Что-то пошло не так :(");
            return false;
        }
        if (registeredUser.getName() == null || registeredUser.getName().isEmpty() ||
                registeredUser.getSurname() == null || registeredUser.getSurname().isEmpty() ||
                registeredUser.getLogin() == null || registeredUser.getLogin().isEmpty() ||
                regUserType == null || regUserType.isEmpty() ||
                regUserPassword == null || regUserPassword.isEmpty() ||
                regUserConfirmPassword == null || regUserConfirmPassword.isEmpty() ||
                registeredUser.getBirthday() == null) {
            growlView.showWarn("Необходимо заполнить все поля формы");
            return false;
        }
        if (!regUserPassword.equals(regUserConfirmPassword)) {
            growlView.showWarn("Указанные пароли не совпадают");
            return false;
        }
        UserDao dao = new UserDao();
        if (dao.findByLogin(registeredUser.getLogin()) != null) {
            growlView.showWarn("Пользователь с таким логином уже существует");
            return false;
        }



        return true;
    }
}
