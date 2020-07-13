package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import data.Order;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * gestise la schermata di modifica dati dell'user.
 *
 */
public class UserProfileController extends Controller {

	// FXML

	@FXML
	private JFXTextField fieldEmail;

	@FXML
	private JFXPasswordField fieldPassword;

	@FXML
	private JFXTextField fieldName;

	@FXML
	private JFXTextField fieldSurname;

	@FXML
	private JFXTextField fieldAddress;

	@FXML
	private JFXTextField fieldCAP;

	@FXML
	private JFXTextField fieldCity;

	@FXML
	private JFXTextField fieldNumber;

	@FXML
	private Label lblError;

	// Ordini
	@FXML
	private ScrollPane scrollPaneOrders;

	// Pagamento predefinito
	@FXML
	private JFXRadioButton radioPayPal;
	@FXML
	private JFXRadioButton radioCarta;
	@FXML
	private JFXRadioButton radioConsegna;

	// Consegna
	@FXML
	private AnchorPane paneConsegna;

	// PayPal
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
	private JFXTextField fieldCartaNumero;
	@FXML
	private JFXTextField fieldCartaCVV;
	@FXML
	private JFXTextField fieldCartaNome;
	@FXML
	private JFXTextField fieldCartaCognome;
	
	
	//Fidelity card
	@FXML
	private Text txtFCardPoints;
	@FXML
	private Text txtFCardNumber;
	@FXML
	private Text txtFCardSaldo;
	
	

	/***
	 * Serve a mostrare i dati salvati dall'utente dentro i rispettivi campi
	 */
	public void initialize() {

		// Informazioni generali
		fieldEmail.setText(Globals.currentUser.getEmail());
		fieldPassword.setText(Globals.currentUser.getPassword());
		fieldName.setText(Globals.currentUser.getAnagrafica().getName());
		fieldSurname.setText(Globals.currentUser.getAnagrafica().getFamilyName());
		fieldAddress.setText(Globals.currentUser.getAnagrafica().getAddress());
		fieldCAP.setText((Globals.currentUser.getAnagrafica().getCAP()) + "");
		fieldCity.setText((Globals.currentUser.getAnagrafica().getCity()));
		fieldNumber.setText((Globals.currentUser.getAnagrafica().getMobileNumber()));

		// Se questo è un user, allora dobbiamo mostrare anche lo storico, la carta e il metodo di
		// pagamento.
		if (Globals.currentUser instanceof User) {
			
			
			User currUser = (User)Globals.currentUser;
			
			//Carta fidelity
			
			if(currUser.getFCardNumber()==-1) {
				txtFCardPoints.setVisible(false);
				txtFCardSaldo.setVisible(false);
				txtFCardNumber.setText("Nessuna carta associata! :(");
			}
			else {
				txtFCardNumber.setText(""+currUser.getFCardNumber());
				txtFCardPoints.setText(""+currUser.getActualPoints());
			}
			
			//Pagamento

			final ToggleGroup group = new ToggleGroup();
			radioConsegna.setToggleGroup(group);
			radioCarta.setToggleGroup(group);
			radioPayPal.setToggleGroup(group);
			
			
			System.out.println(currUser.getPaymentOrdinal());
			
			int payment = (currUser.getPaymentOrdinal());
			switch(payment) {
			case 1:
				radioPayPal.setSelected(true);
				fieldPayPalEmail.setText(currUser.getPayPalEmail());
				fieldPayPalPassword.setText(currUser.getPayPalPassword());
				break;
			case 2:
				radioCarta.setSelected(true);
				fieldCartaNome.setText(currUser.getCreditCardName());
				fieldCartaCognome.setText(currUser.getCreditCardFamilyName());
				fieldCartaNumero.setText(""+currUser.getCreditCardNumber());
				fieldCartaCVV.setText(""+currUser.getCreditCardCVV());
				break;
			default:
				radioConsegna.setSelected(true);		
			}
			

			paneConsegna.setVisible(radioConsegna.isSelected());
			paneCarta.setVisible(radioCarta.isSelected());
			panePayPal.setVisible(radioPayPal.isSelected());
			
			
			
			//Storico
			
			
			System.out.println(Globals.storico);

			FlowPane flower = new FlowPane();
			AnchorPane pane = null;
			if (Globals.storico.size() != 0) {
				new Controller();
				for (Order o : Globals.storico) {
					try {
						if (o.getUser().getUserID() == ((User) Globals.currentUser).getUserID()) {
							OrderPaneController.initializeOrder(o);
							pane = (FXMLLoader.load(getClass().getResource("/application/OrderPaneUser.fxml")));
							// System.out.println(pane);
							if (pane != null)
								flower.getChildren().add(pane);
						}
					} catch (Exception e) {
						System.out.println("[x] Errore a caricare UI interna");
					}

				}

				AnchorPane.setTopAnchor(flower, 0.0);
				AnchorPane.setBottomAnchor(flower, 0.0);
				AnchorPane.setLeftAnchor(flower, 0.0);
				AnchorPane.setRightAnchor(flower, 0.0);

				scrollPaneOrders.setContent(flower);
			}

		}

	}

