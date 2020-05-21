package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
	
	/***
	 * Serve a mostrare i dati salvati dall'utente dentro i rispettivi campi
	 * ovviamente, TODO
	 */
	public void initialize() {
		fieldEmail.setText("email@email.email");
		fieldPassword.setText("Password");
		fieldName.setText("Nome");
		fieldSurname.setText("Cognome");
		fieldAddress.setText("Strada Le Grazie");
		fieldCAP.setText("1111");
		fieldCity.setText("Città");
		fieldNumber.setText("1234567890");
	}

	/***
	 * Salva i nuovi dati e chiude la finestra
	 * @param ae evento click su salva
	 */
	public void saveAndClose(ActionEvent ae) {
		String err="";
		
		
		if(fieldEmail.getText().isEmpty() || fieldPassword.getText().isEmpty() ||
				fieldName.getText().isEmpty() || fieldSurname.getText().isEmpty() ||
				fieldAddress.getText().isEmpty() || fieldCAP.getText().isEmpty() || 
				fieldCity.getText().isEmpty() || fieldNumber.getText().isEmpty()) {
			lblError.setText("ATTENZIONE: è necessario compilare tutti i campi!");
			lblError.setVisible(true);
			
		}
		
		/*
		 * Controllo errori TODO, eventualmente display errore TODO, salvataggio TODO
		 */
		
		closeUI(ae);
		
		System.out.println("Salvato?");
		
	}
/*	Se l'inizializzazione dovesse dare problemi dobbiamo aggiungere questo
	protected void init()
	{
		// this class is the HelloworldController class because init() is not private method
		// do init staff if you want
		// now FML fields are not null
	}

*/

}
