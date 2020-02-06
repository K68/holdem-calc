package holdem;

public enum PokerSuit {
	SPADES("Spade"),
    CLUBS("Club"),
    HERATS("Heart"),
    DIAMS("Diamond");

	PokerSuit(String s) {
        suit = s;
	}

	public static PokerSuit bySuit(String suit) {
	    if (SPADES.suit.equals(suit)) {
	        return SPADES;
        } else if (CLUBS.suit.equals(suit)) {
	        return CLUBS;
        } else if (HERATS.suit.equals(suit)) {
	        return HERATS;
        } else {
	        return DIAMS;
        }
    }

    public static PokerSuit bySuitShort(char suit) {
        if ('s' == suit) {
            return SPADES;
        } else if ('c' == suit) {
            return CLUBS;
        } else if ('h' == suit) {
            return HERATS;
        } else {
            return DIAMS;
        }
    }

	public String getSuit() {
		return this.suit;
	}

	public int getSuitNum() {
        if (this.suit.equals(PokerSuit.SPADES.getSuit())) {
            return 1;
        }
        if (this.suit.equals(PokerSuit.CLUBS.getSuit())) {
            return 2;
        }
        if (this.suit.equals(PokerSuit.HERATS.getSuit())) {
            return 4;
        }
        if (this.suit.equals(PokerSuit.DIAMS.getSuit())) {
            return 8;
        }
        return 1;
	}

    public char getSuitShort() {
        if (this.suit.equals(PokerSuit.SPADES.getSuit())) {
            return 's';
        }
        if (this.suit.equals(PokerSuit.CLUBS.getSuit())) {
            return 'c';
        }
        if (this.suit.equals(PokerSuit.HERATS.getSuit())) {
            return 'h';
        }
        if (this.suit.equals(PokerSuit.DIAMS.getSuit())) {
            return 'd';
        }
        return 's';
    }

	private final String suit;
}
