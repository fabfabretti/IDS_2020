package application;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class WorkerHomeController extends Controller{	
	@FXML
	private JFXButton btnProducts;
	@FXML
	private JFXButton btnOrders;
	@FXML
	private Text txtWelcome;

	@FXML
	private AnchorPane basePane;
	
	public void initialize() {
		txtWelcome.setText("Buongiorno, " + Globals.currentUser.getAnagrafica().getName() + " :D");
		
	}	
	
	
	public void openProductManager(ActionEvent e) {
		basePane.getChildren().addAll(launchUIPanel("./application/ProductManager.fxml"));
	}


}
