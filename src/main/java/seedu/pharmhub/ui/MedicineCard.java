package seedu.pharmhub.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.pharmhub.model.medicine.Medicine;


/**
 * A UI component that displays information of a {@code Medicine}.
 */
public class MedicineCard extends UiPart<Region> {
    private static final String FXML = "MedicineListCard.fxml";

    public final Medicine medicine;

    // FXML Elements

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label medicineName;
    @FXML
    private Label shortForm;
    @FXML
    private Label id;


    /**
     * Creates a {@code MedicineCode} with the given {@code Medicine} and index to display.
     */
    public MedicineCard(Medicine medicine, int displayedIndex) {
        super(FXML);
        this.medicine = medicine;
        id.setText(displayedIndex + ". ");
        medicineName.setText(medicine.getMedicineName());
        if (medicine.hasShortForm()) {
            shortForm.setText("Shortform: " + medicine.getShortForm());
        }
    }
}
