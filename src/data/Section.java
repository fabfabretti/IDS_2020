package data;

import java.util.TreeSet;

public class Section {

	String name;
	TreeSet<Product> products = new TreeSet<Product>();
	
	
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
	
	public TreeSet<Product> getProducts(){
		return products;
	}
	
	
}
