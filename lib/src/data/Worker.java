package data;

public class Worker extends UserGeneral{

	private int WorkerID;
	
	
	public Worker(String email, String password,String name, String familyname, String address, String city, String cap, String number, int workerid) {
		this.email=email;
		this.password=password;
		//System.out.println(getAnagrafica());
		getAnagrafica().setName(name);
		getAnagrafica().setFamilyName(familyname);
		getAnagrafica().setAddress(address);
		getAnagrafica().setCity(city);
		getAnagrafica().setCAP(cap);
		getAnagrafica().setMobileNumber(number);
		setWorkerID(workerid);
	}


	public int getWorkerID() {
		return WorkerID;
	}


	public void setWorkerID(int workerID) {
		WorkerID = workerID;
	}
}
	
	
