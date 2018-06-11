package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.AlertBox;
import it.polimi.se2018.view.tools.fx.alert.ChangeLayerBox;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import it.polimi.se2018.view.tools.fx.alert.ShowInvitesBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages lobby page
 *
 * @author Mathyas Giudici
 */
public class LobbyController implements FXController {

    private static final String USER_STRING = "Utente";

    @FXML
    TableView<ScoreEntry> leaderBoardTable;

    @FXML
    TableView<ScoreEntry> connectedUserTable;

    @FXML
    TableView<ScoreEntry> inviteTable;

    @FXML
    Button matchmakingButton;

    private ObservableList<ScoreEntry> inviteUserList;

    /**
     * Creates lobby table's content
     */
    public void setTables() {
        if (!JavaFXStageProducer.getApp().getConnectedUsers().isEmpty()) {
            Platform.runLater(this::setConnectedTable);
        }
        if (!JavaFXStageProducer.getApp().getLeaderBoard().isEmpty()) {
            setLeaderBoardTable();
        }

        setInviteTable();
    }

    /**
     * Exits from server (logout)
     */
    public void logout() {
        if (ConfirmBox.displaySafeExit())
            JavaFXStageProducer.getApp().getViewActions().logout();
    }

    /**
     * Opens change layer's box
     */
    public void changeLayer() {
        ChangeLayerBox.display();
    }

    /**
     * Refreshes lobby
     */
    public void refresh() {
        JavaFXStageProducer.getApp().getViewActions().askLobby();
        JavaFXStageProducer.getApp().createLobby();
    }

    /**
     * Starts matchmaking
     */
    public void startMatchmaking() {
        matchmakingButton.setText("Stop Matchmaking");
        matchmakingButton.setDefaultButton(false);
        matchmakingButton.setOnAction(e -> stopMatchmaking());

        JavaFXStageProducer.getApp().getViewActions().joinMatchMaking();
    }

    /**
     * Stops matchmaking
     */
    private void stopMatchmaking() {
        matchmakingButton.setText("Matchmaking");
        matchmakingButton.setDefaultButton(true);
        matchmakingButton.setOnAction(e -> startMatchmaking());

        JavaFXStageProducer.getApp().getViewActions().leaveMatchMaking();
    }

    /**
     * Creates connected user table's content
     */
    private void setConnectedTable() {
        TableColumn<ScoreEntry, String> tableColumnName = new TableColumn<>(USER_STRING);
        tableColumnName.setPrefWidth(connectedUserTable.getPrefWidth());
        tableColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usn));

        connectedUserTable.getColumns().clear();
        connectedUserTable.getColumns().add(tableColumnName);

        ObservableList<ScoreEntry> observableList = FXCollections.observableArrayList();
        for (ScoreEntry scoreEntry : JavaFXStageProducer.getApp().getConnectedUsers()) {
            if (!JavaFXStageProducer.getApp().getOwnerPlayerName().equals(scoreEntry.usn)) {
                observableList.add(new ScoreEntry(scoreEntry.usn, scoreEntry.tot, scoreEntry.tot));
            }
        }

        connectedUserTable.setItems(observableList);

        connectedUserTable.setRowFactory(trEvent -> {
            TableRow<ScoreEntry> tableRow = new TableRow<>();

            tableRow.setOnDragDetected(ddEvent -> {
                if (!tableRow.isEmpty()) {
                    String user = tableRow.getTableView().getItems().get(tableRow.getIndex()).usn;
                    Dragboard dragboard = tableRow.startDragAndDrop(TransferMode.COPY);
                    dragboard.setDragView(tableRow.snapshot(null, null));
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(user);
                    dragboard.setContent(clipboardContent);

                    ddEvent.consume();
                }
            });
            return tableRow;
        });
    }

    /**
     * Creates leader board table's content
     */
    private void setLeaderBoardTable() {
        TableColumn<ScoreEntry, String> tableColumnName = new TableColumn<>(USER_STRING);
        tableColumnName.setPrefWidth(leaderBoardTable.getPrefWidth() / 2);
        tableColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usn));

        TableColumn<ScoreEntry, Integer> tableColumnPoints = new TableColumn<>("Punti");
        tableColumnPoints.setPrefWidth(leaderBoardTable.getPrefWidth() / 4);
        tableColumnPoints.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().tot));

        TableColumn<ScoreEntry, Integer> tableColumnWins = new TableColumn<>("Vittorie");
        tableColumnWins.setPrefWidth(leaderBoardTable.getPrefWidth() / 4);
        tableColumnWins.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().wins));

        leaderBoardTable.getColumns().clear();
        leaderBoardTable.getColumns().add(tableColumnName);
        leaderBoardTable.getColumns().add(tableColumnPoints);
        leaderBoardTable.getColumns().add(tableColumnWins);

        ObservableList<ScoreEntry> observableList = FXCollections.observableArrayList();
        observableList.addAll(JavaFXStageProducer.getApp().getLeaderBoard());

        leaderBoardTable.setItems(observableList);
    }

    /**
     * Creates invite's table and sets operations to catch new entries
     */
    private void setInviteTable() {
        TableColumn<ScoreEntry, String> tableColumnName = new TableColumn<>(USER_STRING);
        tableColumnName.setPrefWidth(inviteTable.getPrefWidth());
        tableColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usn));

        inviteTable.getColumns().clear();
        inviteTable.getColumns().add(tableColumnName);

        inviteUserList = FXCollections.observableArrayList();

        inviteTable.setItems(inviteUserList);

        inviteTable.setOnDragOver(doEvent -> {
            if (doEvent.getDragboard().hasString()) {
                doEvent.acceptTransferModes(TransferMode.COPY);
            }
        });

        inviteTable.setOnDragDropped(ddEvent -> {
            if (inviteUserList.size() < 3) {
                String user = ddEvent.getDragboard().getString();
                for (ScoreEntry scoreEntry : inviteUserList) {
                    if (scoreEntry.usn.equals(user)) {
                        AlertBox.attentionBox("Non puoi inserire lo stesso utente più volte in un invito");
                        return;
                    }
                }
                inviteUserList.add(new ScoreEntry(user, 0, 0));
            } else {
                AlertBox.attentionBox("Non puoi inserire più di 3 utenti in un invito");
            }
        });

        inviteTable.setRowFactory(trEvent -> {
            TableRow<ScoreEntry> tableRow = new TableRow<>();

            ContextMenu contextMenu = new ContextMenu();
            MenuItem delete = new MenuItem("Elimina");
            delete.setOnAction(event -> {
                inviteUserList.remove(inviteTable.getSelectionModel().getSelectedItem());
                event.consume();
            });
            contextMenu.getItems().add(delete);
            tableRow.setContextMenu(contextMenu);

            return tableRow;
        });
    }

    /**
     * Creates a new invite using people in invite's table
     */
    public void invite() {
        if (inviteUserList.isEmpty()) {
            AlertBox.attentionBox("Deve essere presente almeno una persona nell'invito");
        } else {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                try {
                    list.add(inviteUserList.get(i).usn);
                } catch (Exception e) {
                    list.add(null);
                }
            }
            MatchIdentifier matchIdentifier = new MatchIdentifier(JavaFXStageProducer.getApp().getOwnerPlayerName(), list.get(0), list.get(1), list.get(2));
            JavaFXStageProducer.getApp().getViewActions().pushInvite(matchIdentifier);
            clearInvite();
        }

    }

    /**
     * Clears invite's table
     */
    public void clearInvite() {
        inviteUserList.clear();
    }

    /**
     * Shows invites
     */
    public void showInvites() {
        ShowInvitesBox.display();
    }
}
