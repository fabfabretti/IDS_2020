package application;

import data.Order;
import data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class CartReviewController {
	protected static Order orderInit = null;
	protected Order order = null;

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
	@FXML
	private Text txtState;
	@FXML
	private Text txtProducts;
	@FXML
	private Text txtAddress;
	@FXML
	private ScrollPane scrollProducts;

	public void initialize() {
		order = orderInit;

		// Stringhe più carine di quelle di default!!
		String[] stringhePayment = { "Alla consegna", "PayPal", "Carta di credito" };
		String[] stringheTime = { "Mattina (08:00-11:00)", "Pranzo (11:00-14:00)", "Pomeriggio (14:00-17:00)",
				"Sera (17:00-20:00)" };
		String[] stringheState = { "Confermata", "In preparazione", "Consegnata" };

		// Info sull'ordine
		String format = String.format("Ordine #%04d", order.getOrderid());
		txtOrderId.setText(format);
		txtDate.setText(order.getDate().toString());
		format = String.format("€ %.2f", order.getCart().getTotal());
		txtTotal.setText(format);
		txtTime.setText(stringheTime[order.getTime().ordinal()]);
		txtPayment.setText(stringhePayment[order.getPayment().ordinal()]);
		txtPoints.setText("" + ((int) order.getCart().getTotal()));
		txtState.setText("" + stringheState[order.getState().ordinal()]);
		txtAddress.setText(order.getAddress());

		// Info sui prodotti; devo generare la stringa...
		String prodInfo = "";

		for (Integer i : order.getCart().getProducts().keySet()) {
			Product p = Globals.barCodeTable.get(i);
			prodInfo += order.getCart().getProducts().get(i) + " X " + p.getName() + " " + p.getWeight() + p.getUnit()
					+ "(€" + String.format("%.2f", order.getPrices().get(i)) + ")" + "\n\t\t\t\t €"
					+ String.format("%.2f", order.getPrices().get(i) * order.getCart().getProducts().get(i)) + "\n";
		}

		txtProducts.setText(prodInfo);

	}

	public static void initializeOrder(Order order) {
		orderInit = order;
	}

}
