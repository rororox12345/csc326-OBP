package edu.ncsu.csc326.coffee_maker.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Inventory for the coffee maker. Inventory is a Data Access Object (DAO) is
 * tied to the database using Hibernate libraries. InventoryRepository provides
 * the methods for database CRUD operations.
 */
@Entity
public class Inventory {

    /** id for inventory entry */
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long          id;

    /** list of ingredient names */
    private List<String>  ingredients;
    /** list of ingredient amounts */
    private List<Integer> ingredientAmounts;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory () {
        this.ingredientAmounts = new ArrayList<>();
        this.ingredients = new ArrayList<>();
    }

    /**
     * constructor for Inventory
     *
     * @param id
     *            id of the inventory
     * @param ingredients
     *            ingredient names
     * @param amount
     *            amount of each ingredient
     */
    public Inventory ( final Long id, final List<String> ingredients, final List<Integer> amount ) {
        this.id = id;
        this.ingredientAmounts = amount;
        this.ingredients = ingredients;
    }

    /**
     * returns the ingredient list
     *
     * @return ingredient list
     */
    public List<String> getIngredients () {
        return ingredients;
    }

    /**
     * sets the ingredient list
     *
     * @param ingredients
     *            ingredient list
     */
    public void setIngredients ( final List<String> ingredients ) {
        this.ingredients = ingredients;
    }

    /**
     * returns the amount list
     *
     * @return amount list
     */
    public List<Integer> getIngredientAmounts () {
        return ingredientAmounts;
    }

    /**
     * sets the amount list
     *
     * @param ingredientAmount
     *            amount list
     */
    public void setIngredientAmounts ( final List<Integer> ingredientAmount ) {
        this.ingredientAmounts = ingredientAmount;
    }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

}
