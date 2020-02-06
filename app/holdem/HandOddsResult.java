package holdem;

/**
 * 赢牌概率结果
 */
public class HandOddsResult {
    private long totalHands;
    private long [] wins;
    private long [] ties;
    private long [] losses;

    HandOddsResult(int pocketsNum){
        totalHands = 0;
        wins = new long[pocketsNum];
        ties = new long[pocketsNum];
        losses = new long[pocketsNum];
    }

    public long getTotalHands() {
        return totalHands;
    }

    public void setTotalHands(long totalHands) {
        this.totalHands = totalHands;
    }

    public long[] getWins() {
        return wins;
    }

    public void setWins(long[] wins) {
        this.wins = wins;
    }

    public long[] getTies() {
        return ties;
    }

    public void setTies(long[] ties) {
        this.ties = ties;
    }

    public long[] getLosses() {
        return losses;
    }

    public void setLosses(long[] losses) {
        this.losses = losses;
    }
}
