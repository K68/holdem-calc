import scala.collection.JavaConverters._
import holdem.{HandAnalyzer, HandOdds, PokerSuit}
import zooModels.Messages.Card
import zooModels.{CardValue, PokerHands, Suit}
import play.api.libs.json.Json
import zooModels.Deck

val cs = List[Long](4, 5, 11, 14, 9)
val ss = List[Long](1, 2, 4, 8, 8)

val hv1 = HandAnalyzer.rankPokerHand(cs.toArray, ss.toArray)
println(hv1)

val dd = new Deck()
dd.shuffleDeck()
println(dd.popCard())

val boards = List[holdem.Card](
  new holdem.Card("3", PokerSuit.SPADES),
  new holdem.Card("10", PokerSuit.HERATS),
  new holdem.Card("A", PokerSuit.HERATS),
  new holdem.Card("K", PokerSuit.HERATS),
  new holdem.Card("7", PokerSuit.SPADES)
)

val pocket = List[holdem.Card](
  new holdem.Card("J", PokerSuit.HERATS),
  new holdem.Card("Q", PokerSuit.HERATS),
)

val pockets = List[List[holdem.Card]](
  pocket,
  List[holdem.Card](
    new holdem.Card("3", PokerSuit.CLUBS),
    new holdem.Card("8", PokerSuit.DIAMS),
  ),
  List[holdem.Card](
    new holdem.Card("10", PokerSuit.CLUBS),
    new holdem.Card("3", PokerSuit.DIAMS),
  ),
)

val pp = pockets.map(i => i.asJava).asJava

val result = HandOdds.odds(pp, boards.asJava)

//val startTime = System.currentTimeMillis()
//val result1 = HandOdds.opponentOdds(pocket.asJava, boards.asJava)
//val endTime = System.currentTimeMillis()
//
//println(endTime - startTime)

