package com.rustedbrain.crud.utils;

import java.util.List;

/**
 * Created by RustedBrain on 26.02.2016.
 */
public interface ICRUD<T> {

    List<T> getEntities();

    T getEntity(int id);

    void createEntity(T entity);

    void deleteEntity(Integer id) throws Exception;

    void updateEntity(T oldEntity, T newEntity) throws IllegalAccessException;

}
