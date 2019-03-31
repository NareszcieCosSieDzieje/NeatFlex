package GUI;

import NeatFlexClasses.Model.Contract;
import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import NeatFlexClasses.Simulation.Distributor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class TableViewDistributor extends TableViewItem {


    public TableView<Distributor> distributorTable;
    public TableColumn<Distributor, String> nameColumn;
    public TableColumn<Distributor, String> contractColumn;
    public TableColumn<Distributor, String> productsColumn;

    @FXML
    public void initialize(){

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contractColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue().getStringContract()));
        productsColumn.setCellValueFactory( cellDataFeatures -> new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue().getStringProducts()));
        ObservableList<Distributor> list = FXCollections.observableList(new ArrayList<>());
        list.add( (Distributor)this.getObject() );
        this.distributorTable.setItems(list);

    }

    public void deleteObject(){
        markToDelete();
        ((Stage)distributorTable.getScene().getWindow()).close();
    }



}
