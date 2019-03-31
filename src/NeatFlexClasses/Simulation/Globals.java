package NeatFlexClasses.Simulation;

import java.time.Duration;

public class Globals {
    //1 dzien == 1 sekunda
    public static final Duration dayDuration = Duration.ofSeconds(2);
    public static final int actionsPerDay = 2;

    public static final double discountProbability = 0.2;
    public static final double userCreationProbability = 0.1;
    public static final double userPurchaseProbability = 0.2;
    public static final double userSubscriptionProbability = 0.3;
    public static final double userWatchProbability = 0.6;
    public static final double distributorRenegotiateContractProbability = 0.2;
    public static final double distributorGenerateProductProbability = 0.05;
    public static final double distributorMonthlyPaymentProbability = 0.6;
    public static final double productMaxPrice = 30.99;
    public static final double productMinPrice = 9.99;
    public static final double distributorMonthlyMinimum = 1000;
    public static final double distributorMonthlyMaximum = 5000;
}
