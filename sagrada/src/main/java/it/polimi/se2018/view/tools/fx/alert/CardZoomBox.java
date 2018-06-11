package it.polimi.se2018.view.tools.fx.alert;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class to create card zoom box to show to user
 *
 * @author Mathyas Giudici
 */

public class CardZoomBox {

    /**
     * Class constructor
     */
    private CardZoomBox() {
        throw new IllegalStateException("Utility JavaFX class");
    }

    private static Stage window;

    /**
     * Shows an image in the center of the screen (without window's box)
     *
     * @param image contains the image to show
     */
    public static void display(Image image) {
        window = new Stage();

        //Locks event in this window
        window.centerOnScreen();
        window.initStyle(StageStyle.UNDECORATED);

        window.setMinHeight(300);

        VBox container = new VBox();
        ImageView imageView = new ImageView(image);
        container.getChildren().add(imageView);
        window.setScene(new Scene(container));

        container.setMinWidth(300);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        window.show();
    }

    /**
     * Closes the window
     */
    public static void close() {
        window.close();

    }
}