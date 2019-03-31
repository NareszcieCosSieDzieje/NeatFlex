package NeatFlexClasses.Model.Products;

import NeatFlexClasses.Simulation.DataGenerator;
import NeatFlexClasses.Simulation.Distributor;
import NeatFlexClasses.Simulation.RandomGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/** Klasa modelująca seriale
 *  Zawiera listę sezonów oraz zbiór aktorów (zbiór po to żeby się nie powtarzali)
 * */
public class Series extends Product implements Serializable {

    //===============================MEMBERS===================================\\

    private Genre genre;
    private LocalDate productionDate;
    private HashSet<String> actorList = new HashSet<>();
    private LinkedList<Season> seasonList = new LinkedList<>();

    //=================================CONSTRUCTOR=============================\\

    public Series(Distributor distributor) {
        super(distributor);
        this.generateGenre();
        this.generateProductionDate();
        this.generateSeasons();
        this.generateActors();
    }

    //================================GENERATORS=================================\\

    private void generateProductionDate() {
        this.productionDate = LocalDate.now().minus(Period.ofDays((RandomGenerator.randomWithRange(0, 365 * 124))));
    }

    private void generateGenre(){
        this.genre=Genre.generateGenre();
    }

    private void generateSeasons() {
        double probability = RandomGenerator.randomWithRange(0.00, 1.00);
        int seasons=0;
        if (probability <= 0.45) {
            seasons = RandomGenerator.randomWithRange(1, 2);
        } else if (probability > 0.45 && probability <= 0.75) {
            seasons = RandomGenerator.randomWithRange(1, 8);
        } else if (probability > 0.75 && probability <= 0.90) {
            seasons = RandomGenerator.randomWithRange(4, 12);
        } else if (probability > 0.90) {
            seasons = RandomGenerator.randomWithRange(8, 20);
        }
        for(int i=0;i<seasons;i++){
               this.seasonList.add(new Season(seasons));
        }
    }

    private void generateActors(){
        int actorCount=0;
        int seasons=this.seasonList.size();
        if (seasons <= 8) {
            actorCount=RandomGenerator.randomWithRange(10,20);
        }  else if (seasons <= 15) {
            actorCount=RandomGenerator.randomWithRange(20,40);
        } else {
            actorCount=RandomGenerator.randomWithRange(30,60);
        }
        for(int i=0;i<actorCount;i++){
               this.actorList.add(DataGenerator.generateActor());
        }
    }

    //====================================METHODS=====================================\\

    //====================================GETTERS======================================\\

    public int getTotalNumberOfEpisodes() {
        int totalEpisodeCount=0;
        for(Season s: this.seasonList){
            totalEpisodeCount+=s.getNumberOfEpisodesinSeason();
        }
    return totalEpisodeCount;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public HashSet<String> getActorList() {
        return actorList;
    }

    public String getStringActors(){return actorList.toString();}

    public LinkedList<Season> getSeasonList() {
        return seasonList;
    }

    public String getStringSeasons(){return  seasonList.toString();}

}
