import { useEffect, useState } from 'react'
import { getInventory, updateInventory } from '../services/InventoryService'

/** Creates the page for viewing and updating the inventory. */
const InventoryComponent = () => {

	const [inventory, setInventory] = useState([]);
	const [ingredients, setIngredients] = useState([]);
	const [ingredientAmounts, setIngredientAmounts] = useState([]);
	const [successMessage, setSuccessMessage] = useState("");
	const [errorMessage, setErrorMessage] = useState("");

	useEffect(() => {
		getThisInventory();
	}, []);

	const getThisInventory = () => {
		getInventory().then((response) => {
			setInventory(response.data);
			setIngredients(response.data.ingredients);
			setIngredientAmounts(response.data.ingredientAmounts);
		}).catch(error => {
			console.error(error);
		})
	};

	const handleAmount = (amount, idx) => {

		const updatedIngredientAmounts = [...ingredientAmounts];
		updatedIngredientAmounts[idx] = parseInt(amount, 10);
		setIngredientAmounts(updatedIngredientAmounts);
	};

	const handleSubmit = () => {
		const updatedInventory = { ingredients: ingredients, ingredientAmounts: ingredientAmounts };
		console.log(updatedInventory);

		updateInventory(updatedInventory).then((response) => {

			console.log(response.data);
			setSuccessMessage(`Updated Successfully!`);
			setErrorMessage("");

		}).catch(error => {

			if(error.response.status == 500) {
				setErrorMessage("Empty Box, Please enter a value before you update")
				setSuccessMessage("");
			}
			else {
				setErrorMessage(error); 
			}
		})

	};

	return (
		<div className="container">
			<br></br>
			<h2 className="text-center">Inventory</h2>
			<br></br>
			<button className="btn btn-primary mb-2" onClick={handleSubmit} >Update</button>
			<br></br>
			{successMessage && (
				<div className="alert alert-success mt-3" role="alert">
					{successMessage}
				</div>
			)}
			{errorMessage && (
			        <div className="alert alert-danger mt-3" role="alert">
			            {errorMessage}
			        </div>
			    )}
			<table className="table table-striped table-bordered">
				<thead>
					<tr>
						<th>Ingredient</th>
						<th>Count</th>
					</tr>
				</thead>
				<tbody>

					{
						ingredients.map((ingredient, idx) => (
							<tr key={ingredient}>
								<td>{ingredient}</td>
								<td>
									<form>
										<div className="form-group mb-2">
											<input
												type="number"
												name="ingredientName"
												value={ingredientAmounts[idx]}
												min="1"
												step="1"
												required
												className="form-control"
												onChange={(e) => handleAmount(e.target.value, idx)}
											>
											</input>
										</div>
									</form>

								</td>
							</tr>
						))
					}
				</tbody>
			</table>
		</div>
	)
}

export default InventoryComponent