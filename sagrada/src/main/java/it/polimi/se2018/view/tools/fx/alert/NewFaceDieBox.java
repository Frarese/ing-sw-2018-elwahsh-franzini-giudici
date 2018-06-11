package it.polimi.se2018.view.tools.fx.alert;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class to create a box to show to user new face die choice
 *
 * @author Mathyas Giudici
 */

public class NewFaceDieBox extends GeneralBox {

    private static int returnValue;

    /**
     * Class constructor
     */
    private NewFaceDieBox() {
        super();
    }


    public static int display() {

        String title = "Scelta faccia dado";

        initStage(title, false, null);

        Label labelTitle = new Label(title);
        labelTitle.setText(title);
        labelTitle.setStyle("-fx-font-size: 36.0; -fx-font-family: System; -fx-font-weight: Bold");


        //Creates two buttons
        Button oneButton = new Button("1");
        Button twoButton = new Button("2");
        Button threeButton = new Button("3");
        Button fourButton = new Button("4");
        Button fiveButton = new Button("5");
        Button sixButton = new Button("6");


        oneButton.setOnAction(e -> {
            returnValue = 1;
            window.close();
        });
        twoButton.setOnAction(e -> {
            returnValue = 2;
            window.close();
        });
        threeButton.setOnAction(e -> {
            returnValue = 3;
            window.close();
        });
        fourButton.setOnAction(e -> {
            returnValue = 4;
            window.close();
        });
        fiveButton.setOnAction(e -> {
            returnValue = 5;
            window.close();
        });
        sixButton.setOnAction(e -> {
            returnValue = 6;
            window.close();
        });


        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton);
        buttonContainer.setAlignment(Pos.CENTER);

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
