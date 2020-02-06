package controllers

import holdem.{HandAnalyzer, HandOdds}
import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.api.cache.SyncCacheApi
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.HoldemService

import scala.concurrent.{ExecutionContext, Future}
import scala.collection.JavaConverters._

@Singleton
class AdapterController @Inject()(cc: ControllerComponents,
                                  cache: SyncCacheApi,
                                  holdemService: HoldemService
                                 )(implicit executionContext: ExecutionContext) extends AbstractController(cc) with Logging {

  def helloWorld(): Action[AnyContent] = Action.async { req =>
    Future {
      Ok("Hello World")
    }
  }

  def combinatorialCalculator(bc: Int): Action[AnyContent] = Action.async { req =>
    Future {
      val nowTime = System.currentTimeMillis()
//      holdemService.testCards()
      holdemService.testOpponentOdds(bc)
      val costTime = System.currentTimeMillis() - nowTime
      val result = s"Cost time: $costTime"
      println(result)
      Ok(result)
    }
  }

  def calculator(): Action[JsValue] = Action.async(parse.json) { req =>
    val pcs = (req.body \ "pcs").as[List[String]]
    val bcs = (req.body \ "bcs").as[List[String]]
    Future {
      val res = holdemService.calculatorOpponentOdds(pcs, bcs)
      Ok(res)
    }
  }

}
