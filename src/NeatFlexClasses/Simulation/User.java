package NeatFlexClasses.Simulation;

import NeatFlexClasses.Model.Products.LiveStream;
import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import NeatFlexClasses.Model.Products.Series;
import NeatFlexClasses.Model.Purchase;
import NeatFlexClasses.Model.Subscriptions.Subscription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class User implements Runnable, Serializable {

//------------------------------------------MEMBERS------------------------------------------\\
    private String name;
    private LocalDate birthDate;
    private String email;
    private String creditCard;
    private Subscription subscription;
    transient private ObservableList<Purchase> boughtProducts;
    private Vector<Purchase> serializationProducts;
    private HashMap<Series,Integer> episodesWatched;

    private NeatFlexService service;

//-----------------------------------------------CONSTRUCTOR-------------------------------------------\\

    public User(NeatFlexService service) {
        serializationProducts=null;
        this.subscription = null;
        this.service = service;
        this.boughtProducts = FXCollections.observableList(new Vector<>());
        this.episodesWatched = new HashMap<>();
        this.generateName();
        this.generateEMail();
        this.generateBirthDate();
        this.generateCreditCard();
        this.generateSubscription();

    }

//----------------------------------------------METHODS--------------------------------------------------\\

    private void generateName(){
        this.name = DataGenerator.generateUserName();
    }

    private void generateEMail(){
        String[] eMails = { "gmail.com", "yahoo.com","hotmail.com", "msn.com","wp.pl","onet.pl", "live.com", "web.de", "libero.it"};
        StringBuilder sB = new StringBuilder(RandomGenerator.randomString(RandomGenerator.randomWithRange(1,20)));
        sB.append("@");
        sB.append(eMails[RandomGenerator.randomWithRange(0,eMails.length-1)]);
        this.email = sB.toString();
    }

    private void generateCreditCard(){
        int cardLimit = RandomGenerator.randomWithRange(12,19);
        StringBuilder sB=new StringBuilder();
        for(int i=0;i<cardLimit;i++) {
            sB.append(RandomGenerator.randomWithRange(0, 9));
        }
        this.creditCard = sB.toString();
    }

    private void generateBirthDate(){
         this.birthDate = LocalDate.now().minus(Period.ofDays((RandomGenerator.randomWithRange(365*12, 365 * 80))));
    }

    private void generateSubscription(){
        if(RandomGenerator.randomWithRange(0.,1.)<Globals.userSubscriptionProbability) {
            int subType = RandomGenerator.randomWithRange(1, 3);
            this.subscription = this.service.buySubscription(subType);
        }
    }

    //========================================METHODS========================================\\

    public String toString(){
        return ("Name: " + this.name + "\nCredit card number: " + this.creditCard + "\nSubscription: "
                + this.getSubscriptionToString() + "\ne-Mail: " + this.email + "Birth date: " + this.birthDate
                );
    }

    public void serializeProducts(){
        this.serializationProducts = new Vector<>(this.boughtProducts);
    }

    public void deserializeProducts(){
        this.boughtProducts = FXCollections.observableList(this.serializationProducts);
    }

    private void watchRandomProduct() {
        if(!this.boughtProducts.isEmpty()){
        Purchase purchase = this.boughtProducts.get(RandomGenerator.randomWithRange(0, boughtProducts.size() - 1));
        if(purchase==null){
            return;
        }//todo
        Product p = purchase.getProduct();
            p.incrementViews();
            if (p instanceof Series) {
                this.service.userWatchProduct(p);
                if (this.episodesWatched.containsKey(p)) {
                    //watch another episode
                    this.episodesWatched.put((Series) p, this.episodesWatched.get(p) + 1);
                    if (this.episodesWatched.get(p) == ((Series) p).getTotalNumberOfEpisodes()) {
                        //delete if all episodes watched
                        this.boughtProducts.remove(purchase);
                    }
                } else {
                    this.episodesWatched.put((Series) p, 1);
                }
            } //jak to odwrocic https://stackoverflow.com/questions/11189708/is-it-possible-to-tell-intellij-idea-to-automatically-invoke-tostring-on-the-o
            else if (p instanceof Movie || p instanceof LiveStream) {
                if (purchase.isValid()) {
                    this.service.userWatchProduct(p);
                } else {
                    this.boughtProducts.remove(purchase);
                }
            }
        }
    }


    private void buyRandomProduct(){
        Product p = this.service.getRandomProduct();
        if(p != null)
            this.boughtProducts.add( service.buy(p, this));
    }

    public void run(){
        while( !(Thread.currentThread().isInterrupted()) ) {
            if (RandomGenerator.randomWithRange(0.0,1.0) < Globals.userPurchaseProbability){
                this.buyRandomProduct();
            }
            else if ( RandomGenerator.randomWithRange(0.0,1.0) < Globals.userWatchProbability ) {
                if (this.boughtProducts.size() > 0)
                    this.watchRandomProduct();
            }
            try {
                Thread.sleep(Globals.dayDuration.toMillis() / Globals.actionsPerDay);
            } catch (InterruptedException ignored) { }

        }

    }

    //------------------------------------------GETTERS-------------------------------------------\\
    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getSubscriptionToString() {
        if (this.subscription == null) {
            return ("Brak subskrypcji");
        } else {
            return this.subscription.toString();
        }
    }

    public String getCreditCard() {
        return creditCard;
    }

    public boolean hasSubscription() {
        if ( subscription != null && !subscription.isValid()){
            generateSubscription();
        }
        return subscription != null && subscription.isValid();
    }

    public String getSubscriptionType() {
        return subscription.getClass().getSimpleName();
    }

    public ObservableList<Purchase> getBoughtProducts(){return boughtProducts; }

    public String getStringProducts() {
        StringBuilder sB = new StringBuilder();
        for (Purchase purchase : boughtProducts) {
            sB.append(purchase.getProduct().toString());
            sB.append("\n");
        }
        return sB.toString();
    }
}
