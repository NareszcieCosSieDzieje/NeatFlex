package NeatFlexClasses.Simulation;

import GUI.Main;
import NeatFlexClasses.Model.Contract;
import NeatFlexClasses.Model.Products.LiveStream;
import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import NeatFlexClasses.Model.Products.Series;
import NeatFlexClasses.Model.Purchase;
import NeatFlexClasses.Model.Subscriptions.BasicSubscription;
import NeatFlexClasses.Model.Subscriptions.FamilySubscription;
import NeatFlexClasses.Model.Subscriptions.PremiumSubscription;
import NeatFlexClasses.Model.Subscriptions.Subscription;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.*;


/** This class is responsible for modelling an instance of a VOD service.
 *  It contains the necessary managers to maintain control over
 *  the users, distributors and products.
 *  It runs as a separate thread that is responsible for
 *  Simulating time and transactions, as well as adding users and generating discounts
 *  For products.
 *  Serializing this object lets the system save the status of the simulation.
 * */
public class NeatFlexService implements Runnable, Serializable {


    //------------------------------------MEMBERS-----------------------------------------------\\

    private ProductManager products; //manager for prdcts
    private DummyManager dummyManager; //manager for dists and usrs
    private Vector<Contract> contracts;

    private int dayCount;      //how many simulation days passed
    private double income;    //service income



    /** Service constructor.
     *  Sets the number of days passed and income to 0
     * Instantiates its managers and the list of contracts.
     */
    public NeatFlexService(){
        contracts = new Vector<>();
        income = 0;
        dayCount = 0;
        products = new ProductManager();
        dummyManager = new DummyManager(this);
    }

    //==============================================RUN===================================\\

