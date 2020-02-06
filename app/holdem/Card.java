package holdem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Card {
	public Card(String value, PokerSuit suit) {
		this.value = value;
		this.suit = suit;
		this.sortPoint = 0.0f;
	}

    public Card(String value, PokerSuit suit, float sortPoint) {
        this.value = value;
        this.suit = suit;
        this.sortPoint = sortPoint;
    }

	public int getValueNum() {
		return Card.valueMap.get(this.value);
	}

	public static Long[] getCSArray(List<Card> cards) {
		final List<Long> cs = new ArrayList<Long>();
        for (Card card : cards) {
            cs.add((long)(card.getValueNum()));
        }
        return cs.toArray(new Long[cards.size()]);
	}

	public static Long[] getSSArray(List<Card> cards) {
        final List<Long> ss = new ArrayList<Long>();
        for (Card card : cards) {
            ss.add((long)(card.getSuit().getSuitNum()));
        }
        return ss.toArray(new Long[cards.size()]);
	}

	public final String getValue() {
		return value;
	}

	public final PokerSuit getSuit() {
		return suit;
	}

	public final float getSortPoint() {
		return sortPoint;
	}

	public static LinkedHashMap<String, Integer> getValueMap() {
		return valueMap;
	}

	private final String value;
	private final PokerSuit suit;
	private final float sortPoint;

	static {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>(13);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);
		map.put("6", 6);
		map.put("7", 7);
		map.put("8", 8);
		map.put("9", 9);
		map.put("10", 10);
		map.put("J", 11);
		map.put("Q", 12);
		map.put("K", 13);
		map.put("A", 14);
		valueMap = map;
	}

	public static final LinkedHashMap<String, Integer> valueMap;
}
