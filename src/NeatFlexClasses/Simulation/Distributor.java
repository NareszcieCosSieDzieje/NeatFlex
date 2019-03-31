package NeatFlexClasses.Simulation;

import NeatFlexClasses.Model.*;
import NeatFlexClasses.Model.Products.LiveStream;
import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import NeatFlexClasses.Model.Products.Series;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;


public class Distributor implements Runnable, Serializable {


    private String name;
    private Contract contract;
    transient private ObservableList<Product> distributorProducts;
    private Vector<Product> serializationProducts;

    private NeatFlexService service;

    //=======================================CONSTRUCTOR==============================\\

    public Distributor(NeatFlexService service) {
        serializationProducts = null;
        distributorProducts = FXCollections.observableList(new Vector<>());
        this.service = service;
        for(int initialProducts=0;initialProducts<5;initialProducts++){
            this.generateProduct();
        }
        this.contract = new Contract(this.service, this);
        this.generateName();
    }


    //-------------------------------------------RUN------------------------------------\\

    public void run() {
        while( !(Thread.currentThread().isInterrupted()) ) {
            if ( RandomGenerator.randomWithRange(0.0,1.0) < Globals.distributorRenegotiateContractProbability){
                this.renegotiateContract();
            }
            else if ( RandomGenerator.randomWithRange(0.0,1.0) < Globals.distributorGenerateProductProbability) {
                int newProductCount = RandomGenerator.randomWithRange(1,java.lang.Math.max(1,this.getProductCount()/8));
                for (int i = 0; i < newProductCount; i++) {
                    this.generateProduct();
                }
            }
            try {
                Thread.sleep(Globals.dayDuration.toMillis() / Globals.actionsPerDay);
            } catch (InterruptedException ignored) {}
        }
    }


    //-----------------------------------------GENERATORS-------------------------------------\\

    private void generateName(){
           this.name = DataGenerator.generateDistributor();
    }

    public void generateProduct(){
        int productType = RandomGenerator.randomWithRange(1,3);
        if (productType == 3) {
            this.addLiveStream();
        }
        else if (productType == 2) {
           this.addMovie();
        }
        else if (productType == 1) {
          this.addSeries();
        }
    }

    @Override
    public String toString(){
        return ("Name: " + this.name + "\nContract: " + this.contract.toString());
    }

    //========================================METHODS============================\\


   public void serializeProducts(){
        this.serializationProducts = new Vector<>(this.distributorProducts);
    }

    public void deserializeProducts(){
        this.distributorProducts = FXCollections.observableList(this.serializationProducts);
    }

    public void addSeries(){
        Series series = new Series(this);
        this.distributorProducts.add(series);
        this.service.addSeries(series);
    }

    public void addMovie(){
        Movie movie = new Movie(this);
        this.distributorProducts.add(movie);
        this.service.addMovie(movie);

    }

    public void addLiveStream(){
        LiveStream liveStream = new LiveStream(this);
        this.distributorProducts.add(liveStream);
        this.service.addLiveStream(liveStream);

    }

    public void removeProduct(Product p){
        this.distributorProducts.remove(p);
    }

    private void renegotiateContract(){
        this.service.removeContract(this.contract);
        this.contract=new Contract(this.service, this);
    }

    //========================================GETTERS=================================\\

    public String getName(){
        return this.name;
    }

    public Contract getContract(){return this.contract;}

    public String getStringContract(){return this.contract.toString();}

    public int getProductCount(){
        return distributorProducts.size();
    }

    public int getViewsCount(){
        return ProductManager.getTotalViewsCount(distributorProducts);
    }

    public ObservableList<Product> getDistributorProductListWrapper(){ return this.distributorProducts;}

    public String getStringProducts(){ return this.distributorProducts.toString();}

}

