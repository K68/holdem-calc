package holdem;

/**
 * 自己和对手牌赢牌概率
 */
public class HandOpponentOddsResult {
    private long totalHands;
    private long []player;
    private long []opponent;

    HandOpponentOddsResult(){
        totalHands = 0;
        player = new long[11];
        opponent = new long[11];
    }

    public long getTotalHands() {
        return totalHands;
    }

    public void setTotalHands(long totalHands) {
        this.totalHands = totalHands;
    }

    public long[] getPlayer() {
        return player;
    }

    public void setPlayer(long[] player) {
        this.player = player;
    }

    public long[] getOpponent() {
        return opponent;
    }

    public void setOpponent(long[] opponent) {
        this.opponent = opponent;
    }
}
