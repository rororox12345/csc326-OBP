/**
 *
 */
package edu.ncsu.csc326.coffee_maker.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ncsu.csc326.coffee_maker.repositories.IngredientRepository;

/**
 * Test class for ingredient controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
class IngredientControllerTest {

    /** Mock MVC for testing controller */
    @Autowired
    private MockMvc              mvc;

    /** Object mapper to create json */
    @Autowired
    private ObjectMapper         objectMapper;

    /** Reference to ingredient repository */
    @Autowired
    private IngredientRepository ingredientRepository;

    /**
     * Sets up the test case.
     *
     * @throws java.lang.Exception
     *             if error
     */
    @BeforeEach
    public void setUp () throws Exception {
        ingredientRepository.deleteAll();
    }

    /**
     * Test method for creating ingredients. Test method for
     * {@link edu.ncsu.csc326.coffee_maker.controllers.IngredientController#createIngredient(java.util.Map)}.
     */
    @Test
    @Transactional
    public void testCreateIngredient () throws Exception {
        final Map<String, Object> body = new HashMap<>();
        body.put( "name", "Coffee" );
        body.put( "amount", 5 );

        final String json = objectMapper.writeValueAsString( body );

        mvc.perform( post( "/api/ingredients" ).contentType( MediaType.APPLICATION_JSON ).content( json )
                .accept( MediaType.APPLICATION_JSON ) ).andDo( print() ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$.name" ).value( "Coffee" ) );
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.controllers.IngredientController#getIngredient(java.lang.String)}.
     */
    @Test
    @Transactional
    public void testGetIngredient () throws Exception {
        final Map<String, Object> body = new HashMap<>();
        body.put( "name", "Coffee" );
        body.put( "amount", 5 );

        final String json = objectMapper.writeValueAsString( body );

        mvc.perform( post( "/api/ingredients" ).contentType( MediaType.APPLICATION_JSON ).content( json )
                .accept( MediaType.APPLICATION_JSON ) );

        mvc.perform( get( "/api/ingredients/Coffee" ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$.name" ).value( "Coffee" ) );
    }

    /**
     * Test method for getting ingredients.
     * {@link edu.ncsu.csc326.coffee_maker.controllers.IngredientController#getAllIngredients()}.
     *
     * @throws Exception
     *             if error
     */
    @Test
    @Transactional
    public void testGetAllIngredients () throws Exception {
        final String ingredient = mvc.perform( get( "/api/ingredients" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        assertFalse( ingredient.contains( "Coffee" ) );
    }

}
