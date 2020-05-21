package application;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class Main extends Application {
	@Override
	public void start(Stage loginStage) {
		
		/**
		 * 
		 *  ________________________________________________________
		 * |   														|
		 * |            LE PASSWORD DI TEST ATTUALI SONO			|
		 * |  														|
		 * |   	 [modalità utente]			   [modalità admin]     |
		 * |		user  / a						admin			|
		 * |		user  / a						admin			|
		 * |________________________________________________________|
		 *
		 * 
		 */
		
		
		
		String workingDir = System.getProperty("user.dir");
	    System.out.println("Current working directory : " + workingDir);
	    
	    
        
        
		//Surroundo con try/catch perché è un input da file!
		try {
			

			JsonLoader.loadProduct();
			//carica file FXML con la formattazione
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			
			//setta la scena
			Scene scene = new Scene(root);
			
			//Collego i CSS
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Aggancia la scena al nostro stage
			loginStage.setScene(scene);
			
			//Un paio di cose visive utili
			loginStage.setResizable(false);

			loginStage.initStyle(StageStyle.UNDECORATED);

			//UNDECORATED cancella la shadow, ma ci serve x non avere la system_gui, cerco soluzioni TODO
			DropShadow dropShadow = new DropShadow();
			 dropShadow.setRadius(50.0);
			 dropShadow.setOffsetX(3.0);
			 dropShadow.setOffsetY(3.0);
			 dropShadow.setColor(Color.color(0.0, 0.0, 0.0));
	        root.setEffect(dropShadow);

			//Finalizzazione
			loginStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}



	}

	public static void main(String[] args) {

		launch(args);
	}
}
