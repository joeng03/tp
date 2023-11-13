package seedu.pharmhub.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.model.medicine.Medicine;

/**
 * Panel containing the list of medicines.
 */
public class MedicineListPanel extends UiPart<Region> {
    private static final String FXML = "MedicineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    private final MainWindow mw;

    @javafx.fxml.FXML
    private ListView<Medicine> medicineListView;

    /**
     * Creates a {@code MedicineListPanel} with the given {@code ObservableList}.
     */
    public MedicineListPanel(MainWindow mw, ObservableList<Medicine> medicineList) {
        super(FXML);
        this.mw = mw;
        medicineListView.setItems(medicineList);
        medicineListView.setCellFactory(listView -> new MedicineListPanel.MedicineListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Medicine} using an {@code MedicineCard}.
     */
    class MedicineListViewCell extends ListCell<Medicine> {
        @Override
        protected void updateItem(Medicine medicine, boolean empty) {
            super.updateItem(medicine, empty);

            if (empty || medicine == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MedicineCard(medicine, getIndex() + 1).getRoot());
            }
        }
    }
}
