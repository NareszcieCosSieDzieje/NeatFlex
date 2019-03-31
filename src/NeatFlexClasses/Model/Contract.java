package NeatFlexClasses.Model;

import NeatFlexClasses.Simulation.Distributor;
import NeatFlexClasses.Simulation.Globals;
import NeatFlexClasses.Simulation.NeatFlexService;
import NeatFlexClasses.Simulation.RandomGenerator;

import java.io.Serializable;

public class Contract implements Serializable {


    static final private double lowerBound = Globals.distributorMonthlyMinimum;
    static final private double upperBound = Globals.distributorMonthlyMaximum;

    private double payment;
    private PaymentType paymentType;

    private NeatFlexService service;
    private Distributor distributor;


//======================================CONSTRUCTOR===================================\\


    public Contract(NeatFlexService service, Distributor distributor) {
        this.service = service;
        this.distributor = distributor;

        this.generatePaymentType();
        this.generatePayment();
        this.service.addContract(this);
    }


//=================================GENERATORS=========================================\\

    private void generatePaymentType() {
        this.paymentType=PaymentType.generateType();
    }


    private void generatePayment(){
        if (this.paymentType.name().equals("PERVIEW")){
            this.payment= RandomGenerator.randomWithRange(Globals.productMinPrice*5/100,Globals.productMaxPrice*5/100);
        }
        else if (this.paymentType.name().equals("PERMONTH")){
            this.payment=RandomGenerator.randomWithRange(lowerBound,upperBound);
        }
    }


    //=======================================METHODS===================================================\\

    public double calculateCost(){
        if (this.paymentType.name().equals("PERVIEW")){
            return this.payment*this.distributor.getViewsCount();
        }
        else if (this.paymentType.name().equals("PERMONTH")){
            return this.payment;
        }
        else {
            System.out.println("NeatFlexClasses.Model.Contract err");
            return 0;
        }
    }

    //============================================toString================================================\\
    public String toString(){
        return ("Contract:\nType of payment: " + this.getPaymentType().toString() + "\nPayment value : " +  this.getPayment() );
    }

    //======================================GETTERS=========================================\\

    public double getPayment() {
        return payment;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    //========================================================================================

}
