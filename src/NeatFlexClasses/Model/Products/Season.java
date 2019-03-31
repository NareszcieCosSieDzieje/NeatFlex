package NeatFlexClasses.Model.Products;

import NeatFlexClasses.Simulation.RandomGenerator;

import java.io.Serializable;
import java.util.LinkedList;

/** Klasa modelująca sezon serialu.
 * Sezony składają się z odcinków
 * */
public class Season implements Serializable {

    private LinkedList<Episode> episodeList = new LinkedList<>();

    //======================================CONSTRUCTOR========================\\

    public Season(int seasons){
        this.generateEpisodes(seasons);
    }

    //========================================GENERATORS========================\\

    private void generateEpisodes(int seasons) {
        int ep;
        if (seasons <= 8) {
                ep = RandomGenerator.randomWithRange(6, 15);
                for (int i = 0; i < ep; i++) {
                    this.episodeList.add(new Episode());
                }
            } else if ( seasons <= 15) {
                ep = RandomGenerator.randomWithRange(8, 20);
                for (int i = 0; i < ep; i++) {
                    this.episodeList.add(new Episode());
                }
            } else {
                ep = RandomGenerator.randomWithRange(15, 30);
                for (int i = 0; i < ep; i++) {
                    this.episodeList.add(new Episode());
                }
            }
        }

        //=============================METHODS===========================\\


    public int getNumberOfEpisodesinSeason(){
        return this.episodeList.size();
    }


    public String episodesToString(){
        StringBuilder sB=null;
        for(Episode episode: this.episodeList){
            sB.append(episode);
            sB.append("\n");
        }
       return sB.toString();
    }

}
