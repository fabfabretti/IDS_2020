package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;

import data.CartDraft;
import data.Product;
import data.Section;
import data.User;
import data.Worker;
import data.Order;
import minimalJson.JsonArray;
import minimalJson.JsonObject;
import minimalJson.WriterConfig;

/**
 * Il contenuto, per comodità implementativa, viene completamente riscritto nei
 * vari json file.
 * 
 * Tale scelta è stata apportata a posteri rispetto all'implementazione di
 * JsonLoader. La libreria minimalJson, infatti, non permette una facile
 * gestione dei cambi dei singoli valori come nel nostro caso.
 */
public class JsonSaver {

	/**
	 * La funzione salva eventuali modifiche relative ai dati degli utenti se queste
	 * modifiche vengono opportunamente confermate.
	 */
	public static void saveUser() {
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

	/**
	 * La funzione salva eventuali modifiche relative ai dati dei dipendenti se
	 * queste modifiche vengono opportunamente confermate.
	 */
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

	/**
	 * La funzione salva eventuali modifiche relative ai dati dei prodotti se queste
	 * modifiche vengono opportunamente confermate.
	 */
	public static void saveProducts() {

		JsonArray /* product = new JsonArray(), */ productTmp = new JsonArray();

		JsonObject newJson = new JsonObject();

		for (Section s : Globals.reparti) {
			for (Product p : s.getProducts()) {

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
			newJson.add(s.getName(), productTmp);

			productTmp = new JsonArray();
		}
		// System.out.println("\n[?] " + product);

		/**
		 * Trascrizione su file del nuovo JsonObj creato con relativo try/catch.
		 */
		try (Writer writer = new FileWriter("./data/product.json")) {
			newJson.writeTo(writer, WriterConfig.PRETTY_PRINT);
		} catch (IOException e) {
			System.out.println("[x] Errore scrittura Json Product!!!");
		}

	}

	/**
	 * La funzione salva il carrello (se avente prodotti) quando viene terminata
	 * l'applicazione (o si esegue un logout) senza aver concluso l'acquisto.
	 */
	public static void saveCart() {
		/*
		 * Array che conterrà l'intero Json dei carrelli in stato di bozza.
		 */
		JsonArray cartsJson = new JsonArray();

		/*
		 * Singolo carrello temporaneo (usato per formare gli ordini in stallo).
		 */
		JsonObject singleCart = new JsonObject();

		/*
		 * Array che conterrà varie instanze di oggetti (barCode - quantity).
		 */
		JsonArray products = new JsonArray();

		/*
		 * Utile per ottenere informazioni sull'user
		 */
		User actualUser = (User) Globals.currentUser;

		// (Eventuali) Carrelli delle sessioni precedenti.

		System.out.println("\nSto salvando eventuali carrelli in draft!\n");

		for (CartDraft c : Globals.drafts) {
			singleCart = new JsonObject();
			products = new JsonArray();

			singleCart.add("userID", c.getUserID());

			for (Entry<Integer, Integer> p : c.getProducts().entrySet()) {
				JsonObject jsonProduct = new JsonObject();

				jsonProduct.add("barCode", p.getKey());
				jsonProduct.add("quantity", p.getValue());

				products.add(jsonProduct);
			}

			singleCart.add("products", products);

			cartsJson.add(singleCart);
		}

		singleCart = new JsonObject();
		products = new JsonArray();

		// Carrello della sessione attuale

		System.out.println("\nSto salvando il carrello attuale in draft!\n");

		singleCart.add("userID", actualUser.getUserID());

		// Scorrimento prodotti del carrello e inserimento nell'arrai "products".
		for (Entry<Integer, Integer> p : Globals.cart.getProducts().entrySet()) {
			JsonObject jsonProduct = new JsonObject();

			jsonProduct.add("barCode", p.getKey());
			jsonProduct.add("quantity", p.getValue());

			products.add(jsonProduct);
		}

		singleCart.add("products", products);

		cartsJson.add(singleCart);

		JsonObject newJson = new JsonObject();

		newJson.add("carrelli non confermati", cartsJson);

		System.out.println("\n[?] " + cartsJson);

		/**
		 * Trascrizione su file del nuovo JsonObj creato con relativo try/catch.
		 */
		try (Writer writer = new FileWriter("./data/cartDrafts.json")) {
			newJson.writeTo(writer, WriterConfig.PRETTY_PRINT);
		} catch (IOException e) {
			System.out.println("[x] Errore scrittura Json Draft!!!");
		}

	}

	/**
	 * La funzione salva l'ordine eseguito dall'utente aggiungendolo allo storico
	 * degli ordini del medesimo utente (Gloabls->storico).
	 */
	public static void savePurchaseHistory() {
		/*
		 * Array che conterrà l'intero Json degli ordini.
		 */
		JsonArray historyJson = new JsonArray();

		/*
		 * Singolo ordine temporaneo (usato per formare gli ordini in stallo).
		 */
		JsonObject singleOrder = new JsonObject();

		/*
		 * Array che conterrà varie instanze di oggetti.
		 */
		JsonArray products = new JsonArray();


		// (Eventuali) Ordini delle sessioni precedenti.

		for (Order o : Globals.storico) {
			singleOrder = new JsonObject();
			products = new JsonArray();

			// Salvataggio informazioni ordine

			singleOrder.add("orderID", o.getOrderid());
			singleOrder.add("userID", o.getUser().getUserID());
			singleOrder.add("stateOrdinal", o.getState().ordinal());
			singleOrder.add("deliveryDate", o.getDate().toString());
			singleOrder.add("deliveryTimeOrdinal", o.getTime().ordinal());
			singleOrder.add("totalAmount", o.getCart().getTotal());
			singleOrder.add("paymentInfo", o.getPaymentInfo());
			singleOrder.add("paymentOrdinal", o.getPayment().ordinal());
			singleOrder.add("address", o.getAddress());
			// Salvataggio prodotti
			for (Entry<Integer, Integer> p : o.getCart().getProducts().entrySet()) {
				JsonObject jsonProduct = new JsonObject();

				jsonProduct.add("barCode", p.getKey());
				jsonProduct.add("quantity", p.getValue());

			}

			singleOrder.add("products", products);

			historyJson.add(singleOrder);
		}
/*
		singleOrder = new JsonObject();
		products = new JsonArray();

		// Carrello della sessione attuale

		singleOrder.add("userID", actualUser.getUserID());
		singleOrder.add("orderID", Globals.currentOrder.getOrderid());
		singleOrder.add("stateOrdinal", Globals.currentOrder.getState().ordinal());
		singleOrder.add("deliveryDate", Globals.currentOrder.getDate().toString());
		singleOrder.add("deliveryTimeOrdinal", Globals.currentOrder.getTime().ordinal());
		singleOrder.add("totalAmount", Globals.currentOrder.getCart().getTotal());
		singleOrder.add("paymentInfo", Globals.currentOrder.getPaymentInfo());
		singleOrder.add("paymentOrdinal", Globals.currentOrder.getPayment().ordinal());

		// Scorrimento prodotti del carrello e inserimento nell'arrai "products".
		for (Entry<Integer, Integer> p : Globals.cart.getProducts().entrySet()) {
			JsonObject jsonProduct = new JsonObject();

			jsonProduct.add("barCode", p.getKey());
			jsonProduct.add("quantity", p.getValue());

			products.add(jsonProduct);
		}

		singleOrder.add("products", products);

		historyJson.add(singleOrder);
*/
		JsonObject newJson = new JsonObject();

		newJson.add("storico ordini", historyJson);

		System.out.println("\n[?] " + historyJson);

		/**
		 * Trascrizione su file del nuovo JsonObj creato con relativo try/catch.
		 */
		try (Writer writer = new FileWriter("./data/purchaseHistory.json")) {
			newJson.writeTo(writer, WriterConfig.PRETTY_PRINT);
		} catch (IOException e) {
			System.out.println("[x] Errore scrittura Json History!!!");
		}

	}

}
