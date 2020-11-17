package com.rezdy.lunch.dao;

import com.rezdy.lunch.service.domain.Ingredient_;
import com.rezdy.lunch.service.domain.Recipe;
import com.rezdy.lunch.service.domain.Recipe_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RecipeDao {

    private final EntityManager entityManager;

    @Autowired
    public RecipeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Returns all Recipes where the Ingredients are not expired based on the given date.
     *
     * An Ingredient is considered expired if the given date falls after the Use By date.
     *
     * @param date Date used to compare Use By date against for expiry
     * @return List of Recipes with Non Expired Ingredients
     */
    public List<Recipe> findAllRecipesWithNonExpiredIngredients(LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = cb.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);

        recipeRoot.fetch(Recipe_.ingredients, JoinType.LEFT);

        CriteriaQuery<Recipe> query = criteriaQuery.select(recipeRoot).distinct(true);

        Subquery<Recipe> nonExpiredIngredientSubquery = query.subquery(Recipe.class);
        Root<Recipe> nonExpiredIngredient = nonExpiredIngredientSubquery.from(Recipe.class);
        nonExpiredIngredientSubquery.select(nonExpiredIngredient);

        Predicate matchingRecipe = cb.equal(nonExpiredIngredient.get(Recipe_.title), recipeRoot.get(Recipe_.title));
        Predicate expiredIngredient = cb.greaterThanOrEqualTo(nonExpiredIngredient.join(Recipe_.ingredients).get(Ingredient_.USE_BY), date);

        Predicate allNonExpiredIngredients = cb.exists(nonExpiredIngredientSubquery.where(matchingRecipe, expiredIngredient));

        return entityManager.createQuery(query.where(allNonExpiredIngredients)).getResultList();
    }
}
