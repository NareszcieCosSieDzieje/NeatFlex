package GUI;
import NeatFlexClasses.Model.Products.LiveStream;
import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import NeatFlexClasses.Model.Products.Series;
import NeatFlexClasses.Simulation.Distributor;
import NeatFlexClasses.Simulation.NeatFlexService;
import NeatFlexClasses.Simulation.Serializer;
import NeatFlexClasses.Simulation.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.util.EventObject;

// REDIRECT SYSTEM OUTS TO TEXT AREA!!!!
// JAKOS MUSZE ZBINDOWAC TE LISTY PO PROSTU


//NIE WIADOMO JAK WATKI BEDA UPDATOWAC TABELE, MOZE BEDZIE TRZEVEA KOMBINOWAC DALEJ

public class Controller {

    public TableView<Movie> movieTable;
    public TableView<Series> seriesTable;
    public TableView<LiveStream> liveStreamTable;
    public TableView<Distributor> distributorTable;
    public TableView<User> userTable;

    public TableColumn<Movie, String> movieColumn;
    public TableColumn<Movie, String> seriesColumn;
    public TableColumn<Movie, String> streamsColumn;
    public TableColumn<Movie, String> distributorColumn;
    public TableColumn<Movie, String> userColumn;
    public ChoiceBox newObjectChoiceBox;

    public Label currentIncomeLabel;
    public Label totalProductsLabel;
    public Label currentMonthLabel;
    public Label currentDayLabel;
    public TextField searchText;

    //-----------------------------------------MEMBERS-----------------------------------\\

    private NeatFlexService vodService;
    private Thread vodThread;


