package NeatFlexClasses.Simulation;

import java.security.SecureRandom;

/**
 * Klasa statyczna odpowiadająca za generowanie losowych liczb i słów.
 * */
public class RandomGenerator {

    //-------------------------------------METHODS--------------------------------\\

    /** Generowanie losowej liczby całkowitoliczbowej z zadanego przedziału.
     *  @param min - dolna granica przedziału do losowania
     *  @param max - górna granica przedziału do losowania
     *  @return - wygenerowana liczba
     * */
    static public int randomWithRange(int min, int max)
    {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (min <= max ? min : max);
    }

    /** Generowanie losowej liczby zmiennoprzecinkowej z żądanego przedziału.
     * @param min - dolna granica przedziału do losowania
     * @param max - górna granica przedziału do losowania
     * @return - wygenerowana liczba
     * */
    static public double randomWithRange(double min, double max)
    {
        double range = Math.abs(max - min);
        return (Math.random() * range) + (min <= max ? min : max);
    }

    /** Generowanie losowego ciągu słów o zadanej długości.
     * @param len - długość słowa generowangeo.
     * @return słowo wygenerowane
     * */
    static public String randomString( int len ){
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuffer sB = new StringBuffer( len );
        for( int i = 0; i < len; i++ ) {
            sB.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
        }
         return sB.toString();
    }

}