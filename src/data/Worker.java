package data;

public class Worker extends UserGeneral{

	private int WorkerID;
	
	
	public Worker(String email, String password,String name, String familyname, String address, String city, int cap, int number, int workerid) {
		this.email=email;
		this.password=password;
		this.getAnagrafica().setName(name);
		this.getAnagrafica().setName(familyname);
		this.getAnagrafica().setAddress(address);
		this.getAnagrafica().setCity(city);
		this.getAnagrafica().setCAP(cap);
		this.getAnagrafica().setMobileNumber(number);
		WorkerID=workerid;
	}
}
	
	
