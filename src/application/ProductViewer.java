package application;

import java.io.IOException;
import java.util.PriorityQueue;

import data.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class ProductViewer {
	
	
	private ScrollPane scroller = new ScrollPane();
	private PriorityQueue<Product> displayed = new PriorityQueue<Product>();
	
	
	
	public ProductViewer(AnchorPane parent, PriorityQueue<Product> displayed) {
		this.displayed=displayed;
		formPanel(displayed);
		ScrollPane newpane = scroller;
		parent.getChildren().add(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
	}


	public ProductViewer(PriorityQueue<Product> displayed) {
		this.displayed=displayed;
		formPanel(displayed);
	}

	private void formPanel(PriorityQueue<Product> section) {
		

		//// DICHIARO I PANNELLI NECESSARI:

		ScrollPane scroller = new ScrollPane();
		FlowPane flowProdotti = new FlowPane();


		
		
		// 1. Genero la barra x filtrare //TODO ora è un dummy
		try {
			flowProdotti.getChildren().add(FXMLLoader.load(section.getClass().getResource("/application/FilterOrderBar.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		if(section==null || (section!=null && section.size()==0) ) {
			System.out.println("[✓] Caricata sezione vuota");
			this.scroller=scroller;
			}
		
		
		// 2. Genero tutti i prodotti

		for (Product p : section) {

			ProductPaneController.setInitProduct(p);
			AnchorPane productPane=null;
			
			try {
				productPane = FXMLLoader.load(section.getClass().getResource("/application/ProductPane.fxml"));

			//	System.out.println("[✓] Generato productpane: " + productPane +" for "+ p.getName());
			} catch (Exception e) {
				System.out.println("[x] Fxml non pervenuto :(" + e);
			}
			flowProdotti.getChildren().add(productPane);
			System.out.println("[✓] Mostrato: " + p);
			scroller.setFitToWidth(true);
			
			
		}

		scroller.setContent(flowProdotti);
		this.scroller=scroller;
	}

	public ScrollPane getScroller() {
		// TODO Auto-generated method stub
		return scroller;
	}
	
	
	
}