    @FXML
    public void initialize() throws InterruptedException {


        this.vodService = new NeatFlexService();
        this.vodThread = new Thread(vodService);
        this.vodThread.setDaemon(true);
        this.vodThread.start();
        currentIncomeLabel.setText("");
        TableViewItem.setService(this.vodService);

        //==================================TABLES==========================================\\
        //Table binding and setting
        movieColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        seriesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        streamsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        distributorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        movieTable.setItems(this.vodService.getMoviesWrapper());
        seriesTable.setItems(this.vodService.getSeriesWrapper());
        liveStreamTable.setItems(this.vodService.getLiveStreamsWrapper());
        distributorTable.setItems(this.vodService.getDistributorsWrapper());
        userTable.setItems(this.vodService.getUsersWrapper());
        movieTable.setEditable(true);
        seriesTable.setEditable(true);
        liveStreamTable.setEditable(true);
        distributorTable.setEditable(true);
        userTable.setEditable(true);
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                (EventHandler) event -> {
                    currentIncomeLabel.setText("Income: " + vodService.getIncome());
                    currentMonthLabel.setText("month: " + vodService.getMonthCount());
                    currentDayLabel.setText("day: " + vodService.getDayCount());
                    if( (vodService.getMonthCount()>=3) && (vodService.getIncome()<0) ){ Platform.exit();}
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }



    public void buttonSerializeSimulation() {
        this.vodService.interruptAllThreads();
        this.vodThread.interrupt();
        this.vodService.prepareToSerialize();
        try {
            Serializer.serializeSimulation(this.vodService);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.vodThread = new Thread(this.vodService);
        this.vodThread.start();
        this.vodService.startAllThreads();
    }

    public void buttonDeserializeSimulation() {
        if (vodThread.isAlive()) {
            this.vodService.interruptAllThreads();
            this.vodThread.interrupt();
        }
        movieTable.getItems().clear();
        seriesTable.getItems().clear();
        liveStreamTable.getItems().clear();
        distributorTable.getItems().clear();
        userTable.getItems().clear();
        this.vodService = Serializer.deserializeSimulation();
        this.vodThread = new Thread(this.vodService);
        this.vodService.deserializeObservableLists();
        this.vodThread.start();
        this.vodService.startAllThreads();
        movieTable.setItems(this.vodService.getMoviesWrapper());
        seriesTable.setItems(this.vodService.getSeriesWrapper());
        liveStreamTable.setItems(this.vodService.getLiveStreamsWrapper());
        distributorTable.setItems(this.vodService.getDistributorsWrapper());
        userTable.setItems(this.vodService.getUsersWrapper());
        movieTable.refresh();
        seriesTable.refresh();
        liveStreamTable.refresh();
        distributorTable.refresh();
        userTable.refresh();
        TableViewItem.setService(this.vodService);
    }

    public void buttonCreateObject(ActionEvent actionEvent) {
        String objectName = (String) newObjectChoiceBox.getSelectionModel().getSelectedItem();
        if (objectName == null) {
            // msg
        } else if (objectName.equals("Movie")) {
            this.vodService.addRandMovie();
        } else if (objectName.equals("Series")) {
            this.vodService.addRandSeries();
        } else if (objectName.equals("LiveStream")) {
            this.vodService.addRandLiveStream();
        } else if (objectName.equals("User")) {
            this.vodService.addUser();
        } else if (objectName.equals("Distributor")) {
            this.vodService.addDistributor();
        }
    }


    public void inspectMovie(TableColumn.CellEditEvent cellEditEvent) throws IOException {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        this.changeScene(selectedMovie);
    }

    public void inspectLiveStream(TableColumn.CellEditEvent cellEditEvent) throws IOException {
        LiveStream selectedLiveStream = liveStreamTable.getSelectionModel().getSelectedItem();
       this.changeScene(selectedLiveStream);
    }

    public void inspectSeries(TableColumn.CellEditEvent cellEditEvent) throws IOException {
        Series selectedSeries = seriesTable.getSelectionModel().getSelectedItem();
        this.changeScene(selectedSeries);
    }

    public void inspectUser(TableColumn.CellEditEvent cellEditEvent) throws IOException {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        this.changeScene(selectedUser);
    }

    public void inspectDistributor(TableColumn.CellEditEvent cellEditEvent) throws IOException {
        Distributor selectedDistributor = distributorTable.getSelectionModel().getSelectedItem();
        this.changeScene(selectedDistributor);
    }


    public void changeScene(Object object) throws IOException {
        if (object != null) {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            if (object instanceof Movie) {
                loader.setLocation(getClass().getResource("TableViewMovie.fxml"));
                TableViewItem.initialize(object);
                parent = loader.load();
            } else if (object instanceof LiveStream) {
                loader.setLocation(getClass().getResource("TableViewLiveStream.fxml"));
                TableViewItem.initialize(object);
                parent = loader.load();
            } else if (object instanceof Series) {
                loader.setLocation(getClass().getResource("TableViewSeries.fxml"));
                TableViewItem.initialize(object);
                parent = loader.load();
            } else if (object instanceof User) {
                loader.setLocation(getClass().getResource("TableViewUser.fxml"));
                TableViewItem.initialize(object);
                parent = loader.load();
            } else if (object instanceof Distributor) {
                loader.setLocation(getClass().getResource("TableViewDistributor.fxml"));
                 TableViewItem.initialize(object);
                 parent = loader.load();
            }
            if(parent==null){
              //  throw NullPointerException;
            }
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            if(TableViewItem.wasItemDeleted()){
               this.vodService.removeObject(TableViewItem.getObject());
            }
        }
    }


    public void search() throws IOException{
        ObservableList<Product> nameList = this.vodService.searchByProductName(searchText.getText());
        ObservableList<Product> actorList = this.vodService.searchProductsByActor(searchText.getText());
        searchText.clear();
        if (nameList == null && actorList == null){
            return;
        }
        if( nameList != null ){
            SearchViewController.setSearchList(nameList);
        }
        else {
            SearchViewController.setSearchList(actorList);
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SearchView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        SearchViewController.nullifySearchList();
    }

}

