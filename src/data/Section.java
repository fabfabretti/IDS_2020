package data;

import java.util.HashSet;
import java.util.PriorityQueue;

public class Section {

	String name;
	PriorityQueue<Product> products = new PriorityQueue<Product>();
	
	
	public Section(String nome) {
		name=nome;
	}

	public void addProduct(Product p) {
		System.out.println("[✓] Loaded product " + p.getName());
		products.add(p);
	}

	public String getName(){
		return name;
	}
	
	public PriorityQueue<Product> getProducts(){
		return products;
	}
	
	
}
