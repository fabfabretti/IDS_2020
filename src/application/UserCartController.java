package application;

import java.util.TreeSet;

import data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * Gestisce le parti "esterne" (ovvero, tutto tranne i prodotti) di un carrello.
 *
 */
public class UserCartController extends Controller{

	@FXML
	private Label lblTotal;
	
	@FXML
	private Text txtPoints;

	@FXML
	private Label lblItems;
	
	@FXML
	private AnchorPane cartviewPane;
	
	public void initialize() {
		
		Globals.cartController=this;
		lblTotal.setText("€ "+ String.format("%.2f", Globals.cart.getTotal()));
		txtPoints.setText("Questa spesa vale " + (int)Globals.cart.getTotal() + " punti!");
		lblItems.setText(""+Globals.cart.getNumberOfProd());
		
		ProductViewer viewer = new ProductViewer(cartviewPane,Globals.cart);
		
	}


	

}