    /** Service thread - handles the simulation of the service.
     * */
    public void run(){
        int actionsCount = 0;
        while( !(Thread.currentThread().isInterrupted()) ) {
            if( ++actionsCount % Globals.actionsPerDay == 0 ) {
                products.updatePlotData(this.getDayCount());
                this.dayCount++;
                //end of the month
                if(this.dayCount % 30 == 0) {
                    for (Contract c : contracts) {
                        income -=  c.calculateCost();
                    }
                    if (getMonthCount() >= 3 ){
                        if(income < 0){
                            this.interruptAllThreads();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                actionsCount = 0;
            }
            if ( RandomGenerator.randomWithRange(0.0,1.0) < Globals.discountProbability) {
                this.generateDiscounts();
            }
            if ( RandomGenerator.randomWithRange(0.0,1.0) < Globals.userCreationProbability) {
                int prop = this.products.getProductCount()/8;
                prop = Math.max(1,prop);
                for(int i=0;i<prop;i++){
                    this.dummyManager.addUser();
                }
            }
            try {
                Thread.sleep(Globals.dayDuration.toMillis() / Globals.actionsPerDay);
            } catch (InterruptedException ignored) {
            }
        }
    }


//-----------------------------------METHODS-------------------------------------------------\\

    /** Adds a distributor to the VOD service.
     *  Used by GUI for interaction.
     * */
    public void addDistributor(){
        dummyManager.addDistributor();
    }

    /** Adds a user to the VOD service.
     *   Used by GUI for interaction.
     * */
    public void addUser(){dummyManager.addUser();}

    /** Generates a series for the VOD service.
     *  Does so by adding the product
     *  Using a random distributor.
     *  Used by GUI for interaction.
     * */
    public void addRandSeries() {
        if (dummyManager.hasAnyDistributors()) {
            this.dummyManager.getRandomDistributor().addSeries();
        }
    }

    /** Generates a movie for the VOD service.
     *   Does so by adding the product
     *   Using a random distributor.
     *   Used by GUI for interaction.
     * */
    public void addRandMovie(){
        if (dummyManager.hasAnyDistributors()) {
            this.dummyManager.getRandomDistributor().addMovie();
        }
    }

    /** Generates a live stream for the VOD service.
     *   Does so by adding the product
     *   Using a random distributor.
     *   Used by GUI for interaction.
     * */
    public void addRandLiveStream(){
        if (dummyManager.hasAnyDistributors()) {
            this.dummyManager.getRandomDistributor().addLiveStream();
        }
    }

    /** Adds a generated series by some distributor, to the vod service.
     * */
    public void addSeries(Series series){
        this.products.addSeries(series);
    }

    /** Adds a generated movie by some distributor, to the vod service.
     * */
    public void addMovie(Movie movie){
        this.products.addMovie(movie);
    }

    /** Adds a generated live stream by some distributor, to the vod service.
     * */
    public void addLiveStream(LiveStream livestream){
        this.products.addLiveStream(livestream);
    }

    /** todo
     * */
    public void addContract(Contract contract){
        this.contracts.add(contract);
    }


    /** todo
     * */
    public void removeContract(Contract contract){
        this.contracts.remove(contract);
    }

    /** @return - a randomly chosen product from all the available products in the service
     * */
    public Product getRandomProduct(){
        return this.products.getRandomProduct();
    }

    /** Enables users to watch a given product
     *  From the ones available to them.
     * */
    public void userWatchProduct(Product p){
            p.incrementViews();
    }

    /** Method responsible for for filtering a list,
     * with the key being the products name.
     * @return - a list to be displayed by the GUI
     * */
    public ObservableList<Product> searchByProductName(String productName) {
            return this.products.getFilteredProductListByGivenName(productName);
    }


    /** Method responisble for filtering movies and series,
     * with the key being an actors name.
     * @return - a list to be displayed by the GUI
     */
    public ObservableList<Product> searchProductsByActor(String actorName) {
           return this.products.getFilteredProductListByGivenActor(actorName);
    }

    /** Calculates the passing of months in the simulation.
     * @return - number of months passed
     * */
    public int getMonthCount(){
        return this.dayCount/30;
    }

    /** @return - number of days passed in the simualtion
     * */
    public int getDayCount(){return this.dayCount;}

    /** Generates discounts for a random number of products.
     *  Up to 20% of products can get a discount, starting from 10%.
     * */
    public void generateDiscounts(){
        int howMany = RandomGenerator.randomWithRange(5,10);
        int discountNumber=this.products.getNumberOfAllProducts()/howMany;
        for(int i=0;i<discountNumber;i++) {
            Product product = this.products.getRandomProduct();
            if(product==null){
                return;
            }
            if (product instanceof Movie) {
                Movie movie = (Movie) product;
                if(!movie.hasValidDiscount()){
                    movie.generateDiscount();
                }
            } else if (product instanceof LiveStream) {
                LiveStream liveStream = (LiveStream) product;
                if(!liveStream.hasValidDiscount()){
                    liveStream.generateDiscount();
                }
            }
        }
    }

    /** Handles the purchase of products by users.
     *  Checks whether a user has a subscription or a discount.
     * @return todo
     * */
    public Purchase buy(Product product, User user) {
        if (products.containsGivenProduct(product)) {
            if (!user.hasSubscription()) {
                if (product instanceof Movie){
                    Movie movie = (Movie)product;
                    if (movie.hasValidDiscount()) {
                        this.income += product.getPrice() * (1.00 - movie.getDiscount().getPercentage());
                    }
                }
                else if (product instanceof LiveStream) {
                    LiveStream liveStream = (LiveStream) product;
                    if (liveStream.hasValidDiscount()) {
                        this.income += product.getPrice() * (1.00 - liveStream.getDiscount().getPercentage());
                    }
                }
                else {
                    this.income += product.getPrice();
                }
            }
            return new Purchase(product);
        }
        return null;
    }

    /** Method lets users get subsriptions to the service and pay for them.
     * @paran - type of subsription to buy, by a user
     * @return - type of subscription bought, by a user
     * */
    public Subscription buySubscription(int subType){
        Subscription subscription = null;
        if (subType == Subscription.BASIC) {
            subscription = new BasicSubscription();
        } else if (subType == Subscription.FAMILY) {
            subscription = new FamilySubscription();
        } else if (subType == Subscription.PREMIUM) {
            subscription = new PremiumSubscription();
        }

        if (subscription != null){
            this.income += subscription.getPrice();
        }
        return subscription;

    }


        /** Methods stops all the user and distributor threads.
         * */
    public void startAllThreads(){
        this.dummyManager.startAllDistributorThreads();
        this.dummyManager.startAllUserThreads();
    }

    public void interruptAllThreads(){
        this.dummyManager.interruptAllDistributorThreads();
        this.dummyManager.interruptAllUserThreads();
    }

    /** @return - views in time of a given product
     * */
    public LinkedHashMap<Integer,Integer> getPlotDataForProduct(Product product){
        return this.products.getPlotDataForGivenProduct(product);
    }

    public void prepareToSerialize(){
        this.products.serializeProducts();
        this.dummyManager.serializeDistributors();
        this.dummyManager.serializeUsers();
    }

    //after start threads
    public void deserializeObservableLists(){
        this.products.deserializeProducts();
        this.dummyManager.deserializeToObservableLists();
    }

    /** @return - the service's current income
     * */
    public double getIncome(){
        return this.income;
    }

    /** @return - series list for GUI use
     * */
    public ObservableList<Series> getSeriesWrapper() {
        return this.products.getAllSeriesWrapper();
    }

    /** @return - movie list for GUI use
     * */
    public ObservableList<Movie> getMoviesWrapper() {
        return this.products.getAllMoviesWrapper();
    }

    /** @return - live stream list for GUI use
     * */
    public ObservableList<LiveStream> getLiveStreamsWrapper() {
        return this.products.getAllLiveStreamsWrapper();
    }

    /** @return - user list for GUI use
     * */
    public ObservableList<User> getUsersWrapper() {
        return this.dummyManager.getUserListWrapper();
    }

    /** @return - distributor list for GUI use
     * */
    public ObservableList<Distributor> getDistributorsWrapper() {
        return this.dummyManager.getDistributorListWrapper();
    }

    public LinkedHashMap<Integer,Integer> getViewsDataForProduct(Product product){
        return this.products.getViewsDataForProduct(product);
    }

    public void removeObject(Object object){
        if(object instanceof Movie){
            this.products.removeMovie( (Movie)object );
        }
        else if (object instanceof Series){
            this.products.removeSeries( (Series)object );
        }
        else if (object instanceof LiveStream){
            this.products.removeLiveStream( (LiveStream)object );
        }
        else if (object instanceof User){
            this.dummyManager.removeUser( (User)object );
        }
        else if (object instanceof Distributor){
            this.dummyManager.removeDistributor((Distributor)object);
        }
    }


}



