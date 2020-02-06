package holdem;

import java.util.*;

public class Hands {

    public static List<String> hands = new ArrayList<String>(Arrays.asList(
            "FourOfAKind",
            "StraightFlush",
            "Straight",
            "Flush",
            "HighCard",
            "OnePair",
            "TwoPairs",
            "RoyarFlush",
            "ThreeOfAKind",
            "FullHouse",
            "Invalid"
    ));

    public static List<String> handsByRank = new ArrayList<String>(Arrays.asList(
            "Invalid",
            "HighCard",
            "OnePair",
            "TwoPairs",
            "ThreeOfAKind",
            "Straight",
            "Flush",
            "FullHouse",
            "FourOfAKind",
            "StraightFlush",
            "RoyarFlush"
    ));

    static {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>(11);
        map.put("FourOfAKind", 8);
        map.put("StraightFlush", 9);
        map.put("Straight", 5);
        map.put("Flush", 6);
        map.put("HighCard", 1);
        map.put("OnePair", 2);
        map.put("TwoPairs", 3);
        map.put("RoyarFlush", 10);
        map.put("ThreeOfAKind", 4);
        map.put("FullHouse", 7);
        map.put("Invalid", 0);
        handsRank = map;
    }

    public static Map<String, Integer> handsRank;

    public static List<String> getHands() {
        return Hands.hands;
    }

    public static List<String> getHandsByRank() {
        return Hands.handsByRank;
    }

    public static Map<String, Integer> getHandsRank() {
        return Hands.handsRank;
    }
}
