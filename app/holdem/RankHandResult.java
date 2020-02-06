package holdem;

import java.util.List;

public class RankHandResult {

	private HandAnalyzerResult handAnalyzerResult;
	private long score;
	private List<Card> cards;

	public HandAnalyzerResult getHandAnalyzerResult() {
		return handAnalyzerResult;
	}

	public void setHandAnalyzerResult(HandAnalyzerResult handAnalyzerResult) {
		this.handAnalyzerResult = handAnalyzerResult;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public int betterThan(RankHandResult result) {
		if (this.handAnalyzerResult.getRank() > result.handAnalyzerResult.getRank()) {
			return 1;
		} else if (this.handAnalyzerResult.getRank() < result.handAnalyzerResult.getRank()) {
			return -1;
		} else {
			if (this.score > result.score) {
				return 1;
			} else if (this.score < result.score) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
}
