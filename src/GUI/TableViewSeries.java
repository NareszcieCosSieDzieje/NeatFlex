package GUI;

import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Series;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class TableViewSeries extends TableViewItem {

    public TableView<Series> seriesTable;
    public TableColumn<Series, String> nameColumn;
    public TableColumn<Series, String[]> descriptionColumn;
    public TableColumn<Series, LocalDate> productionColumn;
    public TableColumn<Series, Duration> durationColumn;
    public TableColumn<Series, String> distributorColumn;
    public TableColumn<Series, String> genreColumn;
    public TableColumn<Series, String> countryColumn;
    public TableColumn<Series, Number> priceColumn;
    public TableColumn<Series, Number> ratingColumn;
    public TableColumn<Series, Number> viewsColumn;
    public TableColumn<Series, Number> episodesColumn;
    public TableColumn<Series, String> discountColumn;
    public TableColumn<Series, String> actorListColumn;
    public TableColumn<Series, String> seasonListColumn;


    @FXML
    public void initialize(){
        //==============================SERIES===============================
        nameColumn.setCellValueFactory(new PropertyValueFactory<Series, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Series, String[]>("description"));
        productionColumn.setCellValueFactory(new PropertyValueFactory<Series, LocalDate>("productionDate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Series, Duration>("duration"));
        distributorColumn.setCellValueFactory(new PropertyValueFactory<Series, String>("distributor"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Series, String>("genre"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Series, String>("country"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Series, Number>("price"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Series, Number>("rating"));
        viewsColumn.setCellValueFactory(new PropertyValueFactory<Series, Number>("views"));
        episodesColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue().getTotalNumberOfEpisodes()));
        discountColumn.setCellValueFactory(new PropertyValueFactory<Series, String >("discount"));
        actorListColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue().getStringActors()));
        seasonListColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue().getStringSeasons()));
        ObservableList<Series> list = FXCollections.observableList(new ArrayList<>());
        list.add( (Series)this.getObject() );
        this.seriesTable.setItems(list);
    }

    public void plotGraph() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Plot.fxml"));
        Parent parent = loader.load();
        GraphPlotter controller = loader.getController();
        controller.populateChart( getService().getViewsDataForProduct((Series)getObject()) );
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void deleteObject(){
        markToDelete();
        ((Stage)seriesTable.getScene().getWindow()).close();
    }

    /*public void goToMainScene() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("mainGUI.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = Main.getPrimaryStage();
        stage.setScene(scene);
        stage.show();
        seriesTable.getItems().clear();
    }*/




}
