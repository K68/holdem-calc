package controllers

import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.api.mvc._
import play.api.cache.SyncCacheApi
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               cache: SyncCacheApi
                              )(implicit executionContext: ExecutionContext, actorSystem: ActorSystem, mat: Materializer)
  extends AbstractController(cc) with Logging {

}
