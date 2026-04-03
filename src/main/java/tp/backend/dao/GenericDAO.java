package tp.backend.dao;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class GenericDAO<T, ID> {

    protected final EntityManager entityManager;
    private final Class<T> entityClass;

    protected GenericDAO(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(jpql, entityClass).getResultList();
    }

    public void delete(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            T managedEntity = entityManager.merge(entity);
            entityManager.remove(managedEntity);
        }
    }

    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
}
