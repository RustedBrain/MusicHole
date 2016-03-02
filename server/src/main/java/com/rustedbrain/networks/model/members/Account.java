package com.rustedbrain.networks.model.members;

import com.rustedbrain.networks.model.PostgresEntity;
import com.rustedbrain.networks.utils.model.Validator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "account", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "uq_person", columnNames = {"name", "surname"}),
        @UniqueConstraint(name = "uq_mail", columnNames = "mail"),
        @UniqueConstraint(name = "uq_login", columnNames = "login")})
public class Account extends PostgresEntity implements Serializable {

    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "mail", nullable = false)
    private String mail;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "registration", nullable = false)
    private Date registration;
    @Column(name = "password", nullable = false)
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws Exception {
        this.login = Validator.LOGIN.validate(login);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        this.password = Validator.PASSWORD.validate(password);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws Exception {
        this.mail = Validator.EMAIL.validate(mail);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws Exception {
        this.surname = Validator.NAME.validate(surname);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegistration() {
        return registration;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) throws Exception {
        this.nationality = Validator.NAME.validate(nationality);
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + login + '\'' +
                ", name='" + super.getName() + '\'' +
                ", surname='" + surname + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
