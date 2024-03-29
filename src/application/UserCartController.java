package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import data.Cart;
import data.Order;
import data.Payment;
import data.Product;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * Gestisce le parti "esterne" (ovvero, tutto tranne i prodotti) di un carrello.
 *
 */
public class UserCartController extends Controller {

	// Parametri FXML
	
	@FXML
	private AnchorPane successPane;

	// Info carrello
	@FXML
	private Label lblTotal;

	@FXML
	private Text txtPoints;

	@FXML
	private Label lblItems;

	@FXML
	private Text txtError;

	// Pagamento

	// Bottoni
	@FXML
	private JFXRadioButton radioConsegna;
	@FXML
	private JFXRadioButton radioPayPal;
	@FXML
	private JFXRadioButton radioCarta;

	// Pannelli Info

	// Paypal
	@FXML
	private AnchorPane panePayPal;
	@FXML
	private JFXTextField fieldPayPalEmail;
	@FXML
	private JFXPasswordField fieldPayPalPassword;

	// Carta
	@FXML
	private AnchorPane paneCarta;
	@FXML
	private JFXTextField fieldCartaNome;
	@FXML
	private JFXTextField fieldCartaCognome;
	@FXML
	private JFXTextField fieldCartaNumero;
	@FXML
	private JFXTextField fieldCartaCVV;

	// Alla consegna
	@FXML
	private AnchorPane paneConsegna;

	// Spedizione

	@FXML
	private JFXTextField fieldCAP;
	@FXML
	private JFXTextField fieldAddress;
	@FXML
	private JFXTextField fieldCity;

	// Display prodotti
	@FXML
	private AnchorPane cartviewPane;

	public void initialize() {

		successPane.setVisible(false);
		
		Globals.cartController = this;

		// Dati generali
		lblTotal.setText("€ " + String.format("%.2f", Globals.cart.getTotal()));
		txtPoints.setText("Questa spesa vale " + (int) Globals.cart.getTotal() + " punti!");
		lblItems.setText("" + Globals.cart.getNumberOfProd());
		txtError.setVisible(false);

		new ProductViewer(cartviewPane, Globals.cart);

		// Pagamento predefinito
		final ToggleGroup group = new ToggleGroup();
		radioConsegna.setToggleGroup(group);
		radioCarta.setToggleGroup(group);
		radioPayPal.setToggleGroup(group);

		User currUser = (User) Globals.currentUser;

		// System.out.println(currUser.getPaymentOrdinal());

		int payment = (currUser.getPaymentOrdinal());
		switch (payment) {
		case 1:
			radioPayPal.setSelected(true);
			fieldPayPalEmail.setText(currUser.getPayPalEmail());
			fieldPayPalPassword.setText(currUser.getPayPalPassword());
			break;
		case 2:
			radioCarta.setSelected(true);
			fieldCartaNome.setText(currUser.getCreditCardName());
			fieldCartaCognome.setText(currUser.getCreditCardFamilyName());
			fieldCartaNumero.setText("" + currUser.getCreditCardNumber());
			fieldCartaCVV.setText("" + currUser.getCreditCardCVV());
			break;
		default:
			radioConsegna.setSelected(true);
		}

		paneConsegna.setVisible(radioConsegna.isSelected());
		paneCarta.setVisible(radioCarta.isSelected());
		panePayPal.setVisible(radioPayPal.isSelected());

	}

	public void changePayment(ActionEvent ae) {
		paneConsegna.setVisible(radioConsegna.isSelected());
		paneCarta.setVisible(radioCarta.isSelected());
		panePayPal.setVisible(radioPayPal.isSelected());
	}

	// Crea un ordine dal carrello corrente; ovviamente fa tutte le verifiche
	// (validità dei campi E availability dei prodotti)
	public void makeOrder(ActionEvent ae) {

		User currUser = (User) Globals.currentUser;

		// Step 0: carrello non vuoto e valori validi
		if (checkParameters() == false) {
			return;
		}

		// Step 1: Controlliamo se tutti i prodotti sono ancora disponibili!
		if (checkIfEverythingAvailable() == false) {
			System.out.println("WARNING: products removed");
			txtError.setText(
					"Alcuni dei prodotti del carrello sono terminati e sono stati rimossi. Puoi riprovare ad eseguire l'acquisto con il tasto \"conferma\".");
			Globals.viewController.initialize();
			Globals.cartController.initialize();
			return;
		}

		// Step 2: generiamo un ordine con i prodotti del carrello e aggiungiamolo alla
		// lista
		Payment payment = data.Payment.CONSEGNA;
		String info = "Nessuna informazione aggiuntiva";

		if (radioPayPal.isSelected()) {
			payment = data.Payment.PAYPAL;
			info = "Account: " + fieldPayPalEmail.getText();
		}
		if (radioCarta.isSelected()) {
			payment = data.Payment.CARTA;
			info = "Carta: " + fieldCartaNumero.getText() + " (" + fieldCartaNome.getText() + " "
					+ fieldCartaCognome.getText() + " )";
		}

		// Aggiorno i punti dell'user

		System.out.println(((User) Globals.currentUser).getActualPoints() + "");
		System.out.println((int) Globals.cart.getTotal() + "");

		currUser.setActualPoints(((User) Globals.currentUser).getActualPoints() + (int) Globals.cart.getTotal());
		System.out.println("-->" + ((User) Globals.currentUser).getActualPoints() + "");

		Globals.currentOrder = new Order(Globals.cart, payment, info,
				fieldAddress.getText() + ", " + fieldCAP.getText() + ", " + fieldCity.getText());

		// Step 3: svuotiamo il carrello.
		Globals.cart.flushCart();

		JsonSaver.saveUser();

		// Step4: selezione giorno e ora
		launchUI("/application/UserDatePicker.fxml");
	}

