
package edu.ncsu.csc326.coffee_maker.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ncsu.csc326.coffee_maker.dto.InventoryDto;
import edu.ncsu.csc326.coffee_maker.entity.Inventory;
import edu.ncsu.csc326.coffee_maker.exception.ResourceNotFoundException;
import edu.ncsu.csc326.coffee_maker.mapper.InventoryMapper;
import edu.ncsu.csc326.coffee_maker.repositories.InventoryRepository;
import edu.ncsu.csc326.coffee_maker.services.IngredientService;
import edu.ncsu.csc326.coffee_maker.services.InventoryService;

/**
 * Implementation of the InventoryService interface.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    /** Connection to the repository to work with the DAO + database */
    @Autowired
    private InventoryRepository inventoryRepository;
    /** ingredient service object */
    @Autowired
    private IngredientService   ingredientService;

    /**
     * Creates the inventory.
     *
     * @param inventoryDto
     *            inventory to create
     * @return updated inventory after creation
     */
    @Override
    public InventoryDto createInventory ( final InventoryDto inventoryDto ) {
        final Inventory inventory = InventoryMapper.mapToInventory( inventoryDto );
        final Inventory savedInventory = inventoryRepository.save( inventory );
        return InventoryMapper.mapToInventoryDto( savedInventory );
    }

    /**
     * Returns the single inventory.
     *
     * @return the single inventory
     */
    @Override
    public InventoryDto getInventory () {
        final List<Inventory> inventory = inventoryRepository.findAll();
        if ( inventory.size() == 0 ) {
            final InventoryDto newInventoryDto = new InventoryDto();

            newInventoryDto.setIngredientAmounts( new ArrayList<Integer>() );
            newInventoryDto.setIngredients( new ArrayList<String>() );

            final InventoryDto savedInventoryDto = createInventory( newInventoryDto );
            return savedInventoryDto;
        }
        return InventoryMapper.mapToInventoryDto( inventory.get( 0 ) );
    }

    /**
     * Updates the contents of the inventory.
     *
     * @param inventoryDto
     *            values to update
     * @return updated inventory
     */
    @Override
    public InventoryDto updateInventory ( final InventoryDto inventoryDto ) {
        final List<Inventory> inventoryList = inventoryRepository.findAll();

        if ( inventoryList.size() == 0 ) {
            throw new ResourceNotFoundException( "Inventory does not exist." );
        }

        final Inventory inventory = inventoryList.get( 0 );
        if ( inventoryDto.getIngredientAmounts().size() != inventoryDto.getIngredients().size() ) {
            throw new IllegalArgumentException();
        }
        for ( int i = 0; i < inventoryDto.getIngredientAmounts().size(); i++ ) {
            if ( inventoryDto.getIngredientAmounts().get( i ) < 0 ) {
                throw new IllegalArgumentException(
                        "Ingredient " + inventoryDto.getIngredients().get( i ) + " cannot have a negative value." );
            }

        }
        for ( int i = 0; i < inventoryDto.getIngredients().size(); i++ ) {
            if ( !ingredientService.isDuplicateName( inventoryDto.getIngredients().get( i ) ) ) {
                throw new IllegalArgumentException(
                        "Ingredient " + inventoryDto.getIngredients().get( i ) + " does not exist" );
            }
        }

        inventory.setIngredientAmounts( inventoryDto.getIngredientAmounts() );
        inventory.setIngredients( inventoryDto.getIngredients() );

        final Inventory savedInventory = inventoryRepository.save( inventory );

        return InventoryMapper.mapToInventoryDto( savedInventory );
    }

}
