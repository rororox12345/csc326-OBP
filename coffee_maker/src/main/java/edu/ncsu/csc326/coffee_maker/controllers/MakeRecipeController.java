package edu.ncsu.csc326.coffee_maker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import edu.ncsu.csc326.coffee_maker.dto.RecipeDto;
import edu.ncsu.csc326.coffee_maker.services.InventoryService;
import edu.ncsu.csc326.coffee_maker.services.MakeRecipeService;
import edu.ncsu.csc326.coffee_maker.services.RecipeService;

/**
 * MakeRecipeController provides the endpoint for making a recipe.
 */
@CrossOrigin ( "*" )
@RestController
@RequestMapping ( "/api/makerecipe" )
public class MakeRecipeController {

    /** Connection to InventoryService */
    @Autowired
    private InventoryService  inventoryService;

    /** Connection to RecipeService */
    @Autowired
    private RecipeService     recipeService;

    /** Connection to MakeRecipeService */
    @Autowired
    private MakeRecipeService makeRecipeService;

    /**
     * REST API method to make coffee by completing a POST request with the ID
     * of the recipe as the path variable and the amount that has been paid as
     * the body of the response
     *
     * @param recipeName
     *            recipe name
     * @param amtPaid
     *            amount paid
     * @return The change the customer is due if successful
     */
    @PostMapping ( "{name}" )
    public ResponseEntity<Integer> makeRecipe ( @PathVariable ( "name" ) final String recipeName,
            @RequestBody final Integer amtPaid ) {
        final RecipeDto recipeDto = recipeService.getRecipeByName( recipeName );

        final int change = makeRecipe( recipeDto, amtPaid );
        if ( change == amtPaid ) {
            if ( amtPaid < recipeDto.getPrice() ) {
                return new ResponseEntity<>( amtPaid, HttpStatus.CONFLICT );
            }
            else {
                return new ResponseEntity<>( amtPaid, HttpStatus.BAD_REQUEST );
            }
        }
        return ResponseEntity.ok( change );
    }

    /**
     * Helper method to make coffee
     *
     * @param toPurchase
     *            recipe that we want to make
     * @param amtPaid
     *            money that the user has given the machine
     * @return change if there was enough money to make the coffee, throws
     *         exceptions if not
     */
    private int makeRecipe ( final RecipeDto toPurchase, final int amtPaid ) {
        int change = amtPaid;
        final InventoryDto inventoryDto = inventoryService.getInventory();

        if ( toPurchase.getPrice() <= amtPaid ) {
            if ( makeRecipeService.makeRecipe( inventoryDto, toPurchase ) ) {
                change = amtPaid - toPurchase.getPrice();
                return change;
            }
            else {
                // not enough inventory
                return change;
            }
        }
        else {
            // not enough money
            return change;
        }

    }

}
