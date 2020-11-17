package com.rezdy.lunch.service.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

public class RecipeTests {

    private final LocalDate DATE_PAST = LocalDate.of(2020, 1, 1);
    private final LocalDate DATE_NOW = LocalDate.of(2021, 1, 1);
    private final LocalDate DATE_FUTURE = LocalDate.of(2022, 1, 1);


    @Test
    public void isPastBestBefore_returnTrue_whenSingleIngredientWithBestBeforeInPast() {
        Set<Ingredient> ingredients = Set.of(new Ingredient().setTitle("Title 1").setBestBefore(DATE_PAST));
        Recipe recipe = new Recipe().setTitle("Title 1").setIngredients(ingredients);

        Assertions.assertThat(recipe.isPastBestBefore(DATE_NOW)).isTrue();
    }

    @Test
    public void isPastBestBefore_returnFalse_whenSingleIngredientWithBestBeforeNow() {
        Set<Ingredient> ingredients = Set.of(new Ingredient().setTitle("Title 1").setBestBefore(DATE_NOW));
        Recipe recipe = new Recipe().setTitle("Title 1").setIngredients(ingredients);

        Assertions.assertThat(recipe.isPastBestBefore(DATE_NOW)).isFalse();
    }

    @Test
    public void isPastBestBefore_returnFalse_whenSingleIngredientWithBestBeforeInFuture() {
        Set<Ingredient> ingredients = Set.of(new Ingredient().setTitle("Title 1").setBestBefore(DATE_FUTURE));
        Recipe recipe = new Recipe().setTitle("Title 1").setIngredients(ingredients);

        Assertions.assertThat(recipe.isPastBestBefore(DATE_NOW)).isFalse();
    }

    @Test
    public void isPastBestBefore_returnTrue_whenMultipleIngredientWithBestBeforeInPast() {
        Set<Ingredient> ingredients = Set.of(
                new Ingredient().setTitle("Title 1").setBestBefore(DATE_PAST),
                new Ingredient().setTitle("Title 2").setBestBefore(DATE_NOW),
                new Ingredient().setTitle("Title 3").setBestBefore(DATE_FUTURE)
        );
        Recipe recipe = new Recipe().setIngredients(ingredients);

        Assertions.assertThat(recipe.isPastBestBefore(DATE_NOW)).isTrue();
    }

    @Test
    public void isPastBestBefore_returnFalse_whenMultipleIngredientWithoutBestBeforeInPast() {
        Set<Ingredient> ingredients = Set.of(
                new Ingredient().setTitle("Title 1").setBestBefore(DATE_NOW),
                new Ingredient().setTitle("Title 2").setBestBefore(DATE_FUTURE)
        );
        Recipe recipe = new Recipe().setIngredients(ingredients);

        Assertions.assertThat(recipe.isPastBestBefore(DATE_NOW)).isFalse();
    }
}
