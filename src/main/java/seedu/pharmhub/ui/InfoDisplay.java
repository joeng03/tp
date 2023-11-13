package seedu.pharmhub.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A ui for the Info Display that is displayed at the right side of the application
 */
public class InfoDisplay extends UiPart<Region> {
    private static final String FXML = "InfoDisplay.fxml";

    @FXML
    private StackPane infoContent;

    public InfoDisplay() {
        super(FXML);
    }

    /**
     * Attaches the given UI component onto the InfoDisplay, replacing any current component mounted
     * @param component UI component to be attached
     */
    public void attach(UiPart<Region> component) {
        infoContent.getChildren().clear();
        infoContent.getChildren().add(component.getRoot());
    }

}
