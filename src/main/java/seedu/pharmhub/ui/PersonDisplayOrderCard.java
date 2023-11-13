package seedu.pharmhub.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;

/**
 * A UI component that displays information of a {@code Order}.
 */
public class PersonDisplayOrderCard extends UiPart<Region> {
    private static final String FXML = "PersonDisplayOrderListCard.fxml";

    public final Order order;

    // FXML Elements

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label orderNumber;
    @FXML
    private Label personName;
    @FXML
    private FlowPane medicines;
    @FXML
    private Label id;
    @FXML
    private Label status;

    /**
     * Creates a {@code OrderCode} with the given {@code Order} and index to display.
     */
    public PersonDisplayOrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        orderNumber.setText("Order #" + order.getOrderNumber());
        personName.setText(order.getPerson().getName().fullName);
        order.getMedicines().stream().map(Medicine::getMedicineName)
                .sorted()
                .forEach(medicine -> medicines.getChildren().add(new Label(medicine)));
        status.setText(order.getStatus().toString());
        status.setWrapText(true);

        // Set the appropriate CSS style class based on the status
        String statusStyleClass = "status-" + order.getStatus().toString();
        status.getStyleClass().add(statusStyleClass);
    }
}
