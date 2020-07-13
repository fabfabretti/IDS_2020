package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


/**
 * Gestisce la home dei worker.
 */
public class WorkerHomeController extends Controller{	
	@FXML
	private JFXButton btnProducts;
	@FXML
	private JFXButton btnOrders;
	@FXML
	private Text txtWelcome;

	@FXML
	private AnchorPane basePane;
	@FXML
	private AnchorPane homePane;
	
	
	
	
	
	/**
	 * Inizializza la scena (Es. scritta "hi admin")
	 */
	public void initialize() {
		txtWelcome.setText("Buongiorno, " + Globals.currentUser.getAnagrafica().getName() + " :D");
		
	}	
	
	
	public void openProductManager(ActionEvent e) {
		try {
			GridPane p = (GridPane)FXMLLoader.load(getClass().getResource("/application/ProductManager.fxml"));
			basePane.getChildren().clear();
			basePane.getChildren().addAll(p);
			AnchorPane.setTopAnchor(p, 0.0);
			AnchorPane.setBottomAnchor(p, 0.0);
			AnchorPane.setLeftAnchor(p, 0.0);
			AnchorPane.setRightAnchor(p, 0.0);
		} catch (IOException e1) {
		}
	}
	
	public void openOrderManager(ActionEvent e) {
		try {
			GridPane p = (GridPane)FXMLLoader.load(getClass().getResource("/application/WorkerOrderManager.fxml"));
			basePane.getChildren().clear();
			basePane.getChildren().addAll(p);
			AnchorPane.setTopAnchor(p, 0.0);
			AnchorPane.setBottomAnchor(p, 0.0);
			AnchorPane.setLeftAnchor(p, 0.0);
			AnchorPane.setRightAnchor(p, 0.0);
		} catch (IOException e1) {
		}
	}
	
	
	
	
	public void loadHome(ActionEvent e) {
		basePane.getChildren().clear();
		basePane.getChildren().addAll(homePane);
		
	}

	public void loadProfile(ActionEvent e) {
		launchUI("/application/WorkerProfile.fxml");
	}


}
