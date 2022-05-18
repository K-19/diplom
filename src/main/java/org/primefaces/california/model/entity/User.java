package org.primefaces.california.model.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userlogin")
    private String login;
    @Column(name = "userpassword")
    private Integer password;
    @Column(name = "username")
    private String name;
    private String surname;
    private String patronymic;

    @Enumerated(EnumType.STRING)
    @Column(name = "usertype")
    private UserType type;

    @Column
    private Date birthday;

    public String getFullName() {
        return surname + " " + name;
    }

    public String getRole() {
        return type.getRole();
    }
}
