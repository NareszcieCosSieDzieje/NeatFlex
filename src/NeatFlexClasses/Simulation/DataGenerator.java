package NeatFlexClasses.Simulation;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.nio.charset.StandardCharsets;

/** Static class for loading data from "*.txt" files to generate data for objects in the VOD service.
 *  Loads in lists of actors, countries, distributors, nouns, adjectives, and names.
 *  Has to be initialized, preferably at the very start of the program.
*/
 public class DataGenerator {

    //----------------------------------------MEMBERS----------------------------------------\\
    static private ArrayList<String> actorList = new ArrayList<String>();
    static private ArrayList<String> countryList = new ArrayList<String>();
    static private ArrayList<String> distributorList = new ArrayList<String>();
    static private ArrayList<String> nounList = new ArrayList<>();
    static private ArrayList<String> adjectiveList = new ArrayList<String>();
    static private ArrayList<String> nameList = new ArrayList<>();

    /** Loads in data, from "*.txt" files.
     * */
    static public void initialize() throws FileNotFoundException {
        URL urlOne = DataGenerator.class.getResource("/actors.txt");
        URL urlTwo = DataGenerator.class.getResource("/countries.txt");
        URL urlThree = DataGenerator.class.getResource("/distributors.txt");
        URL urlFour = DataGenerator.class.getResource("/nouns.txt");
        URL urlFive = DataGenerator.class.getResource("/adjectives.txt");
        URL urlSix = DataGenerator.class.getResource("/human_names.txt");
        URL[] urls = {urlOne, urlTwo, urlThree, urlFour, urlFive, urlSix};
        ArrayList<ArrayList<String>> masterList = new ArrayList<>();
        masterList.add(actorList);
        masterList.add(countryList);
        masterList.add(distributorList);
        masterList.add(nounList);
        masterList.add(adjectiveList);
        masterList.add(nameList);
        for (int i = 0; i < 6; i++) {
            try (BufferedReader br = new BufferedReader(new FileReader(urls[i].getPath()))) {
                for (String line; (line = br.readLine()) != null; ) {
                    masterList.get(i).add(line);
                }
            } catch (java.io.IOException e) {
                //handle or smth
            }
        }
    }

    //-----------------------------------------METHODS----------------------------------------\\

    static private String handleLackOfElements(){
        return RandomGenerator.randomString(RandomGenerator.randomWithRange(5,20));
    }

    public static String generateActor(){
     return actorList.get(RandomGenerator.randomWithRange(0,(actorList.size()-1)));
    }

    public static String generateCountry(){
     return countryList.get(RandomGenerator.randomWithRange(0,(countryList.size()-1)));
    }

    public static String generateDistributor(){
        if(distributorList.isEmpty()){
            return DataGenerator.handleLackOfElements();
        }
        else {
            int elem = RandomGenerator.randomWithRange(0, (distributorList.size() - 1));
            String dist = distributorList.get(elem);
            distributorList.remove(elem);
            return dist;
        }
     }

     public static String generateNoun(){
        return nounList.get(RandomGenerator.randomWithRange(0,(nounList.size()-1)));
    }

    public static String generateAdjective(){
        return adjectiveList.get(RandomGenerator.randomWithRange(0,(adjectiveList.size()-1)));
    }

    public static String generateUserName(){
        return nameList.get(RandomGenerator.randomWithRange(0,(nameList.size()-1)));
    }


}

