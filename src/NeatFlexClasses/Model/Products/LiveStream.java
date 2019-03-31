package NeatFlexClasses.Model.Products;

import NeatFlexClasses.Simulation.Distributor;
import NeatFlexClasses.Model.Discount;
import NeatFlexClasses.Simulation.Globals;
import NeatFlexClasses.Simulation.RandomGenerator;

import java.io.Serializable;
import java.time.OffsetDateTime;

/** Models a live stream, extends product.
 *  On top of products members, it has an airdate, and a discount.
 * */
public class LiveStream extends Product implements Serializable {

    private OffsetDateTime airDate;
    private Discount discount;

    //========================================CONSTRUCTOR=============================================\\

    /** Generates inherited members and an airdate and a discount.
     * */
    public LiveStream(Distributor distributor) {
        super(distributor);
        this.generateDate();
        this.generateDiscount();
    }

    //=======================================GENERATORS================================================\\

    /** Generates an air date set in the furure after its creation.
     * */
    private void generateDate() {
        long daysToAir =  + (long) RandomGenerator.randomWithRange(0,90);
        this.airDate = OffsetDateTime.now().plusSeconds(daysToAir * Globals.dayDuration.toMillis()/1000);
    }

    /** 50-50 chance for generating a discount
     * */
    public void generateDiscount() {
        if (java.util.concurrent.ThreadLocalRandom.current().nextBoolean()) {
            this.discount = new Discount();
        }
    }




    //======================================SETTERS===============================================\\

    //======================================GETTERS================================================\\

    public OffsetDateTime getAirDate() {
        return airDate;
    }

    public Discount getDiscount(){ return this.discount;}

    /** Check if the discount is valid, if not removes it.
     * */
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
}