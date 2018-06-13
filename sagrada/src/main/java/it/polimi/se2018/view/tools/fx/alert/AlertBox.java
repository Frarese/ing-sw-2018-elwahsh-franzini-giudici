package it.polimi.se2018.view.tools.fx.alert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class to create alert box to show to user
 *
 * @author Mathyas Giudici
 */

public class AlertBox {

    /**
     * Class constructor
     */
    private AlertBox() {
        throw new IllegalStateException("Utility JavaFX class");
    }

    /**
     * Shows the alert box
     *
     * @param title   contains the alert box's title
     * @param message contains the message to show
     * @param image   @Nullable contains the image to show, if it's null AlertBox doesn't show image
     */
    private static void display(String title, String message, Image image) {
        Stage window = new Stage();

        //Locks event in this window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);

        window.setTitle(title);

        Label labelTitle = new Label();
        labelTitle.setText(title);
        labelTitle.setStyle("-fx-font-size: 36.0; -fx-font-family: System; -fx-font-weight: Bold");

        Label labelMessage = new Label();
        labelMessage.setText(message);

        Button close = new Button("Chiudi");
        close.setDefaultButton(true);
        close.setOnAction(e -> window.close());

        VBox container = new VBox(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.getChildren().addAll(labelTitle, labelMessage, close);
        container.setAlignment(Pos.CENTER);

        if (image != null) {
            ImageView imageView = new ImageView(image);
            container.getChildren().add(1, imageView);
        }

        Scene scene = new Scene(container);
        window.centerOnScreen();
        window.setScene(scene);

        //Current window must be close to come back to the caller
        window.showAndWait();
    }

    /**
     * Shows a notify alert box
     *
     * @param message contains the message to show
     */
    public static void notifyBox(String message) {
        AlertBox.display("Notifica", message, null);
    }

    /**
     * Shows a notify alert box
     *
     * @param message contains the message to show
     * @param image   @Nullable contains the image to show, if it's null AlertBox doesn't show image
     */
    public static void notifyWithImageBox(String message, Image image) {
        AlertBox.display("Notifica", message, image);
    }

    /**
     * Shows an attention alert box
     *
     * @param message contains the message to show
     */
    public static void attentionBox(String message) {
        AlertBox.display("Attenzione", message, null);
    }
}
