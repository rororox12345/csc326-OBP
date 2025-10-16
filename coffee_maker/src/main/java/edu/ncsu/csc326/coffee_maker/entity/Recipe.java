package edu.ncsu.csc326.coffee_maker.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Recipe for the coffee maker. Recipe is a Data Access Object (DAO) is tied to
 * the database using Hibernate libraries. RecipeRepository provides the methods
 * for database CRUD operations.
 */
@Entity
@Table ( name = "recipes" )
public class Recipe {

    /** Recipe id */
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )

    private Long          id;

    /** Recipe name */
    private String        name;

    /** Recipe price */
    private Integer       price;

    /** List of ingredient names */
    private List<String>  ingredients;

    /** List of the ingredient amounts */
    private List<Integer> ingredientAmounts;

    /**
     * Creates a default recipe for the coffee maker.
     */
    public Recipe () {
        this.name = "";
        this.ingredients = new ArrayList<>();
        this.ingredientAmounts = new ArrayList<>();
    }

    /**
     * Creates a recipe from all the fields
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param price
     *            the price
     * @param ingredients
     *            the ingredients
     * @param ingredientAmounts
     *            the ingredient amounts
     */
    public Recipe ( final Long id, final String name, final Integer price, final List<String> ingredients,
            final List<Integer> ingredientAmounts ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.ingredientAmounts = ingredientAmounts;

    }

    /**
     * Get the ID of the Recipe
     *
     * @return the ID
     */
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Recipe (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    @SuppressWarnings ( "unused" )
    private void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns name of the recipe.
     *
     * @return Returns the name.
     */
    public String getName () {
        return name;
    }

    /**
     * Sets the recipe name.
     *
     * @param name
     *            The name to set.
     */
    public void setName ( final String name ) {
        this.name = name;
    }

    /**
     * Returns the price of the recipe.
     *
     * @return Returns the price.
     */
    public Integer getPrice () {
        return price;
    }

    /**
     * Sets the recipe price.
     *
     * @param price
     *            The price to set.
     */
    public void setPrice ( final Integer price ) {
        this.price = price;
    }

    /**
     * Returns the ingredient name list
     *
     * @return Returns the ingredients names.
     */
    public List<String> getIngredients () {
        return ingredients;
    }

    /**
     * Sets the ingredients' names list
     *
     * @param ingredients
     *            the ingredient list to be set
     */
    public void setIngredients ( final List<String> ingredients ) {
        this.ingredients = ingredients;
    }

    /**
     * Gets the ingredients's amounts list
     *
     * @return Returns the ingredients' amounts.
     */
    public List<Integer> getIngredientAmounts () {
        return ingredientAmounts;
    }

    /**
     * Sets the ingredients' amounts list.
     *
     * @param ingredientAmounts
     *            The amounts to be set
     */
    public void setIngredientAmount ( final List<Integer> ingredientAmounts ) {
        this.ingredientAmounts = ingredientAmounts;
    }

}
