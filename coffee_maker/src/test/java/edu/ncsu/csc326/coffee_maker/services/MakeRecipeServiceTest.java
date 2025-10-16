package edu.ncsu.csc326.coffee_maker.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import edu.ncsu.csc326.coffee_maker.dto.RecipeDto;

/**
 * Tests Make Recipe Service
 */
@SpringBootTest
public class MakeRecipeServiceTest {

    /** Copied from InventoryServiceTest */
    @Autowired
    private InventoryService  inventoryService;

    /** MakeRecipeService Object */
    @Autowired
    private MakeRecipeService makeRecipeService;

    /**
     * Test method for getting recipes.
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testMakeRecipe () throws Exception {
        final List<String> ingredients = new ArrayList<String>();
        ingredients.add( "coffee" );
        ingredients.add( "sugar" );
        final List<Integer> amount = new ArrayList<Integer>();
        amount.add( 10 );
        amount.add( 3 );
        final InventoryDto inventoryDto = new InventoryDto( 1L, ingredients, amount );

        final InventoryDto createdInventoryDto = inventoryService.createInventory( inventoryDto );
        final List<String> ingredients1 = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients1.add( "coffee" );
        ingredients1.add( "sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 1 );
        final RecipeDto recipeDto = new RecipeDto( 0L, "Mocha", 200, ingredients1, ingredientAmounts );
        final boolean holder = makeRecipeService.makeRecipe( createdInventoryDto, recipeDto );
        assertTrue( holder );

    }

    /**
     * Test making a recipe with insufficient ingredients.
     */
    @Test
    @Transactional
    public void testInvalidMakeRecipe () {
        final List<String> ingredients = new ArrayList<String>();
        ingredients.add( "coffee" );
        ingredients.add( "sugar" );
        final List<Integer> amount = new ArrayList<Integer>();
        amount.add( 2 );
        amount.add( 3 );
        final InventoryDto inventoryDto = new InventoryDto( 1L, ingredients, amount );

        final InventoryDto createdInventoryDto = inventoryService.createInventory( inventoryDto );
        final List<String> ingredients1 = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients1.add( "coffee" );
        ingredients1.add( "sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );
        final RecipeDto recipeDto = new RecipeDto( 0L, "Mocha", 200, ingredients1, ingredientAmounts );

        final boolean result = makeRecipeService.makeRecipe( createdInventoryDto, recipeDto );
        assertFalse( result, "Recipe should not be made due to insufficient ingredients." );
    }
}
