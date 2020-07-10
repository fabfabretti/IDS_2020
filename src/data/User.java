package data;

import java.util.HashSet;

public class User extends UserGeneral {

	private FidelityCard card = null;

	/*
	 * Codice univoco di ogni cliente
	 */
	private int userID;

	// TODO
	// private String PreferedPaymentMethod;

	public User(String email, String password, String name, String familyname, String address, String city, String cap,
			String mobilenumber, int userid) {
		this.email = email;
		this.password = password;
		this.anagrafica = new PersonalInformation();
		getAnagrafica().setName(name);
		getAnagrafica().setFamilyName(familyname);
		getAnagrafica().setAddress(address);
		getAnagrafica().setCity(city);
		getAnagrafica().setCAP(cap);
		getAnagrafica().setMobileNumber(mobilenumber);
		userID = userid;
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
