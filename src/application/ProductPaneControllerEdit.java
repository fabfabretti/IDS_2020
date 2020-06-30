package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ProductPaneControllerEdit extends ProductPaneController {
	
	
	@FXML
	ImageView imagePath;
	@FXML
	Text productName;
	@FXML
	Text txtCode;
	
	
	

	public void initialize() {
		product = productInit;
		productName.setText(product.getName() +" "+product.getBrand() + " " + product.getWeight() + " " + product.getUnit());	
		
		//image prodotto
		Image image;
		try {
			image = new Image(product.getImagePath());    
		}catch(Exception e) {

			image = new Image("file:images/Product_placeholder.png");    
		}
		imagePath.setImage(image);
		
		
		txtCode.setText("Codice prodotto: " + product.getBarCode());
	}
	
	
	public void editInfo(ActionEvent e) {
		Globals.editController.editInfo(product);
	}
}
