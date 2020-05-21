package application;

import data.Cart;
import data.UserGeneral;
import javafx.stage.Stage;

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
	static boolean logged = false;// maybe useful?
	static UserGeneral currentUser = null;
	static Stage stage;//
	static Cart cart = new Cart();
	// static Products
	// static Sections (=reparti)
	// static carrello? ma forse va in Utente
	
}

