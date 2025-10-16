/**
 *
 */
package edu.ncsu.csc326.coffee_maker.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.dto.RecipeDto;
import edu.ncsu.csc326.coffee_maker.repositories.RecipeRepository;

/**
 * Test class for recipe service.
 */
@SpringBootTest
class RecipeServiceTest {

    /** Reference to recipe service */
    @Autowired
    private RecipeService     recipeService;

    /** Reference to ingredient service */
    @Autowired
    private IngredientService ingredientService;

    /** Reference to recipe repository */
    @Autowired
    private RecipeRepository  recipeRepository;

    /**
     * Sets up the test case.
     *
     * @throws java.lang.Exception
     *             if error
     */
    @BeforeEach
    void setUp () throws Exception {
        recipeRepository.deleteAll();
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.RecipeService#createRecipe(edu.ncsu.csc326.coffee_maker.dto.RecipeDto)}.
     */
    @Test
    @Transactional
    void testCreateRecipe () {

        ingredientService.createIngredient( new IngredientDto( 0L, "Coffee" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Sugar" ) );

        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );

        // creates a new recipe
        final RecipeDto recipeDto = new RecipeDto( 0L, "Sugar Coffee", 50, ingredients, ingredientAmounts );
        final RecipeDto savedRecipe = recipeService.createRecipe( recipeDto );

        // ensures the recipe contents are correct based on savedRecipe
        assertAll( "Recipe contents", () -> assertTrue( savedRecipe.getId() > 1L ),
                () -> assertEquals( "Sugar Coffee", savedRecipe.getName() ),
                () -> assertEquals( 50, savedRecipe.getPrice() ) );

        // ensures the recipe contents are correct based on searching by Id
        final RecipeDto retrievedRecipe = recipeService.getRecipeById( savedRecipe.getId() );
        assertAll( "Recipe contents", () -> assertEquals( savedRecipe.getId(), retrievedRecipe.getId() ),
                () -> assertEquals( "Sugar Coffee", retrievedRecipe.getName() ),
                () -> assertEquals( 50, retrievedRecipe.getPrice() ) );

        // ensures the recipe contents are correct based on searching by name
        final RecipeDto retrievedRecipe2 = recipeService.getRecipeByName( savedRecipe.getName() );
        assertAll( "Recipe contents", () -> assertEquals( savedRecipe.getId(), retrievedRecipe2.getId() ),
                () -> assertEquals( "Sugar Coffee", retrievedRecipe2.getName() ),
                () -> assertEquals( 50, retrievedRecipe2.getPrice() ) );
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.RecipeService#isDuplicateName(java.lang.String)}.
     */
    @Test
    @Transactional
    void testIsDuplicateName () {
        ingredientService.createIngredient( new IngredientDto( 0L, "Coffee" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Sugar" ) );

        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );

        // creates a new recipe
        final RecipeDto recipeDto = new RecipeDto( 0L, "Coffee", 50, ingredients, ingredientAmounts );
        recipeService.createRecipe( recipeDto );

        // checks if name already exists
        assertTrue( recipeService.isDuplicateName( "Coffee" ) );
        // checks if another name is already present
        assertFalse( recipeService.isDuplicateName( "Hot Chocolate" ) );
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.RecipeService#getAllRecipes()}.
     */
    @Test
    @Transactional
    void testGetAllRecipes () {

        ingredientService.createIngredient( new IngredientDto( 0L, "Coffee" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Sugar" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Tea" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Cream" ) );

        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );

        final List<String> ingredients2 = new ArrayList<>();
        final List<Integer> ingredientAmounts2 = new ArrayList<>();
        ingredients2.add( "Tea" );
        ingredients2.add( "Cream" );
        ingredientAmounts2.add( 2 );
        ingredientAmounts2.add( 3 );

        // creates a new recipe
        RecipeDto recipeDto = new RecipeDto( 0L, "Coffee", 50, ingredients, ingredientAmounts );
        recipeService.createRecipe( recipeDto );

        // creates another recipe
        recipeDto = new RecipeDto( 0L, "Hot Chocolate", 0, ingredients2, ingredientAmounts2 );
        recipeService.createRecipe( recipeDto );

        // retrieves recipe list
        final List<RecipeDto> allRecipes = recipeService.getAllRecipes();

        // ensures size is correct
        assertEquals( 2, allRecipes.size() );
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.RecipeService#updateRecipe(java.lang.Long, edu.ncsu.csc326.coffee_maker.dto.RecipeDto)}.
     */
    @Test
    @Transactional
    void testUpdateRecipe () {

        ingredientService.createIngredient( new IngredientDto( 0L, "Coffee" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Sugar" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Tea" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Cream" ) );

        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );

        final List<String> ingredients2 = new ArrayList<>();
        final List<Integer> ingredientAmounts2 = new ArrayList<>();
        ingredients2.add( "Tea" );
        ingredients2.add( "Cream" );
        ingredientAmounts2.add( 2 );
        ingredientAmounts2.add( 3 );

        // creates a new recipe
        RecipeDto recipeDto = new RecipeDto( 0L, "Coffee", 50, ingredients, ingredientAmounts );
        final RecipeDto savedRecipe = recipeService.createRecipe( recipeDto );

        // creates a new recipe and updates
        recipeDto = new RecipeDto( 0L, "Special Coffee", 40, ingredients2, ingredientAmounts2 );
        final RecipeDto updatedRecipe = recipeService.updateRecipe( savedRecipe.getId(), recipeDto );

        // ensures the updated values are correct
        assertAll( "Recipe contents", () -> assertTrue( updatedRecipe.getId() > 1L ),
                () -> assertEquals( "Special Coffee", updatedRecipe.getName() ) );
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.RecipeService#deleteRecipe(java.lang.Long)}.
     */
    @Test
    @Transactional
    void testDeleteRecipe () {

        ingredientService.createIngredient( new IngredientDto( 0L, "Coffee" ) );
        ingredientService.createIngredient( new IngredientDto( 0L, "Sugar" ) );

        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );

        // creates a new recipe
        final RecipeDto recipeDto = new RecipeDto( 0L, "Coffee", 50, ingredients, ingredientAmounts );
        final RecipeDto savedRecipe = recipeService.createRecipe( recipeDto );

        // retrieves recipe list
        List<RecipeDto> allRecipes = recipeService.getAllRecipes();
        // ensures size is correct
        assertEquals( 1, allRecipes.size() );

        // deletes savedRecipe
        recipeService.deleteRecipe( savedRecipe.getId() );

        allRecipes = recipeService.getAllRecipes();

        // ensures size is correct
        assertEquals( 0, allRecipes.size() );
    }

}
