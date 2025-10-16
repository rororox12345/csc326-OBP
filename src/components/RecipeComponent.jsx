import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import { createRecipe } from '../services/RecipesService'
import { listIngredients } from '../services/IngredientsService'

/** Form to create a new recipe. */
const RecipeComponent = () => {

    const [name, setName] = useState("");
    const [price, setPrice] = useState("");
    const [thisIngredients, setThisIngredients] = useState([]);
    const [thisIngredientAmounts, setThisIngredientAmounts] = useState([]);

    const [allIngredients, setAllIngredients] = useState([]);
    
    const [isFrozen, setIsFrozen] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errors, setErrors] = useState({});

    const navigator = useNavigate();

    useEffect(() => {
        getAllIngredients();
    }, []);


    const getAllIngredients = () => {
        listIngredients().then((response) => {
            setAllIngredients(response.data);
        }).catch(error => {
            console.error(error);
        })
    };

    const handleCheckBox = (isChecked, ingredient) => {
        if (isChecked) {
            setThisIngredients((prevIngredients) => [...prevIngredients, ingredient.name]);
            setThisIngredientAmounts((prevIngredientAmounts) => [...prevIngredientAmounts, 0]);
        } else {
            const idx = thisIngredients.indexOf(ingredient.name);
            setThisIngredients((prevIngredients) =>
                prevIngredients.filter((_, index) => index !== idx)
            );
            setThisIngredientAmounts((prevIngredientAmounts) =>
                prevIngredientAmounts.filter((_, index) => index !== idx)
            ); 
        }
    };

    const handleAmount = (amount, ingredient) => {
        const idx = thisIngredients.indexOf(ingredient.name);
        const updatedIngredientAmounts = [...thisIngredientAmounts];
        updatedIngredientAmounts[idx] = parseInt(amount, 10);
        setThisIngredientAmounts(updatedIngredientAmounts);
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const intPrice = parseInt(price, 10);

        const recipe = {name, price: intPrice, ingredients: thisIngredients, ingredientAmounts: thisIngredientAmounts};
        console.log(recipe);

        if (allIngredients.length == 0) {
            const errorsCopy = {... errors};
            errorsCopy.general = "No Ingredients Exist! Redirecting to add ingredients..."
            setErrors(errorsCopy);
            setTimeout(() => {
                navigator('/add-ingredient');
            }, 3000);
            return;
        }
        if (thisIngredients.length == 0) {
            const errorsCopy = {... errors};
            errorsCopy.general = "Select at least one Ingredient!"
            setErrors(errorsCopy);
            return;
        }

        createRecipe(recipe).then((response) => {
                    
            console.log(response.data);
            setIsFrozen(true);
            setSuccessMessage(`Added Recipe "${name}" successfully!`);
            setErrors({});
            setTimeout(() => {
                navigator('/recipes');
            }, 3000);
            

        }).catch(error => {

            console.error(error);
            const errorsCopy = {... errors};

            if (error.response.status == 409) {
                errorsCopy.name = "Duplicate Recipe name!";
            } else if (error.response.status == 507) {
                errorsCopy.general = "Cannot add more than 3 Recipes!";
            }

            setErrors(errorsCopy);
        })

    };


    return (
        <div className="container">
            <br /><br />
            <div className="row">
                <div className="card col-md-6 offset-md-3">
                    <h2 className="text-center">Add Recipe</h2>

                    <div className="card-body">
                        <form onSubmit={handleSubmit}>
                            
                            <div className="form-group mb-2">
                                <label className="form-label">Name</label>
                                <input 
                                    type="text"
                                    name="name"
                                    value={name}
                                    required
                                    onChange={(e) => setName(e.target.value)}
                                    className={`form-control ${errors.name ? "is-invalid":""}`}
                                    disabled={isFrozen}
                                >
                                </input>
                                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Price</label>
                                <input 
                                    type="number"
                                    name="price"
                                    value={price}
                                    min="1"
                                    step="1"
                                    required
                                    onChange={(e) => setPrice(e.target.value)}
                                    className={`form-control ${errors.price ? "is-invalid":""}`}
                                    disabled={isFrozen}
                                >
                                </input>
                                {errors.price && <div className="invalid-feedback">{errors.price}</div>}
                            </div>
                            
                            <br></br>

                            {allIngredients.length > 0 && (
                                <table className="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Select</th>
                                            <th>Ingredient Name</th>
                                            <th>Amount</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            allIngredients.map(ingredient => (
                                                <tr key={ingredient.name}>
                                                    <td>
                                                        <div className="form-check" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                                            <input 
                                                                type="checkbox" 
                                                                className="form-check-input position-static"
                                                                onChange={(e) => handleCheckBox(e.target.checked, ingredient)}
                                                                disabled={isFrozen}
                                                            >
                                                            </input>
                                                        </div>
                                                    </td>
                                                    <td>{ingredient.name}</td>
                                                    <td>
                                                        
                                                        <div className="form-group mb-2">
                                                        {thisIngredients.indexOf(ingredient.name) != -1 && (
                                                            <input 
                                                                type="number"
                                                                name="amount"
                                                                min="1"
                                                                step="1"
                                                                required
                                                                className="form-control form-control-sm"
                                                                onChange={(e) => handleAmount(e.target.value, ingredient)}
                                                                disabled={isFrozen}
                                                            >
                                                            </input>
                                                        )}
                                                        </div>
                                                        
                                                    </td>                                                    
                                                </tr>
                                            ))
                                        }
                                    </tbody>
                                </table>
                            )}  

                            <br></br><br></br>
                            <button type="submit" className="btn btn-success">Add</button>

                        </form>
                    </div>
                        {successMessage && (
                                <div className="alert alert-success mt-3" role="alert">
                                    {successMessage}
                                </div>
                        )}
                        {errors.general && (
                                <div className="alert alert-danger mt-3" role="alert">
                                    {errors.general}
                                </div>
                        )}
                </div>
            </div>
        </div>
    )

}

export default RecipeComponent