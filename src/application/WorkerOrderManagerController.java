package application;

import data.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class WorkerOrderManagerController extends Controller {
	
	@FXML
	private ScrollPane scrollPaneOrders;

	public void initialize(){

		FlowPane flower = new FlowPane();
		System.out.println(Globals.storico);
		Globals.storico.clear();
		JsonLoader.loadHistory();
		System.out.println(Globals.storico);
		AnchorPane pane = null;
		if (Globals.storico.size() != 0) {
			new Controller();
			for (Order o : Globals.storico) {
				try {

						OrderPaneController.initializeOrder(o);
						pane = (FXMLLoader.load(getClass().getResource("/application/OrderPaneWorker.fxml")));

						if (pane != null)
							flower.getChildren().add(pane);
					
				} catch (Exception e) {
					System.out.println("[x] Errore a caricare UI interna");
					e.printStackTrace();
				}

			}

			AnchorPane.setTopAnchor(flower, 0.0);
			AnchorPane.setBottomAnchor(flower, 0.0);
			AnchorPane.setLeftAnchor(flower, 0.0);
			AnchorPane.setRightAnchor(flower, 0.0);

			scrollPaneOrders.setContent(flower);
		}

	}
	
	
	public void save (ActionEvent ae) {
		JsonSaver.saveHistory();
		Globals.workerController.loadHome(ae);
		System.out.println(Globals.storico);
		
	}
	
	
}
