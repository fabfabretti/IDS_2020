package data;

import java.util.HashSet;

public class Sections {

	//
	//
	// VARIABILI E FLAG
	//
	//

	public static HashSet<Product> vegetali = new HashSet<Product>();
	private HashSet<Product> carne = new HashSet<Product>();
	private HashSet<Product> pesce = new HashSet<Product>();;
	private HashSet<Product> latticini = new HashSet<Product>();;
	private HashSet<Product> bevande = new HashSet<Product>();;
	
	
	public static void addVeg(Product p) {
		//System.out.println("Aggiunto" + p.toString() + "\n");
		vegetali.add(p);
	}

}
