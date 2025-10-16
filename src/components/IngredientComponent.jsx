import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import { createIngredient } from '../services/IngredientsService'

/** Form to create a new Ingredient. */
const IngredientComponent = () => {

    const [name, setName] = useState("");
    const [amount, setAmount] = useState("");

    const [isFrozen, setIsFrozen] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [errors, setErrors] = useState({});

    const navigator = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        const intAmount = parseInt(amount, 10);

        const ingredient = {name, amount: intAmount};
        console.log(ingredient);

        createIngredient(ingredient).then((response) => {
            
            console.log(response.data);
            setIsFrozen(true);
            setSuccessMessage(`Added Ingredient "${name}" successfully!`);
            setErrors({});
            setTimeout(() => {
                navigator(0);
            }, 3000);
            

        }).catch(error => {

            console.error(error);
            const errorsCopy = {... errors};

            if (error.response.status == 409) {
                errorsCopy.name = "Duplicate Ingredient name!";
            }

            setErrors(errorsCopy);
            return;
        })
    };

    return (
        <div className="container">
            <br /><br />
            <div className="row">
                <div className="card col-md-6 offset-md-3">
                    <h2 className="text-center">Add Ingredient</h2>

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
                                <label className="form-label">Amount</label>
                                <input 
                                    type="number"
                                    name="amount"
                                    value={amount}
                                    min="1"
                                    step="1"
                                    required
                                    onChange={(e) => setAmount(e.target.value)}
                                    className={`form-control ${errors.amount ? "is-invalid":""}`}
                                    disabled={isFrozen}
                                >
                                </input>
                                {errors.amount && <div className="invalid-feedback">{errors.amount}</div>}
                            </div>
                            <br></br><br></br>
                            <button type="submit" className="btn btn-success">Add</button>

                        </form>

                        {successMessage && (
                            <div className="alert alert-success mt-3" role="alert">
                                {successMessage}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )

}

export default IngredientComponent