package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import data.Cart;
import data.CartDraft;
import data.Order;
import data.Product;
import data.Section;
import data.User;
import data.UserGeneral;
import data.Worker;

/**
 * Dato che Java non prevede la possibilità di avere variabili globali, la cosa
 * più vicina che c'è (afaik) è creare una classe che contenga tutte le info che
 * vogliamo siano visibili in tutti i file non so se effettivamente servano, ma
 * immagino che sia utile salvarsi almeno: ->utente per poter risalire ai vary
 * dati (es. pagamenti e carrelli salvati) ->root stage se dovessimo gestire lo
 * stage da più classi diverse (ma forse ci sono workaround vari)
 * 
 * Possiamo anche chiamarlo Sessione così sembra più legit
 *
 *
 */
public class Globals {
	
	
	//Users
	
	static HashSet<User> users = JsonLoader.loadUsers();
	static HashSet<Worker> workers = JsonLoader.loadWorkers();
	
	static ArrayList<CartDraft> drafts = new ArrayList<CartDraft>(); //TODO: qui ci andrà JsonLoader.loadDrafts()
	
	//Storico
	
	public static ArrayList<Order> storico= new ArrayList<Order>() ;
	
	
	// Sections e prodotti
	
	public static Section vegetali = new Section("Frutta e Verdura");
	public static Section pesce = new Section("Pesce");
	public static Section carne = new Section("Carne");
	public static Section latticini = new Section("Latte e Formaggi");
	public static Section bevande = new Section("Bevande");
	
	public static Section reparti[] = {vegetali,pesce,carne,latticini,bevande};
	
	public static HashMap<Integer,Product> barCodeTable = computeTable();


	

	
	
	//Current Session

	public static UserGeneral currentUser = null;	
	public static Cart cart = new Cart();
	
	public static ProductViewer currentView = null;
	public static UserCartController cartController=null;
	public static UserHomeController viewController=null;
	public static WorkerProductManagerController editController=null;
	public static Order currentOrder = null;
	
	public static HashMap<Integer,Product> computeTable() {
		if(barCodeTable!=null)
			barCodeTable.clear();
		
		HashMap<Integer,Product> result = new HashMap<Integer,Product>();
		
		for(Section s : reparti)
			for(Product p : s.getProducts()) 
				result.put(p.getBarCode(),p);
			
		barCodeTable = result;
		return result;
	}

}
