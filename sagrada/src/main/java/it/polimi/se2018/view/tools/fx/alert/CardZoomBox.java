package it.polimi.se2018.view.tools.fx.alert;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * Class to create card zoom box to show to user
 *
 * @author Mathyas Giudici
 */

public class CardZoomBox extends GeneralBox {

    /**
     * Class constructor
     */
    private CardZoomBox() {
        super();
    }

    /**
     * Shows an image in the center of the screen (without window's box)
     *
     * @param image contains the image to show
     */
    public static void display(Image image) {

        initStage("", false, null);

        window.initModality(Modality.WINDOW_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setX(JavaFXStageProducer.getStage().getX());
        window.setY(JavaFXStageProducer.getStage().getY());

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
}