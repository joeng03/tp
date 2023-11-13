package seedu.pharmhub.ui;

import java.util.EnumMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.pharmhub.commons.util.StringUtil;
import seedu.pharmhub.model.order.Status;
import seedu.pharmhub.model.person.Person;


/**
 * An ui for the status bar that is displayed at the header of the application.
 */
public class PersonDisplay extends UiPart<Region> {

    private static final String FXML = "PersonDisplay.fxml";

    @FXML
    private Label name;
    @FXML
    private Label address;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label email;
    @FXML
    private Label allergies;
    @FXML
    private VBox pendingOrderVbox;
    @FXML
    private VBox preparingOrderVbox;
    @FXML
    private VBox completedOrderVbox;
    @FXML
    private VBox cancelledOrderVbox;

    /**
     * Creates the person display.
     */
    public PersonDisplay(Person person) {
        super(FXML);

        name.setText(person.getName().fullName);
        address.setText(person.getAddress().value);
        phoneNumber.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        allergies.setText(StringUtil.setToStr(person.getAllergies()));

        EnumMap<Status.OrderStatus, Integer> indexes = new EnumMap<>(Status.OrderStatus.class);
        for (Status.OrderStatus e : Status.OrderStatus.values()) {
            indexes.put(e, 1);
        }

        person.getOrders().forEach(o -> {
            switch (o.getStatus().getStatus()) {
            case PENDING:
                pendingOrderVbox.getChildren().add(
                        new PersonDisplayOrderCard(o, indexes.get(Status.OrderStatus.PENDING)).getRoot());
                indexes.put(Status.OrderStatus.PENDING, indexes.get(Status.OrderStatus.PENDING) + 1);
                break;
            case PREPARING:
                preparingOrderVbox.getChildren().add(
                        new PersonDisplayOrderCard(o, indexes.get(Status.OrderStatus.PREPARING)).getRoot());
                indexes.put(Status.OrderStatus.PREPARING, indexes.get(Status.OrderStatus.PREPARING) + 1);
                break;
            case COMPLETED:
                completedOrderVbox.getChildren().add(
                        new PersonDisplayOrderCard(o, indexes.get(Status.OrderStatus.COMPLETED)).getRoot());
                indexes.put(Status.OrderStatus.COMPLETED, indexes.get(Status.OrderStatus.COMPLETED) + 1);
                break;
            case CANCELLED:
                cancelledOrderVbox.getChildren().add(
                        new PersonDisplayOrderCard(o, indexes.get(Status.OrderStatus.CANCELLED)).getRoot());
                indexes.put(Status.OrderStatus.CANCELLED, indexes.get(Status.OrderStatus.CANCELLED) + 1);
                break;
            default:
                throw new RuntimeException("Unknown order status type: " + o.getStatus().getStatus());
            }
        });
    }
}
