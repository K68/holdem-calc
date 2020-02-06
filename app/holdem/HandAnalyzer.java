package holdem;

import java.util.*;

public class HandAnalyzer {

	static ArrayList<ArrayList<Integer>> combs55;
	static ArrayList<ArrayList<Integer>> combs56;
	static ArrayList<ArrayList<Integer>> combs57;

	static {
		combs55 = getCombinations(5, 5);
		combs56 = getCombinations(5, 6);
		combs57 = getCombinations(5, 7);
	}

	public static RankHandResult rankHand(List<Card> cards) {
		ArrayList<ArrayList<Integer>> combs;
		int size = cards.size();
		if (size == 5) {
			combs = combs55;
		} else if (size == 6) {
			combs = combs56;
		} else if (size == 7) {
			combs = combs57;
		} else {
			combs = getCombinations(5, cards.size());
		}
		Long[] cs = Card.getCSArray(cards);
		Long[] ss = Card.getSSArray(cards);

		int maxRank = 0;
		long maxScore = 0;

		HandAnalyzerResult hv;
		HandAnalyzerResult winHv = null;
		ArrayList<Integer> wci = null;
		long[] wcs = null;

		for (ArrayList<Integer> c : combs) {
			long[] _cs = { cs[c.get(0)], cs[c.get(1)], cs[c.get(2)], cs[c.get(3)], cs[c.get(4)] };
			long[] _ss = { ss[c.get(0)], ss[c.get(1)], ss[c.get(2)], ss[c.get(3)], ss[c.get(4)] };
			hv = rankPokerHand(_cs, _ss);

			if (hv.getRank() > maxRank) {
				maxRank = hv.getRank();
				winHv = hv;
				wci = c;
				wcs = _cs;
				maxScore = 0; // two low before may change the score, clear it

			} else if (hv.getRank() == maxRank) {
				// If by chance we have a tie, find the best one
				long score1 = getPokerScoreFix(_cs, maxRank, hv.getAce());
				long score2 = getPokerScoreFix(wcs, maxRank, winHv.getAce());
				if (score1 > score2) {
					winHv = hv;
					wci = c;
					wcs = _cs;
					maxScore = score1;
				} else {
					maxScore = score2;
				}
			}
		}

		if (maxScore == 0) {
			maxScore = getPokerScoreFix(wcs, maxRank, winHv.getAce());
		}

		RankHandResult result = new RankHandResult();
		result.setHandAnalyzerResult(winHv);
		result.setScore(maxScore);
		List<Card> r_cards = new ArrayList<Card>();
		r_cards.add(cards.get(wci.get(0)));
		r_cards.add(cards.get(wci.get(1)));
		r_cards.add(cards.get(wci.get(2)));
		r_cards.add(cards.get(wci.get(3)));
		r_cards.add(cards.get(wci.get(4)));
		result.setCards(r_cards);
		return result;
	}

	public static HandAnalyzerResult rankPokerHand(long[] cs, long[] ss) {
		long v, o, s = (long) (1 << cs[0] | 1 << cs[1] | 1 << cs[2] | 1 << cs[3] | 1 << cs[4]);

		v = o = 0L;
		for (int i = -1; i < 5; i++) {
			if (o != 0) {
				v += o * ((v / o & 15) + 1);
			}
			if (i != 4) {
				o = (long) Math.pow(2, cs[i + 1] * 4);
			}
		}

		v = v % 15 - ((s / (s & -s) == 31) || (s == 0x403c) ? 3 : 1);

		if (ss[0] == (ss[1] | ss[2] | ss[3] | ss[4])) {
			v -= ((s == 0x7c00) ? -5 : 1);
		}
		return new HandAnalyzerResult(Hands.getHands().get((int) v), (s == 0x403c ? "L" : "H"));
	}

	public static long getPokerScoreFix(long[] cs, int rank, String ace) {
		if ((rank == 9 || rank == 5) && ("L".equals(ace))) {
			return 1;
		} else {
			return getPokerScore(cs);
		}
	}

	public static long getPokerScore(long[] cs) {
		ArrayList<Long> a = new ArrayList<Long>();
		for (int i = 0; i < cs.length; i++) {
			a.add(cs[i]);
		}
		final Map<Long, Long> d = new HashMap<Long, Long>();
		for (int i = 0; i < 5; i++) {
			long value = 1;
			if (d.get(a.get(i)) != null && d.get(a.get(i)) >= 1) {
				value = d.get(a.get(i)) + 1;
			}
			d.put(a.get(i), value);
		}
		
		Collections.sort(a, new Comparator<Long>() {
			@Override
			public int compare(Long a, Long b) {
				return (int) ((d.get(a) < d.get(b)) ? +1 : (d.get(a) > d.get(b)) ? -1 : (b - a));
			}
		});
		/*
		a.sort(new Comparator<Long>() {
			@Override
			public int compare(Long a, Long b) {
				return (int) ((d.get(a) < d.get(b)) ? +1 : (d.get(a) > d.get(b)) ? -1 : (b - a));
			}
		});*/
		
		return a.get(0) << 16 | a.get(1) << 12 | a.get(2) << 8 | a.get(3) << 4 | a.get(4);
	}

	public static ArrayList<ArrayList<Integer>> getCombinations(int k, int n) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> comb = new ArrayList<Integer>();

		while (next_comb(comb, k, n)) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.addAll(comb);
			result.add(temp);
		}
		return result;
	}

	public static boolean next_comb(ArrayList<Integer> comb, int k, int n) {
		Integer i = 0;
		if (comb.isEmpty()) {
			for (i = 0; i < k; i++) {
				comb.add(i);
			}
			return true;
		}
		i = k - 1;
		comb.set(i, comb.get(i) + 1);
		while ((i > 0) && (comb.get(i) >= n - k + 1 + i)) {
			--i;
			comb.set(i, comb.get(i) + 1);
		}
		if (comb.get(0) > n - k) {
			return false;
		}
		for (i = i + 1; i < k; ++i) {
			comb.set(i, comb.get(i - 1) + 1);
		}
		return true;
	}

}