import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import { listRecipes, deleteRecipe } from '../services/RecipesService'
import { listIngredients } from '../services/IngredientsService'

/** Lists all the recipes and provide the option to create a new recipe
 * and delete an existing recipe.
 */
const ListRecipesComponent = () => {

    const [recipes, setRecipes] = useState([]);
    const [ingredients, setIngredients] = useState([]);
    const [successMessage, setSuccessMessage] = useState("");

    const navigator = useNavigate();

    useEffect(() => {
        getAllRecipes();
        getAllIngredients();
    }, []);

    const getAllRecipes = () => {
        listRecipes().then((response) => {
            setRecipes(response.data);
        }).catch(error => {
            console.error(error);
        })
    };

    const getAllIngredients = () => {
        listIngredients().then((response) => {
            setIngredients(response.data);
        }).catch(error => {
            console.error(error);
        })
    };

    const addNewRecipe = () => {
        navigator('/add-recipe');
    }

    const editRecipe = (id) => {
        navigator(`/edit-recipe/${id}`)
    }

    const removeRecipe = (recipe) => {
        console.log(recipe.id);

        deleteRecipe(recipe.id).then((response) => {
            getAllRecipes();
            setSuccessMessage(`Deleted recipe "${recipe.name}" successfully!`);
            setTimeout(() => {
                setSuccessMessage("");
            }, 3000);
        }).catch(error => {
            console.error(error);
        })
    }

    return (
        <div className="container">
            <br></br>
            <h2 className="text-center">Recipes</h2>

            <br></br>
            <button className="btn btn-primary mb-2" onClick={ addNewRecipe }>Add Recipe</button>
            <br></br>

            {ingredients.length > 0 && recipes.length > 0 && (
            <table className="table table-bordered">
                <thead>
                    <tr>
                        <th>Recipe Name</th>
                        <th>Recipe Price</th>
                        {
                            ingredients.map( ingredient => (
                                <th key={ingredient.name}>Amount {ingredient.name}</th>
                            ))
                        }
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>

                    {
                        recipes.map(recipe => (
                            <tr key={recipe.name}>
                                <td>{recipe.name}</td>
                                <td>{recipe.price}</td>
                                {
                                    ingredients.map(ingredient => {
                                        const idx = recipe.ingredients.indexOf(ingredient.name);
                                        const cellKey = `${recipe.name}-${ingredient.name}`;
                                        if (idx !== -1) {
                                            return <td key={cellKey}>{recipe.ingredientAmounts[idx]}</td>;
                                        } else {
                                            return <td key={cellKey} style={{ backgroundColor: '#494949'}}></td>;
                                        } 
                                    })
                                }
                                <td>
                                    <button className="btn btn-secondary" onClick={() => editRecipe(recipe.id)}
                                        style={{marginLeft: '10px', width: '50px'}}
                                    >✏️</button>
                                    <button className="btn btn-danger" onClick={() => removeRecipe(recipe)}
                                        style={{marginLeft: '10px', color: 'white', width: '50px'}}
                                    >Ｘ</button>
                                </td>  
                            </tr>
                        ))
                    }                    
                </tbody>
            </table>
            )}
            {successMessage && (
                <div className="alert alert-warning mt-3" role="alert">
                    {successMessage}
                </div>
            )}
        </div>
    )

}

export default ListRecipesComponent