package GUI;

import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchViewController {


    public TableView<Product> searchTable;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, String> descriptionColumn;
    public TableColumn<Product, LocalDate> productionColumn;
    public TableColumn<Product, String> durationColumn;
    public TableColumn<Product, String> distributorColumn;
    public TableColumn<Product, String> genreColumn;
    public TableColumn<Product, String> countryColumn;
    public TableColumn<Product, Number> priceColumn;
    public TableColumn<Product, Number> ratingColumn;
    public TableColumn<Product, Number> viewsColumn;

    static private ObservableList<Product> searchList;

    @FXML
    public void initialize() {
        //===========================UŻYTKOWNIK===============================
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        productionColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("productionDate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("duration"));
        distributorColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("distributor"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("genre"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("country"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Number>("price"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Product, Number>("rating"));
        viewsColumn.setCellValueFactory(new PropertyValueFactory<Product, Number>("views"));
        this.searchTable.setItems(searchList);
    }

    static public void setSearchList(ObservableList<Product> list){
        searchList=list;
    }

    static public void nullifySearchList(){
        searchList=null;
    }

}
