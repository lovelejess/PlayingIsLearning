package utils;

import java.util.*;

/**
 * User: Charles
 * Date: 5/26/13
 */

public class CodeGenerator {

    private static List<String> getCodeWordList() {
        List<String> codes = new ArrayList<String>();

        codes.add("Bugs Bunny");
        codes.add("Daffy Duck");
        codes.add("Homer Simpson");
        codes.add("Luke Skywalker");
        codes.add("Han Solo");
        codes.add("Ice Age");
        codes.add("Road Runner");
        codes.add("Phineas");
        codes.add("Ferb");
        codes.add("Perry the Platypus");
        codes.add("Dipper Pines");
        codes.add("Mable Pines");
        codes.add("Scooby Doo");
        codes.add("Shaggy");
        codes.add("Pepe le Pew");
        codes.add("Dr. Doofenshmirtz");
        codes.add("Major Monogram");
        codes.add("Candice Flynn");
        codes.add("Elmer Fudd");
        codes.add("Clifford the Big Red Dog");
        codes.add("Word Girl");
        codes.add("Dinosaur Train");
        codes.add("Pokey Little Puppy");
        codes.add("Nut Brown Hare");
        codes.add("Brown Bear");
        codes.add("Winnie the Pooh");
        codes.add("Piglet");
        codes.add("Tigger");
        codes.add("Eeyore");
        codes.add("Lightning McQueen");
        codes.add("Tow Mater");
        codes.add("Stimpy");

        return codes;
    }

    public static String getCodeOfTheDay() {
        return getCodeWordList().get(new GregorianCalendar().get(Calendar.DAY_OF_MONTH)-1);
    }

}
