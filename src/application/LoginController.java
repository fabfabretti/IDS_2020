package application;

import java.util.HashSet;

import data.UserGeneral;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Gestisce la finestra di Login e tutti i suoi input.
 *
 */
public class LoginController extends Controller{

	// oggetti in FXML

	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField pwdPassword;


	// gestori eventi
	/**
	 * Avvia {@link #tentativoAccesso()} quando si clicca sul bottone
	 * 
	 * @param e azione "premi button"
	 */
	public void Login(ActionEvent e) {
		tentativoAccesso();
	}

	/**
	 * Avvia {@link #tentativoAccesso()} quando si preme invio mentre uno due due
	 * field è in focus
	 * 
	 * @param e Azione "premi tasto"
	 */
	public void LoginKEY(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			tentativoAccesso();
	}



	// funzioni di supporto:
	/**
	 * Data l'interazione col pulsante Accedi, verifica se username e password sono
	 * corretti chiamando {@link data.User#checkLogin(String,String)} e modifica la
	 * pagina di conseguenza.
	 * 
	 * In base al tipo di login rilevato (incompleto, invalido, user o commesso)
	 * gestisce e aggiorna la GUI + i dati in Global
	 */
	private void tentativoAccesso() {

		// Salvo i valori dei txtField in variabili per leggibilità e comodità
		String logEmail = txtEmail.getText();
		String logPassword = pwdPassword.getText();

		// Controllo preliminare; se va male faccio a meno di caricare gli User
		// Uno o più campi sono vuoti:

		if (logEmail.equals("") || logPassword.equals("")) {
			lblStatus.setText("Per favore, compila entrambi i campi");
			lblStatus.setVisible(true);
			return;
		}

		// Carica gli utenti nel sistema
		// Direi che questa funzione starà dentro Users (classe) ^-^ TODO
		HashSet<UserGeneral> utenti = getUsers();

		for (UserGeneral u : utenti) {

			String res = u.checkLogin(logEmail, logPassword);

			// debug System.out.println(res);

			if (res.equals("utente")) {
				lblStatus.setVisible(false);
				Globals.logged = true;
				Globals.currentUser = u;
				launchUI("/application/UserHome.fxml");
				Stage loginStage = (Stage) lblStatus.getScene().getWindow();
				loginStage.close();
				return;
			}

			if (res.equals("commesso")) {
				lblStatus.setVisible(true);
				lblStatus.setText("La modalità admin verrà implementata presto");
				// TODO: implementarla lol
				launchUI("./WorkerHome.fxml");
				return;
			}
		}

		// caso rimanente: pwd errata
		lblStatus.setVisible(true);
		lblStatus.setText("Username o password errata.");

	}

	/**
	 * Carica nella memoria del programma (intesa come RAM) la lista degli utenti,
	 * salvata in un file
	 * 
	 * (TODO):Al momento sono banalmente due utenti definiti nel programma;
	 * Successivamente utilizzerà una funzione che porterà in memoria la lista degli
	 * utenti presenti su un file (quindi salvati in memoria).
	 * 
	 * 
	 * (TODO) Probabilmente conviene metterla dentro la classe User (ha più senso).
	 * 
	 * @return Hashset di tutti gli user attualmente registrati nel file degli
	 *         utenti
	 */
	private HashSet<UserGeneral> getUsers() {

		// per i test attuali, usiamo solo due accessi di prova
		UserGeneral utente = new UserGeneral("user", "user");
		UserGeneral admin = new UserGeneral("admin", "admin");
		UserGeneral utentebasic = new UserGeneral("a", "a");
		HashSet<UserGeneral> users = new HashSet<UserGeneral>();
		users.add(utente);
		users.add(admin);
		users.add(utentebasic);

		return users;
	}


}
