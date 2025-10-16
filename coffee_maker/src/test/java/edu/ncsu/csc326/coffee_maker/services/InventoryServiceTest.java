package edu.ncsu.csc326.coffee_maker.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Tests InventoryServiceImpl.
 */
@SpringBootTest
public class InventoryServiceTest {

    /** Reference to InventoryService (and InventoryServiceImpl). */
    @Autowired
    private InventoryService  inventoryService;

    /** ingredient Service object */
    @Autowired
    private IngredientService ingredientService;

    /** Reference to EntityManager */
    @Autowired
    private EntityManager     entityManager;

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
     * Tests InventoryService.createInventory().
     */
    @Test
    @Transactional
    public void testCreateInventory () {
        final List<String> ingredients = new ArrayList<String>();
        ingredients.add( "coffee" );
        final List<Integer> amount = new ArrayList<Integer>();
        amount.add( 10 );
        final InventoryDto inventoryDto = new InventoryDto( 1L, ingredients, amount );

        final InventoryDto createdInventoryDto = inventoryService.createInventory( inventoryDto );
        // Check contents of returned InventoryDto
        assertAll( "InventoryDto contents",
                () -> assertEquals( "coffee", createdInventoryDto.getIngredients().get( 0 ) ),
                () -> assertEquals( 10, createdInventoryDto.getIngredientAmounts().get( 0 ) ) );
    }

    /**
     * Tests InventoryService.updateInventory()
     */
    @Test
    @Transactional
    public void testUpdateInventory () {
        final InventoryDto inventoryDto = inventoryService.getInventory();

        final List<String> ingredients1 = new ArrayList<String>();
        ingredients1.add( "coffee" );
        ingredients1.add( "other" );
        final List<Integer> amount1 = new ArrayList<Integer>();
        amount1.add( 10 );
        amount1.add( 11 );

        inventoryDto.setIngredientAmounts( amount1 );
        inventoryDto.setIngredients( ingredients1 );

        ingredientService.createIngredient( new IngredientDto( 0L, "coffee" ) );
        ingredientService.createIngredient( new IngredientDto( 1L, "other" ) );

        final InventoryDto updatedInventoryDto = inventoryService.updateInventory( inventoryDto );
        assertAll( "InventoryDto contents", () -> assertEquals( 1L, updatedInventoryDto.getId() ),
                () -> assertEquals( "coffee", updatedInventoryDto.getIngredients().get( 0 ) ),
                () -> assertEquals( "other", updatedInventoryDto.getIngredients().get( 1 ) ),
                () -> assertEquals( 10, updatedInventoryDto.getIngredientAmounts().get( 0 ) ),
                () -> assertEquals( 11, updatedInventoryDto.getIngredientAmounts().get( 1 ) ) );
    }
}
