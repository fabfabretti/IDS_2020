package application;

import java.time.LocalDate;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class UserDatePickerController extends Controller{

	@FXML
	public JFXComboBox<String> chboxTime;
	@FXML
	public JFXDatePicker calendarDate;
	@FXML
	public Text txtError;

	public void initialize(){
		
		calendarDate.setEditable(false);
		calendarDate.getEditor().setEditable(false);
		
		chboxTime.getItems().add("Mattina (08:00-11:00)");
		chboxTime.setValue("Mattina (08:00-11:00)");
		chboxTime.getItems().add("Pranzo (11:00-14:00)");
		chboxTime.getItems().add("Pomeriggio (14:00-17:00)");
		chboxTime.getItems().add("Sera (17:00-20:00)");
		
		txtError.setVisible(false);

	}

	public void confirmOrder(ActionEvent ae) {
		
		data.OrderDeliveryTime time = data.OrderDeliveryTime.MATTINA;
		
		String value = chboxTime.getValue();
		if(value.equals("Pranzo (11:00-14:00)"))
			time = data.OrderDeliveryTime.PRANZO;
		if(value.equals("Pomeriggio (14:00-17:00)"))
			time = data.OrderDeliveryTime.POMERIGGIO;
		if(value.equals("Sera (17:00-20:00)"))
			time = data.OrderDeliveryTime.SERA;

		
		if(calendarDate.getValue().isBefore(LocalDate.now())) {
			txtError.setVisible(true);
			txtError.setText("Errore: Non possediamo macchine del tempo!");
			return;
		}
		
		Globals.currentOrder.setTime(time);
		Globals.currentOrder.setDate(calendarDate.getValue());
		
		System.out.println("[✓] Ordine confermato!" );
		
		Globals.cart.flushCart();
		Globals.cartController.initialize();
		Globals.cartController.orderConfirmed();
		
		JsonSaver.saveHistory();
		closeUI(ae);
	}

}
