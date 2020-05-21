package data;

import java.util.HashMap;

public class Cart {
	
	HashMap<Product,Integer> products = new HashMap<Product,Integer>();
	float total = 0;
	int numberOfProd = 0;
	
	
	public void addProduct(Product p,int qty) {
		if(products.containsKey(p)==false) {
			products.put(p, qty);
		}
		
		else {
			int oldqty= products.get(p);
			products.replace(p, qty + oldqty);
		}
	}	
	
	public void removeProduct(Product p) {
		
		if(!products.containsKey(p))
			System.out.println("Something went wrong, product is not present");
		else {
			products.remove(p);
			total=total-p.getPrice();
			numberOfProd--;	
		}
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
	
	
	
	
	
}
