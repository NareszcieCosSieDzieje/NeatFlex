package NeatFlexClasses.Model;

import NeatFlexClasses.Simulation.Globals;
import NeatFlexClasses.Simulation.RandomGenerator;
import jdk.nashorn.internal.objects.Global;

public enum PaymentType{

    PERVIEW,
    PERMONTH;

    public static PaymentType generateType(){
        if(RandomGenerator.randomWithRange(0.,1.)< Globals.distributorMonthlyPaymentProbability){
            return PaymentType.PERMONTH;
        }
        else {
            return  PaymentType.PERVIEW;
        }
    }

}
