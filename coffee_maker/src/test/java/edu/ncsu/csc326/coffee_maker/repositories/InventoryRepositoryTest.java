package edu.ncsu.csc326.coffee_maker.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import edu.ncsu.csc326.coffee_maker.entity.Inventory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Tests InventoryRepository. Uses the real database - not an embedded one.
 */
@DataJpaTest
@AutoConfigureTestDatabase ( replace = Replace.NONE )
public class InventoryRepositoryTest {

    /** Reference to inventory repository */
    @Autowired
    private InventoryRepository inventoryRepository;

    /** Reference to EntityManager */
    @Autowired
    private TestEntityManager   testEntityManager;

    /** Reference to inventory */
    private Inventory           inventory;

    /**
     * Sets up the test case. We assume only one inventory row.
     *
     * @throws java.lang.Exception
     *             if error
     */
    @BeforeEach
    public void setUp () throws Exception {
        final EntityManager entityManager = testEntityManager.getEntityManager();
        final Query query = entityManager.createNativeQuery( "TRUNCATE TABLE inventory" );
        query.executeUpdate();

        final List<String> ingredients = new ArrayList<String>();
        ingredients.add( "coffee" );
        final List<Integer> amount = new ArrayList<Integer>();
        amount.add( 10 );
        inventory = new Inventory( 1L, ingredients, amount );
        inventoryRepository.save( inventory );
    }

    /**
     * Test saving the inventory and retrieving from the repository.
     */
    @Test
    public void testSaveAndGetInventory () {
        final Inventory fetchedInventory = inventoryRepository.findById( 1L ).get();
        assertAll( "Inventory contents", () -> assertEquals( 1L, fetchedInventory.getId() ),
                () -> assertEquals( "coffee", fetchedInventory.getIngredients().get( 0 ) ),
                () -> assertEquals( 10, fetchedInventory.getIngredientAmounts().get( 0 ) ) );
    }

    /**
     * Tests updating the inventory
     */
    @Test
    public void testUpdateInventory () {
        final Inventory fetchedInventory = inventoryRepository.findById( 1L ).get();
        final List<String> ingredients = new ArrayList<String>();
        ingredients.add( "coffee" );
        ingredients.add( "other" );
        final List<Integer> amount = new ArrayList<Integer>();
        amount.add( 10 );
        amount.add( 11 );
        fetchedInventory.setIngredients( ingredients );
        fetchedInventory.setIngredientAmounts( amount );
        final Inventory updatedInventory = inventoryRepository.save( fetchedInventory );
        assertAll( "Inventory contents", () -> assertEquals( 1L, updatedInventory.getId() ),
                () -> assertEquals( "coffee", updatedInventory.getIngredients().get( 0 ) ),
                () -> assertEquals( "other", updatedInventory.getIngredients().get( 1 ) ),
                () -> assertEquals( 10, updatedInventory.getIngredientAmounts().get( 0 ) ),
                () -> assertEquals( 11, updatedInventory.getIngredientAmounts().get( 1 ) ) );
    }
}
