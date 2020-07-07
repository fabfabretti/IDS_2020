package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import data.Order;
import data.Payment;
import data.Product;
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
public class UserCartController extends Controller{

	
	//Parametri FXML
	
	//Info carrello	
		@FXML
		private  Label lblTotal;
		
		@FXML
		private  Text txtPoints;

		@FXML
		private  Label lblItems;
		
		@FXML
		private  Text txtError;
		
	//Pagamento
		
		//Bottoni
		@FXML
		private  JFXRadioButton radioConsegna;
		@FXML
		private  JFXRadioButton radioPayPal;
		@FXML
		private  JFXRadioButton radioCarta;
		
		//Pannelli Info
		
			//Paypal
			@FXML
			private  AnchorPane panePayPal;
			@FXML
			private JFXTextField fieldPayPalEmail;
			@FXML
			private JFXPasswordField fieldPayPalPassword;
			
			//Carta
			@FXML
			private  AnchorPane paneCarta;
			@FXML
			private JFXTextField fieldCartaNome;
			@FXML
			private JFXTextField fieldCartaCognome;
			@FXML
			private JFXTextField fieldCartaNumero;
			@FXML
			private JFXTextField fieldCartaCVV;
			
			//Alla consegna
			@FXML
			private AnchorPane paneConsegna;

	//Spedizione

		@FXML
		private JFXTextField fieldCAP;
		@FXML
		private JFXTextField fieldAddress;
		@FXML
		private JFXTextField fieldCity;
		
	// Display prodotti
		@FXML
		private AnchorPane cartviewPane;
	
	
		
	//Date Picker
	
	
	public void initialize() {
		
		Globals.cartController=this;
		lblTotal.setText("€ "+ String.format("%.2f", Globals.cart.getTotal()));
		txtPoints.setText("Questa spesa vale " + (int)Globals.cart.getTotal() + " punti!");
		lblItems.setText(""+Globals.cart.getNumberOfProd());
		txtError.setVisible(false);
		
		ProductViewer viewer = new ProductViewer(cartviewPane,Globals.cart);
		
		
		final ToggleGroup group = new ToggleGroup();

		radioConsegna.setToggleGroup(group);
		radioCarta.setToggleGroup(group);
		radioPayPal.setToggleGroup(group);
		
		radioConsegna.setSelected(true);
		
		paneConsegna.setVisible(radioConsegna.isSelected());
		paneCarta.setVisible(radioCarta.isSelected());
		panePayPal.setVisible(radioPayPal.isSelected());

	}


	public void changePayment(ActionEvent ae) {
		paneConsegna.setVisible(radioConsegna.isSelected());
		paneCarta.setVisible(radioCarta.isSelected());
		panePayPal.setVisible(radioPayPal.isSelected());
	}

	// Crea un ordine dal carrello corrente; ovviamente fa tutte le verifiche (validità dei campi E availability dei prodotti)
	public void makeOrder(ActionEvent ae) {
		
		//Step 0: carrello non vuoto e valori validi
		if (checkParameters()==false) {
			return;
		}

		//Step 1: Controlliamo se tutti i prodotti sono ancora disponibili!
		if(checkIfEverythingAvailable()==false) {
			System.out.println("WARNING: products removed");
			txtError.setText("Alcuni dei prodotti del carrello sono terminati e sono stati rimossi. Puoi riprovare ad eseguire l'acquisto con il tasto \"conferma\".");
			return;
		}

		//Step 2: generiamo un ordine con i prodotti del carrello e aggiungiamolo alla lista
		Payment payment = data.Payment.CONSEGNA;
		String info="Nessuna informazione aggiuntiva";
		
		if(radioPayPal.isSelected()) {
			payment=data.Payment.PAYPAL;
			info="Account: "+fieldPayPalEmail.getText();
			}
		if(radioCarta.isSelected()) {
			payment=data.Payment.CARTA;
			info="Carta: "+fieldCartaNumero.getText() + " (" + fieldCartaNome.getText() + " " + fieldCartaCognome.getText() +" )";
			}
		
		Globals.currentOrder = new Order(Globals.cart, payment, info);
		
		//Step 3: svuotiamo il carrello.
		Globals.cart.flushCart();
		
		//Step4: selezione giorno e ora
		launchUI("/application/UserDatePicker.fxml");
	}
	
	//verifica se nel frattempo sono finiti alcuni prodotti. Torna vero se è tutto a posto, falso se ha dovuto rimuovere dei prodotti.
	private boolean checkIfEverythingAvailable() {
		boolean isStillAvailable = true;
		
		JsonLoader.loadProducts();
		for(Product p : Globals.cart.getProducts().keySet()) {
			if(Globals.cart.getProducts().get(p) > p.getAvailable()){
				System.out.println("[x] "+p.getName()+" is not available anymore!! ");		
				Globals.cart.removeProduct(p);
				isStillAvailable = false;
			}
		}
		
		return isStillAvailable;
	}
	
	private boolean checkParameters() {
		
		boolean result = true;
		
		// 1. Carrello non vuoto
		if(Globals.cart.getProducts().isEmpty() == true) {
			txtError.setText("Errore: il carrello non può essere vuoto!");
			txtError.setVisible(true);
			return false;
			}
		
		// 2. Pagamento impostato (obv. controllo solo il pagamento selezionato!)
		if(radioCarta.isSelected()) {
			result &= !fieldCartaNome.getText().equals("");
			result &= fieldCartaCognome.getText().equals("");
			result &= !fieldCartaCVV.getText().equals("");
			result &= !fieldCartaNumero.getText().equals("");
			if (result==false) {
				txtError.setText("Errore: Il metodo di pagamento non è valido!");
				txtError.setVisible(true);
				return false;
				}
		}
		if(radioPayPal.isSelected()) {
			result &= !fieldPayPalEmail.getText().equals("");
			result &= !fieldPayPalPassword.getText().equals("");
			if (result==false){
				txtError.setText("Errore: Il metodo di pagamento non è valido!");
				txtError.setVisible(true);
				return false;
				}
		}
		
		
		// 3. Indirizzo impostato
		result &= !fieldAddress.getText().equals("");
		result &= !fieldCAP.getText().equals("");
		result &= !fieldCity.getText().equals("");
		if (result==false){
			txtError.setText("Errore: L'indirizzo non è valido!");
			txtError.setVisible(true);
			return false;
			}
		return result;
	}
}
