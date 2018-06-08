package it.polimi.se2018.view.tools.fx.controller;


import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.ChangeLayerBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Manages change layer page
 *
 * @author Mathyas Giudici
 */

public class ChangeLayerController {

    @FXML
    RadioButton socketRadio;
    @FXML
    RadioButton rmiRadio;
    @FXML
    TextField requestPort;
    @FXML
    TextField objectPort;
    @FXML
    Button close;
    @FXML
    Button change;
    @FXML
    HBox errorContent;
    @FXML
    Label error;

    /**
     * Validates form data before call a login request
     */
    @FXML
    public void validation() {
        boolean isRMI = false;
        int intRequestPort = -1;
        int intObjectPort = -1;

        StringBuilder stringBuilder = new StringBuilder();

        if (!rmiRadio.isSelected() && !socketRadio.isSelected()) {
            stringBuilder.append("Deve essere selezionato il tipo di connessione\n");
        } else {
            isRMI = true;
            intRequestPort = checkRequestPort(requestPort, stringBuilder);
            if (socketRadio.isSelected()) {
                isRMI = false;
                intObjectPort = checkObjectPort(objectPort, stringBuilder);
            }
        }

        if (stringBuilder.toString().equals(""))
            this.changeLayerCall(isRMI, intRequestPort, intObjectPort);
        else {
            String returnString = "ERRORI:\n" +
                    stringBuilder;
            Platform.runLater(() -> {
                error.setText(returnString);
                errorContent.setBackground(new Background(new BackgroundFill(Color.valueOf("#D11E22"), null, null)));
                errorContent.setMinHeight(error.getHeight());
            });
        }
    }

    /**
     * Tries a change layer request in the JavaFXApp
     *
     * @param isRMI       contains connection's type
     * @param requestPort contains request port's number
     * @param objectPort  contains object port's number
     */
    private void changeLayerCall(boolean isRMI, int requestPort, int objectPort) {
        JavaFXStageProducer.getApp().getViewActions().changeLayer(isRMI, objectPort, requestPort);
    }

    /**
     * Manages close request
     */
    public void close() {
        ChangeLayerBox.close();
    }

    /**
     * Checks request port field isn't empty and contains a number
     *
     * @param requestPort   contains the field to check
     * @param stringBuilder contains the errors' string builder
     */
    private int checkRequestPort(TextField requestPort, StringBuilder stringBuilder) {
        return LoginController.checkRequestPort(requestPort, stringBuilder);
    }

    /**
     * Checks object port field isn't empty and contains a number
     *
     * @param objectPort    contains the field to check
     * @param stringBuilder contains the errors' string builder
     */
    private int checkObjectPort(TextField objectPort, StringBuilder stringBuilder) {
        return LoginController.checkObjectPort(objectPort, stringBuilder);
    }

    /**
     * Manages correct radio button selections
     */
    public void selectedRadioRMI() {
        rmiRadio.setSelected(true);
        socketRadio.setSelected(false);
        objectPort.setDisable(true);
    }

    /**
     * Manages correct radio button selections
     */
    public void selectedRadioSocket() {
        socketRadio.setSelected(true);
        rmiRadio.setSelected(false);
        objectPort.setDisable(false);
    }
}
