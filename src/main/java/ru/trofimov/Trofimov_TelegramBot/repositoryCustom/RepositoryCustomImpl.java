package ru.trofimov.Trofimov_TelegramBot.repositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.RestaurantEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;

import java.util.List;

/**
 * Реализация методов CRITERIA запросами
 */
@Repository
public class RepositoryCustomImpl implements RepositoryCustom {

    private final EntityManager entityManager;

    public RepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<UserEntity> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = cq.from(UserEntity.class);
        Predicate predicate = cb.equal(root.get("name"), name);
        cq.select(root).where(predicate);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<DishEntity> findByRestaurantId(Long restaurantId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DishEntity> cq = cb.createQuery(DishEntity.class);
        Root<DishEntity> root = cq.from(DishEntity.class);
        Join<DishEntity, RestaurantEntity> restaurantJoin = root.join("restaurant", JoinType.INNER);
        Predicate predicate = cb.equal(restaurantJoin.get("id"), restaurantId);
        cq.select(root).where(predicate);
        return entityManager.createQuery(cq).getResultList();
    }
}