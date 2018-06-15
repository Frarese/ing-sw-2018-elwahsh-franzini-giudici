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
        StringBuilder stringBuilder = new StringBuilder();

        checkConnection(rmiRadio, socketRadio, requestPort, objectPort, stringBuilder);

        if (stringBuilder.toString().equals(""))
            this.changeLayerCall();
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
     */
    private void changeLayerCall() {
        close.setDisable(true);

        if (rmiRadio.isSelected()) {
            JavaFXStageProducer.getApp().getViewActions().changeLayer(true, -1, Integer.parseInt(requestPort.getText()));
        } else {
            JavaFXStageProducer.getApp().getViewActions().changeLayer(false, Integer.parseInt(objectPort.getText()), Integer.parseInt(requestPort.getText()));
        }

        close();
    }

    /**
     * Manages close request
     */
    public void close() {
        ChangeLayerBox.close();
    }


    /**
     * Checks connection's fields
     *
     * @param rmiRadio      contains rmi button
     * @param socketRadio   contains socket button
     * @param requestPort   contains the request port's field
     * @param objectPort    contains the object port's field
     * @param stringBuilder contains the errors' string builder
     */
    static void checkConnection(RadioButton rmiRadio, RadioButton socketRadio, TextField requestPort, TextField objectPort, StringBuilder stringBuilder) {
        if (!rmiRadio.isSelected() && !socketRadio.isSelected()) {
            stringBuilder.append("Deve essere selezionato il tipo di connessione\n");
        } else {
            checkRequestPort(requestPort.getText(), stringBuilder);
            if (socketRadio.isSelected()) {
                checkObjectPort(objectPort.getText(), stringBuilder);
            }
        }
    }

    /**
     * Checks if the given request port is valid
     * @param req request port
     * @param stringBuilder string builder to use
     */
    private static void checkRequestPort(String req,StringBuilder stringBuilder){
        if (req == null || req.equals("")) {
            stringBuilder.append("Porta Richieste non può essere vuota\n");
        } else {
            try {
                int intRequestPort = Integer.parseInt(req);
                if (intRequestPort < 0) {
                    stringBuilder.append("Porta Richieste deve contenere un numero positivo\n");
                }
            } catch (Exception e) {
                stringBuilder.append("Porta Richieste non contiene un numero\n");
            }
        }
    }

    /**
     * Checks if the given request port is valid
     * @param obj request port
     * @param stringBuilder string builder to use
     */
    private static void checkObjectPort(String obj,StringBuilder stringBuilder){
        if (obj == null || obj.equals("")) {
            stringBuilder.append("Porta Oggetti non può essere vuota\n");
        } else {
            try {
                int intObjectPort = Integer.parseInt(obj);
                if (intObjectPort < 0) {
                    stringBuilder.append("Porta Oggetti deve contenere un numero positivo\n");
                }
            } catch (Exception e) {
                stringBuilder.append("Porta Oggetti non contiene un numero\n");
            }
        }
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
