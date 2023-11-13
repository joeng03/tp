package seedu.pharmhub.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.pharmhub.commons.util.StringUtil;
import seedu.pharmhub.model.order.Order;

/**
 * A ui for the order display that is displayed in the info display box.
 */
public class OrderDisplay extends UiPart<Region> {

    private static final String FXML = "OrderDisplay.fxml";

    private Order order;
    @FXML
    private TextArea resultDisplay;
    @FXML
    private Label orderNumber;
    @FXML
    private Label orderPerson;
    @FXML
    private Label orderDetails;
    @FXML
    private Label status;

    /**
     * Creates the order display.
     */
    public OrderDisplay(Order order) {
        super(FXML);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/OrderDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        orderNumber.setText(order.getOrderNumber().toString());
        orderPerson.setText(order.getPerson().getName().fullName);
        orderDetails.setText(StringUtil.setToStr(order.getMedicines()));
        status.setText(order.getStatus().toString());
        status.setWrapText(true);

        // Set the appropriate CSS style class based on the status
        String statusStyleClass = "status-" + order.getStatus().toString();
        status.getStyleClass().add(statusStyleClass);
    }
}
