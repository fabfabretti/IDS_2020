package application;

import java.io.IOException;
import java.util.TreeSet;

import data.Cart;
import data.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ProductViewer {
	
	
	private ScrollPane scroller = new ScrollPane();
	private TreeSet<Product> displayed = new TreeSet<Product>();
	private AnchorPane parent = new AnchorPane();
	
	
	
	public ProductViewer(AnchorPane parent, TreeSet<Product> result) {
		this.displayed=result;
		this.parent = parent;
		
		System.out.println("Visualizzo in modalità section!");
		formPanel(displayed,"section");
		ScrollPane newpane = scroller;
		parent.getChildren().add(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
	}
	
	public ProductViewer(AnchorPane parent, Cart cart) {
		this.displayed= new TreeSet<Product>(cart.getProducts().keySet());
		this.parent = parent;
		System.out.println("Visualizzo in modalità cart!");
		formPanel(displayed,"cart");
		ScrollPane newpane = scroller;
		parent.getChildren().add(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
	}




	private void formPanel(TreeSet<Product> result, String mode) {

		
		//NO PRODOTTI:
		if(result==null || (result!=null && result.size()==0) ) {
			System.out.println("[✓] Nessun prodotto da visualizzare! Carico pannello vuoto...");
			Pane res;
				try {

					System.out.println("Cart?"+ "cart".equals(mode));
					System.out.println("Section?" + "section".equals(mode));
						if(mode.equals("cart"))
							res=FXMLLoader.load( getClass().getResource("/application/CartViewEmpty.fxml") );
						
						else if (mode.equals("section")) 
							res=FXMLLoader.load( getClass().getResource("/application/ProductViewEmpty.fxml") );
							
						
						else res=null;
					scroller.setContent(res);
				} catch (Exception e) {
					System.out.println("[x] Errore caricamento UI interna");
				}
			return;
			}

		//// DICHIARO I PANNELLI NECESSARI:

		ScrollPane scroller = new ScrollPane();
		FlowPane flowProdotti = new FlowPane();


		
		if(!mode.equals("cart"))
		// 1. Genero la barra x filtrare //TODO ora è un dummy
		try {
			flowProdotti.getChildren().add(FXMLLoader.load(result.getClass().getResource("/application/FilterOrderBar.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		
		
		// 2. Genero tutti i prodotti

		for (Product p : result) {

			ProductPaneController.setInitProduct(p);
			AnchorPane productPane=null;
			
			try {
				
				if(mode.equals("section"))
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPane.fxml"));
				else
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPaneCompact.fxml"));
					

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