	/***
	 * Salva i nuovi dati e chiude la finestra
	 * 
	 * @param ae evento click su salva
	 */
	public void saveAndClose(ActionEvent ae) {
		

		
		boolean valid=true;
		lblError.setVisible(false);
		
		try {
			Integer.parseInt(fieldCAP.getText());
			System.out.println(fieldCAP.getText());
			Long.parseLong(fieldNumber.getText());
			System.out.println(fieldNumber.getText());
		}catch(NumberFormatException e) {
			lblError.setText("ATTENZIONE: dati non validi!");
			lblError.setVisible(true);
			return;
		}
		//Controllo 1: dati normali
		if (fieldEmail.getText().isEmpty() || fieldPassword.getText().isEmpty() || fieldName.getText().isEmpty()
				|| fieldSurname.getText().isEmpty() || fieldAddress.getText().isEmpty() || fieldCAP.getText().isEmpty()
				|| fieldCity.getText().isEmpty() || fieldNumber.getText().isEmpty()) 
			valid=false;
		
		//Controllo 2: pagamento (solo se User)

		if(Globals.currentUser instanceof User) {

			//Paypal
			if(radioPayPal.isSelected())
				if(fieldPayPalEmail.getText().isEmpty() || fieldPayPalPassword.getText().isEmpty())
					valid=false;

			//Carta
			if(radioCarta.isSelected())
				if(fieldCartaNumero.getText().isEmpty() || fieldCartaCVV.getText().isEmpty() 
						|| fieldCartaNome.getText().isEmpty() || fieldCartaCognome.getText().isEmpty() ) 
					valid=false;

			if (valid==false) {
				lblError.setText("ATTENZIONE: è necessario compilare tutti i campi!");
				lblError.setVisible(true);
			}

			//Verifico i numeri
			try {

				//Carta if User
				if(Globals.currentUser instanceof User) {
					if(radioCarta.isSelected()) {
						System.out.println(radioCarta.isSelected());
						Integer.parseInt(fieldCartaNumero.getText());
						Integer.parseInt(fieldCartaCVV.getText());
					}
				}

			}catch(NumberFormatException e){
				valid=false;
				lblError.setText("ATTENZIONE: dati non validi!");
				lblError.setVisible(true);
				return;
			}


		}
		

		/*
		 * Controllo errori TODO, eventualmente display errore TODO, salvataggio TODO
		 */
		if(valid==true) {

			// Salvataggio dati
			Globals.currentUser.setEmail(fieldEmail.getText());
			Globals.currentUser.setPassword(fieldPassword.getText());
			Globals.currentUser.getAnagrafica().setName(fieldName.getText());
			Globals.currentUser.getAnagrafica().setFamilyName(fieldSurname.getText());
			Globals.currentUser.getAnagrafica().setAddress(fieldAddress.getText());
			Globals.currentUser.getAnagrafica().setAddress(fieldAddress.getText());
			Globals.currentUser.getAnagrafica().setCity(fieldCity.getText());
			Globals.currentUser.getAnagrafica().setCAP(fieldCAP.getText());
			Globals.currentUser.getAnagrafica().setMobileNumber(fieldNumber.getText());
			
			
			
			if(Globals.currentUser instanceof User) {
				
				User currUser = (User) Globals.currentUser;
				//Pagamenti:
				currUser.setCreditCardCVV(0);
				currUser.setCreditCardFamilyName("");
				currUser.setCreditCardName("");
				currUser.setCreditCardNumber("");
				currUser.setPayPalEmail("");
				currUser.setPayPalPassword("");


				if(radioConsegna.isSelected())
					currUser.setPaymentOrdinal(0);

				if(radioPayPal.isSelected()) {
					currUser.setPaymentOrdinal(1);
					currUser.setPayPalEmail(fieldPayPalEmail.getText());
					currUser.setPayPalPassword(fieldPayPalPassword.getText());
				}

				if(radioCarta.isSelected()) {
					currUser.setPaymentOrdinal(2);
					currUser.setCreditCardCVV(Integer.parseInt(fieldCartaCVV.getText()));
					currUser.setCreditCardNumber(fieldCartaNumero.getText());
					currUser.setCreditCardName(fieldCartaNome.getText());
					currUser.setCreditCardFamilyName(fieldCartaCognome.getText());
				}
			}
			
			
			System.out.println("[✓] Nuovo set dati salvato in memoria (ram)");

			JsonSaver.saveUser();
			JsonSaver.saveWorker();
			System.out.println("[✓] Nuovo set dati salvato in memoria (secondaria) ");
			closeUI(ae);

		}

	}

	public void changePayment(ActionEvent ae) {
		paneConsegna.setVisible(radioConsegna.isSelected());
		paneCarta.setVisible(radioCarta.isSelected());
		panePayPal.setVisible(radioPayPal.isSelected());
	}
}
