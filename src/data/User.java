package data;

import java.time.LocalDate;
import java.util.HashSet;

public class User extends UserGeneral {

	/*
	 * USER UTILITY
	 */
	private int userID;

	/*
	 * FIDELTY CARD˙
	 */
	// Unic number of the fidelty card.
	private int number;

	// Total amount of point earned by the user.
	private int actualPoints;

	// Date of the emission of the card.
	private LocalDate emissionDate;

	/*
	 * PREFERED PAYMENT
	 */
	// Ordinal of preferd payment method
	private int paymentOrdinal = -1;

	// Credit card data
	private int creditCardNumber = 0;
	private int creditCardCVV = 0;
	private String creditCardName = null;
	private String creditCardFamilyName = null;

	// PayPal data
	private String payPalEmail = null;
	private String payPalPassword = null;

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

	/*
	 * GETTER AND SETTER
	 */
	public void initializeFideltyCard() {
		this.emissionDate = LocalDate.now();
		this.actualPoints = 0;
		this.number = userID ^ this.emissionDate.hashCode();
	}

	public void updateTotalPointFideltyCard(float totalOrderAmount) {
		int totalOrderPoint = (int) totalOrderAmount;
		this.actualPoints += totalOrderPoint;
	}

	public int getUserID() {
		return userID;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getActualPoints() {
		return actualPoints;
	}

	public void setActualPoints(int actualPoints) {
		this.actualPoints = actualPoints;
	}

	public LocalDate getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(LocalDate emissionDate) {
		this.emissionDate = emissionDate;
	}

	public int getPaymentOrdinal() {
		return paymentOrdinal;
	}

	public void setPaymentOrdinal(int paymentOrdinal) {
		this.paymentOrdinal = paymentOrdinal;
	}

	public int getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(int creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getCreditCardCVV() {
		return creditCardCVV;
	}

	public void setCreditCardCVV(int creditCardCVV) {
		this.creditCardCVV = creditCardCVV;
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	public String getCreditCardFamilyName() {
		return creditCardFamilyName;
	}

	public void setCreditCardFamilyName(String creditCardFamilyName) {
		this.creditCardFamilyName = creditCardFamilyName;
	}

	public String getPayPalEmail() {
		return payPalEmail;
	}

	public void setPayPalEmail(String payPalEmail) {
		this.payPalEmail = payPalEmail;
	}

	public String getPayPalPassword() {
		return payPalPassword;
	}

	public void setPayPalPassword(String payPalPassword) {
		this.payPalPassword = payPalPassword;
	}
}
