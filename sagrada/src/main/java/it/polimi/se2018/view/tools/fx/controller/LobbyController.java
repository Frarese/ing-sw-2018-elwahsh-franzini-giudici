package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.ChangeLayerBox;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Manages actions in lobby page
 *
 * @author Mathyas Giudici
 */
public class LobbyController extends FXController {

    @FXML
    TableView<ScoreEntry> leaderBoardTable;

    @FXML
    TableView<ScoreEntry> connectedUserTable;

    @FXML
    TableView<MatchIdentifier> inviteTable;

    @FXML
    Button inviteButton;

    @FXML
    Button listOfInviteButton;

    @FXML
    Button matchmakingButton;

    public void setTables() {
        if (!JavaFXStageProducer.getApp().getConnectedUsers().isEmpty()) {
            Platform.runLater(this::setConnectedTable);
        }
        if (!JavaFXStageProducer.getApp().getLeaderBoard().isEmpty()) {
            setLeaderBoardTable();
        }
    }

    public void logout() {
        if (ConfirmBox.display("Uscita", "Sei sicuro di voler uscire?"))
            JavaFXStageProducer.getApp().getViewActions().logout();
    }

    public void changeLayer() {
        ChangeLayerBox.display();
    }

    public void refresh() {
        JavaFXStageProducer.getApp().getViewActions().askLobby();
    }

    public void startMatchmaking() {
        matchmakingButton.setText("Stop Matchmaking");
        matchmakingButton.setDefaultButton(false);
        matchmakingButton.setOnAction(e -> stopMatchmaking());

        JavaFXStageProducer.getApp().getViewActions().joinMatchMaking();
    }

    private void stopMatchmaking() {
        matchmakingButton.setText("Matchmaking");
        matchmakingButton.setDefaultButton(true);
        matchmakingButton.setOnAction(e -> startMatchmaking());

        JavaFXStageProducer.getApp().getViewActions().leaveMatchMaking();
    }

    private void setConnectedTable() {
        TableColumn<ScoreEntry, String> tableColumnName = new TableColumn<>("Utente");
        tableColumnName.setPrefWidth(connectedUserTable.getPrefWidth());
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("usn"));

        connectedUserTable.getColumns().clear();
        connectedUserTable.getColumns().add(tableColumnName);

        ObservableList<ScoreEntry> observableList = FXCollections.observableArrayList();
        for (ScoreEntry scoreEntry : JavaFXStageProducer.getApp().getConnectedUsers()) {
            observableList.add(new ScoreEntry(scoreEntry.usn, scoreEntry.tot, scoreEntry.tot));
        }
        connectedUserTable.setItems(observableList);
    }

    private void setLeaderBoardTable() {
        TableColumn<ScoreEntry, String> tableColumnName = new TableColumn<>("Utente");
        tableColumnName.setPrefWidth(leaderBoardTable.getPrefWidth() / 2);
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("usn"));

        TableColumn<ScoreEntry, Integer> tableColumnPoints = new TableColumn<>("Punti");
        tableColumnPoints.setPrefWidth(leaderBoardTable.getPrefWidth() / 4);
        tableColumnPoints.setCellValueFactory(new PropertyValueFactory<>("tot"));

        TableColumn<ScoreEntry, Integer> tableColumnWins = new TableColumn<>("Vittorie");
        tableColumnWins.setPrefWidth(leaderBoardTable.getPrefWidth() / 4);
        tableColumnWins.setCellValueFactory(new PropertyValueFactory<>("wins"));

        leaderBoardTable.getColumns().clear();
        leaderBoardTable.getColumns().add(tableColumnName);
        leaderBoardTable.getColumns().add(tableColumnPoints);
        leaderBoardTable.getColumns().add(tableColumnWins);

        ObservableList<ScoreEntry> observableList = FXCollections.observableArrayList();
        observableList.addAll(JavaFXStageProducer.getApp().getLeaderBoard());

        leaderBoardTable.setItems(observableList);
    }
}
