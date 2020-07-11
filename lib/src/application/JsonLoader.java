package application;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import data.Cart;
import data.CartDraft;
import data.Order;
import data.OrderDeliveryState;
import data.OrderDeliveryTime;
import data.Payment;
import data.Product;
import data.Section;
import data.User;
import data.Worker;
import minimalJson.Json;
import minimalJson.JsonArray;
import minimalJson.JsonObject;
import minimalJson.JsonValue;

public class JsonLoader {

	/*
	 * La funzione carica i prodotti in memoria (Globals -> product).
	 */
	static void loadProducts() {

		// Se è un aggiornamento, devo prima svuotare il reparto.
		for (Section s : Globals.reparti)
			if (!(s.getProducts().isEmpty()))
				s.getProducts().clear();

		// (try/catch è necessario causa operazione input output)

		// uso try/catch *with resource* (cioè con il reader dentro la parentesi)
		// perché è più bello (cit. Spoto)

		try (Reader reader = new FileReader("./data/product.json")) {

			JsonObject read = Json.parse(reader).asObject();

			for (Section s : Globals.reparti) {

				s.getProducts().clear();
				JsonArray sezione = read.get(s.getName()).asArray();

				for (JsonValue prodotto : sezione) {
					// System.out.println(prodotto);
					/*
					 * [! ! ! ! ! ] |-----> Da questa print di debug si evince che ciascun JsonValue
					 * (ovvero ciascun elemento di ciascun indice del JsonArray)è una schifosissima
					 * STRINGA con dentro tutta la roba del nostro oggetto (assolutamente non
					 * parsata, ripeto: è una schifosissima stringa)
					 * 
					 * Get asObject lo rende una cosa papabile (perché la schifosissima stringa non
					 * gli piace) su cui si riesce a parse-are la roba
					 * 
					 * Le funzioni getString/getInt/etc... traducono il Json Object nei suoi valori
					 * e sono sostanzialmente come una hashmap ("dammi la key e io ti do la value")
					 * 
					 * Il primo valore che si passa è la key, mentre il secondo è il valore di
					 * controllo (Aka se ci ritorna quella roba lì significa che non ha trovato
					 * l'elemento
					 */

					String name = prodotto.asObject().getString("name", "Unknown prodotto");

					int barCode = prodotto.asObject().getInt("barCode", -1);

					String imagePath = prodotto.asObject().getString("imagePath", "Unknown image");

					String brand = prodotto.asObject().getString("brand", "Unknown image");

					float weight = prodotto.asObject().getFloat("weight", (float) -1.0);

					String unit = prodotto.asObject().getString("unit", "Unknown unit");

					float price = prodotto.asObject().getFloat("price", (float) -1.0);

					int available = prodotto.asObject().getInt("available", -1);

					boolean bio = prodotto.asObject().getBoolean("bio", false);
					boolean glutenfree = prodotto.asObject().getBoolean("glutenfree", false);
					boolean vegan = prodotto.asObject().getBoolean("vegan", false);
					boolean lactosefree = prodotto.asObject().getBoolean("lactosefree", false);

					new Product(name, barCode, imagePath, brand, weight, unit, price, available, bio, glutenfree, vegan,
							lactosefree, s);
					Globals.computeTable();

				}
			}

		} catch (IOException e) {
			System.out.println("Errore I/O");
			e.printStackTrace();

		}

	}

	/*
	 * La funzione carica i profili degli user (Globals -> users)
	 */
	static HashSet<User> loadUsers() {

		System.out.println("[...] Loading users...");
		HashSet<User> users = new HashSet<User>();
		// (try/catch è necessario causa operazione input output)

		// uso try/catch *with resource* (cioè con il reader dentro la parentesi)
		// perché è più bello (cit. Spoto)

		try (Reader reader = new FileReader("./data/users.json")) {

			JsonArray fileUtenti = Json.parse(reader).asObject().get("users").asArray();

			for (JsonValue user : fileUtenti) {
				// System.out.println(user);
				String email = user.asObject().getString("email", "NoEmail");
				// System.out.println("[?]" + email);
				String password = user.asObject().getString("password", "NoPassword");
				// System.out.println("[?]" + password);
				String name = user.asObject().getString("name", "NoName");
				// System.out.println("[?]" + name);
				String familyname = user.asObject().getString("familyname", "NoFamName");
				// System.out.println("[?]" + familyname);
				String address = user.asObject().getString("address", "NoAddress");
				// System.out.println("[?]" + address);
				String city = user.asObject().getString("city", "noCity");
				// System.out.println("[?]" + city);
				String cap = user.asObject().getString("CAP", "noCap");
				// System.out.println("[?]" + cap);
				String mobilenumber = user.asObject().getString("mobilenumber", "errore");
				// System.out.println("[?]" + mobilenumber);
				int userid = user.asObject().getInt("userid", -1);
				// System.out.println("[?]" + userid);
				User u = new User(email, password, name, familyname, address, city, cap, mobilenumber, userid);
				users.add(u);

				// System.out.println(user);
				// System.out.println(
				// "\n[✓] Loaded user " + u.getAnagrafica().getName() + " " +
				// u.getAnagrafica().getFamilyName());
			}

		} catch (IOException e) {
			System.out.println("[x] Errore I/O nel caricamento utenti");
		}

		return users;
	}

