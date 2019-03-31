package GUI;

import NeatFlexClasses.Model.Discount;
import NeatFlexClasses.Model.Products.LiveStream;
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
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class TableViewLiveStream extends TableViewItem {


    public TableView<LiveStream> liveStreamTable;
    public TableColumn<LiveStream, String> nameColumn;
    public TableColumn<LiveStream, String> descriptionColumn;
    public TableColumn<LiveStream, String> durationColumn;
    public TableColumn<LiveStream, String> distributorColumn;
    public TableColumn<LiveStream, String> countryColumn;
    public TableColumn<LiveStream, Number> priceColumn;
    public TableColumn<LiveStream, Number> ratingColumn;
    public TableColumn<LiveStream, Number> viewsColumn;
    public TableColumn<LiveStream, OffsetDateTime> airDateColumn;
    public TableColumn<LiveStream, String> discountColumn;



    public void initialize(){
        //==============================STREAM===============================\\
        nameColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, String>("description"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, String>("duration"));
        distributorColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, String>("distributor"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, String>("country"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, Number>("price"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, Number>("rating"));
        viewsColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, Number>("views"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, String >("discount"));
        airDateColumn.setCellValueFactory(new PropertyValueFactory<LiveStream, OffsetDateTime >("airDate"));
        ObservableList<LiveStream> list = FXCollections.observableList(new ArrayList<>());
        list.add( (LiveStream)this.getObject() );
        this.liveStreamTable.setItems(list);
    }

    public void plotGraph() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Plot.fxml"));
        Parent parent = loader.load();
        GraphPlotter controller = loader.getController();
        controller.populateChart( getService().getViewsDataForProduct((LiveStream)getObject()) );
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void deleteObject(){
        markToDelete();
        ((Stage)liveStreamTable.getScene().getWindow()).close();
    }




}
