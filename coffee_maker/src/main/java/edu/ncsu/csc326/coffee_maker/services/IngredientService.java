package edu.ncsu.csc326.coffee_maker.services;

import java.util.List;

import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.exception.ResourceNotFoundException;

/**
 * Interface defining the ingredient behaviors.
 */
public interface IngredientService {

    /**
     * Creates an ingredient with the given information.
     *
     * @param ingredientDto
     *            ingredient to create
     * @return created ingredient
     */
    IngredientDto createIngredient ( IngredientDto ingredientDto );

    /**
     * Returns the ingredient with the given name
     *
     * @param name
     *            ingredien's name
     * @return the ingredient with the given name.
     * @throws ResourceNotFoundException
     *             if the ingredient doesn't exist
     */
    IngredientDto getIngredientByName ( String name );

    /**
     * Returns true if the ingredient already exists in the database.
     *
     * @param name
     *            ingredient's name to check
     * @return true if already in the database
     */
    boolean isDuplicateName ( String name );

    /**
     * Returns a list of all the ingredients.
     *
     * @return all the ingredients
     */
    List<IngredientDto> getAllIngredients ();
}
