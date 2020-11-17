package com.rezdy.lunch.service;

import com.rezdy.lunch.service.domain.Ingredient;
import com.rezdy.lunch.service.domain.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class LunchServiceTests {

    private final LocalDate DATE_PAST = LocalDate.of(2020, 1, 1);
    private final LocalDate DATE_NOW = LocalDate.of(2021, 1, 1);
    private final LocalDate DATE_FUTURE = LocalDate.of(2022, 1, 1);

    @Test
    public void sortRecipesWithPastBestBeforeLast_sortRecipeTitleAsc_whenBestBeforeDateNonExpired() {
        List<Recipe> recipes = Arrays.asList(
                new Recipe().setTitle("Title 1").setIngredients(Set.of(new Ingredient().setTitle("Title 1").setBestBefore(DATE_FUTURE))),
                new Recipe().setTitle("Title 3").setIngredients(Set.of(new Ingredient().setTitle("Title 3").setBestBefore(DATE_FUTURE))),
                new Recipe().setTitle("Title 2").setIngredients(Set.of(new Ingredient().setTitle("Title 2").setBestBefore(DATE_NOW)))
        );

        LunchService.sortRecipesWithPastBestBeforeLast(recipes, DATE_NOW);

        Assertions.assertThat(recipes.get(0).getTitle()).isEqualTo("Title 1");
        Assertions.assertThat(recipes.get(1).getTitle()).isEqualTo("Title 2");
        Assertions.assertThat(recipes.get(2).getTitle()).isEqualTo("Title 3");
    }

    @Test
    public void sortRecipesWithPastBestBeforeLast_sortRecipeTitleAsc_whenBestBeforeDateExpired() {
        List<Recipe> recipes = Arrays.asList(
                new Recipe().setTitle("Title 1").setIngredients(Set.of(new Ingredient().setTitle("Title 1").setBestBefore(DATE_PAST))),
                new Recipe().setTitle("Title 3").setIngredients(Set.of(new Ingredient().setTitle("Title 3").setBestBefore(DATE_PAST))),
                new Recipe().setTitle("Title 2").setIngredients(Set.of(new Ingredient().setTitle("Title 2").setBestBefore(DATE_PAST)))
        );

        LunchService.sortRecipesWithPastBestBeforeLast(recipes, DATE_NOW);

        Assertions.assertThat(recipes.get(0).getTitle()).isEqualTo("Title 1");
        Assertions.assertThat(recipes.get(1).getTitle()).isEqualTo("Title 2");
        Assertions.assertThat(recipes.get(2).getTitle()).isEqualTo("Title 3");
    }

    @Test
    public void sortRecipesWithPastBestBeforeLast_sortRecipeTitleAscWithExpiredLast_whenBestBeforeDateExpired() {
        List<Recipe> recipes = Arrays.asList(
                new Recipe().setTitle("Title 1").setIngredients(Set.of(new Ingredient().setTitle("Title 1").setBestBefore(DATE_PAST))),
                new Recipe().setTitle("Title 3").setIngredients(Set.of(new Ingredient().setTitle("Title 3").setBestBefore(DATE_NOW))),
                new Recipe().setTitle("Title 2").setIngredients(Set.of(new Ingredient().setTitle("Title 2").setBestBefore(DATE_FUTURE)))
        );

        LunchService.sortRecipesWithPastBestBeforeLast(recipes, DATE_NOW);

        Assertions.assertThat(recipes.get(0).getTitle()).isEqualTo("Title 2");
        Assertions.assertThat(recipes.get(1).getTitle()).isEqualTo("Title 3");
        Assertions.assertThat(recipes.get(2).getTitle()).isEqualTo("Title 1");
    }
}
