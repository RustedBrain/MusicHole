package com.rustedbrain.networks.model;

import com.rustedbrain.networks.controllers.utils.model.Validator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by RustedBrain on 28.02.2016.
 */
@MappedSuperclass
public abstract class PostgresEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostgresEntity that = (PostgresEntity) o;

        return id.equals(that.id) && name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        this.name = Validator.NAME.validate(name);
    }
}
