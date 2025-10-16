package edu.ncsu.csc326.coffee_maker.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.entity.Ingredient;
import edu.ncsu.csc326.coffee_maker.exception.ResourceNotFoundException;
import edu.ncsu.csc326.coffee_maker.mapper.IngredientMapper;
import edu.ncsu.csc326.coffee_maker.repositories.IngredientRepository;
import edu.ncsu.csc326.coffee_maker.services.IngredientService;

/**
 * Implementation of the RecipeService interface.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    /** Connection to the repository to work with the DAO + database */
    @Autowired
    private IngredientRepository ingredientRepository;

    /**
     * Creates an ingredient with the given information.
     *
     * @param ingredientDto
     *            ingredient to create
     * @return created ingredient
     */
    @Override
    public IngredientDto createIngredient ( final IngredientDto ingredientDto ) {
        final Ingredient ingredient = IngredientMapper.mapToIngredient( ingredientDto );
        final Ingredient savedIngredient = ingredientRepository.save( ingredient );
        return IngredientMapper.mapToIngredientDto( savedIngredient );
    }

    /**
     * Returns the ingredient with the given name
     *
     * @param name
     *            ingredien's name
     * @return the ingredient with the given name.
     * @throws ResourceNotFoundException
     *             if the ingredient doesn't exist
     */
    @Override
    public IngredientDto getIngredientByName ( final String name ) {
        final Ingredient ingredient = ingredientRepository.findByName( name )
                .orElseThrow( () -> new ResourceNotFoundException( "Ingredient does not exist with name " + name ) );
        return IngredientMapper.mapToIngredientDto( ingredient );
    }

    /**
     * Returns true if the ingredient already exists in the database.
     *
     * @param name
     *            ingredient's name to check
     * @return true if already in the database
     */
    @Override
    public boolean isDuplicateName ( final String name ) {
        try {
            getIngredientByName( name );
            return true;
        }
        catch ( final ResourceNotFoundException e ) {
            return false;
        }
    }

    /**
     * Returns a list of all the ingredients.
     *
     * @return all the ingredients
     */
    @Override
    public List<IngredientDto> getAllIngredients () {
        final List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream().map( ( ingredient ) -> IngredientMapper.mapToIngredientDto( ingredient ) )
                .collect( Collectors.toList() );
    }

}
