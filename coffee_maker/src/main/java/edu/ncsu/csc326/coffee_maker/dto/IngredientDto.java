package edu.ncsu.csc326.coffee_maker.dto;

/**
 * Used to transfer Ingredient data between the client and server. This class
 * will serve as the response in the REST API.
 */
public class IngredientDto {

    /** Ingredient Id */
    private Long   id;

    /** Ingredient name */
    private String name;

    /**
     * Default constructor for Ingredient.
     */
    public IngredientDto () {
    }

    /**
     * Creates ingredient from field values.
     *
     * @param id
     *            ingredient's id
     * @param name
     *            ingredient's name
     *
     */
    public IngredientDto ( final Long id, final String name ) {
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
