package edu.ncsu.csc326.coffee_maker.dto;

import java.util.List;

/**
 * Used to transfer Inventory data between the client and server. This class
 * will serve as the response in the REST API.
 */
public class InventoryDto {

    /** id for inventory entry */
    private Long id;

    /**
     * ingredient name list
     */
    private List<String>  ingredients;
    /** amount name list */
    private List<Integer> ingredientAmounts;

    /**
     * Default InventoryDto constructor.
     */
    public InventoryDto () {
        //empty constructor
    }

    /**
     * inventory Dto constructor
     * @param id id of the inventory
     * @param ingredients list of ingredient names
     * @param amount amount of each ingredient
     */
    public InventoryDto ( final Long id, final List<String> ingredients, final List<Integer> amount ) {
        this.id = id;
        this.ingredientAmounts = amount;
        this.ingredients = ingredients;
    }

    /**
     * Gets the inventory id.
     *
     * @return the id
     */
    public Long getId () {
        return id;
    }

    /**
     * Inventory id to set.
     *
     * @param id
     *            the id to set
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * returns the ingredient name list
     * @return name list
     */
    public List<String> getIngredients () {
        return ingredients;
    }

    /**
     * sets the ingredient list
     * @param ingredients list of ingredients
     */
    public void setIngredients ( final List<String> ingredients ) {
        this.ingredients = ingredients;
    }
    
    /**
     * returns the amount list
     * @return ingredient amount
     */
    public List<Integer> getIngredientAmounts () {
        return ingredientAmounts;
    }
    
    /**
     * sets the list of amounts
     * @param ingredientAmount list of amounts
     */
    public void setIngredientAmounts ( final List<Integer> ingredientAmount ) {
        this.ingredientAmounts = ingredientAmount;
    }

}
