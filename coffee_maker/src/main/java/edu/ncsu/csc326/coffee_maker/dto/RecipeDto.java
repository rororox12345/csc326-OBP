package edu.ncsu.csc326.coffee_maker.dto;

import java.util.List;

/**
 * Used to transfer Recipe data between the client and server. This class will
 * serve as the response in the REST API.
 */
public class RecipeDto {

    /** Recipe Id */
    private Long          id;

    /** Recipe name */
    private String        name;

    /** Recipe price */
    private Integer       price;

    /** List of the ingredient names */
    private List<String>  ingredients;

    /** List of the ingredient amounts */
    private List<Integer> ingredientAmounts;

    /**
     * Default constructor for Recipe.
     */
    public RecipeDto () {

    }

    /**
     * Creates recipe from field values.
     *
     * @param id
     *            recipe's id
     * @param name
     *            recipe's name
     * @param price
     *            recipe's price
     *
     * @param ingredients
     *            recipe's names
     *
     * @param ingredientAmounts
     *            recipe's amounts
     */
    public RecipeDto ( Long id, String name, Integer price, List<String> ingredients,
            List<Integer> ingredientAmounts ) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.ingredientAmounts = ingredientAmounts;
    }

    /**
     * Gets the recipe id.
     *
     * @return the id
     */
    public Long getId () {
        return id;
    }

    /**
     * Recipe id to set.
     *
     * @param id
     *            the id to set
     */
    public void setId ( Long id ) {
        this.id = id;
    }

    /**
     * Gets recipe's name
     *
     * @return the name
     */
    public String getName () {
        return name;
    }

    /**
     * Recipe name to set.
     *
     * @param name
     *            the name to set
     */
    public void setName ( String name ) {
        this.name = name;
    }

    /**
     * Gets the recipe's price
     *
     * @return the price
     */
    public Integer getPrice () {
        return price;
    }

    /**
     * Prices value to set.
     *
     * @param price
     *            the price to set
     */
    public void setPrice ( Integer price ) {
        this.price = price;
    }

    /**
     * Returns the ingredients
     *
     * @return Returns the ingredients
     */
    public List<String> getIngredients () {
        return ingredients;
    }

    /**
     * Sets the ingredients
     *
     * @param ingredients
     *            The ingredients to be set
     */
    public void setIngredients ( List<String> ingredients ) {
        this.ingredients = ingredients;
    }

    /**
     * Returns the ingredient's amounts
     *
     * @return Returns the ingredient's amounts
     */
    public List<Integer> getIngredientAmounts () {
        return ingredientAmounts;
    }

    /**
     * Sets the ingredient's amounts
     *
     * @param ingredientAmounts
     *            The amounts to be set
     */
    public void setIngredientAmount ( List<Integer> ingredientAmounts ) {
        this.ingredientAmounts = ingredientAmounts;
    }
}
