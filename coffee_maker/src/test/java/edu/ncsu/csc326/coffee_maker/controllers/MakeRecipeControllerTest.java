package edu.ncsu.csc326.coffee_maker.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc326.coffee_maker.TestUtils;
import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import edu.ncsu.csc326.coffee_maker.dto.RecipeDto;
import edu.ncsu.csc326.coffee_maker.services.IngredientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Test class for makeRcipe controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MakeRecipeControllerTest {
    /** Mock MVC for testing controller */
    @Autowired
    private MockMvc              mvc;

    /** MakeRecipe variable */
    @Autowired
    private MakeRecipeController holder;
    /** ingredient service object */
    @Autowired
    private IngredientService    ingredientService;

    /** Reference to EntityManager */
    @Autowired
    private EntityManager        entityManager;

    /**
     * Test method for getting recipes.
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testMakeRecipe () throws Exception {
        final Query query = entityManager.createNativeQuery( "TRUNCATE TABLE inventory" );
        query.executeUpdate();
        final List<Integer> amount = new ArrayList<Integer>();
        final List<String> ingredients = new ArrayList<String>();
        ingredients.add( "Coffee" );
        amount.add( 10 );
        final InventoryDto expectedInventory = new InventoryDto( 1L, ingredients, amount );

        ingredientService.createIngredient( new IngredientDto( 0L, "Coffee" ) );

        mvc.perform( get( "/api/inventory" ) ).andExpect( status().isOk() );

        mvc.perform( put( "/api/inventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( expectedInventory ) ).accept( MediaType.APPLICATION_JSON ) )
                .andDo( print() ).andExpect( status().isOk() );

        mvc.perform( get( "/api/inventory" ) )
                .andExpect( content().string( TestUtils.asJsonString( expectedInventory ) ) )
                .andExpect( status().isOk() );
        final List<String> ingredients1 = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients1.add( "Coffee" );
        ingredientAmounts.add( 5 );
        final RecipeDto recipeDto = new RecipeDto( 0L, "Mocha", 200, ingredients1, ingredientAmounts );
        mvc.perform( post( "/api/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipeDto ) ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        final String recipe = mvc.perform( get( "/api/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        assertTrue( recipe.contains( "Mocha" ) );
        final ResponseEntity<Integer> response = holder.makeRecipe( "Mocha", 1000 );
        assertNotNull( response );
        final BodyBuilder r = ResponseEntity.ok();
        assertEquals( response.getStatusCode(), r.build().getStatusCode() );
    }

}
