package GUI;

import NeatFlexClasses.Simulation.Distributor;
import NeatFlexClasses.Simulation.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class TableViewUser extends TableViewItem {


    public TableView<User> userTable;
    public TableColumn<User, String> nameColumn;
    public TableColumn<User, LocalDate> birthColumn;
    public TableColumn<User, String> mailColumn;
    public TableColumn<User, String> cardColumn;
    public TableColumn<User, String> subscriptionColumn;
    public TableColumn<User, String> productsColumn;


    @FXML
    public void initialize() {
        //===========================UŻYTKOWNIK===============================

        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        birthColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("birthDate"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        cardColumn.setCellValueFactory(new PropertyValueFactory<User, String>("creditCard"));
        subscriptionColumn.setCellValueFactory(new PropertyValueFactory<User, String>("subscription"));
        productsColumn.setCellValueFactory( cellDataFeatures -> new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue().getStringProducts()));
        ObservableList<User> list = FXCollections.observableList(new ArrayList<>());
        list.add((User)this.getObject());
        this.userTable.setItems(list);
    }

    public void deleteObject(){
        markToDelete();
        ((Stage)userTable.getScene().getWindow()).close();
    }


}
