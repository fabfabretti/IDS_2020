package application;

import com.jfoenix.controls.JFXButton;

import data.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
/**
 * Superclasse che contiene la roba condivisa fra tutti i productPane.
 *
 */
public class ProductPaneController extends Controller{

	protected static Product  productInit = null;
	protected Product  product = null;


	@FXML
	protected ImageView imagePath;
	
	@FXML 
	protected Text productName;
	

	
	


	protected static Image flags[]= {new Image("file:images/UI_organic.png"),new Image("file:images/UI_glutenfree.png"),
			new Image("file:images/UI_vegan.png"),new Image("file:images/UI_diaryfree.png")};

	
	static void setInitProduct(Product p) {
		productInit=p;
	}


	
}
