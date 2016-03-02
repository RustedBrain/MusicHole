package com.rustedbrain.networks.model.music;

import com.rustedbrain.networks.model.PostgresEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by RustedBrain on 13.01.2016.
 */
@Entity
@Table(name = "group", schema = "public", uniqueConstraints =
@UniqueConstraint(name = "uq_group", columnNames = "name"))
public class Group extends PostgresEntity {

    @Column(name = "nationality", nullable = false)
    private String nationality;
    @Column(name = "creation", nullable = false)
    private Date creation;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "group_genres", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "genre_id",
                    nullable = false, updatable = false)})
    private Set<Genre> genres;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private Set<Album> albums;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "group_members", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "member_id",
                    nullable = false, updatable = false)})
    private Set<Member> members;

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", nationality='" + nationality + '\'' +
                ", creation=" + creation +
                '}';
    }
}
