package application;

import data.Order;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CartReviewController {
	protected static Order  orderInit = null;
	protected Order  order = null;

	@FXML
	private Text txtOrderId;
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
	
	public void initialize(){
		order=orderInit;

		String[] stringhePayment = {"Alla consegna", "PayPal", "Carta di credito"};
		String[] stringheTime = {"Mattina (08:00-11:00)", "Pranzo (11:00-14:00)", "Pomeriggio (14:00-17:00)","Sera (17:00-20:00)"};


		txtOrderId.setText(String.format("Ordine #%5d", order.getOrderid()));
		txtDate.setText(order.getDate().toString());
		txtTime.setText(stringheTime[order.getTime().ordinal()]);
		txtPayment.setText(stringhePayment[order.getPayment().ordinal()]);
		txtPoints.setText(""+((int)order.getCart().getTotal()));	
	}

	public static void initializeOrder(Order order) {
		orderInit=order;
	}

}
