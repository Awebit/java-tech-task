package com.rezdy.lunch.service.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Entity
public class Recipe {

    @Id
    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe"),
            inverseJoinColumns = @JoinColumn(name = "ingredient"))
    private Set<Ingredient> ingredients = Collections.emptySet();


    public boolean isPastBestBefore(LocalDate date) {
        return ingredients.stream().anyMatch(i -> i.getBestBefore().isBefore(date));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe recipe = (Recipe) o;

        return title != null ? title.equals(recipe.title) : recipe.title == null;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }


    public String getTitle() {
        return title;
    }

    public Recipe setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }
}
