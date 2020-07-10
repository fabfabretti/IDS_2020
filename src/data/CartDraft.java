package data;

import java.util.HashMap;
import java.util.Map.Entry;

import application.Globals;

public class CartDraft {

	// Hashmap prodotti barcode->quantity
	HashMap<Integer, Integer> products = new HashMap<Integer, Integer>();

	// ID dello user che ha effettuato la spesa attualmente in bozza
	int userID;

	public HashMap<Integer, Integer> getProducts() {
		return products;
	}

	public void setProducts(HashMap<Integer, Integer> products) {
		this.products = products;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void addProduct(int barCode, int quantity) {
		this.products.put(barCode, quantity);
	}

	public static void draftToCart() {
		Cart newCart = new Cart();
		
		User tmp = (User) Globals.currentUser;
		int indexForRemovingEntry = 0;

		for (CartDraft d : Globals.drafts) {
			// Si valuta se esiste un draft avente medesimo userID dell'utente attivo
			if (d.getUserID() == tmp.getUserID()) {
				indexForRemovingEntry = Globals.drafts.indexOf(d);

				for (Entry<Integer, Integer> e : d.getProducts().entrySet())
					newCart.addProduct(Globals.barCodeTable.get(e.getKey()), e.getValue());

			}
			
			Globals.drafts.remove(indexForRemovingEntry);
			Globals.cart = newCart;
		}
	}
}
