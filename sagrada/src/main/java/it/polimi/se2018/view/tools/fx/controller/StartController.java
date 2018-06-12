package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Manages start page
 *
 * @author Mathyas Giudici
 */

public class StartController implements FXController,Initializable {

    @FXML
    ImageView imageView;

    /**
     * Recalls JavaFXApp and creates login page
     */
    @FXML
    public void loadLogin() {
        JavaFXStageProducer.getApp().startLogin(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setImage(new Image("/it/polimi/se2018/view/images/others/home.png"));
    }
}
