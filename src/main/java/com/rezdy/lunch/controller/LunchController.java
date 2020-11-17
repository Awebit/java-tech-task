package com.rezdy.lunch.controller;

import com.rezdy.lunch.service.LunchService;
import com.rezdy.lunch.service.domain.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LunchController {

    private final LunchService lunchService;

    @Autowired
    public LunchController(LunchService lunchService) {
        this.lunchService = lunchService;
    }


    /**
     * Returns all the Recipes that are suitable for lunch on the given date, such that,
     * no Recipes are returned where an Ingredient is past its Use By date.
     *
     * Recipes that are past the Best Before date are returned last in the list.
     *
     * @param request Request object containing date for lunch in ISO 8601 format i.e. YYYY-MM-DD (e.g. 2020-11-17)
     * @return List of Recipes suitable for the given date
     */
    @GetMapping("/lunch")
    public List<Recipe> getRecipes(@Valid LunchRequest request) {
        return lunchService.getNonExpiredRecipesOnDate(request.getDate());
    }
}
