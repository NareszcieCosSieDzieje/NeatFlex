package NeatFlexClasses.Model.Subscriptions;

import NeatFlexClasses.Simulation.Globals;

import java.time.OffsetDateTime;
import java.util.*;

/** Abstrakcyjna klasa subskrybcji
 *  Klasy dziedziczące - premium, family, basic
 * */
public abstract class Subscription {

    //---------------------------------------MEMBERS--------------------------------\\
    public static final int BASIC = 1;
    public static final int FAMILY = 2;
    public static final int PREMIUM = 3;

    private OffsetDateTime purchaseDate;

    //---------------------------------------CONSTRUCTOR-------------------------------\\

    public Subscription(){
        purchaseDate = OffsetDateTime.now();
    }

    //----------------------------------------METHODS-----------------------------------\\

    public boolean isValid(){
        return OffsetDateTime.now().isBefore(purchaseDate.plusSeconds( (Globals.dayDuration.toMillis()/1000) * 30));
    }

    @Override
    public String toString(){
        return ("Price: " + getPrice() + "\nNumber of devices: " + getNumberOfDevices() +
                "\nMax resolution: " + getMaxResolution());
    }

    //---------------------------------------GETTERS---------------------------------\\

    public abstract double getPrice();

    public abstract int getNumberOfDevices();

    public abstract String getMaxResolution();

    //---------------------------------------SETTERS---------------------------------\\

    public abstract void setPrice(double price);

    public abstract void setNumberOfDevices(int n);

    public abstract void setMaxResolution(String res);
}
