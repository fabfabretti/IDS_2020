package application;

import com.jfoenix.controls.JFXButton;

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
 * Gestisce i pannelli dei prodotti mostrati nella "home page" dell'user.
 *
 */
public class ProductPaneControllerUser extends ProductPaneController {
	@FXML
	protected JFXButton btnAdd;
	@FXML
	protected Spinner<Integer> spinnerQuantity;
	
	@FXML
	protected Text txtFinished;

	@FXML
	protected Label weightPrice; 

	@FXML
	protected Label price;

	@FXML
	protected FlowPane flagFlowPane;

	public void initialize() {
		product = productInit;
		productName.setText(product.getName() +" "+product.getBrand() + " " + product.getWeight()+ " " + product.getUnit());
		price.setText(String.format("€ %.2f", product.getPrice()));  
		weightPrice.setText(product.getWeightPrice());
		
		
		
		//spinner q.tà
		if(product.getAvailable()!=0) {
			txtFinished.setVisible(false);
			spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getAvailable(), 1));
		}else {
			spinnerQuantity.setVisible(false);
			btnAdd.setDisable(true);
			txtFinished.setVisible(true);
		}
		

		//image prodotto
		Image image;
		try {
			image = new Image(product.getImagePath());    
		}catch(Exception e) {

			image = new Image("file:images/Product_placeholder.png");    
		}
		imagePath.setImage(image);
		
		
		//Lista caratteristiche
		boolean characteristics[] = product.getCharacteristics();
		ImageView a=null;
		for(int i=0;i<4;i++) {
			if (characteristics[i]==true) {
				a= new ImageView(flags[i]);
				a.setFitHeight(25);
				a.setFitWidth(25);
				flagFlowPane.getChildren().add(a);
			}

		}
	}
	

	public void addProduct(ActionEvent e) {
		System.out.println("[✓] "+spinnerQuantity.getValue() +" "+ product.getName()+" added to cart");
		Globals.cart.addProduct(product, spinnerQuantity.getValue());
		
		System.out.println("    There's " + (product.getAvailable()-spinnerQuantity.getValue()) + " "+ product.getName() + " left.");
		product.setAvailable(product.getAvailable()-spinnerQuantity.getValue());

		if(product.getAvailable()!=0) {
			txtFinished.setVisible(false);
			spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getAvailable(), 1));
		}else {
			System.out.println("[✓] No " + product.getName() + " left.");
			spinnerQuantity.setVisible(false);
			btnAdd.setDisable(true);
			txtFinished.setVisible(true);
		}
		
	}
	

}
