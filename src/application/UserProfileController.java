package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import data.Order;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;


/**
 * gestise la schermata di modifica dati dell'user.
 *
 */
public class UserProfileController extends Controller {

	//FXML
	
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
	
	@FXML
	private ScrollPane scrollPaneOrders;

	/***
	 * Serve a mostrare i dati salvati dall'utente dentro i rispettivi campi
	 */
	public void initialize() {
		fieldEmail.setText(Globals.currentUser.getEmail());
		fieldPassword.setText(Globals.currentUser.getPassword());
		fieldName.setText(Globals.currentUser.getAnagrafica().getName());
		fieldSurname.setText(Globals.currentUser.getAnagrafica().getFamilyName());
		fieldAddress.setText(Globals.currentUser.getAnagrafica().getAddress());
		fieldCAP.setText((Globals.currentUser.getAnagrafica().getCAP()) + "");
		fieldCity.setText((Globals.currentUser.getAnagrafica().getCity()));
		fieldNumber.setText((Globals.currentUser.getAnagrafica().getMobileNumber()));

		//Se questo è un user, allora dobbiamo mostrare anche lo storico.
		if(Globals.currentUser instanceof User) {
			System.out.println(Globals.storico);
			
			//pannello degli ordini
			FlowPane flower = new FlowPane();
			AnchorPane pane=null;
			if(Globals.storico.size()!=0) {
				 new Controller();
				for(Order o : Globals.storico) {
					try {
						if(o.getUser().getUserID() == ((User)Globals.currentUser).getUserID()) {
							CartReviewController.initializeOrder(o);
							pane=(FXMLLoader.load(getClass().getResource("/application/CartReview.fxml")));
							//System.out.println(pane);
						}
					} catch (Exception e) {
						System.out.println("[x] Errore a caricare UI interna");
					}
					
					if(pane!=null)
						flower.getChildren().add(pane);
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

		if (fieldEmail.getText().isEmpty() || fieldPassword.getText().isEmpty() || fieldName.getText().isEmpty()
				|| fieldSurname.getText().isEmpty() || fieldAddress.getText().isEmpty() || fieldCAP.getText().isEmpty()
				|| fieldCity.getText().isEmpty() || fieldNumber.getText().isEmpty()) {
			lblError.setText("ATTENZIONE: è necessario compilare tutti i campi!");
			lblError.setVisible(true);

		}

		/*
		 * Controllo errori TODO, eventualmente display errore TODO, salvataggio TODO
		 */
		else {

			//Salvataggio dati
			Globals.currentUser.setEmail(fieldEmail.getText());
			Globals.currentUser.setPassword(fieldPassword.getText());
			Globals.currentUser.getAnagrafica().setName(fieldName.getText());
			Globals.currentUser.getAnagrafica().setFamilyName(fieldSurname.getText());
			Globals.currentUser.getAnagrafica().setAddress(fieldAddress.getText());
			Globals.currentUser.getAnagrafica().setAddress(fieldAddress.getText());
			Globals.currentUser.getAnagrafica().setCity(fieldCity.getText());
			Globals.currentUser.getAnagrafica().setCAP(fieldCAP.getText());
			Globals.currentUser.getAnagrafica().setMobileNumber(fieldNumber.getText());
			
			System.out.println("[✓] Nuovo nome salvato in memoria (ram)");

			JsonSaver.saveUser();
			JsonSaver.saveWorker();
			closeUI(ae);

		}

	}
	
	
	public void changePayment(ActionEvent ae) {
		
	}
}
