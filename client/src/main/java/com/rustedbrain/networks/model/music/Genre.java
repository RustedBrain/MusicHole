package com.rustedbrain.networks.model.music;

import com.rustedbrain.networks.model.PostgresEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by RustedBrain on 13.01.2016.
 */
@Entity
@Table(name = "genre", schema = "public", uniqueConstraints =
@UniqueConstraint(name = "uq_genre", columnNames = "name"))
public class Genre extends PostgresEntity {

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "group_genres", joinColumns = {
            @JoinColumn(name = "genre_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "group_id",
                    nullable = false, updatable = false)})
    private Set<Group> groups;

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
