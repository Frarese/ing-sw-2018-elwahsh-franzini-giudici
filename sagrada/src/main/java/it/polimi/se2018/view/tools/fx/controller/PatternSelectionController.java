package it.polimi.se2018.view.tools.fx.controller;


import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.AlertBox;
import it.polimi.se2018.view.tools.fx.creators.FXGridViewCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

/**
 * Manages pattern selection page
 *
 * @author Mathyas Giudici
 */

public class PatternSelectionController implements FXController {

    @FXML
    RadioButton radioFirstPattern;
    @FXML
    VBox boxFirstPattern;

    @FXML
    RadioButton radioSecondPattern;
    @FXML
    VBox boxSecondPattern;

    @FXML
    RadioButton radioThirdPattern;
    @FXML
    VBox boxThirdPattern;

    @FXML
    RadioButton radioFourthPattern;
    @FXML
    VBox boxFourthPattern;

    @FXML
    Button useButton;

    private PatternView patternView1;

    private PatternView patternView2;

    private PatternView patternView3;

    private PatternView patternView4;


    public void showPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4) {
        this.patternView1 = pattern1;
        this.patternView2 = pattern2;
        this.patternView3 = pattern3;
        this.patternView4 = pattern4;

        String favourLabel = " - FAVORI: ";
        String bgColor = "BLACK";
        radioFirstPattern.setText(pattern1.patternName + favourLabel + pattern1.favours);
        radioSecondPattern.setText(pattern2.patternName + favourLabel + pattern2.favours);
        radioThirdPattern.setText(pattern3.patternName + favourLabel + pattern3.favours);
        radioFourthPattern.setText(pattern4.patternName + favourLabel + pattern4.favours);

        boxFirstPattern.getChildren().add(new FXGridViewCreator(null, pattern1.template, bgColor).display());
        boxSecondPattern.getChildren().add(new FXGridViewCreator(null, pattern2.template, bgColor).display());
        boxThirdPattern.getChildren().add(new FXGridViewCreator(null, pattern3.template, bgColor).display());
        boxFourthPattern.getChildren().add(new FXGridViewCreator(null, pattern4.template, bgColor).display());
    }

    /**
     * Manages correct radio button selections
     */
    @FXML
    public void selectedRadioFirstPattern() {
        radioFirstPattern.setSelected(true);
        radioSecondPattern.setSelected(false);
        radioThirdPattern.setSelected(false);
        radioFourthPattern.setSelected(false);
        useButton.setDisable(false);
    }

    /**
     * Manages correct radio button selections
     */
    @FXML
    public void selectedRadioSecondPattern() {
        radioFirstPattern.setSelected(false);
        radioSecondPattern.setSelected(true);
        radioThirdPattern.setSelected(false);
        radioFourthPattern.setSelected(false);
        useButton.setDisable(false);
    }

    /**
     * Manages correct radio button selections
     */
    @FXML
    public void selectedRadioThirdPattern() {
        radioFirstPattern.setSelected(false);
        radioSecondPattern.setSelected(false);
        radioThirdPattern.setSelected(true);
        radioFourthPattern.setSelected(false);
        useButton.setDisable(false);
    }

    /**
     * Manages correct radio button selections
     */
    @FXML
    public void selectedRadioFourthPattern() {
        radioFirstPattern.setSelected(false);
        radioSecondPattern.setSelected(false);
        radioThirdPattern.setSelected(false);
        radioFourthPattern.setSelected(true);
        useButton.setDisable(false);
    }

    /**
     * Validates form data before call a login request
     */
    @FXML
    public void validation() {
        if (!radioFirstPattern.isSelected() && !radioSecondPattern.isSelected() && !radioThirdPattern.isSelected() && !radioFourthPattern.isSelected())
            useButton.setDisable(true);
        else {
            appRecall();
            useButton.setDisable(true);
            AlertBox.notifyBox("Attesa inizio partita");
        }
    }

    /**
     * Tries a selectedPattern request in the JavaFXApp
     */
    private void appRecall() {
        if (radioFirstPattern.isSelected())
            JavaFXStageProducer.getApp().getViewActions().selectedPattern(patternView1.patternName);
        else if (radioSecondPattern.isSelected())
            JavaFXStageProducer.getApp().getViewActions().selectedPattern(patternView2.patternName);
        else if (radioThirdPattern.isSelected())
            JavaFXStageProducer.getApp().getViewActions().selectedPattern(patternView3.patternName);
        else if (radioFourthPattern.isSelected())
            JavaFXStageProducer.getApp().getViewActions().selectedPattern(patternView4.patternName);
    }
}
