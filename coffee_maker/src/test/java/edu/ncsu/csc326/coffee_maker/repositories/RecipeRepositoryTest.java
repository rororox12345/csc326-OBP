/**
 *
 */
package edu.ncsu.csc326.coffee_maker.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.ncsu.csc326.coffee_maker.entity.Recipe;

/**
 * Tests Recipe repository
 */
@DataJpaTest
@AutoConfigureTestDatabase ( replace = Replace.NONE )
class RecipeRepositoryTest {

    /** Reference to recipe repository */
    @Autowired
    private RecipeRepository recipeRepository;

    /**
     * Sets up the test case.
     *
     * @throws java.lang.Exception
     *             if error
     */
    @BeforeEach
    public void setUp () throws Exception {
        recipeRepository.deleteAll();

        List<String> ingredients = new ArrayList<>();
        List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );

        List<String> ingredients2 = new ArrayList<>();
        List<Integer> ingredientAmounts2 = new ArrayList<>();
        ingredients.add( "Tea" );
        ingredients.add( "Cream" );
        ingredientAmounts.add( 2 );
        ingredientAmounts.add( 3 );

        Recipe recipe1 = new Recipe( 1L, "Coffee", 50, ingredients, ingredientAmounts );
        Recipe recipe2 = new Recipe( 2L, "Latte", 100, ingredients2, ingredientAmounts2 );

        recipeRepository.save( recipe1 );
        recipeRepository.save( recipe2 );
    }

    /**
     * Test for getting a recipe with its name
     */
    @Test
    public void testGetRecipeByName () {
        Optional<Recipe> recipe = recipeRepository.findByName( "Coffee" );
        Recipe actualRecipe = recipe.get();
        assertAll( "Recipe contents", () -> assertEquals( "Coffee", actualRecipe.getName() ),
                () -> assertEquals( 50, actualRecipe.getPrice() ) );

        Optional<Recipe> newRecipe = recipeRepository.findByName( "Latte" );
        Recipe latteRecipe = newRecipe.get();
        assertEquals( "Latte", latteRecipe.getName() );
        assertEquals( 100, latteRecipe.getPrice() );
        latteRecipe.setPrice( 10 );
        assertEquals( 10, latteRecipe.getPrice() );
    }

    /**
     * Test method for getting a recipe with an invalid name
     */
    @Test
    public void testGetRecipeByNameInvalid () {
        Optional<Recipe> recipe = recipeRepository.findByName( "Unknown" );
        assertTrue( recipe.isEmpty() );
    }

}
