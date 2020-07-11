package data;

import java.util.HashMap;

import application.Globals;

public class CartDraft {

	// Hashmap prodotti barcode->quantity
	HashMap<Integer, Integer> products = new HashMap<Integer, Integer>();
	int userID;

	public void addProduct(int barCode, int quantity) {
		products.put(barCode, quantity);

	}

	public CartDraft() {
	};

	public CartDraft(Cart cart) {

		System.out.println("Draft created");
		for (Integer i : cart.getProducts().keySet()) {
			products.put(i, cart.getProducts().get(i));
		}
		userID = ((User) Globals.currentUser).getUserID();

		System.out.println("User   " + userID + "cart   " + products);

	}

	/**
	 * @return the products
	 */
	public HashMap<Integer, Integer> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(HashMap<Integer, Integer> products) {
		this.products = products;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

}
