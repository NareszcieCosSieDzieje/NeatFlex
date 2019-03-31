package NeatFlexClasses.Model.Products;

import NeatFlexClasses.Simulation.DataGenerator;
import NeatFlexClasses.Simulation.Distributor;
import NeatFlexClasses.Simulation.Globals;
import NeatFlexClasses.Simulation.RandomGenerator;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.time.*;

/** Abstrakcyjna klasa modelująca produkty
 *  Dziedziczą z niej - NeatFlexClasses.Model.Products.LiveStream, NeatFlexClasses.Model.Products.Series, NeatFlexClasses.Model.Products.Movie
 * */
public abstract class Product implements Serializable {

//--------------------------------------------Members--------------------------------------------------\\

    protected String name;
    protected String description;
    protected Duration duration;
    protected Distributor distributor;
    protected String country;
    protected double price;
    protected double rating;
    protected int views;


//---------------------------------------------CONSTRUCTOR---------------------------------------------------\\

    public Product(Distributor distributor) {
        this.generateName();
        this.generateDescription();
        this.generateDuration();
        this.generateCountry();
        this.generatePrice();
        this.generateRating();
        this.setViews(0);
        this.setDistributor(distributor);
    }

    public Product(){}

//--------------------------------------------GENERATORS----------------------------------------------------\\


    private void generateDuration() {
        this.duration = Duration.ofMillis(RandomGenerator.randomWithRange(20,240));
    }

    private void generatePrice() {
        double random = RandomGenerator.randomWithRange(Globals.productMinPrice, Globals.productMaxPrice);
        double cut = 0.00 + random;
        this.price = cut;
    }

    private void generateRating() {
        double random = RandomGenerator.randomWithRange(0, 10);
        double cut = 0.0 + random;
        this.rating = cut;
    }


    private void generateDescription() {
        int words = RandomGenerator.randomWithRange(1, 100);
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < words; i++) {
            sB.append(RandomGenerator.randomString(RandomGenerator.randomWithRange(1, 20)));
            sB.append(" ");
        }
        this.description = sB.toString();
    }

    private void generateName() {
        int spaces = RandomGenerator.randomWithRange(0, 2);
        StringBuilder sB = new StringBuilder();
        sB.append(DataGenerator.generateAdjective());
        sB.append(" ");
        sB.append(DataGenerator.generateNoun());
        if (spaces>0) {
            sB.append(" of ");
            sB.append(DataGenerator.generateAdjective());
            sB.append(" ");
            String multiple = DataGenerator.generateNoun();
            String end = multiple.substring(multiple.length() - 1);
            sB.append(multiple);
            if (end.equals("s") || end.equals("h") || end.equals("x") || end.equals("z")) {
                sB.append("es");
            }
            else if (end.equals("y")){
                sB.replace(sB.length()-1,sB.length(),"ies");
            }
            else {
                sB.append("s");
            }
        }
        if (spaces >1) {
            sB.append(" and the ");
            sB.append(DataGenerator.generateAdjective());
            sB.append(" ");
            sB.append(DataGenerator.generateNoun());
        }
        this.name = sB.toString();
    }

    private void generateCountry() {
        this.country = DataGenerator.generateCountry();
    }


    public String toString() {
        return  ("Name: " +  this.name + "\nDescription: " + this.description.toString() +
                "\nPrice: " + this.price + "\nRating: " + this.rating + "\nDuration - :"
                 + //this.duration.toHours() + " - Minutes:" + (this.duration.toMinutes() % (60)) +
                "\nDistributor: " + distributor.toString()
                 + "\nDate of production: " + "\nOrigin country: " + this.country
                + "\nNumber of views" + this.views);
    }


    //==========================================METHODS================================================\\


    public synchronized void incrementViews() {
        this.views++;
    }


//-------------------------------------------Setters----------------------------------------------------\\


    private void setViews(int viewz){
        this.views=viewz;
    }

    private void setDistributor(Distributor distributor) {
        this.distributor=distributor;
    }


    //--------------------------------------------Getters----------------------------------------------------\\


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public Duration getDuration() {
        return duration;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public String getCountry() {
        return country;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getViews() {
        return views;
    }
}