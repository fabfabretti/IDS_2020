package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;

import data.Product;
import data.User;
import data.Worker;

import json.*;
import json.JsonArray;
import json.JsonValue;

//javax.json

public class JsonSaver {

	 public static void saveUser() {

		/**
		 * JsonObject user = Json.object().add("name", "Alice").add("points", 23); // ->
		 * {"name": "Alice", "points": 23}
		 */

		JsonArray users = new JsonArray();

		for (User u : Globals.users) {
			
			JsonObject jsonUser = new JsonObject().add("email", u.getEmail());
			jsonUser.add("password", u.getPassword());

			jsonUser.add("name", u.getAnagrafica().getName());
			jsonUser.add("familyname", u.getAnagrafica().getFamilyName());

			jsonUser.add("address", u.getAnagrafica().getAddress());
			jsonUser.add("city", u.getAnagrafica().getCity());
			jsonUser.add("CAP", u.getAnagrafica().getCAP());
			jsonUser.add("mobilenumber", u.getAnagrafica().getMobileNumber());
			jsonUser.add("fidelitycard", "");
			jsonUser.add("userid", u.getUserID());
			
			users.add(jsonUser);
			System.out.println(jsonUser);
		}
		

		
		System.out.println("[?]" + users);
		/*
		 * "email": "a", "password": "a", "name":"User", "familyname":"Debugging",
		 * "address":"Via bestemmie, 666", "city":"Quel Paese", "CAP":66420,
		 * "mobilenumber": "4136181111", "fidelitycard":"null", "userid":0
		 */

		
		try(Writer writer = new FileWriter("./data/users.json")) {
			String json = users.toString(WriterConfig.PRETTY_PRINT);
			users.writeTo(writer, WriterConfig.PRETTY_PRINT);
		} catch (IOException e) {
			System.out.println("[x] Errore scrittura Json User!!!");
		}
	}

	public void saveWorker(Worker u) {

	}

}
