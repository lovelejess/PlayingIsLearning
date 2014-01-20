package models;

import org.mongojack.MongoCollection;

/**
 * User: Charles
 * Date: 5/4/13
 */

@MongoCollection(name = "cards")
public class Card {

    public String cardName;

    public String frontURLImage;

    public String backURLImage;

    public Card() {   }

    public Card(String cardName, String frontURLImage, String backURLImage) {
        this.cardName = cardName;
        this.frontURLImage = frontURLImage;
        this.backURLImage = backURLImage;
    }
}
