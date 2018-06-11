package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Manages login page
 *
 * @author Mathyas Giudici
 */

public class LoginController implements FXController {

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
    HBox errorContent;


    /**
     * Validates form data before call a login request
     */
    @FXML
    public void validation() {
        StringBuilder stringBuilder = new StringBuilder();

        checkName(stringBuilder);
        checkPassword(stringBuilder);
        checkServer(stringBuilder);

        checkConnection(rmiRadio, socketRadio, requestPort, objectPort, stringBuilder);

        if (stringBuilder.toString().equals(""))
            this.loginCall();
        else {
            String returnString = "ERRORI:\n" + stringBuilder;
            error.setText(returnString);
            errorContent.setBackground(new Background(new BackgroundFill(Color.valueOf("#D11E22"), null, null)));
            errorContent.setMinHeight(error.getHeight());
        }
    }

    /**
     * Calls a login request
     */
    private void loginCall() {
        //Save information in JavaFXApp
        JavaFXStageProducer.getApp().tryLogin(name.getText(), rmiRadio.isSelected());

        String loginResult;
        //Call View Actions
        if (rmiRadio.isSelected()) {
            loginResult = JavaFXStageProducer.getApp().getViewActions().login(
                    name.getText(), password.getText(), this.newUser.isSelected(),
                    server.getText(), true, -1, Integer.parseInt(requestPort.getText()));
        } else {
            loginResult = JavaFXStageProducer.getApp().getViewActions().login(
                    name.getText(), password.getText(), this.newUser.isSelected(),
                    server.getText(), false, Integer.parseInt(objectPort.getText()), Integer.parseInt(requestPort.getText()));
        }

        if (loginResult == null) {
            JavaFXStageProducer.getApp().loginResult(true, null);
        } else {
            JavaFXStageProducer.getApp().loginResult(false, loginResult);
        }
    }

    /**
     * Checks connection's fields using ChangeLayerController methods
     *
     * @param rmiRadio      contains rmi button
     * @param socketRadio   contains socket button
     * @param requestPort   contains the request port's field
     * @param objectPort    contains the object port's field
     * @param stringBuilder contains the errors' string builder
     */
    private void checkConnection(RadioButton rmiRadio, RadioButton socketRadio, TextField requestPort, TextField objectPort, StringBuilder stringBuilder) {
        ChangeLayerController.checkConnection(rmiRadio, socketRadio, requestPort, objectPort, stringBuilder);
    }

    /**
     * Checks name's field isn't empty
     *
     * @param stringBuilder contains the errors' string builder
     */
    private void checkName(StringBuilder stringBuilder) {
        if (name.getText() == null || name.getText().equals("")) {
            stringBuilder.append("Nome non può essere vuoto\n");
        }
    }

    /**
     * Checks password's field isn't empty
     *
     * @param stringBuilder contains the errors' string builder
     */
    private void checkPassword(StringBuilder stringBuilder) {
        if (password.getText() == null || password.getText().equals("")) {
            stringBuilder.append("Password non può essere vuota\n");
        }
    }

    /**
     * Checks server's field isn't empty
     *
     * @param stringBuilder contains the errors' string builder
     */
    private void checkServer(StringBuilder stringBuilder) {
        if (server.getText() == null || server.getText().equals("")) {
            stringBuilder.append("Server non può essere vuoto\n");
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