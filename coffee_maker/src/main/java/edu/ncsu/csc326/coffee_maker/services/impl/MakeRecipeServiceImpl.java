package edu.ncsu.csc326.coffee_maker.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import edu.ncsu.csc326.coffee_maker.dto.RecipeDto;
import edu.ncsu.csc326.coffee_maker.entity.Inventory;
import edu.ncsu.csc326.coffee_maker.entity.Recipe;
import edu.ncsu.csc326.coffee_maker.mapper.InventoryMapper;
import edu.ncsu.csc326.coffee_maker.mapper.RecipeMapper;
import edu.ncsu.csc326.coffee_maker.repositories.InventoryRepository;
import edu.ncsu.csc326.coffee_maker.services.MakeRecipeService;

/**
 * Implementation of the MakeRecipeService interface.
 */
@Service
public class MakeRecipeServiceImpl implements MakeRecipeService {

    /** Connection to the repository to work with the DAO + database */
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param inventoryDto
     *            current inventory
     * @param recipeDto
     *            recipe to make
     * @return updated inventory
     */
    @Override
    public boolean makeRecipe ( final InventoryDto inventoryDto, final RecipeDto recipeDto ) {
        final Inventory inventory = InventoryMapper.mapToInventory( inventoryDto );
        final Recipe recipe = RecipeMapper.mapToRecipe( recipeDto );
        if ( enoughIngredients( inventory, recipe ) ) {
            for ( int i = 0; i < recipe.getIngredientAmounts().size(); i++ ) {
                final String ingredient = recipe.getIngredients().get( i );
                final int amount = recipe.getIngredientAmounts().get( i );
                final int id = inventory.getIngredients().indexOf( ingredient );
                final int inventoryAmount = inventory.getIngredientAmounts().get( id );
                inventory.getIngredientAmounts().set( id, inventoryAmount - amount );
            }
            // inventory.setCoffee( inventory.getCoffee() - recipe.getCoffee()
            // );
            // inventory.setMilk( inventory.getMilk() - recipe.getMilk() );
            // inventory.setSugar( inventory.getSugar() - recipe.getSugar() );
            // inventory.setChocolate( inventory.getChocolate() -
            // recipe.getChocolate() );

            inventoryRepository.save( inventory );
            return true;

        }
        return false;
    }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param inventory
     *            coffee maker inventory
     * @param recipe
     *            recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    private boolean enoughIngredients ( final Inventory inventory, final Recipe recipe ) {
        for ( int i = 0; i < recipe.getIngredients().size(); i++ ) {
            final int id = inventory.getIngredients().indexOf( recipe.getIngredients().get( i ) );
            if ( id == -1 ) {
                return false;
            }
            else if ( recipe.getIngredientAmounts().get( i ) > inventory.getIngredientAmounts().get( id ) ) {
                return false;
            }
        }

        return true;
    }

}
