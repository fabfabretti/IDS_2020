package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class UserCartController extends Controller{

	@FXML
	private Label lblTotal;
	
	@FXML
	private Text txtPoints;
	
	@FXML
	private Label lblItems;
	
	public void initialize() {
		lblTotal.setText("€ "+ + Globals.cart.getTotal());
		txtPoints.setText("Questa spesa vale " + (int)Globals.cart.getTotal() + " punti!");
		lblItems.setText(""+Globals.cart.getNumberOfProd());
		
		
	}

}
