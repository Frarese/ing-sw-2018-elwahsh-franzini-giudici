package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.ScoreViewCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * Class to create score visualizer in GUI
 *
 * @author Mathyas Giudici
 */

public class FXScoreViewCreator extends ScoreViewCreator<VBox> {

    /**
     * Class constructor
     */
    public FXScoreViewCreator() {
        super();
    }

    @Override
    public VBox display(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {

        this.createOrderList(matchIdentifier, player0, player1, player2, player3);

        VBox container = new VBox();
        container.setPadding(new Insets(10, 10, 10, 10));
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);

        container.setMinWidth(420);

        Label title = new Label("Punteggi finali");
        title.setStyle("-fx-font-size: 36.0; -fx-font-family: System; -fx-font-weight: Bold");
        container.getChildren().add(title);

        TableColumn<Pair<String, Integer>, String> tableColumnName = new TableColumn<>("Nome");
        tableColumnName.setMinWidth(200);
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("first"));
        TableColumn<Pair<String, Integer>, Integer> tableColumnScore = new TableColumn<>("Punteggio");
        tableColumnScore.setMinWidth(200);
        tableColumnScore.setCellValueFactory(new PropertyValueFactory<>("second"));

        TableView<Pair<String, Integer>> tableView = new TableView<>();
        tableView.getColumns().add(tableColumnName);
        tableView.getColumns().add(tableColumnScore);

        tableView.setItems(getScores());

        container.getChildren().add(tableView);

        Button returnButton = new Button("Torna alla lobby");
        returnButton.setDefaultButton(true);

        returnButton.setOnAction(event -> JavaFXStageProducer.getApp().cleanMatch());

        container.getChildren().add(returnButton);

        return container;
    }

    private ObservableList<Pair<String, Integer>> getScores() {
        ObservableList<Pair<String, Integer>> observableList = FXCollections.observableArrayList();
        for (Pair<String, Integer> score : scores) {
            observableList.add(new Pair<>(score.getFirst(), score.getSecond()));
        }
        return observableList;
    }
}
