package data;

import java.util.HashMap;

import application.Globals;

/**
 * Contiene le info di un carrello.
 *
 */
public class Cart {
	/*
	 * Prodotti contenuti nel carrello: barCode - quantità
	 */
	HashMap<Integer, Integer> products = new HashMap<Integer, Integer>();

	/*
	 * Ammontare totale della spesa
	 */
	float total = 0;

	/*
	 * Numero totale di prodotti.
	 */
	int numberOfProd = 0;

	/**
	 * Aggiunge un prodotto al carrello e aggiorna i campi necessari.
	 * 
	 * @param p   prodotto da aggiungere
	 * @param qty quantità del prodotto da aggiungere
	 */
	public void addProduct(Product p, int qty) {
		if (products.containsKey(p.getBarCode()) == false) {
			products.put(p.getBarCode(), qty);
		}

		else {
			int oldqty = products.get(p.getBarCode());
			products.replace(p.getBarCode(), qty + oldqty);
		}

		total = total + qty * p.getPrice();
		numberOfProd += qty;

		Globals.viewController.refreshCartIcon();

	}

	/**
	 * Rimuove un prodotto dal carrello e aggiorna i campi necessari.
	 * 
	 * @param p prodotto da rimuovere.
	 */
	public void removeProduct(Product p) {

		if (!(products.containsKey(p.getBarCode())))
			System.out.println("Something went wrong, product is not present");
		else {
			total = total - products.get(p.getBarCode()) * p.getPrice();
			numberOfProd = numberOfProd - products.get(p.getBarCode());
			products.remove(p.getBarCode());

			if (products.size() == 0)
				total = 0;
		}

		Globals.viewController.refreshCartIcon();
	}

	/**
	 * Svuota il carrello.
	 */
	public void flushCart() {
		products.clear();
		total = 0;
		numberOfProd = 0;

		Globals.viewController.refreshCartIcon();
	}

	public Cart copyCart() {
		Cart res = new Cart();
		res.setNumberOfProd(numberOfProd);
		res.setTotal(total);
		res.setProducts(new HashMap<Integer, Integer>(products));
		return res;

	}

	/**
	 * Ritorna il totale della spesa nel carrello.
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * Imposta il totale della spesa nel carrello.
	 * 
	 * @param total il valore da impostare come total.
	 */
	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * Ritorna il numero totale di prodotti nel carrello.
	 */
	public int getNumberOfProd() {
		return numberOfProd;
	}

	/**
	 * Imposta il numero totale di prodotti nel carrello.
	 */
	public void setNumberOfProd(int numberOfProd) {
		this.numberOfProd = numberOfProd;
	}

	/**
	 * Ritorna un HashMap <barCode, quantità> dei prodotti nel carrello.
	 */
	public HashMap<Integer, Integer> getProducts() {
		return products;
	}

	/**
	 * Imposta un HashMap <barCode, quantità> come prodotti nel carrello.
	 */
	public void setProducts(HashMap<Integer, Integer> hashMap) {
		this.products = hashMap;
	}
}
