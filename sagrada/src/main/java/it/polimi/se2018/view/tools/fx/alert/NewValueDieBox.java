package it.polimi.se2018.view.tools.fx.alert;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class to create a box to show to user new value die choice
 *
 * @author Mathyas Giudici
 */

public class NewValueDieBox {

    private static int returnValue;

    /**
     * Class constructor
     */
    private NewValueDieBox() {
        throw new IllegalStateException("Utility JavaFX class");
    }


    public static int display(int low, int high) {
        Stage window = new Stage();

        //Locks event in this window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);

        String title = "Scelta valore dado";
        window.setTitle(title);

        Label labelTitle = new Label(title);
        labelTitle.setText(title);
        labelTitle.setStyle("-fx-font-size: 36.0; -fx-font-family: System; -fx-font-weight: Bold");


        //Creates two buttons
        Button lowButton = new Button(Integer.toString(low));
        Button highButton = new Button(Integer.toString(high));


        lowButton.setOnAction(e -> {
            returnValue = low;
            window.close();
        });
        highButton.setOnAction(e -> {
            returnValue = high;
            window.close();
        });


        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(lowButton, highButton);
        buttonContainer.setAlignment(Pos.CENTER);

        lowButton.setPrefWidth(buttonContainer.getPrefWidth() / 2);
        highButton.setPrefWidth(buttonContainer.getPrefWidth() / 2);

        lowButton.setPrefHeight(buttonContainer.getPrefHeight() / 2);
        highButton.setPrefHeight(buttonContainer.getPrefHeight() / 2);

        VBox container = new VBox(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.getChildren().addAll(labelTitle, buttonContainer);
        container.setAlignment(Pos.CENTER);


        Scene scene = new Scene(container);
        window.centerOnScreen();
        window.setScene(scene);
        window.setOnCloseRequest(Event::consume);

        //Current window must be close to come back to the caller
        window.showAndWait();


        return returnValue;
    }
}
