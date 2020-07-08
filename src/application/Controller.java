package application;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Superclasse che gestisce tutte le questioni comuni a tutti i controller.
 * @author Fabiola
 *
 */
public class Controller {
	
	
	/***
	 * Apre una nuova finestra in JavaFX (gestendo il try/catch e impostando un paio di cose utili, come il css o il not-resizable)
	 * @param path path dell'fxml.
	 */
	public void launchUI(String path) {
		try {

			Stage mainStage = new Stage();
			Parent root = FXMLLoader.load(this.getClass().getResource(path));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
			mainStage.setScene(scene);

			// Aggiungiamo questo stage alle variabili visibili a tutto il rpogramma per
			// comodità

			// Dato che si tratta di un prototipo, non ci preoccupiamo di rendere la
			// finestra responsiva(?)
			mainStage.setResizable(false);

			// No barra superiore
			mainStage.initStyle(StageStyle.UNDECORATED);

			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	/**
	 * Lancia una nuova parte di UI all'interno di una già presente; torna l'anchorpane che contiene la nuova finestra.
	 * @param path
	 * @return
	 */
	public AnchorPane launchUIPanel(String path) {
		AnchorPane res=null;
		try {
			res=(FXMLLoader.load(getClass().getResource("/application/ProductManager.fxml")));
			System.out.println(res);
		} catch (Exception e) {
			System.out.println("[x] Errore a caricare UI interna");
		}
		return res;
	}
	
	/**
	 * Apre la finestra di dialogo di conferma chiusura
	 * @param e
	 */
	public void quitConfirm(ActionEvent e) {
		launchUI("/application/Exit.fxml");
	}
	
	/***
	 * Chiude la finestra da cui è partito il segnale. NON salva!!! 
	 * @param ae e
	 */
	public void closeUI(Event ae) {
		final Node source = (Node) ae.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
	
	
	
	/**
	 * Chiude e salva le cose in sospeso
	 * @param ae
	 */
	public void quit(ActionEvent ae) {

		///TODO 	SALVA LE COSE IN SOSPESO
		System.exit(0);	
	}
	
	/**
	 *  Chiude tutto senza salvare.
	 * @param ae
	 */
	public void quitNoSave(ActionEvent ae) {
		System.exit(0);	
	}
	
	public AnchorPane loadInternalUI(String path) {
		AnchorPane res=null;
		try {
			res=(FXMLLoader.load(getClass().getResource(path)));
			System.out.println(res);
		} catch (Exception e) {
			System.out.println("[x] Errore a caricare UI interna");
		}
		return res;
	}
	
}
