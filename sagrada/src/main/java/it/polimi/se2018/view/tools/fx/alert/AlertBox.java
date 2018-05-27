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
     * Shows the alert box
     *
     * @param title   contains the alert box's title
     * @param message contains the message to show
     * @param image   @Nullable contains the image to show
     */
    public static void display(String title, String message, Image image) {
        Stage window = new Stage();

        //Locks event in this window
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMaxWidth(500);

        Label label = new Label();
        label.setText(message);

        Button close = new Button("Chiudi");
        close.setOnAction(e -> window.close());

        VBox container = new VBox(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.getChildren().addAll(label, close);
        container.setAlignment(Pos.CENTER);

        if (image != null) {
            ImageView imageView = new ImageView(image);
            container.getChildren().add(0, imageView);
        }

        Scene scene = new Scene(container);
        window.setScene(scene);

        //Current window must be close to come back to father
        window.showAndWait();
    }
}
