package com.rustedbrain.networks.model.music;

import com.rustedbrain.networks.model.PostgresEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by RustedBrain on 13.01.2016.
 */
@Entity
@Table(name = "song", schema = "public", uniqueConstraints =
@UniqueConstraint(name = "uq_song", columnNames = "name"))
public class Song extends PostgresEntity {

    @Column(name = "size")
    private Double size;
    @Column(name = "creation")
    private Date creation;
    @Column(name = "path")
    private String path;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "album_songs", joinColumns = {
            @JoinColumn(name = "song_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "album_id",
                    nullable = false, updatable = false)})
    private Set<Album> albums;

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", size=" + size +
                ", creation=" + creation +
                ", path='" + path + '\'' +
                '}';
    }
}
