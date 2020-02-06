package holdem;

public class HandAnalyzerResult {
	private String hand;
	private String ace;
	private int rank;
	
	HandAnalyzerResult(String hand, String ace) {
		this.hand = hand;
		this.ace = ace;
		this.rank = Hands.getHandsRank().get(hand);
	}
	
	public String getHand() {
		return hand;
	}
	public void setHand(String hand) {
		this.hand = hand;
	}
	public String getAce() {
		return ace;
	}
	public void setAce(String ace) {
		this.ace = ace;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Override
	public String toString() {
		return "hand:" + this.hand + " ace:" + this.ace;
	}
	
}
