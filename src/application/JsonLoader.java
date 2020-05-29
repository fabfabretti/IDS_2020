package application;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

import data.Product;
import data.Section;
import data.User;
import data.Worker;
import json.Json;
import json.JsonArray;
import json.JsonValue;

public class JsonLoader {

	static void  loadProducts() {

		//(try/catch è necessario causa operazione input output)

		//		 uso try/catch *with resource* (cioè con il reader dentro la parentesi)
		//		 perché è più bello (cit. Spoto)

		try(Reader reader= new FileReader("./data/product.json")){

			
			for(Section s : Globals.reparti) {
				

				System.out.println();
				JsonArray sezione = Json.parse(reader).asObject().get(s.getName()).asArray();
				
				for(JsonValue prodotto : sezione) {
					//System.out.println(prodotto); 	
					/* 	[! ! ! ! ! ]
					  		|----->	Da questa print di debug si evince che  
					  				ciascun JsonValue (ovvero ciascun elemento di ciascun
					  				 indice del JsonArray)è una schifosissima STRINGA
					  				con dentro tutta la roba del nostro oggetto (assolutamente non parsata,
					  				 ripeto: è una schifosissima stringa)
					  
					  				Get asObject lo rende una cosa papabile 
					  				(perché la schifosissima stringa non gli piace)
					  				su cui si riesce a parse-are la roba
					  				
					  				Le funzioni getString/getInt/etc... traducono il Json Object 
					  				nei suoi valori e sono sostanzialmente 
					  				come una hashmap ("dammi la key e io ti do la value")
					  				
					  				Il primo valore che si passa è la key, mentre il secondo è il
					  				valore di controllo (Aka se ci ritorna quella roba lì significa
					  				che non ha trovato l'elemento
					 */
					
					
					
					
					String name = prodotto.asObject().getString("name", "Unknown prodotto");
					
					int barCode = prodotto.asObject().getInt("barCode", -1);
	
					String imagePath = prodotto.asObject().getString("imagePath", "Unknown image");
	
					String brand = prodotto.asObject().getString("brand", "Unknown image");
					
					String weight = prodotto.asObject().getString("weight", "Unknown weight");
	
					float price = prodotto.asObject().getFloat("price", (float) -1.0);
	
					String weightPrice = prodotto.asObject().getString("weightPrice", "Unknown w. price");
	
					int available = prodotto.asObject().getInt("available", -1);
	
					boolean bio = prodotto.asObject().getBoolean("bio", false);
					boolean glutenfree = prodotto.asObject().getBoolean("glutenfree", false);
					boolean vegan = prodotto.asObject().getBoolean("vegan", false);
					boolean lactosefree = prodotto.asObject().getBoolean("lactosefree", false);
					
				
					
					Product p = new Product(name,barCode,imagePath,brand,weight, price,weightPrice,available,bio,glutenfree,vegan,lactosefree);
	
					
	
				}
			}

	

		}catch(IOException e){
			System.out.println("Errore I/O");
			e.printStackTrace();

		}


	}

	
	static HashSet<User> loadUsers() {
		
		System.out.println("[...] Loading users...");
		HashSet<User> users = new HashSet<User>();
		//(try/catch è necessario causa operazione input output)

				//		 uso try/catch *with resource* (cioè con il reader dentro la parentesi)
				//		 perché è più bello (cit. Spoto)

				try(Reader reader= new FileReader("./data/users.json")){

					JsonArray fileUtenti = Json.parse(reader).asObject().get("users").asArray();

					for(JsonValue user : fileUtenti) {
						//System.out.println(user); 	
						String email = user.asObject().getString("email", "NoEmail");
						//System.out.println("[?]" + email);
						String password = user.asObject().getString("password", "NoPassword");
						//System.out.println("[?]" + password);
						String name = user.asObject().getString("name", "NoName");
						//System.out.println("[?]" + name);
						String familyname = user.asObject().getString("familyname", "NoFamName");
						//System.out.println("[?]" + familyname);
						String address = user.asObject().getString("address", "NoAddress");
						//System.out.println("[?]" + address);
						String city = user.asObject().getString("city", "noCity");
						//System.out.println("[?]" + city);
						String cap = user.asObject().getString("CAP", "noCap");
						//System.out.println("[?]" + cap);
						String mobilenumber = user.asObject().getString("mobilenumber","errore");
						//System.out.println("[?]" + mobilenumber);
						int userid = user.asObject().getInt("userid", -1);
						//System.out.println("[?]" + userid);
						User u = new User(email,password,name,familyname,address,city,cap,mobilenumber,userid);
						users.add(u);

						//System.out.println(user);
						System.out.println("[✓] Loaded user " + u.getAnagrafica().getName() + " " + u.getAnagrafica().getFamilyName());
						}

			

				}catch(IOException e){
					System.out.println("[x] Errore I/O nel caricamento utenti");
				}
				
				return users;
	}
	
	
	
	
	static public HashSet<Worker> loadWorkers() {
		
		System.out.println("[...] Loading workers...");
		HashSet<Worker> users = new HashSet<Worker>();
		//(try/catch è necessario causa operazione input output)

				//		 uso try/catch *with resource* (cioè con il reader dentro la parentesi)
				//		 perché è più bello (cit. Spoto)

				try(Reader reader= new FileReader("./data/workers.json")){

					JsonArray fileWorkers = Json.parse(reader).asObject().get("workers").asArray();

					for(JsonValue w : fileWorkers) {
						//System.out.println(w); 	
						String email = w.asObject().getString("email", "NoEmail");
						//System.out.println("[?]" + email);
						String password = w.asObject().getString("password", "NoPassword");
						//System.out.println("[?]" + password);
						String name = w.asObject().getString("name", "NoName");
						//System.out.println("[?]" + name);
						String familyname = w.asObject().getString("familyname", "NoFamName");
						//System.out.println("[?]" + familyname);
						String address = w.asObject().getString("address", "NoAddress");
						//System.out.println("[?]" + address);
						String city = w.asObject().getString("city", "noCity");
						//System.out.println("[?]" + city);
						String cap = w.asObject().getString("CAP", "noCap");
						//System.out.println("[?]" + cap);
						String mobilenumber = w.asObject().getString("mobilenumber","errore");
						//System.out.println("[?]" + mobilenumber);
						int workerid = w.asObject().getInt("workerid", -1);
						//System.out.println("[?]" + workerid);
						Worker worker = new Worker(email,password,name,familyname,address,city,cap,mobilenumber,workerid);
						users.add(worker);

						System.out.println("[✓] Loaded worker " + worker.getAnagrafica().getName() + " " + worker.getAnagrafica().getFamilyName());
					}

			

				}catch(IOException e){
					System.out.println("[x] Errore I/O nel caricamento utenti");
				}
				
				return users;
	}
	
}



