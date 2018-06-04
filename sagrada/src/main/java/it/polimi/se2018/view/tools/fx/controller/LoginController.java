package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Manages actions in login page
 *
 * @author Mathyas Giudici
 */

public class LoginController extends FXController {

    @FXML
    TextField name;
    @FXML
    private PasswordField password;
    @FXML
    CheckBox newUser;
    @FXML
    TextField server;
    @FXML
    RadioButton socketRadio;
    @FXML
    RadioButton rmiRadio;
    @FXML
    TextField requestPort;
    @FXML
    TextField objectPort;
    @FXML
    Label error;


    @FXML
    public void validation() {
        boolean isRMI = false;
        int intRequestPort = -1;
        int intObjectPort = -1;

        StringBuilder stringBuilder = new StringBuilder();

        checkName(stringBuilder);
        checkPassword(stringBuilder);
        checkServer(stringBuilder);

        if (!rmiRadio.isSelected() && !socketRadio.isSelected()) {
            stringBuilder.append("Deve essere selezionato il tipo di connessione\n");
        } else {
            isRMI = true;
            intRequestPort = checkRequestPort(stringBuilder);
            if (socketRadio.isSelected()) {
                isRMI = false;
                intObjectPort = checkObjectPort(stringBuilder);
            }
        }

        if (stringBuilder.toString().equals(""))
            this.loginCall(isRMI, intRequestPort, intObjectPort);
        else {
            String returnString = "ERRORI:\n" +
                    stringBuilder;
            Platform.runLater(() -> error.setText(returnString));
        }
    }

    private void loginCall(boolean isRMI, int requestPort, int objectPort) {
        JavaFXStageProducer.getApp().getViewActions().login(
                name.getText(), password.getText(), this.newUser.isSelected(),
                server.getText(), isRMI, objectPort, requestPort);
    }

    private void checkName(StringBuilder stringBuilder) {
        if (name.getText() == null || name.getText().equals("")) {
            stringBuilder.append("Nome non può essere vuoto\n");
        }
    }

    private void checkPassword(StringBuilder stringBuilder) {
        if (password.getText() == null || password.getText().equals("")) {
            stringBuilder.append("Password non può essere vuota\n");
        }
    }

    private void checkServer(StringBuilder stringBuilder) {
        if (server.getText() == null || name.getText().equals("")) {
            stringBuilder.append("Nome non può essere vuoto\n");
        }
    }

    private int checkRequestPort(StringBuilder stringBuilder) {
        if (requestPort.getText() == null || requestPort.getText().equals(""))
            stringBuilder.append("Porta Richieste non può essere vuota\n");

        int intRequestPort = -1;
        try {
            intRequestPort = Integer.parseInt(this.requestPort.getText());
        } catch (Exception e) {
            stringBuilder.append("Porta Richieste non contiene un numero");
        }
        return intRequestPort;
    }

    private int checkObjectPort(StringBuilder stringBuilder) {
        if (objectPort.getText() == null || objectPort.getText().equals(""))
            stringBuilder.append("Porta Oggetti non può essere vuota\n");

        int intObjectPort = -1;
        try {
            intObjectPort = Integer.parseInt(this.objectPort.getText());
        } catch (Exception e) {
            stringBuilder.append("Porta Oggetti non contiene un numero");
        }
        return intObjectPort;
    }

    public void selectedRadioRMI() {
        rmiRadio.setSelected(true);
        socketRadio.setSelected(false);
    }

    public void selectedRadioSocket() {
        socketRadio.setSelected(true);
        rmiRadio.setSelected(false);
    }
}