	/*
	 * La funzione carica i profili dei worker (Globals -> workers)
	 */
	static public HashSet<Worker> loadWorkers() {

		System.out.println("[...] Loading workers...");
		HashSet<Worker> users = new HashSet<Worker>();
		// (try/catch è necessario causa operazione input output)

		// uso try/catch *with resource* (cioè con il reader dentro la parentesi)
		// perché è più bello (cit. Spoto)

		try (Reader reader = new FileReader("./data/workers.json")) {

			JsonArray fileWorkers = Json.parse(reader).asObject().get("workers").asArray();

			for (JsonValue w : fileWorkers) {
				// System.out.println(w);
				String email = w.asObject().getString("email", "NoEmail");
				// System.out.println("[?]" + email);
				String password = w.asObject().getString("password", "NoPassword");
				// System.out.println("[?]" + password);
				String name = w.asObject().getString("name", "NoName");
				// System.out.println("[?]" + name);
				String familyname = w.asObject().getString("familyname", "NoFamName");
				// System.out.println("[?]" + familyname);
				String address = w.asObject().getString("address", "NoAddress");
				// System.out.println("[?]" + address);
				String city = w.asObject().getString("city", "noCity");
				// System.out.println("[?]" + city);
				String cap = w.asObject().getString("CAP", "noCap");
				// System.out.println("[?]" + cap);
				String mobilenumber = w.asObject().getString("mobilenumber", "errore");
				// System.out.println("[?]" + mobilenumber);
				int workerid = w.asObject().getInt("workerid", -1);
				// System.out.println("[?]" + workerid);
				Worker worker = new Worker(email, password, name, familyname, address, city, cap, mobilenumber,
						workerid);
				users.add(worker);

				// System.out.println("[✓] Loaded worker " + worker.getAnagrafica().getName() +
				// " "
				// + worker.getAnagrafica().getFamilyName());
			}

		} catch (IOException e) {
			System.out.println("[x] Errore I/O nel caricamento utenti");
		}

		return users;
	}

	/*
	 * La funzione carica i draft dei carrelli non terminati (Globals -> drafts).
	 * 
	 * La funzione draftToCart (vedi CartDraft) carica un eventuale carrello
	 * lasciato in sospeso all'utente di appartenenza
	 */
	static public ArrayList<CartDraft> loadDrafts() {

		System.out.println("[...] Loading drafts...");

		ArrayList<CartDraft> drafts = new ArrayList<CartDraft>();

		try (Reader reader = new FileReader("./data/cartDrafts.json")) {

			JsonArray fileDrafts = Json.parse(reader).asObject().get("carrelli non confermati").asArray();

			for (JsonValue d : fileDrafts) {

				CartDraft tmp = new CartDraft();

				tmp.setUserID(d.asObject().getInt("userID", -1));

				int barCode;
				int quantity;
				
				for (JsonValue p : d.asObject().get("products").asArray()) {
					// int barCode = d.asObject().getInt("barCode", -1);
					barCode = p.asObject().getInt("barCode", -1);
					quantity = p.asObject().getInt("quantity", 0);

					tmp.addProduct(barCode, quantity);
				}
				drafts.add(tmp);
			}
		} catch (IOException e) {
			System.out.println("[x] Errore I/O nel caricamento draft");
		}

		return drafts;
	}

	/*
	 * La funzione carica lo storico degli ordini eseguiti dal medesimo utente
	 * (Globals->storico).
	 */
	static public ArrayList<Order> loadHistory() {
		Globals.computeTable();

		System.out.println("[...] Loading purchase history...");

		ArrayList<Order> storico = new ArrayList<Order>();

		try (Reader reader = new FileReader("./data/purchaseHistory.json")) {
			JsonArray fileHistory = Json.parse(reader).asObject().get("storico ordini").asArray();
			
			//Variabili necessarie:
				int userID;
				int orderID;
				OrderDeliveryState deliveryState;
				LocalDate deliveryDate;
				OrderDeliveryTime deliveryTime;
				float totalAmount ;
				String paymentInfo;
				Payment payment;

			for (JsonValue d : fileHistory) {
				
				//IDs
				userID = d.asObject().getInt("userID", -1);
				User user= null;
				
				for(User u : Globals.users) {
					if(u.getUserID()==userID)
						user=u;
				}

				orderID = d.asObject().getInt("orderID", -1);

				//State&Time
				deliveryState = OrderDeliveryState.values()[d.asObject().getInt("stateOrdinal", -1)];

				deliveryDate = LocalDate.parse(d.asObject().getString("deliveryDate", null));

				deliveryTime = OrderDeliveryTime.values()[d.asObject().getInt("deliveryTimeOrdinal", -1)];
				
				//Total
				totalAmount= d.asObject().getFloat("totalAmount", 0);
				
				//Payment
				paymentInfo = d.asObject().getString("paymentInfo", null);
				payment = Payment.values()[d.asObject().getInt("paymentOrdinal", -1)];

				//Address
				String address = d.asObject().getString("address", null);

				Cart newCart = new Cart();

				HashMap<Integer, Integer> tmpProductsSet = new HashMap<Integer, Integer>();
				HashMap<Integer, Float> tmpPricesSet = new HashMap<Integer, Float>();

				for (JsonValue p : d.asObject().get("products").asArray()) {

					int barCode = p.asObject().getInt("barCode", -1);
					int quantity = p.asObject().getInt("quantity", 0);
					int price = p.asObject().getInt("price", -1);
					

					tmpProductsSet.put(barCode, quantity);
					tmpProductsSet.put(barCode, price);
				}

				newCart.setProducts(tmpProductsSet);

				Order singleOrder = new Order(newCart, payment, paymentInfo, address,deliveryDate,deliveryTime,user,tmpPricesSet);
				Globals.storico.add(singleOrder);
				storico.add(singleOrder);
			}
		} catch (IOException e) {
			System.out.println("[x] Errore I/O nel caricamento storico acquisti");
		}

		return storico;

	}

}
