package NeatFlexClasses.Model.Subscriptions;

import java.io.Serializable;

/** Statyczna klasa - subskrybcja premium
 *
 */
public class PremiumSubscription extends Subscription implements Serializable {

    transient static private double price = 50.49;
    transient static private int numberOfDevices = 8;
    transient static private String maxResolution = "10240 * 4320";

    @Override
    public double getPrice(){
        return PremiumSubscription.price;
    }

    @Override
    public int getNumberOfDevices() {
        return PremiumSubscription.numberOfDevices;
    }

    @Override
    public String getMaxResolution() {
        return PremiumSubscription.maxResolution;
    }

    @Override
    public void setPrice(double price) {
        PremiumSubscription.price = price;
    }

    @Override
    public void setNumberOfDevices(int n) {
        PremiumSubscription.numberOfDevices = n;
    }

    @Override
    public void setMaxResolution(String res) {
        PremiumSubscription.maxResolution = res;
    }
}
