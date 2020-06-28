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


/**
 * Classe che gestisce la visualizzazione di un elenco di prodotti.
 *
 */
public class ProductViewer {
	
	// Scroller ove verrà agganciato il flowpane con i prodotti
	private ScrollPane scroller = new ScrollPane();
	
	//Insieme dei prodotti attualmente mostrati
	private TreeSet<Product> displayed = new TreeSet<Product>();
	
	//Pannello al quale voglio agganciare lo scrollpane
	private AnchorPane parent = new AnchorPane();
	
	
	/**
	 * Costruisce un ProductViewer attaccando già lo scrollpane al parent indicato e impostando la visualizzazione in modalità  SECTION
	 * 
	 * @param parent pannello su cui attaccare lo scrollpane con tuttecose
	 * @param result elenco prodotti da mostrare
	 */
	public ProductViewer(AnchorPane parent, TreeSet<Product> result, String type) {
		this.displayed=result;
		this.parent = parent;
		
		System.out.println("Visualizzo in modalità section!");
		formPanel(displayed,type);
		ScrollPane newpane = scroller;
		parent.getChildren().add(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
	}
	
	/**
	 * Costruisce un ProductViewer attaccando già lo scrollpane al parent indicato e impostando la visualizzazione in modalità  CART
	 * 
	 * @param parent pannello su cui attaccare lo scrollpane con tuttecose
	 * @param result elenco prodotti da mostrare
	 */
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



	/**
	 * Crea il pannello con tutti i prodotti (presumibilmente, questo pannello andrà in uno scrollpane).
	 * 
	 * @param result elenco dei prodotti da mostrare
	 * @param mode è "cart" o "section"; cambia alcune cose grafiche
	 */
	private void formPanel(TreeSet<Product> result, String mode) {


		//
		// Case 0: Non ho prodotti
		//

		
		if(result==null || (result!=null && result.size()==0) ) {
			System.out.println("[✓] Nessun prodotto da visualizzare! Carico pannello vuoto...");
			Pane res;
				try {
						if(mode.equals("cart"))
							res=FXMLLoader.load( getClass().getResource("/application/CartViewEmpty.fxml") );
						
						else if (mode.equals("section")) 
							res=FXMLLoader.load( getClass().getResource("/application/ProductViewEmpty.fxml") );
						
						else if (mode.equals("edit"))  //TODO
							res=FXMLLoader.load( getClass().getResource("/application/ProductViewEmpty.fxml") );
							
						
						else res=null;
					scroller.setContent(res);
				} catch (Exception e) {
					System.out.println("[x] Errore caricamento UI interna");
				}
				
			System.out.println("[✓] Cart visualizzato");
			return;
			}

		//
		// Case 1: Ho prodotti!
		//
		
		ScrollPane scroller = new ScrollPane();
		FlowPane flowProdotti = new FlowPane();
		
		
		// 1. Genero la barra x filtrare //TODO ora è un dummy// se necessaria
		if(!mode.equals("cart"))
		try {
			flowProdotti.getChildren().add(FXMLLoader.load(result.getClass().getResource("/application/FilterOrderBar.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 2. Genero tutti i prodotti: per ogni prodotto genero il pannellino
		for (Product p : result) {

			ProductPaneController.setInitProduct(p);
			AnchorPane productPane=null;
			
			try {
				
				if(mode.equals("section"))
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPane.fxml"));
				if(mode.equals("cart"))
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPaneCompact.fxml"));
				if(mode.equals("edit"))
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPaneEdit.fxml"));
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
		return scroller;
	}
	
}




