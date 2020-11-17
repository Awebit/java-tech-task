package com.rezdy.lunch.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Ingredient {

    @Id
    private String title;

    @JsonIgnore
    private LocalDate bestBefore;

    @JsonIgnore
    private LocalDate useBy;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }


    public String getTitle() {
        return title;
    }

    public Ingredient setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public Ingredient setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
        return this;
    }

    public LocalDate getUseBy() {
        return useBy;
    }

    public Ingredient setUseBy(LocalDate useBy) {
        this.useBy = useBy;
        return this;
    }
}
