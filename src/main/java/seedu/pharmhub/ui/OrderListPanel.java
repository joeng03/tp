package seedu.pharmhub.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.model.order.Order;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    private final MainWindow mw;

    @javafx.fxml.FXML
    private ListView<Order> orderListView;

    /**
     * Creates a {@code OrderListPanel} with the given {@code ObservableList}.
     */
    public OrderListPanel(MainWindow mw, ObservableList<Order> orderList) {
        super(FXML);
        this.mw = mw;
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListPanel.OrderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Order} using an {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);
            super.setOnMouseClicked(event -> {
                logger.info(String.format("Order #%s clicked", order.getOrderNumber()));
                mw.handleDisplayInfo(order);
            });

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }
}
