package data;

import java.util.HashMap;

/**
 * Contiene le info di un carrello.
 *
 */
public class Cart {
	
	HashMap<Product,Integer> products = new HashMap<Product,Integer>();
	float total = 0;
	int numberOfProd = 0;
	
		
	/**
	 * Aggiunge un prodotto al carrello e aggiorna i campi necessari.
	 * @param p prodott da aggiungere
	 * @param qty quantità da aggiungere
	 */
	public void addProduct(Product p,int qty) {
		if(products.containsKey(p)==false) {
			products.put(p, qty);
		}
		
		else {
			int oldqty= products.get(p);
			products.replace(p, qty + oldqty);
		}
		
		
		total = total + qty*p.getPrice();
		numberOfProd += qty;
		
		
	}	
	
	/**
	 * Rimuove un prodotto dal carrello e aggiorna i campi necessari.
	 * @param p prodotto
	 */
	public void removeProduct(Product p) {
		
		if(!(products.containsKey(p)))
			System.out.println("Something went wrong, product is not present");
		else {
			total=total-products.get(p)*p.getPrice();
			numberOfProd=numberOfProd-products.get(p);	
			products.remove(p);
			
			if(products.size()==0)
				total=0;
		}
	}
	
	/**
	 * Svuota il carrello.
	 */
	public void flushCart() {
		products.clear();
		total = 0;
		numberOfProd = 0;
	}
	
	
	public Cart copyCart() {
		Cart res = new Cart();
		res.setNumberOfProd(numberOfProd);
		res.setTotal(total);
		res.setProducts(new HashMap<Product,Integer>(products));
		return res;
	}
	
	
	
	
	
	/**
	 * @return the total
	 */
	public float getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(float total) {
		this.total = total;
	}
	/**
	 * @return the numberOfProd
	 */
	public int getNumberOfProd() {
		return numberOfProd;
	}
	/**
	 * @param numberOfProd the numberOfProd to set
	 */
	public void setNumberOfProd(int numberOfProd) {
		this.numberOfProd = numberOfProd;
	}
	/**
	 * 
	 * @return the products set
	 */
	public  HashMap<Product, Integer> getProducts() {
		return products;
	}
	
	/** 
	 * @param the product set
	 */
	public void setProducts(HashMap<Product, Integer> products) {
		this.products=products;
	}	
}
