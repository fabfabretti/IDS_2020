package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
	@FXML
	private AnchorPane homePane;
	
	public void initialize() {
		txtWelcome.setText("Buongiorno, " + Globals.currentUser.getAnagrafica().getName() + " :D");
		
	}	
	
	
	public void openProductManager(ActionEvent e) {
		try {
			GridPane p = (GridPane)FXMLLoader.load(getClass().getResource("/application/ProductManager.fxml"));
			basePane.getChildren().clear();
			basePane.getChildren().addAll(p);
			basePane.setTopAnchor(p, 0.0);
			basePane.setBottomAnchor(p, 0.0);
			basePane.setLeftAnchor(p, 0.0);
			basePane.setRightAnchor(p, 0.0);
			
			
			
		} catch (IOException e1) {
		}
	}


}
