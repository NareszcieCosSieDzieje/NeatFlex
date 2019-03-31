package GUI;

import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Series;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class TableViewMovie extends TableViewItem {


    public TableView<Movie> movieTable;
    public TableColumn<Movie, String> nameColumn;
    public TableColumn<Movie, String> descriptionColumn;
    public TableColumn<Movie, LocalDate> productionColumn;
    public TableColumn<Movie, String> durationColumn;
    public TableColumn<Movie, String> distributorColumn;
    public TableColumn<Movie, String> genreColumn;
    public TableColumn<Movie, String> countryColumn;
    public TableColumn<Movie, Number> priceColumn;
    public TableColumn<Movie, Number> ratingColumn;
    public TableColumn<Movie, Number> viewsColumn;
    public TableColumn<Movie, String> actorListColumn; //actorList
    public TableColumn<Movie, String> trailerColumn; //linki do zwiastunow
    public TableColumn<Movie, String> timeLeftColumn; //czas do ogladania po zakupie
    public TableColumn<Movie, String> discountColumn; //promocja




    @FXML
    public void initialize() {
        //=============================MOVIE=================================

        nameColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("description"));
        productionColumn.setCellValueFactory(new PropertyValueFactory<Movie, LocalDate>("productionDate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("duration"));
        distributorColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("distributor"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("genre"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("country"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Movie, Number>("price"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Movie, Number>("rating"));
        viewsColumn.setCellValueFactory(new PropertyValueFactory<Movie, Number>("views"));
        actorListColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("actorList"));
        trailerColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("trailerLink"));
        timeLeftColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("timeAvailable"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<Movie, String >("discount"));
        ObservableList<Movie> list = FXCollections.observableList(new ArrayList<>());
        list.add( (Movie)this.getObject() );
        this.movieTable.setItems(list);

        //episodesColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue().getTotalNumberOfEpisodes()));
        //callback
        //crewIdCol.setCellValueFactory(cellData -> cellData.getValue().crewIdProperty());
        //typeCOLUMN.setCellValueFactory(cellData -> cellData.getValue().CLASSMETHODTHATRETURNSGIVENVARIABLE());

    }


        public void plotGraph() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Plot.fxml"));
            Parent parent = loader.load();
            GraphPlotter controller = loader.getController();
            controller.populateChart( getService().getViewsDataForProduct((Movie)getObject()) );
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        }

        public void deleteObject(){
            markToDelete();
            ((Stage)movieTable.getScene().getWindow()).close();
        }

}


