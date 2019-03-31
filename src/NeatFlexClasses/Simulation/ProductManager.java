package NeatFlexClasses.Simulation;

import NeatFlexClasses.Model.Products.LiveStream;
import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import NeatFlexClasses.Model.Products.Series;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ProductManager implements Serializable {

    //=======================================MEMBERS=======================================\\

    transient private ObservableList<Series> allSeriesList;
    transient private ObservableList<Movie> allMoviesList;
    transient private ObservableList<LiveStream> allLiveStreamsList;

    private Vector<Series> serializationSeries;
    private Vector<Movie> serializationMovies;
    private Vector<LiveStream> serializationStreamList;

    private HashMap<Product, LinkedHashMap<Integer,Integer>> productViews;


    public ProductManager(){
        serializationMovies=null;
        serializationSeries=null;
        serializationStreamList=null;
        productViews = new LinkedHashMap<>();
        allSeriesList = FXCollections.observableList(new Vector<>());
        allMoviesList = FXCollections.observableList(new Vector<>());
        allLiveStreamsList = FXCollections.observableList(new Vector<>());
    }

    public synchronized void updatePlotData(int currentDay){
        for (Map.Entry<Product, LinkedHashMap<Integer,Integer>> entry : productViews.entrySet()) {
            entry.getValue().put(Integer.valueOf(currentDay),Integer.valueOf(entry.getKey().getViews()));
        }
    }


    public static int getTotalViewsCount(ObservableList<Product> products){
        int count = 0;
        for(Product p : products) {
            count += p.getViews();
        }
        return count;
    }


    public void addSeries(Series s) {
        allSeriesList.add(s);
        this.productViews.put(s, new LinkedHashMap<>());
    }

    public  void addMovie(Movie m) {
        allMoviesList.add(m);
        this.productViews.put(m, new LinkedHashMap<>());
    }

    public void addLiveStream(LiveStream lS) {
        allLiveStreamsList.add(lS);
        this.productViews.put(lS, new LinkedHashMap<>());
    }


    public void removeSeries(Series s){
        s.getDistributor().removeProduct(s);
        allSeriesList.remove(s);
    }

    public void removeMovie(Movie m){
        m.getDistributor().removeProduct(m);
        allMoviesList.remove(m);
    }

    public void removeLiveStream(LiveStream lS){
        lS.getDistributor().removeProduct(lS);
        allLiveStreamsList.remove(lS);
    }


    public int getProductCount(){
        return allSeriesList.size()+allLiveStreamsList.size()+allMoviesList.size();
    }


    public boolean containsGivenProduct(Product p){
        return ( this.allLiveStreamsList.contains(p) || this.allMoviesList.contains(p) || this.allSeriesList.contains(p) );
    }

    public Product getRandomProduct(){
        int missCount = 0;
        int ran = RandomGenerator.randomWithRange(1, 3);
        while(true) {
            if (missCount == 3)
                return null;
            if (ran == 1) {
                if(allSeriesList.isEmpty()) {
                    missCount++;
                    ran=2;
                    continue;
                }
                return allSeriesList.get(RandomGenerator.randomWithRange(0, allSeriesList.size() - 1));
            } else if (ran == 2) {
                if(allLiveStreamsList.isEmpty()) {
                    missCount++;
                    ran=3;
                    continue;
                }
                return allLiveStreamsList.get(RandomGenerator.randomWithRange(0, allLiveStreamsList.size() - 1));
            } else if (ran == 3) {
                if(allMoviesList.isEmpty()) {
                    missCount++;
                    ran=0;
                    continue;
                }
                return allMoviesList.get(RandomGenerator.randomWithRange(0, allMoviesList.size() - 1));
            }

        }
    }


    public ObservableList<Product> getFilteredProductListByGivenName(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList(new ArrayList<>());
        ArrayList<Product> allProducts = new ArrayList<>();
        allProducts.addAll(this.allLiveStreamsList);
        allProducts.addAll(this.allMoviesList);
        allProducts.addAll(this.allSeriesList);
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                foundProducts.add(product);
            }
        }
        if (foundProducts.isEmpty()) {
            return null;
        } else {
            return foundProducts;
        }
    }


    // Tylko dla filmów i seriali
    public ObservableList<Product> getFilteredProductListByGivenActor(String actorName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList(new ArrayList<>());
        for (Movie movie : this.allMoviesList) {
            for(String actor : movie.getActorList()) {
                if (actor.contains(actorName)) { //nie musi sie rownac
                    foundProducts.add(movie);
                }
            }
        }
        for (Series series : this.allSeriesList) {
            for (String actor : series.getActorList()) {
                if (actor.equals(actorName)) {
                    foundProducts.add(series);
                }
            }
        }
        if (foundProducts.isEmpty()) {
            return null;
        } else {
            return foundProducts;
        }
    }

    public int getNumberOfAllProducts(){
        return (this.allLiveStreamsList.size() + this.allSeriesList.size() + this.allMoviesList.size() );
    }

    public LinkedHashMap<Integer,Integer> getPlotDataForGivenProduct(Product product){
        return this.productViews.get(product);
    }

    public void serializeProducts(){
        this.serializationSeries = new Vector<>(this.allSeriesList);
        this.serializationMovies = new Vector<>(this.allMoviesList);
        this.serializationStreamList = new Vector<>(this.allLiveStreamsList);
    }
    public void deserializeProducts(){
        this.allSeriesList = FXCollections.observableList(this.serializationSeries);
        this.allMoviesList = FXCollections.observableList(this.serializationMovies);
        this.allLiveStreamsList = FXCollections.observableList(this.serializationStreamList);
    }

    public LinkedHashMap<Integer,Integer> getViewsDataForProduct(Product product){
        return this.productViews.get(product);
    }

    public ObservableList<Series> getAllSeriesWrapper() {
        return this.allSeriesList;
    }

    public ObservableList<Movie> getAllMoviesWrapper() {
        return this.allMoviesList;
    }

    public ObservableList<LiveStream> getAllLiveStreamsWrapper() {
        return this.allLiveStreamsList;
    }
}
