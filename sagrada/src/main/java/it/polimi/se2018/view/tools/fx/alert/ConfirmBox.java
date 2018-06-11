package it.polimi.se2018.view.tools.fx.alert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class to create confirm box to manage user choice (yes or no question)
 *
 * @author Mathyas Giudici
 */

public class ConfirmBox extends GeneralBox {

    private static boolean approve;

    /**
     * Class constructor
     */
    private ConfirmBox() {
        super();
    }

    /**
     * Shows the confirm box
     *
     * @param title   contains the alert box's title
     * @param message contains the message to show
     * @return contains the user's choice
     */
    public static boolean display(String title, String message) {

        initStage(title, false, null);

        Label labelTitle = new Label();
        labelTitle.setText(title);
        labelTitle.setStyle("-fx-font-size: 36.0; -fx-font-family: System; -fx-font-weight: Bold");

        Label labelMessage = new Label();
        labelMessage.setText(message);

        //Creates two buttons
        Button yesButton = new Button("Si");
        Button noButton = new Button("No");

        noButton.setDefaultButton(true);

        yesButton.setOnAction(e -> {
            approve = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            approve = false;
            window.close();
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(noButton, yesButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox container = new VBox(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.getChildren().addAll(labelTitle, labelMessage, buttonContainer);
        container.setAlignment(Pos.CENTER);

        Scene scene = new Scene(container);
        window.centerOnScreen();
        window.setScene(scene);

        //Current window must be close to come back to the caller
        window.showAndWait();

        return approve;
    }

    /**
     * Shows a confirm box for exit and close operations
     *
     * @return contains the user's choice
     */
    public static boolean displaySafeExit() {
        return ConfirmBox.display("Uscita", "Sei sicuro di voler uscire?");
    }
}
