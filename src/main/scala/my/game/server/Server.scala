package my.game.server

import akka.actor.{ActorSystem, Props, ActorRef}
import akka.routing.{Router, BroadcastRoutingLogic}

import my.game.server.actor.{ServerConnectionActor, GameServerActor}
import my.game.server.utils.PlayerRef
import my.game.server.dictionary.ServerDictionary._

import scala.collection.mutable.{ListBuffer, Map}
import scala.io.StdIn

object Server extends App{
	val system = ActorSystem("bludbourne")
	val serverConnectionActor = system.actorOf(Props[ServerConnectionActor], "serverconenction")
	val gameServerActor = system.actorOf(Props[GameServerActor], "gameserver")
	val players = new ListBuffer[PlayerRef]

	val mapRouter:Map[String, Router] = Map("TOWN" -> new Router(new BroadcastRoutingLogic()), 
		"TOP_WORLD" -> new Router(new BroadcastRoutingLogic()), "CASTLE_OF_DOOM" -> new Router(new BroadcastRoutingLogic()))

	while(true){
		for(player <- players){
			player.aliveFlag = false
		}
		Thread.sleep(3000)
		for(player <- players){
			if(!player.aliveFlag){
				player.map match{
					case Some(somemap) => 
						mapRouter(somemap) = mapRouter(somemap).removeRoutee(player.actorRef)
						gameServerActor ! NotAlive(player.uuid, somemap)
						players -= player
					case None => 
				}
			}
		}
	}
}