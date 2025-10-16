/**
 *
 */
package edu.ncsu.csc326.coffee_maker.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc326.coffee_maker.TestUtils;
import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import edu.ncsu.csc326.coffee_maker.services.IngredientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Test class for inventory controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {

    /** Mock MVC for testing controller */
    @Autowired
    private MockMvc           mvc;

    /** Reference to EntityManager */
    @Autowired
    private EntityManager     entityManager;

    /** ingredient service object */
    @Autowired
    private IngredientService ingredientService;

    /**
     * Sets up the test case. We assume only one inventory row. Because
     * inventory is treated as a singleton (only one row), we must truncate for
     * auto increment on the id to work correctly.
     *
     * @throws java.lang.Exception
     *             if error
     */
    @BeforeEach
    public void setUp () throws Exception {
        final Query query = entityManager.createNativeQuery( "TRUNCATE TABLE inventory" );
        query.executeUpdate();
    }

    /**
     * Tests the GET /api/inventory endpoint.
     *
     * @throws Exception
     *             if issue when running the test.
     */
    @Test
    @Transactional
    public void testGetInventory () throws Exception {
        final List<Integer> amount = new ArrayList<Integer>();
        final List<String> ingredients = new ArrayList<String>();
        final InventoryDto expectedInventory = new InventoryDto( 1L, ingredients, amount );

        mvc.perform( get( "/api/inventory" ) )
                .andExpect( content().string( TestUtils.asJsonString( expectedInventory ) ) )
                .andExpect( status().isOk() );
    }

    /**
     * Tests the PUT /api/inventory endpoint.
     *
     * @throws Exception
     *             if issue when running the test.
     */
    @Test
    @Transactional
    public void testUpdateInventory () throws Exception {
        final List<Integer> amount = new ArrayList<Integer>();
        final List<String> ingredients = new ArrayList<String>();
        ingredients.add( "first" );
        amount.add( 10 );
        final InventoryDto expectedInventory = new InventoryDto( 1L, ingredients, amount );

        ingredientService.createIngredient( new IngredientDto( 0L, "first" ) );

        mvc.perform( get( "/api/inventory" ) ).andExpect( status().isOk() );

        mvc.perform( put( "/api/inventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( expectedInventory ) ).accept( MediaType.APPLICATION_JSON ) )
                .andDo( print() ).andExpect( status().isOk() );

        mvc.perform( get( "/api/inventory" ) )
                .andExpect( content().string( TestUtils.asJsonString( expectedInventory ) ) )
                .andExpect( status().isOk() );

        final List<Integer> amount1 = new ArrayList<Integer>();
        amount1.add( 1 );
        final List<String> ingredients1 = new ArrayList<String>();
        ingredients1.add( "first" );
        final InventoryDto updatedInventory = new InventoryDto( 1L, ingredients1, amount1 );

        try {
            mvc.perform( put( "/api/inventory" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( updatedInventory ) ).accept( MediaType.APPLICATION_JSON ) )
                    .andExpect( status().isOk() );
        }
        catch ( final IllegalArgumentException e ) {
            // catches the exception
        }
    }

}
