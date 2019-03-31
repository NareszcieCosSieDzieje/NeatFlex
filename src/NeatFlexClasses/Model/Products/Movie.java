package NeatFlexClasses.Model.Products;

import NeatFlexClasses.Model.Discount;
import NeatFlexClasses.Simulation.DataGenerator;
import NeatFlexClasses.Simulation.Distributor;
import NeatFlexClasses.Simulation.Globals;
import NeatFlexClasses.Simulation.RandomGenerator;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/** Models a movie, extends product.
 *  Has a list of actors, a genre, a discount, time for which it will be available
 *  A link to its trailer (not real) and a production date.
 * */
public class Movie extends Product implements Serializable  {

    //-----------------------------------MEMBERS-------------------------------\\

    private HashSet<String> actorList;
    private String trailerLink;
    private Genre genre;
    private Discount discount;
    private Duration timeAvailable;
    private LocalDate productionDate; // DD.MM.YYYY


    //-----------------------------------CONSTRUCTOR------------------------------\\

    public Movie(Distributor distributor) {
        super(distributor);
        this.actorList = new HashSet<>();
        this.generateTimeAvailable();
        this.generateDiscount();
        this.generateProductionDate();
        this.generateGenre();
        this.generateTrailerLink();
        this.generateActorList();
    }


    //-----------------------------------GENERATORS----------------------------------\\

    private void generateTimeAvailable() {

        this.timeAvailable =  Duration.ofMillis(RandomGenerator.randomWithRange(1, 10) * Globals.dayDuration.toMillis());
    }

    private void generateProductionDate() {
        this.productionDate = LocalDate.now().minus(Period.ofDays((RandomGenerator.randomWithRange(0, 365 * 124))));
    }

    private void generateGenre(){
        this.genre = Genre.generateGenre();
    }

    public void generateDiscount() {
        if (java.util.concurrent.ThreadLocalRandom.current().nextBoolean()) {
            this.discount = new Discount();
        }
    }

    private void generateTrailerLink() {
        StringBuilder sB = new StringBuilder("https://www.imdb.com/title/tt");
        for (int i = 0; i < 7; i++) {
            sB.append(RandomGenerator.randomWithRange(0, 9));
        }
        sB.append("/?ref_=vi_tr_mp_");
        sB.append(RandomGenerator.randomWithRange(0, 9));
        sB.append("_");
        sB.append(RandomGenerator.randomWithRange(0, 9));
        this.trailerLink = sB.toString();
    }

    private void generateActorList(){
        int movieActors=RandomGenerator.randomWithRange(5,100);
        for(int i=0;i<movieActors;i++){
            this.actorList.add(DataGenerator.generateActor());
        }
    }

    //=====================================METHODS====================================\\

    public boolean hasValidDiscount() {
        if (this.discount!=null) {
            if (this.getDiscount().isValid()){
                return true;
            }
            else {
                this.discount=null;
                return false;
            }
        } else {
            return false;
        }
    }



    //-----------------------------------GETTERS--------------------------------\\

    public HashSet<String> getActorList() {
        return actorList;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public Duration getTimeAvailable() {
        return timeAvailable;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Genre getGenre(){return genre;}

    public LocalDate getProductionDate(){return productionDate;}

}
