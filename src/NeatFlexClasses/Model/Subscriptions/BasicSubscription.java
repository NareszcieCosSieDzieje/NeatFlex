package NeatFlexClasses.Model.Subscriptions;

import java.io.Serializable;

public class BasicSubscription extends Subscription implements Serializable {

    transient static private double price = 10.50;
    transient static private int numberOfDevices = 1;
    transient static private String maxResolution = "1080 * 1200";

    @Override
    public double getPrice() {
        return BasicSubscription.price;
    }

    @Override
    public int getNumberOfDevices() {
        return BasicSubscription.numberOfDevices;
    }

    @Override
    public String getMaxResolution() {
        return BasicSubscription.maxResolution;
    }

    @Override
    public void setPrice(double price) {
        BasicSubscription.price = price;
    }

    @Override
    public void setNumberOfDevices(int n) {
        BasicSubscription.numberOfDevices = n;
    }

    @Override
    public void setMaxResolution(String res) {
        BasicSubscription.maxResolution = res;
    }
}
