package NeatFlexClasses.Model.Subscriptions;

import java.io.Serializable;

public class FamilySubscription extends Subscription implements Serializable {

    transient static private double price = 25.99;
    transient static private int numberOfDevices = 3;
    transient static private String maxResolution = "3840 * 2160";

    @Override
    public double getPrice() {
        return FamilySubscription.price;
    }

    @Override
    public int getNumberOfDevices() {
        return FamilySubscription.numberOfDevices;
    }

    @Override
    public String getMaxResolution() {
        return FamilySubscription.maxResolution;
    }

    @Override
    public void setPrice(double price) {
        FamilySubscription.price = price;
    }

    @Override
    public void setNumberOfDevices(int n) {
        FamilySubscription.numberOfDevices = n;
    }

    @Override
    public void setMaxResolution(String res) {
        FamilySubscription.maxResolution = res;
    }
}
