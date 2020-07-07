package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import data.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;


/**
 * gestise la schermata di modifica dati dell'user.
 *
 */
public class UserProfileController extends Controller {

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
	 * ovviamente, TODO
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

		
		System.out.println(Globals.storico);
		//pannello degli ordini
		FlowPane flower = new FlowPane();
		AnchorPane pane;
		if(Globals.storico.size()!=0) {
			for(Order o : Globals.storico) {
				pane=Globals.viewController.launchUIPanel("/application/CartReview.fxml");
				flower.getChildren().add(pane);
			}
			
			/*AnchorPane.setTopAnchor(scrollPaneOrders, 0.0);
			AnchorPane.setBottomAnchor(scrollPaneOrders, 0.0);
			AnchorPane.setLeftAnchor(scrollPaneOrders, 0.0);
			AnchorPane.setRightAnchor(scrollPaneOrders, 0.0);*/
		
		scrollPaneOrders.setContent(flower);
		}
		
		
		
		
	}

	/***
	 * Salva i nuovi dati e chiude la finestra
	 * 
	 * @param ae evento click su salva
	 */
	public void saveAndClose(ActionEvent ae) {
		String err = "";

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

			String newName = fieldName.getText();
			Globals.currentUser.getAnagrafica().setName(newName);
			System.out.println("[✓] Nuovo nome salvato in memoria (ram)");

			JsonSaver.saveUser();
			JsonSaver.saveWorker();
			closeUI(ae);

		}

	}
	
	
	public void changePayment(ActionEvent ae) {
		
	}
}