	/***
	 * verifica se nel frattempo sono finiti alcuni prodotti. Torna vero se è tutto
	 * a posto, falso se ha dovuto rimuovere dei prodotti.
	 * 
	 * @return
	 */
	private boolean checkIfEverythingAvailable() {
		boolean isStillAvailable = true;

		// debug
		// System.out.println("OLD: " + Globals.cart.getProducts());

		// Aggiorno elenco prodotti
		JsonLoader.loadProducts();

		// debug
		// for(Product p : Globals.cart.getProducts().keySet())
		// System.out.println("NEW: " +Globals.barCodeTable.get(p.getBarCode()));

		// ATTENZIONE!!
		// Dato che ho ricaricato i prodotti daccapo, adesso gli oggetti salvati nel
		// carrello sono
		// prodotti SEPARATI da quelli presenti nei reparti (oh noes!!)
		// Mi riconduco ai prodotti nei reparti usando la mitica barCodeTable

		// Devo lavorare su una lista separata perché java è stupido e non ti fa
		// eliminare le cose se usi iterator >:(
		Cart tmpcart = new Cart();

		for (Integer oldProduct : Globals.cart.getProducts().keySet()) {

			// E' la versione "aggiornata" del prodotto
			Product newProduct = Globals.barCodeTable.get(oldProduct);

			// debug
			// System.out.println("cart="+ Globals.cart.getProducts().get(oldProduct) +"
			// available="+newProduct.getAvailable());

			// Se il prodotto non è disponibile abbasso il flag
			if (Globals.cart.getProducts().get(oldProduct) > newProduct.getAvailable()) {
				// System.out.println("[x] "+ Globals.barCodeTable.get(oldProduct).getName()+"
				// is not available anymore!! ");
				// Globals.cart.removeProduct(p);
				isStillAvailable = false;
			}

			// Se il prodotto è disponibile lo aggiungo alla versione "finale" del carrello.
			// (dato che java è stupido e non lo posso togliere.)
			// Inoltre, sottraggo la quantità richiesta dalla lista di product (datoc he
			// come già detto in questo momento i product
			// del carrello non coincidono con quelli dei reparti.
			else {
				tmpcart.addProduct(newProduct, Globals.cart.getProducts().get(oldProduct));
				newProduct.setAvailable(newProduct.getAvailable() - Globals.cart.getProducts().get(oldProduct));
			}
		}

		// System.out.println(tmpcart);
		Globals.cart = tmpcart;

		return isStillAvailable;
	}

	private boolean checkParameters() {

		boolean result = true;

		// 1. Carrello non vuoto
		if (Globals.cart.getProducts().isEmpty() == true) {
			txtError.setText("Errore: il carrello non può essere vuoto!");
			txtError.setVisible(true);
			return false;
		}

		// Controllo 2: pagamento
		boolean valid = true;

		// Paypal
		if (radioPayPal.isSelected())
			if (fieldPayPalEmail.getText().isEmpty() || fieldPayPalPassword.getText().isEmpty())
				valid = false;

		// Carta
		if (radioCarta.isSelected())
			if (fieldCartaNumero.getText().isEmpty() || fieldCartaCVV.getText().isEmpty()
					|| fieldCartaNome.getText().isEmpty() || fieldCartaCognome.getText().isEmpty())
				valid = false;

		if (valid == false) {
			txtError.setText("ATTENZIONE: è necessario compilare tutti i campi!");
			txtError.setVisible(true);
			return valid;
		}

		// Verifico i numeri
		try {
			Integer.parseInt(fieldCAP.getText());

			// Carta
			if (radioCarta.isSelected()) {
				// System.out.println(radioCarta.isSelected());
				Long.parseLong(fieldCartaNumero.getText());
				Integer.parseInt(fieldCartaCVV.getText());
			}

		} catch (NumberFormatException e) {
			valid = false;
			txtError.setText("ATTENZIONE: dati non validi!");
			e.printStackTrace();
			txtError.setVisible(true);
			return valid;
		}

		// 3. Indirizzo impostato
		result &= !fieldAddress.getText().equals("");
		result &= !fieldCAP.getText().equals("");
		result &= !fieldCity.getText().equals("");
		if (result == false) {
			txtError.setText("Errore: L'indirizzo non è valido!");
			txtError.setVisible(true);
			return false;
		}
		return result;
	}

	public void orderConfirmed() {
		txtError.setVisible(true);
		JsonSaver.saveProducts();
		txtError.setText("L'ordine è stato confermato!");
	
		
		successPane.setVisible(true);

	}
}
