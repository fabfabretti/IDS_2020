package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;

import data.Product;
import data.Section;
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

	public static void saveWorker() {
		JsonArray worker = new JsonArray();

		for (Worker w : Globals.workers) {

			JsonObject jsonWorker = new JsonObject();

			jsonWorker.add("email", w.getEmail());
			jsonWorker.add("password", w.getPassword());
			jsonWorker.add("name", w.getAnagrafica().getName());
			jsonWorker.add("familyname", w.getAnagrafica().getFamilyName());
			jsonWorker.add("address", w.getAnagrafica().getAddress());
			jsonWorker.add("city", w.getAnagrafica().getCity());
			jsonWorker.add("CAP", w.getAnagrafica().getCAP());
			jsonWorker.add("mobilenumber", w.getAnagrafica().getMobileNumber());
			jsonWorker.add("workerid", w.getWorkerID());

			worker.add(jsonWorker);

			System.out.println(jsonWorker);
		}

		JsonObject newJson = new JsonObject();

		newJson.add("workers", worker);

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

	public static void saveProducts() {

		JsonArray product = new JsonArray(), productTmp = new JsonArray();

		JsonObject newJson = new JsonObject();

		/*
		 * BEVANDE
		 */
		for (Product p : Globals.bevande.getProducts()) {

			JsonObject jsonProduct = new JsonObject();

			jsonProduct.add("name", p.getName());
			jsonProduct.add("barCode", p.getBarCode());
			jsonProduct.add("imagePath", p.getImagePath());
			jsonProduct.add("brand", p.getBrand());
			jsonProduct.add("weight", p.getWeight());
			jsonProduct.add("unit", p.getUnit());
			jsonProduct.add("price", p.getPrice());
			jsonProduct.add("weightPrice", p.getWeightPrice());
			jsonProduct.add("available", p.getAvailable());
			jsonProduct.add("bio", p.isChar("bio")); 
			jsonProduct.add("glutenfree", p.isChar("gluten"));
			jsonProduct.add("vegan", p.isChar("vegan")); 
			jsonProduct.add("lactosefree", p.isChar("diary"));

			productTmp.add(jsonProduct);

			System.out.println("\n[?] " + productTmp);
		}
		newJson.add(Globals.bevande.getName(), productTmp);

		productTmp = new JsonArray();

		/*
		 * CARNE
		 */
		for (Product p : Globals.carne.getProducts()) {

			JsonObject jsonProduct = new JsonObject();

			jsonProduct.add("name", p.getName());
			jsonProduct.add("barCode", p.getBarCode());
			jsonProduct.add("imagePath", p.getImagePath());
			jsonProduct.add("brand", p.getBrand());
			jsonProduct.add("weight", p.getWeight());
			jsonProduct.add("unit", p.getUnit());
			jsonProduct.add("price", p.getPrice());
			jsonProduct.add("weightPrice", p.getWeightPrice());
			jsonProduct.add("available", p.getAvailable());
			jsonProduct.add("bio", p.isChar("bio")); 
			jsonProduct.add("glutenfree", p.isChar("gluten"));
			jsonProduct.add("vegan", p.isChar("vegan")); 
			jsonProduct.add("lactosefree", p.isChar("diary"));

			productTmp.add(jsonProduct);

			System.out.println("\n[?] " + productTmp);
		}
		newJson.add(Globals.carne.getName(), productTmp);

		productTmp = new JsonArray();

		/*
		 * PESCE
		 */
		for (Product p : Globals.pesce.getProducts()) {

			JsonObject jsonProduct = new JsonObject();

			jsonProduct.add("name", p.getName());
			jsonProduct.add("barCode", p.getBarCode());
			jsonProduct.add("imagePath", p.getImagePath());
			jsonProduct.add("brand", p.getBrand());
			jsonProduct.add("weight", p.getWeight());
			jsonProduct.add("unit", p.getUnit());
			jsonProduct.add("price", p.getPrice());
			jsonProduct.add("weightPrice", p.getWeightPrice());
			jsonProduct.add("available", p.getAvailable());
			jsonProduct.add("bio", p.isChar("bio")); 
			jsonProduct.add("glutenfree", p.isChar("gluten"));
			jsonProduct.add("vegan", p.isChar("vegan")); 
			jsonProduct.add("lactosefree", p.isChar("diary"));

			productTmp.add(jsonProduct);

			System.out.println("\n[?] " + productTmp);
		}
		newJson.add(Globals.pesce.getName(), productTmp);

		productTmp = new JsonArray();

		/*
		 * FRUTTA E VERDURA
		 */
		for (Product p : Globals.vegetali.getProducts()) {

			JsonObject jsonProduct = new JsonObject();

			jsonProduct.add("name", p.getName());
			jsonProduct.add("barCode", p.getBarCode());
			jsonProduct.add("imagePath", p.getImagePath());
			jsonProduct.add("brand", p.getBrand());
			jsonProduct.add("weight", p.getWeight());
			jsonProduct.add("unit", p.getUnit());
			jsonProduct.add("price", p.getPrice());
			jsonProduct.add("weightPrice", p.getWeightPrice());
			jsonProduct.add("available", p.getAvailable());
			jsonProduct.add("bio", p.isChar("bio")); 
			jsonProduct.add("glutenfree", p.isChar("gluten"));
			jsonProduct.add("vegan", p.isChar("vegan")); 
			jsonProduct.add("lactosefree", p.isChar("diary"));

			productTmp.add(jsonProduct);

			System.out.println("\n[?] " + productTmp);
		}
		newJson.add(Globals.vegetali.getName(), productTmp);

		productTmp = new JsonArray();

		/*
		 * LATTICINI
		 */
		for (Product p : Globals.latticini.getProducts()) {

			JsonObject jsonProduct = new JsonObject();

			jsonProduct.add("name", p.getName());
			jsonProduct.add("barCode", p.getBarCode());
			jsonProduct.add("imagePath", p.getImagePath());
			jsonProduct.add("brand", p.getBrand());
			jsonProduct.add("weight", p.getWeight());
			jsonProduct.add("unit", p.getUnit());
			jsonProduct.add("price", p.getPrice());
			jsonProduct.add("weightPrice", p.getWeightPrice());
			jsonProduct.add("available", p.getAvailable());
			jsonProduct.add("bio", p.isChar("bio")); 
			jsonProduct.add("glutenfree", p.isChar("gluten"));
			jsonProduct.add("vegan", p.isChar("vegan")); 
			jsonProduct.add("lactosefree", p.isChar("diary"));
		 

			productTmp.add(jsonProduct);

			System.out.println("\n[?] " + productTmp);
		}
		newJson.add(Globals.latticini.getName(), productTmp);

		
		// System.out.println("\n[?] " + product);

		/**
		 * Trascrizione su file del nuovo JsonObj creato con relativo try/catch.
		 */
		try (Writer writer = new FileWriter("./data/product.json")) {
			newJson.writeTo(writer, WriterConfig.PRETTY_PRINT);
		} catch (IOException e) {
			System.out.println("[x] Errore scrittura Json Worker!!!");
		}

	}

}
