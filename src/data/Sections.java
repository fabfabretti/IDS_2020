package data;

import java.util.HashSet;

public class Sections {

	//
	//
	// VARIABILI E FLAG
	//
	//

	public static HashSet<Product> vegetali = new HashSet<Product>();
	
	
	public static void addVeg(Product p) {
		System.out.println("[✓] Loaded product " + p.getName());
		vegetali.add(p);
	}

}
