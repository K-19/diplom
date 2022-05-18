package org.primefaces.california.model.entity;

import lombok.Getter;

@Getter
public enum UserType {
    ADMIN("Администратор"),
    DIRECTOR("Директор"),
    MANAGER("Менеджер");
    private String role;

    UserType(String role) {
        this.role = role;
    }
}