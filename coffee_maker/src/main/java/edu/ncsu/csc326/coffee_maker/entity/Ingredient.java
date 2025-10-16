package edu.ncsu.csc326.coffee_maker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Ingredient for the coffee maker. Ingredient is a Data Access Object (DAO) is
 * tied to the database using Hibernate libraries. IngredientRepository provides
 * the methods for database CRUD operations.
 */
@Entity
@Table ( name = "ingredients" )
public class Ingredient {

    /** Ingredient Id */
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long   id;

    /** Ingredient name */
    private String name;

    /**
     * Default constructor for IngredientDto.
     */
    public Ingredient () {
    }

    /**
     * Creates ingredientDto from field values.
     *
     * @param id
     *            ingredient's id
     * @param name
     *            ingredient's name
     *
     */
    public Ingredient ( final Long id, final String name ) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for id.
     *
     * @return id
     */
    public Long getId () {
        return this.id;
    }

    /**
     * Setter for id.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings ( "unused" )
    private void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Getter for name.
     *
     * @return name
     *
     */
    public String getName () {
        return this.name;
    }

    /**
     * Setter for name.
     *
     * @param name
     *            the name
     */
    public void setName ( final String name ) {
        this.name = name;
    }

}
