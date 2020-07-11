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
	
	
	protected String email;
	protected String password;
	protected PersonalInformation anagrafica=new PersonalInformation();
	
	
	//
	//
	// METODI
	//
	//
	
	

	
	/**
	 * E' un get
	 * @return la mail
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	
	/**
	 * E' un get
	 * @return la password
	 */
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
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
	
	public boolean checkLogin(String otheremail, String otherpassword) {

		boolean res=getEmail().equals(otheremail)&&getPassword().equals(otherpassword)? true:false;
		return res;
	}

	public PersonalInformation getAnagrafica() {
		return anagrafica;
	}

	/**
	 * 
	 * @author andreatoffaletti
	 * Informazioni relative all'anagrafica di un User generico.
	 */
	public class PersonalInformation{
		/**
		 * Informazioni relative all'anagrafica
		 */
		private String Name="placeholder";
		private String FamilyName;
		private String Address;
		private String City;
		private String CAP;
		private String MobileNumber;
		
		
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
		public String getCAP() {
			return CAP;
		}
		public void setCAP(String cAP) {
			CAP = cAP;
		}
		public String getCity() {
			return City;
		}
		public void setCity(String city) {
			City = city;
		}
		public String getMobileNumber() {
			return MobileNumber;
		}
		public void setMobileNumber(String mobilenumber2) {
			MobileNumber = mobilenumber2;
		}
		
		
	}

	
}




