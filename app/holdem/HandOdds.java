package holdem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HandOdds {
    static ArrayList<ArrayList<Integer>> combs2_45;
    static ArrayList<ArrayList<Integer>> combs2_43;
    static ArrayList<ArrayList<Integer>> combs2_41;
    static ArrayList<ArrayList<Integer>> combs2_39;
    static ArrayList<ArrayList<Integer>> combs2_37;
    static ArrayList<ArrayList<Integer>> combs2_35;
    static ArrayList<ArrayList<Integer>> combs2_33;
    static ArrayList<ArrayList<Integer>> combs2_31;

    static ArrayList<ArrayList<Integer>> combs5_50;
    static ArrayList<ArrayList<Integer>> combs2_47;

    public static List<Card> allCards;

    static {
        combs2_45 = HandAnalyzer.getCombinations(2, 45);
        combs2_43 = HandAnalyzer.getCombinations(2, 43);
        combs2_41 = HandAnalyzer.getCombinations(2, 41);
        combs2_39 = HandAnalyzer.getCombinations(2, 39);
        combs2_37 = HandAnalyzer.getCombinations(2, 37);
        combs2_35 = HandAnalyzer.getCombinations(2, 35);
        combs2_33 = HandAnalyzer.getCombinations(2, 33);
        combs2_31 = HandAnalyzer.getCombinations(2, 31);

        //combs5_50 = HandAnalyzer.getCombinations(5, 50);//too large to optimize
        combs2_47 = HandAnalyzer.getCombinations(2, 47);

        allCards = new ArrayList<Card>(){{
            add(new Card("2", PokerSuit.SPADES));
            add(new Card("3", PokerSuit.SPADES));
            add(new Card("4", PokerSuit.SPADES));
            add(new Card("5", PokerSuit.SPADES));
            add(new Card("6", PokerSuit.SPADES));
            add(new Card("7", PokerSuit.SPADES));
            add(new Card("8", PokerSuit.SPADES));
            add(new Card("9", PokerSuit.SPADES));
            add(new Card("10", PokerSuit.SPADES));
            add(new Card("J", PokerSuit.SPADES));
            add(new Card("Q", PokerSuit.SPADES));
            add(new Card("K", PokerSuit.SPADES));
            add(new Card("A", PokerSuit.SPADES));

            add(new Card("2", PokerSuit.CLUBS));
            add(new Card("3", PokerSuit.CLUBS));
            add(new Card("4", PokerSuit.CLUBS));
            add(new Card("5", PokerSuit.CLUBS));
            add(new Card("6", PokerSuit.CLUBS));
            add(new Card("7", PokerSuit.CLUBS));
            add(new Card("8", PokerSuit.CLUBS));
            add(new Card("9", PokerSuit.CLUBS));
            add(new Card("10", PokerSuit.CLUBS));
            add(new Card("J", PokerSuit.CLUBS));
            add(new Card("Q", PokerSuit.CLUBS));
            add(new Card("K", PokerSuit.CLUBS));
            add(new Card("A", PokerSuit.CLUBS));

            add(new Card("2", PokerSuit.HERATS));
            add(new Card("3", PokerSuit.HERATS));
            add(new Card("4", PokerSuit.HERATS));
            add(new Card("5", PokerSuit.HERATS));
            add(new Card("6", PokerSuit.HERATS));
            add(new Card("7", PokerSuit.HERATS));
            add(new Card("8", PokerSuit.HERATS));
            add(new Card("9", PokerSuit.HERATS));
            add(new Card("10", PokerSuit.HERATS));
            add(new Card("J", PokerSuit.HERATS));
            add(new Card("Q", PokerSuit.HERATS));
            add(new Card("K", PokerSuit.HERATS));
            add(new Card("A", PokerSuit.HERATS));

            add(new Card("2", PokerSuit.DIAMS));
            add(new Card("3", PokerSuit.DIAMS));
            add(new Card("4", PokerSuit.DIAMS));
            add(new Card("5", PokerSuit.DIAMS));
            add(new Card("6", PokerSuit.DIAMS));
            add(new Card("7", PokerSuit.DIAMS));
            add(new Card("8", PokerSuit.DIAMS));
            add(new Card("9", PokerSuit.DIAMS));
            add(new Card("10", PokerSuit.DIAMS));
            add(new Card("J", PokerSuit.DIAMS));
            add(new Card("Q", PokerSuit.DIAMS));
            add(new Card("K", PokerSuit.DIAMS));
            add(new Card("A", PokerSuit.DIAMS));
            }};
    }

    //根据牌桌上每个人的手牌计算各自的赢面、平局、输面概率
    public static HandOddsResult odds(List<List<Card>> pockets, List<Card>board){
        int pocketsNum = pockets.size();
        HandOddsResult result = new HandOddsResult(pocketsNum);
        List<Card> totalCards = new ArrayList<Card>();
        long[] wins = new long[pocketsNum];
        long[] ties = new long[pocketsNum];
        long[] losses = new long[pocketsNum];
        long totalHands = 0;
        long bestCnt = 0;

        for(int i=0; i<pocketsNum; i++){
            wins[i] = ties[i] = losses[i] = 0;
        }

        /*
        totalCards.addAll(allCards);
        for (List<Card> pocket: pockets) {
            totalCards.removeAll(pocket);
        }
        totalCards.removeAll(board);
        */
        for(Card card : allCards){
            boolean flag = true;

            for(List<Card> l : pockets){
                for(Card c : l){
                    if(c.getValueNum() == card.getValueNum() && c.getSuit().getSuitNum() == card.getSuit().getSuitNum()){
                        flag = false;
                        break;
                    }
                }
                if(!flag){
                    break;
                }
            }

            if(!flag){
                continue;
            }

            for(Card c : board){
                if(c.getValueNum() == card.getValueNum() && c.getSuit().getSuitNum() == card.getSuit().getSuitNum()){
                    flag = false;
                    break;
                }
            }

            if(flag){
                totalCards.add(card);
            }
        }

        RankHandResult[] pocketRankHandResult = new RankHandResult[pocketsNum];
        List<Card> tmpHand = new ArrayList<Card>();
        for (ArrayList<Card> cards: generateCardsIterator(totalCards, 5-board.size())) {
            tmpHand.clear();
            tmpHand.addAll(pockets.get(0));
            tmpHand.addAll(board);
            tmpHand.addAll(cards);
            pocketRankHandResult[0] = HandAnalyzer.rankHand(tmpHand);
            RankHandResult bestPocket  = pocketRankHandResult[0];
            bestCnt = 1;
            for(int i=1; i<pocketsNum; i++){
                tmpHand.clear();
                tmpHand.addAll(pockets.get(i));
                tmpHand.addAll(board);
                tmpHand.addAll(cards);
                pocketRankHandResult[i] = HandAnalyzer.rankHand(tmpHand);

                if(pocketRankHandResult[i].betterThan(bestPocket) == 1){
                    bestPocket = pocketRankHandResult[i];
                    bestCnt = 1;
                }
                else if(pocketRankHandResult[i].betterThan(bestPocket) == 0){
                    bestCnt++;
                }
            }

            for(int i=0; i<pocketsNum; i++){
                if(pocketRankHandResult[i].betterThan(bestPocket) == 0){
                    if(bestCnt > 1){
                        ties[i]++;
                    }
                    else{
                        wins[i]++;
                    }
                }
                else {
                    losses[i]++;
                }
            }
            totalHands++;
        }

        result.setTotalHands(totalHands);
        result.setWins(wins);
        result.setTies(ties);
        result.setLosses(losses);

        return result;
    }

    //根据起手牌和牌桌上的牌计算自己和一个假象的平均对手的各个成牌的概率
    public static HandOpponentOddsResult opponentOdds(List<Card> pocket, List<Card>board){
        HandOpponentOddsResult result = new HandOpponentOddsResult();
        List<Card> totalCards = new ArrayList<Card>();
        long[] players = new long[11];
        long[] opponents = new long[11];
        long totalHands = 0;

        for(int i=0; i<11; i++)
        {
            players[i] = opponents[i] = 0;
        }

        for(Card card : allCards){
            boolean flag = true;

            for(Card c : pocket){
                if(c.getValueNum() == card.getValueNum() && c.getSuit().getSuitNum() == card.getSuit().getSuitNum()){
                    flag = false;
                    break;
                }
            }

            if(!flag){
                continue;
            }

            for(Card c : board){
                if(c.getValueNum() == card.getValueNum() && c.getSuit().getSuitNum() == card.getSuit().getSuitNum()){
                    flag = false;
                    break;
                }
            }

            if(flag){
                totalCards.add(card);
            }
        }

        RankHandResult playerResult;
        RankHandResult opponentResult;
        List<Card> playerCards = new ArrayList<Card>();
        List<Card> opponentCards = new ArrayList<Card>();
        for (ArrayList<Card> cards: generateCardsIterator(totalCards, 5-board.size())) {
            List<Card> remainCards = new ArrayList<Card>(totalCards);
            remainCards.removeAll(cards);
            for(ArrayList<Card> oppcards: generateCardsIterator(remainCards, 2)){
                playerCards.clear();
                playerCards.addAll(pocket);
                playerCards.addAll(board);
                playerCards.addAll(cards);
                playerResult = HandAnalyzer.rankHand(playerCards);

                opponentCards.clear();
                opponentCards.addAll(oppcards);
                opponentCards.addAll(board);
                opponentCards.addAll(cards);
                opponentResult = HandAnalyzer.rankHand(opponentCards);

                if(playerResult.betterThan(opponentResult) == 1){
                    players[playerResult.getHandAnalyzerResult().getRank()] += 2;
                }
                else if(playerResult.betterThan(opponentResult) == 0){
                    players[playerResult.getHandAnalyzerResult().getRank()] += 1;
                    opponents[opponentResult.getHandAnalyzerResult().getRank()] += 1;
                }
                else{
                    opponents[opponentResult.getHandAnalyzerResult().getRank()] += 2;
                }

                totalHands += 2;
            }
        }

        result.setTotalHands(totalHands);
        result.setPlayer(players);
        result.setOpponent(opponents);

        return result;
    }
    private static ArrayList<ArrayList<Card>> generateCardsIterator(List<Card> cards, int size){
        ArrayList<ArrayList<Card>> generate = new ArrayList<ArrayList<Card>>();
        ArrayList<ArrayList<Integer>> combs = combs2_47;

        if(size == 2 || size == 5){
            if(size == 5){
                combs = combs5_50;
            }
            else if(cards.size() == 47){
                combs = combs2_47;
            }
            else if(cards.size() == 45){
                combs = combs2_45;
            }
            else if(cards.size() == 43){
                combs = combs2_43;
            }
            else if(cards.size() == 41){
                combs = combs2_41;
            }
            else if(cards.size() == 39){
                combs = combs2_39;
            }
            else if(cards.size() == 37){
                combs = combs2_37;
            }
            else if(cards.size() == 35){
                combs = combs2_35;
            }
            else if(cards.size() == 33){
                combs = combs2_33;
            }
            else if(cards.size() == 31){
                combs = combs2_31;
            }

            for (ArrayList<Integer> c : combs) {
                ArrayList<Card> tmp = new ArrayList<Card>();
                tmp.add(cards.get(c.get(0)));
                tmp.add(cards.get(c.get(1)));
                generate.add(tmp);
            }
        }
        //assert 1
        else if (size == 1) {
            for(Card c : cards){
                ArrayList<Card> tmp = new ArrayList<Card>();
                tmp.add(c);
                generate.add(tmp);
            }
        }
        else {
            ArrayList<Card> tmp = new ArrayList<Card>();
            generate.add(tmp);
        }

        return generate;
    }
}
