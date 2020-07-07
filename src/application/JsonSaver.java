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
import minimalJson.*;

public class JsonSaver {

	public static void saveUser() {

		/**
		 * Il contenuto, per comodità implementativa, viene completamente riscritto nei
		 * vari json file.
		 * 
		 * Tale scelta è stata apportata a posteri rispetto all'implementazione di
		 * JsonLoader. La libreria minimalJson, infatti, non permette una facile
		 * gestione dei cambi dei singoli valori come nel nostro caso.
		 */
		JsonArray users = new JsonArray();

		for (User u : Globals.users) {

			JsonObject jsonUser = new JsonObject();

			jsonUser.add("email", u.getEmail());

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

		/**
		 * Una volta aggiornati i dati, questi vengono inseriti all'interno di un nuovo
		 * JsonObject e trascritti sul file.
		 */
		JsonObject newJson = new JsonObject();

		newJson.add("users", users);

		System.out.println("\n[?] " + users);

		/**
		 * Trascrizione su file del nuovo JsonObj creato con relativo try/catch.
		 */
		try (Writer writer = new FileWriter("./data/users.json")) {
			newJson.writeTo(writer, WriterConfig.PRETTY_PRINT);
		} catch (IOException e) {
			System.out.println("[x] Errore scrittura Json User!!!");
		}
	}


	// TODO: Fare test quando gui è implementata
	public static void saveWorker() {
		JsonArray worker = new JsonArray();

		for (Worker w : Globals.workers) {

			JsonObject jsonWorker = new JsonObject();

			jsonWorker.add("email", w.getEmail());
			jsonWorker.add("password", w.getPassword());
			jsonWorker.add("name", w.getName());
			jsonWorker.add("familyname", w.getAnagrafica().getFamilyName());
			jsonWorker.add("address", w.getAnagrafica().getAddress());
			jsonWorker.add("city", w.getAnagrafica().getCity());
			jsonWorker.add("CAP", w.getAnagrafica().getCAP());
			jsonWorker.add("mobilenumber", w.getAnagrafica().getMobileNumber());
			jsonWorker.add("fideltycard", "null");
			jsonWorker.add("familyname", w.getWorkerID());

			worker.add(jsonWorker);

			System.out.println(jsonWorker);
		}

		JsonObject newJson = new JsonObject();

		newJson.add("users", worker);

		System.out.println("\n[?] " + worker);

		/**
		 * Trascrizione su file del nuovo JsonObj creato con relativo try/catch.
		 */
		try (Writer writer = new FileWriter("./data/workers.json")) {
			newJson.writeTo(writer, WriterConfig.PRETTY_PRINT);
		} catch (IOException e) {
			System.out.println("[x] Errore scrittura Json Worker!!!");
		}
	
	}

}
