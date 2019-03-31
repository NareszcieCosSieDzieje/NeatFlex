package NeatFlexClasses.Model;

import NeatFlexClasses.Model.Products.LiveStream;
import NeatFlexClasses.Model.Products.Movie;
import NeatFlexClasses.Model.Products.Product;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class Purchase implements Serializable {

    private Product product;
    private OffsetDateTime purchaseDate;

    public Purchase(Product product){
        this.product = product;
        this.purchaseDate = OffsetDateTime.now();
    }

    public boolean isValid(){
        if (product instanceof Movie){
            return OffsetDateTime.now().isBefore( this.purchaseDate.plusSeconds(((Movie)product).getTimeAvailable().getSeconds()));
        }
        else if(product instanceof LiveStream){
            OffsetDateTime now = OffsetDateTime.now();
            if (    ((LiveStream)product).getAirDate().isBefore(now) &&
                    now.isBefore( this.purchaseDate.plusSeconds( product.getDuration().toMillis()/1000 )) ){
                return true;

            }
            else
                return false;
        }
        else return true;
    }

    public Product getProduct(){
        return product;
    }
}
