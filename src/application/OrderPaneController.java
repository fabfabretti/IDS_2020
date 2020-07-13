package application;

import com.jfoenix.controls.JFXComboBox;

import data.Order;
import data.Product;
import data.User;
import data.Worker;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class OrderPaneController {
	protected static Order  orderInit = null;
	protected Order  order = null;

	@FXML
	private Text txtOrderId;
	@FXML
	private Text txtUserId;
	@FXML
	private Text txtDate;
	@FXML
	private Text txtTime;
	@FXML
	private Text txtTotal;
	@FXML
	private Text txtPayment;
	@FXML
	private Text txtPoints;
	@FXML
	private Text txtState;
	@FXML
	private Text txtProducts;
	@FXML
	private Text txtAddress;
	@FXML
	private ScrollPane scrollProducts;
	@FXML
	private JFXComboBox<String> chboxState;
	
	public void initialize(){
		order=orderInit;

		//Stringhe più carine di quelle di default!!
		String[] stringhePayment = {"Alla consegna", "PayPal", "Carta di credito"};
		String[] stringheTime = {"Mattina (08:00-11:00)", "Pranzo (11:00-14:00)", "Pomeriggio (14:00-17:00)","Sera (17:00-20:00)"};
		String[] stringheState = {"Confermata","In preparazione","Consegnata"};

		//Info sull'ordine
		String format = String.format("Ordine #%04d", order.getOrderid());
		txtOrderId.setText(format);
		txtDate.setText(order.getDate().toString());
		format = String.format("€ %.2f",order.getCart().getTotal());
		txtTotal.setText(format);
		txtTime.setText(stringheTime[order.getTime().ordinal()]);
		txtPayment.setText(stringhePayment[order.getPayment().ordinal()]);
		txtPoints.setText(""+((int)order.getCart().getTotal()));	
		txtAddress.setText(order.getAddress());

		if(Globals.currentUser instanceof User)
			txtState.setText(""+ stringheState[order.getState().ordinal()]);
		
		//Info sui prodotti; devo generare la stringa...
		String prodInfo = "";
		
		for(Integer i : order.getCart().getProducts().keySet()) {
			Product p = Globals.barCodeTable.get(i);
			prodInfo+= order.getCart().getProducts().get(i) + " X " +p.getName() + " " + p.getWeight() + p.getUnit() + "(€" +
					String.format("%.2f", order.getPrices().get(i)) + ")" +
						"\n\t\t\t\t €" + 
						String.format("%.2f", order.getPrices().get(i) * order.getCart().getProducts().get(i)) + "\n";
		}
		
		txtProducts.setText(prodInfo);
		
		
		if(Globals.currentUser instanceof Worker) {
			txtUserId.setText("Codice cliente: "+order.getUser().getUserID() +" [ " +order.getUser().getAnagrafica().getName() +" "+ order.getUser().getAnagrafica().getFamilyName() +" ]");
		
			String choices[] = {"Confermata","In preparazione","Consegnata"};
			
			for(String s : choices)
				chboxState.getItems().add(s);
			
			switch(order.getState().ordinal()) {
			case 2 :
				chboxState.setValue("Consegnata");
				break;
			case 1 :
				chboxState.setValue("In preparazione");
				break;
			default: chboxState.setValue("Confermata");
			};
			
			chboxState.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->  changeState(newValue) );
			
		
		
		}
	}
	
	
	private void changeState(String newValue) {
		if(newValue.equals("Confermata"))
			order.setState(data.OrderDeliveryState.CONFERMATA);
		if(newValue.equals("In preparazione"))
			order.setState(data.OrderDeliveryState.IN_PREPARAZIONE);
		if(newValue.equals("Consegnata"))
			order.setState(data.OrderDeliveryState.CONSEGNATA);
	}

	public static void initializeOrder(Order order) {
		orderInit=order;
	}

}
