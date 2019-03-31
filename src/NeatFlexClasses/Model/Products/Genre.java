package NeatFlexClasses.Model.Products;

import java.util.Random;

/** Enum for genres:
 *  acition,comedy,drama,documentary,sensational,for kids, other
 *
 * */
public enum Genre {
    ACTION("UPBEAT"),
    COMEDY("UPBEAT"),
    DRAMA("SERIOUS"),
    DOCUMENTARY("SERIOUS"),
    SENSATIONAL("SERIOUS"),
    forKIDS("UPBEAT"),
    OTHER("UNKNOWN");

    private String type;

    Genre(String type) {
        this.type = type;
    }

    static public void displayGenres() {
        for (Genre genre : Genre.values()) {
            System.out.println(genre);
        }
    }

    static public boolean compareGenre(String genre) {
        boolean flag=true;
        for (Genre gen : Genre.values()) {
            if (genre.equals(gen.toString())) {
                    flag=false;
            }
        }
        return flag;
    }

    /** @returns - a random genre from the available ones
     * */
    static public Genre generateGenre(){
        return Genre.values()[new Random().nextInt(Genre.values().length)];
    }

}
