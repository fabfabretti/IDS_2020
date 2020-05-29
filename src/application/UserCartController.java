package application;

import java.util.TreeSet;

import data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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
		
		lblTotal.setText("€ "+ String.format("%.2f", Globals.cart.getTotal()));
		txtPoints.setText("Questa spesa vale " + (int)Globals.cart.getTotal() + " punti!");
		lblItems.setText(""+Globals.cart.getNumberOfProd());
		
		
		TreeSet<Product> cart = new TreeSet<Product>( Globals.cart.getProducts().keySet());
		ProductViewer viewer = new ProductViewer(cartviewPane,cart);
		
	}

}
