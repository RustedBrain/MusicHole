package com.rustedbrain.crud.utils;

import com.rustedbrain.networks.model.PostgresEntity;
import org.hibernate.Session;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by RustedBrain on 26.02.2016.
 */
public class EntityUtil<T extends PostgresEntity> implements ICRUD<T> {

    protected final Session session;
    private final Class<T> c;
    private List<T> entities;

    public EntityUtil(Session session, Class<T> c) {
        this.session = session;
        this.c = c;
    }

    @Override
    public List<T> getEntities() {
        try {
            session.getTransaction().begin();
            this.entities = session.createCriteria(c).list();
            session.getTransaction().commit();
            return entities;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public T getEntity(int id) {
        try {
            session.getTransaction().begin();
            T object = session.get(c, id);
            session.getTransaction().commit();
            return object;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void createEntity(T entity) {
        try {
            session.getTransaction().begin();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.clear();
        }
    }

    @Override
    public void deleteEntity(Integer id) throws Exception {
        try {
            session.getTransaction().begin();
            for (PostgresEntity entity : entities) {
                if (entity.getId().equals(id)) {
                    session.delete(entity);
                    break;
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.clear();
        }
    }

    @Override
    public void updateEntity(T newEntity, T oldEntity) throws IllegalAccessException {
        try {
            session.getTransaction().begin();

            Field[] fields = c.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                field.set(newEntity, field.get(oldEntity));
            }

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.clear();
        }
    }

    public Session getSession() {
        return session;
    }
}
