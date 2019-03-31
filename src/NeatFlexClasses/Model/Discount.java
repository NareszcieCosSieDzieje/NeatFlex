package NeatFlexClasses.Model;

import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;
import NeatFlexClasses.Simulation.RandomGenerator;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

/** Klasa modelująca zniżkę
 *
 * */
public class Discount implements Serializable {

    //--------------------------------------------Members-------------------------------------\\

    private double percentage;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate ;
    private Duration duration;

    //------------------------------------------CONSTRUCTOR--------------------------------\\

    public Discount() {
        this.generateDiscountPercentage();
        this.generateDuration();
        this.generateStart();
        this.generateEnd();
    }


//-----------------------------------------------METHODS------------------------------------\\

    private void generateDiscountPercentage() {
        this.percentage = RandomGenerator.randomWithRange(0.05,0.50);
    }

    /* dni
     * 1  40%
     * 4  30%
     * 7  15%
     * 10 10%
     * 14 5%
     * */
    private void generateDuration(){
        double probability = RandomGenerator.randomWithRange(0.,1.);
        if (probability<=0.40)
        {
            this.duration = Duration.ofSeconds(1);
        }
        else if ( (probability > 0.40 ) && (probability <= 0.70) )
        {
            this.duration = Duration.ofSeconds(4);
        }
        else if ( (probability>0.70)&&(probability<=0.85) )
        {
            this.duration = Duration.ofSeconds(7);
        }
        else if ( (probability>0.85)&&(probability<=0.095) )
        {
            this.duration = Duration.ofSeconds(10);
        }
        else
        {
            this.duration = Duration.ofSeconds(14);
        }
    }


    private void generateStart() {
        this.startDate = OffsetDateTime.now().plusSeconds(RandomGenerator.randomWithRange(0, 10));
    }

    private void generateEnd() {
        this.endDate = this.startDate.plusSeconds(this.duration.toMillis()/1000);
    }


    public boolean isValid() {
        if (this.getEndDate().isAfter( OffsetDateTime.now() ) ) {
            return false;
        }
        else {
            return true;
        }
    }

    public String toString(){
        return ("Discount percentage value: " + this.getPercentage()
                + "\nStart date : " + this.getStartDate().toString()
                + "\nEnd date: " + this.getEndDate().toString()
                + "\nDuration: " + this.getDuration().toString()
        );
    }


    private long howManyDaysLeft(){
         return ChronoUnit.SECONDS.between(this.getEndDate(), OffsetDateTime.now() );
    }

    //--------------------------------------------Getters--------------------------------------\\


    public double getPercentage() {
        return percentage;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public Duration getDuration() {return duration;}

}
