package it.polimi.se2018.view.tools.fx.controller;


import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

/**
 * Manages actions in pattern selection page
 *
 * @author Mathyas Giudici
 */

public class PatternSelectionController extends FXController {

    @FXML
    RadioButton radioFirstPattern;
    @FXML
    RadioButton radioSecondPattern;
    @FXML
    RadioButton radioThirdPattern;
    @FXML
    RadioButton radioFourthPattern;
    @FXML
    Label error;

    private PatternView patternView1;
    private PatternView patternView2;
    private PatternView patternView3;
    private PatternView patternView4;


    public void showPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4) {
        this.patternView1 = pattern1;
        this.patternView2 = pattern2;
        this.patternView3 = pattern3;
        this.patternView4 = pattern4;

        Platform.runLater(() -> {
            String favourLabel = " - fav: ";
            radioFirstPattern.setText(pattern1.patternName + favourLabel + pattern1.favours);
            radioSecondPattern.setText(pattern2.patternName + favourLabel + pattern2.favours);
            radioThirdPattern.setText(pattern3.patternName + favourLabel + pattern3.favours);
            radioFourthPattern.setText(pattern4.patternName + favourLabel + pattern4.favours);
        });

    }

    @FXML
    public void selectedRadioFirstPattern() {
        radioFirstPattern.setSelected(true);
        radioSecondPattern.setSelected(false);
        radioThirdPattern.setSelected(false);
        radioFourthPattern.setSelected(false);
    }

    @FXML
    public void selectedRadioSecondPattern() {
        radioFirstPattern.setSelected(false);
        radioSecondPattern.setSelected(true);
        radioThirdPattern.setSelected(false);
        radioFourthPattern.setSelected(false);
    }

    @FXML
    public void selectedRadioThirdPattern() {
        radioFirstPattern.setSelected(false);
        radioSecondPattern.setSelected(false);
        radioThirdPattern.setSelected(true);
        radioFourthPattern.setSelected(false);
    }

    @FXML
    public void selectedRadioFourthPattern() {
        radioFirstPattern.setSelected(false);
        radioSecondPattern.setSelected(false);
        radioThirdPattern.setSelected(false);
        radioFourthPattern.setSelected(true);
    }

    @FXML
    public void validation() {
        if (!radioFirstPattern.isSelected() && !radioSecondPattern.isSelected() && !radioThirdPattern.isSelected() && !radioFourthPattern.isSelected())
            Platform.runLater(() -> error.setText("Devi selezionare un pattern"));
        else {
            appRecall();
        }
    }

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
