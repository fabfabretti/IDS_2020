package application;

import java.util.HashSet;

import data.Cart;
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
	
	// Sections
	public static Section vegetali = new Section("Frutta e Verdura");
	public static Section pesce = new Section("Pesce");
	public static Section carne = new Section("Carne");
	public static Section latticini = new Section("Latte e Formaggi");
	public static Section bevande = new Section("Bevande");
	
	public static Section reparti[] = {vegetali,pesce,carne,latticini,bevande};

	public static UserCartController cartController=null;
	public static UserHomeController viewController=null;
	public static WorkerProductManagerController editController=null;
	
	//Current Session

	static UserGeneral currentUser = null;	
	static Cart cart = new Cart();
	

}
