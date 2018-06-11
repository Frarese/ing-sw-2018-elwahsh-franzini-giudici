package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.AlertBox;
import it.polimi.se2018.view.tools.fx.alert.ShowInvitesBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Manages invites show page
 *
 * @author Mathyas Giudici
 */

public class ShowInvitesController {

    @FXML
    TableView<MatchIdentifier> table;

    /**
     * Sets content in the invites' table
     */
    public void setTable() {
        TableColumn<MatchIdentifier, String> tableColumnName = new TableColumn<>("Invito");
        tableColumnName.setMinWidth(table.getPrefWidth());
        tableColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));

        table.getColumns().clear();
        table.getColumns().add(tableColumnName);

        ObservableList<MatchIdentifier> invites = FXCollections.observableArrayList();

        invites.addAll(JavaFXStageProducer.getApp().getInvites());
        table.setItems(invites);

    }

    /**
     * Accepts the selected invite
     */
    public void acceptInvite() {
        if (table.getSelectionModel().isEmpty()) {
            AlertBox.attentionBox("Deve essere prima selezionato un invito");
        } else {
            int index = table.getSelectionModel().getSelectedIndex();
            JavaFXStageProducer.getApp().getViewActions().acceptInvite(JavaFXStageProducer.getApp().getInvites().get(index));
            close();
        }
    }

    /**
     * Closes invites' window
     */
    public void close() {
        ShowInvitesBox.close();
    }
}
