package services

import holdem.{Card, HandAnalyzer, HandOdds, PokerSuit}
import javax.inject.{Inject, Singleton}
import play.api.{Configuration, Environment}
import play.api.cache.AsyncCacheApi
import play.api.libs.json.{JsValue, Json}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.concurrent.ExecutionContext

@Singleton
class HoldemService @Inject() (
                                environment: Environment,
                                configuration: Configuration,
                                cache: AsyncCacheApi,
                                implicit val executionContext: ExecutionContext
                              ) {

  def mkHandStringWithTxt(cards: List[String]): String = {
    cards.sorted.mkString
  }

  def mkHandString(cards: List[Card]): String = {
    cards.map{ i =>
      i.getValue + i.getSuit.getSuitShort
    }.sorted.mkString
  }

  private def csToList(cs: List[String]): List[Card] = {
    cs.map{ i =>
      val value = i.dropRight(1)
      new Card(value, PokerSuit.bySuitShort(i.last))
    }
  }

  def calculatorOpponentOdds(pcs: List[String], bcs: List[String]): JsValue = {
    if (pcs.length == 2 && bcs.length > 3 && bcs.length < 6) {
      val pocket = csToList(pcs).asJava
      val board = csToList(bcs).asJava
      val result = HandOdds.opponentOdds(pocket, board)
      val p = (0.0 + result.getPlayer.sum) / result.getTotalHands
      val hands = csToList(pcs ++ bcs).asJava
      val hand = HandAnalyzer.rankHand(hands).getHandAnalyzerResult
      Json.obj("p" -> p, "h" -> hand.getHand, "r" -> hand.getRank)
    } else if (pcs.length == 2 && bcs.length == 3) {
      val hands = csToList(pcs ++ bcs).asJava
      val hand = HandAnalyzer.rankHand(hands).getHandAnalyzerResult
      Json.obj("p" -> -1, "h" -> hand.getHand, "r" -> hand.getRank)
    } else {
      Json.obj()
    }
  }

  def testOpponentOdds(bc: Int): Unit = {
    val pocket = List(new Card("2", PokerSuit.HERATS), new Card("2", PokerSuit.SPADES)).asJava
    val board = if (bc == 5) {
      List(new Card("A", PokerSuit.CLUBS),
        new Card("10", PokerSuit.SPADES),
        new Card("9", PokerSuit.SPADES),
        new Card("9", PokerSuit.CLUBS),
        new Card("7", PokerSuit.HERATS)).asJava
    } else if (bc == 3) {
      List(new Card("A", PokerSuit.CLUBS),
        new Card("10", PokerSuit.SPADES),
        new Card("9", PokerSuit.SPADES)).asJava
    } else {
      List(new Card("A", PokerSuit.CLUBS),
        new Card("10", PokerSuit.SPADES),
        new Card("9", PokerSuit.SPADES),
        new Card("7", PokerSuit.HERATS)).asJava
    }
    val result = HandOdds.opponentOdds(pocket, board)
    println(result.getPlayer.sum)
    println(result.getOpponent.sum)
    println(result.getTotalHands)
  }

  def testCards(): Unit = {
    val r1 = nextCombinations(None, 5, 52, 10)
    val combStart = r1.lastOption
    val r2 = nextCombinations(combStart, 5, 52, 10)
    (r1 ++ r2).map(i => mkHandString(i.asScala.map(j => HandOdds.allCards.get(j)).toList))
  }

  def nextCombinations(combStart: Option[java.util.ArrayList[Integer]], k: Int, n: Int, maxCount: Int): Seq[java.util.ArrayList[Integer]] = {
    val count = if (maxCount > 10000) 10000 else if (maxCount <= 0) 10 else maxCount
    val comb = if (combStart.isDefined) combStart.get else new java.util.ArrayList[Integer]()
    var cursor = 0
    val result = mutable.ArrayBuffer.empty[java.util.ArrayList[Integer]]
    while(cursor < count && HandAnalyzer.next_comb(comb, k, n)) {
      cursor += 1
      val temp = new java.util.ArrayList[Integer]()
      temp.addAll(comb)
      result += temp
    }
    result
  }

}
