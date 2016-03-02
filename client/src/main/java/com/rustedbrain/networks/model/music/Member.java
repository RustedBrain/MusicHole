package com.rustedbrain.networks.model.music;

import com.rustedbrain.networks.model.PostgresEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by RustedBrain on 13.01.2016.
 */
@Entity
@Table(name = "member", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "uq_artist", columnNames = "pseudonym"),
        @UniqueConstraint(name = "uq_member", columnNames = {"name", "patronymic", "surname"}),
})
public class Member extends PostgresEntity {

    @Column(name = "pseudonym", nullable = false)
    private String pseudonym;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "surname")
    private String surname;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "birthday")
    private Date birthday;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "group_members", joinColumns = {
            @JoinColumn(name = "member_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "group_id",
                    nullable = false, updatable = false)})
    private Set<Group> groups;

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Member{" +
                "pseudonym='" + pseudonym + '\'' +
                ", id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
