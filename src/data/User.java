package data;

import java.util.HashSet;

public class User extends UserGeneral {

	private FidelityCard card = null;

	//
	//
	// VARIABILI E FLAG DEI CLIENTI
	//
	//

	/*
	 * Codice univoco di ogni cliente
	 */
	private int userID;

	// TODO
	// private String PreferedPaymentMethod;

	public User(String email, String password) {
		super(email, password);
		// TODO Auto-generated constructor stub
	}

	
	
	
	/***
	 * Carica tutti gli utenti salvati in memoria e ritorna una collezione di questi
	 * utenti
	 * 
	 * @return hashset di questi utenti in memoria
	 */
	public static HashSet<UserGeneral> getUsers() {

		// ....ci serve un parser

		// TODO
		return null;
	}

	/***
	 * getter
	 * 
	 * @return l'user ID univoco
	 */
	public int getUserID() {
		return userID;
	}

	public void addFidelityCard(FidelityCard card) {
		this.card = card;
	}

}
