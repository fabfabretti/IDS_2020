package application;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;


/**
 * Gestisce i pannelli dei prodotti di un carrello.
 *
 */
public class ProductPaneControllerCart extends ProductPaneController {
	
	@FXML
	protected Text txtFinished;

	@FXML
	protected Label weightPrice; 

	@FXML
	protected Label price;

	@FXML
	protected FlowPane flagFlowPane;
	
	@FXML
	protected JFXButton btnAdd;

	@FXML
	protected Text txtQuantity;


	public void initialize() {
		product = productInit;
		
		System.out.println(product.getUnit());
		productName.setText(product.getName() +" "+product.getBrand() + " " + product.getWeight()+ " " + product.getUnit());
		price.setText("€ "+product.getPrice());
		weightPrice.setText(product.getWeightPrice());

		
		txtQuantity.setText("Q.tà:" + Globals.cart.getProducts().get(product));
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
	
	public void removeProduct(ActionEvent e) {
			
			int qty=product.getAvailable()+Globals.cart.getProducts().get(product);

			Globals.cart.removeProduct(product);
			System.out.println(qty);
			
			product.setAvailable(qty);
			
			Globals.viewController.initialize();
			Globals.cartController.initialize();
	}
}
