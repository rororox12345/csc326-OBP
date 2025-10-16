package edu.ncsu.csc326.coffee_maker.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ncsu.csc326.coffee_maker.TestUtils;
import edu.ncsu.csc326.coffee_maker.dto.RecipeDto;
import edu.ncsu.csc326.coffee_maker.repositories.RecipeRepository;

/**
 * Test class for recipe controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {
    /** Mock MVC for testing controller */
    @Autowired
    private MockMvc          mvc;

    /** Object mapper to create json */
    @Autowired
    private ObjectMapper     objectMapper;

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
    }

    /**
     * Test method for getting recipes.
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testGetRecipes () throws Exception {
        final String recipe = mvc.perform( get( "/api/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        assertFalse( recipe.contains( "Mocha" ) );
    }

    /**
     * Test method for creating recipes.
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testCreateRecipe () throws Exception {

        // Adding ingredients first

        final Map<String, Object> body = new HashMap<>();
        body.put( "name", "Coffee" );
        body.put( "amount", 10 );

        String json = objectMapper.writeValueAsString( body );

        mvc.perform( post( "/api/ingredients" ).contentType( MediaType.APPLICATION_JSON ).content( json )
                .accept( MediaType.APPLICATION_JSON ) );

        body.clear();
        body.put( "name", "Sugar" );
        body.put( "amount", 10 );
        json = objectMapper.writeValueAsString( body );

        mvc.perform( post( "/api/ingredients" ).contentType( MediaType.APPLICATION_JSON ).content( json )
                .accept( MediaType.APPLICATION_JSON ) );

        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );
        final RecipeDto recipeDto = new RecipeDto( 0L, "Mocha", 200, ingredients, ingredientAmounts );
        mvc.perform( post( "/api/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipeDto ) ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test method for creating a recipe with no amounts
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testCreateBadRecipe () throws Exception {
        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        final RecipeDto recipeDto = new RecipeDto( 0L, "Mocha", 200, ingredients, ingredientAmounts );
        mvc.perform( post( "/api/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipeDto ) ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );
    }

    /**
     * Test method for creating a recipe with no names
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testCreateBadRecipe2 () throws Exception {
        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );
        final RecipeDto recipeDto = new RecipeDto( 0L, "Mocha", 200, ingredients, ingredientAmounts );
        mvc.perform( post( "/api/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipeDto ) ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );
    }

    /**
     * Test method for deleting recipe. StackOverflow example link:
     * https://stackoverflow.com/questions/46311896/get-ids-from-json-array
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testDeleteRecipe () throws Exception {

        // Adding ingredients first

        final Map<String, Object> body = new HashMap<>();
        body.put( "name", "Coffee" );
        body.put( "amount", 10 );

        String json = objectMapper.writeValueAsString( body );

        mvc.perform( post( "/api/ingredients" ).contentType( MediaType.APPLICATION_JSON ).content( json )
                .accept( MediaType.APPLICATION_JSON ) );

        body.clear();
        body.put( "name", "Sugar" );
        body.put( "amount", 10 );
        json = objectMapper.writeValueAsString( body );

        mvc.perform( post( "/api/ingredients" ).contentType( MediaType.APPLICATION_JSON ).content( json )
                .accept( MediaType.APPLICATION_JSON ) );

        final List<String> ingredients = new ArrayList<>();
        final List<Integer> ingredientAmounts = new ArrayList<>();
        ingredients.add( "Coffee" );
        ingredients.add( "Sugar" );
        ingredientAmounts.add( 5 );
        ingredientAmounts.add( 4 );

        final RecipeDto recipeDto = new RecipeDto( 0L, "Test", 200, ingredients, ingredientAmounts );
        // Used Stackoverflow example to help get the string from post and used
        // quick fix to set the variable type for holder
        final ResultActions holder = mvc
                .perform( post( "/api/recipes" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( recipeDto ) ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        // used Stackoverflow example to figure out which methods to call to get
        // the ID
        final String response = holder.andReturn().getResponse().getContentAsString();
        // System.out.println(response);
        final String stringID = response.substring( 6, response.indexOf( "," ) );
        final int id = Integer.parseInt( stringID );
        final String recipe = mvc.perform( get( "/api/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        assertTrue( recipe.contains( "Test" ) );
        mvc.perform( delete( "/api/recipes/" + id ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
        final String recipe1 = mvc.perform( get( "/api/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        assertFalse( recipe1.contains( "Test" ) );
    }

}
