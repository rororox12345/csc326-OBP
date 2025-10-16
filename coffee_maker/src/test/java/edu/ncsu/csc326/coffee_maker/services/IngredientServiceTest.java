/**
 *
 */
package edu.ncsu.csc326.coffee_maker.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.repositories.IngredientRepository;

/**
 * Test class for ingredient service.
 */
@SpringBootTest
class IngredientServiceTest {

    /** Reference to ingredient service */
    @Autowired
    private IngredientService    ingredientService;

    /** Reference to ingredient repository */
    @Autowired
    private IngredientRepository ingredientRepository;

    /**
     * Clear ingredient repository.
     *
     * @throws java.lang.Exception
     *             if error
     */
    @BeforeEach
    void setUp () throws Exception {
        ingredientRepository.deleteAll();
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.IngredientService#createIngredient(edu.ncsu.csc326.coffee_maker.dto.IngredientDto)}.
     */
    @Test
    @Transactional
    void testCreateIngredient () {
        // creates a new ingredient
        final IngredientDto ingredientDto = new IngredientDto( 0L, "Coffee" );
        final IngredientDto savedIngredient = ingredientService.createIngredient( ingredientDto );

        // ensures the ingredient contents are correct based on savedIngredient
        assertAll( "Ingredient contents", () -> assertTrue( savedIngredient.getId() > 1L ),
                () -> assertEquals( "Coffee", savedIngredient.getName() ) );

        // ensures the ingredient contents are correct based on searching by
        // name
        final IngredientDto retrievedIngredient = ingredientService.getIngredientByName( savedIngredient.getName() );
        assertAll( "Ingredient contents", () -> assertTrue( retrievedIngredient.getId() > 1L ),
                () -> assertEquals( "Coffee", retrievedIngredient.getName() ) );
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.IngredientService#isDuplicateName(java.lang.String)}.
     */
    @Test
    @Transactional
    void testIsDuplicateName () {
        // creates a new ingredient
        final IngredientDto ingredientDto = new IngredientDto( 0L, "Coffee" );
        ingredientDto.setName( "Sugar" );
        ingredientService.createIngredient( ingredientDto );

        // checks if name already exists
        assertTrue( ingredientService.isDuplicateName( "Sugar" ) );
        // checks if another name is already present
        assertFalse( ingredientService.isDuplicateName( "Hot Chocolate" ) );
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc326.coffee_maker.services.IngredientService#getAllIngredients()}.
     */
    @Test
    @Transactional
    void testGetAllIngredients () {
        // creates a new ingredient
        IngredientDto ingredientDto = new IngredientDto( 0L, "Coffee" );
        ingredientService.createIngredient( ingredientDto );

        // creates another ingredient
        ingredientDto = new IngredientDto( 0L, "Sugar" );
        ingredientService.createIngredient( ingredientDto );

        // retrieves ingredient list
        final List<IngredientDto> allIngredients = ingredientService.getAllIngredients();

        // ensures size is correct
        assertEquals( 2, allIngredients.size() );
    }

}
