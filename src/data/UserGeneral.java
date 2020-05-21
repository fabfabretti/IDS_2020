package data;

/***
 * @author Fabiola
 * 
 * E' la classe che definisce gli utenti. Mi aspetto che sarà una classe astratta con i metodi 
 * @method getEmail
 * @method getPassword
 * 
 * Ogni altro metodo (per esempio il costruttore o altri get, come getCarrello)
 * sarà definito nelle sue sottoclassi (presumibilmente Utente e Commesso)
 */
public class UserGeneral{
	
	//
	//
	// VARIABILI E FLAG 
	//
	//
	
	
	private String email;
	private String password;
	private PersonalInformation anagrafica;
	
	
	//
	//
	// METODI
	//
	//
	
	
	/**
	 * Costruttore
	 * (TODO) presumibulmente sarà usato dalla funzione che legge il file!
	 * @param email indovina cos'è :)
	 * @param password indovina cos'è :)
	 */
	public UserGeneral(String email, String password){
		this.email=email;
		this.password=password;
	}
	
	/**
	 * E' un get
	 * @return la mail
	 */
	public String getEmail() {
		return email;
	}
	
	
	/**
	 * E' un get
	 * @return la password
	 */
	public String getPassword() {
		return password;
	}
	public String getName() {
		//TODO
		return "nome";
	}
	
	/**
	 * Verifica se la combinazione email/password corrisponde all'utente
	 * 
	 * @param otheremail email del login
	 * @param otherpassword password del login
	 * @return ritorna "null" se questo utente NON corrisponde al login; 
	 * 				   "commesso" se corrisponde ed è un commesso;
	 * 				   "utente" se corrisponde ed è un utente
	 * 
	 */
	
	public String checkLogin(String otheremail, String otherpassword) {

		if (!(email.equals(otheremail) && password.equals(otherpassword)))
			return "null";
		else if (otheremail.equals("admin")||this instanceof Worker)
			return "commesso";
		return "utente";
	}

	/**
	 * 
	 * @author andreatoffaletti
	 * Informazioni relative all'anagrafica di un User generico.
	 */
	private class PersonalInformation{
		/**
		 * Informazioni relative all'anagrafica
		 */
		private String Name="placeholder";
		private String FamilyName;
		private String Address;
		private String City;
		private int CAP;
		private int MobileNumber;
		
		
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getFamilyName() {
			return FamilyName;
		}
		public void setFamilyName(String familyName) {
			FamilyName = familyName;
		}
		public String getAddress() {
			return Address;
		}
		public void setAddress(String address) {
			Address = address;
		}
		public int getCAP() {
			return CAP;
		}
		public void setCAP(int cAP) {
			CAP = cAP;
		}
		public String getCity() {
			return City;
		}
		public void setCity(String city) {
			City = city;
		}
		public int getMobileNumber() {
			return MobileNumber;
		}
		public void setMobileNumber(int mobileNumber) {
			MobileNumber = mobileNumber;
		}
		
		
	}

	
}




