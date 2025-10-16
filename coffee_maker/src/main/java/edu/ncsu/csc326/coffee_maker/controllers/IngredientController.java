package edu.ncsu.csc326.coffee_maker.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc326.coffee_maker.dto.IngredientDto;
import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import edu.ncsu.csc326.coffee_maker.services.IngredientService;
import edu.ncsu.csc326.coffee_maker.services.InventoryService;

/**
 * Controller for Ingredients.
 */
@CrossOrigin ( "*" )
@RestController
@RequestMapping ( "/api/ingredients" )
public class IngredientController {

    /** Connection to IngredientService */
    @Autowired
    private IngredientService ingredientService;

    /** Connection to IngredientService */
    @Autowired
    private InventoryService  inventoryService;

    /**
     * REST API method to provide POST access to the Ingredient model.
     *
     * @param body
     *            The valid Ingredient to be saved.
     * @return ResponseEntity indicating success if the Ingredient could be
     *         saved to the inventory, or an error if it could not be
     */
    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient ( @RequestBody final Map<String, Object> body ) {

        System.out.println( body );

        final String name = (String) body.get( "name" );
        final Integer amount = (Integer) body.get( "amount" );

        // body must not have extra content
        if ( body.size() != 2 ) {
            return new ResponseEntity<>( new IngredientDto( 0L, name ), HttpStatus.BAD_REQUEST );
        }

        // name must not be duplicate
        if ( ingredientService.isDuplicateName( name ) ) {
            return new ResponseEntity<>( new IngredientDto( 0L, name ), HttpStatus.CONFLICT );
        }

        final IngredientDto ingredientDto = ingredientService.createIngredient( new IngredientDto( 0L, name ) );

        addToInventory( name, amount );
        return ResponseEntity.ok( ingredientDto );

    }

    /**
     * REST API method to provide GET access to a specific ingredient, as
     * indicated by the path variable provided (the name of the ingredient
     * desired)
     *
     * @param name
     *            ingredient name
     * @return response to the request
     */
    @GetMapping ( "{name}" )
    public ResponseEntity<IngredientDto> getIngredient ( @PathVariable ( "name" ) final String name ) {
        final IngredientDto ingredientDto = ingredientService.getIngredientByName( name );
        return ResponseEntity.ok( ingredientDto );
    }

    /**
     * REST API method to provide GET access to all ingredients in the system
     *
     * @return JSON representation of all ingredients
     */
    @GetMapping
    public List<IngredientDto> getAllIngredients () {
        return ingredientService.getAllIngredients();
    }

    /**
     * Add ingredient to inventory with given amount.
     *
     * @param ingredientName
     *            Name of the ingredient to add
     * @param amount
     *            Amount of ingredient to add
     */
    private void addToInventory ( final String ingredientName, final Integer amount ) {
        final InventoryDto inventoryDto = inventoryService.getInventory();

        final List<String> ingredientNames = inventoryDto.getIngredients();
        final List<Integer> ingredientAmounts = inventoryDto.getIngredientAmounts();

        ingredientNames.add( ingredientName );
        ingredientAmounts.add( amount );

        inventoryDto.setIngredients( ingredientNames );
        inventoryDto.setIngredientAmounts( ingredientAmounts );

        inventoryService.updateInventory( inventoryDto );
    }
}
