package edu.ncsu.csc326.coffee_maker.mapper;

import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.entity.Ingredient;

/**
 * Converts between IngredientDto and Ingredient entity
 */
public class IngredientMapper {

    /**
     * Converts an Ingredient entity to IngredientDto
     *
     * @param ingredient
     *            Ingredient to convert
     * @return IngredientDto object
     */
    public static IngredientDto mapToIngredientDto ( final Ingredient ingredient ) {
        return new IngredientDto( ingredient.getId(), ingredient.getName() );
    }

    /**
     * Converts a IngredientDto object to a Ingredient entity.
     *
     * @param ingredientDto
     *            IngredientDto to convert
     * @return Ingredient entity
     */
    public static Ingredient mapToIngredient ( final IngredientDto ingredientDto ) {
        return new Ingredient( ingredientDto.getId(), ingredientDto.getName() );
    }

}
