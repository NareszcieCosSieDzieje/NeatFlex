package NeatFlexClasses.Model.Products;

import NeatFlexClasses.Simulation.RandomGenerator;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

/** Models an episode within a season.
 *  Has a duration and a premiere date.
 * */
public class Episode implements Serializable {

    private Duration duration; // up to an hour
    private LocalDate premiereDate;


    //======================================CONSTRUCTOR=====================================\\

    /** Generates the duration and the premiere date of an episode.
     * */
    public Episode(){
        this.generateDuration();
        this.generatePremiereDate();
    }

    //========================================toString======================================\\
    public String toString() {
        return ("Data premiery odcinka: " + this.getPremiereDate().toString() +
                "Czas trwania odcinka: " + this.getDuration().toString() + " minut.");
    }

    //=========================================GENERATORS====================================\\

    private void generateDuration() {
        this.duration = Duration.ofMinutes(RandomGenerator.randomWithRange(1,60));
    }

    private void generatePremiereDate() {
        this.premiereDate =  LocalDate.now().minus(Period.ofDays(( RandomGenerator.randomWithRange(0 , 365 * 50))));
    }

    //==========================================GETTERS=======================================\\

    public LocalDate getPremiereDate() {
        return premiereDate;
    }

    public Duration getDuration() {
        return duration;
    }

}
