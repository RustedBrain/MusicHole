package com.rustedbrain.networks.model.music;

import com.rustedbrain.networks.model.PostgresEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by RustedBrain on 13.01.2016.
 */
@Entity
@Table(name = "album", schema = "public", uniqueConstraints =
@UniqueConstraint(name = "uq_album", columnNames = {"name", "group_id"}))
public class Album extends PostgresEntity {

    @Column(name = "time")
    private Double time;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "album_songs", joinColumns = {
            @JoinColumn(name = "album_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "song_id",
                    nullable = false, updatable = false)})
    private Set<Song> songs;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
