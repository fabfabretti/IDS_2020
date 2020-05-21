package application;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
			Globals.stage = mainStage;

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
	
	
	/***
	 * Chiude la finestra da cui è partito il segnale. NON salva!!! 
	 * @param ae e
	 */
	public void closeUI(Event ae) {
		final Node source = (Node) ae.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
	
	public void quitConfirm(ActionEvent e) {
		launchUI("/application/Exit.fxml");
	}
	
	public void quit(ActionEvent ae) {

		///TODO 	SALVA LE COSE IN SOSPESO
		System.exit(0);	
	}
	
	public void quitNoSave(ActionEvent ae) {
		System.exit(0);	
	}
	/*
	protected void init()
	{
		// this class is the HelloworldController class because init() is not private method
		// do init staff if you want
		// now FML fields are not null
	}*/
}
