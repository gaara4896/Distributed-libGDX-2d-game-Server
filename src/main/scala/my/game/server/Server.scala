package my.game.server

import akka.actor.{ActorSystem, Props, ActorRef}
import akka.routing.{Router, BroadcastRoutingLogic}

import my.game.server.actor.{ServerConnectionActor, GameServerActor}
import my.game.server.utils.PlayerRef
import my.game.server.dictionary.ServerDictionary._
import my.game.server.entity.npc.MapNPCs

import scala.collection.mutable.{ListBuffer, Map}
import scala.io.StdIn

object Server extends App{
	val system = ActorSystem("bludbourne")
	val serverConnectionActor = system.actorOf(Props[ServerConnectionActor], "serverconenction")
	val gameServerActor = system.actorOf(Props[GameServerActor], "gameserver")
	val players = new ListBuffer[PlayerRef]

	val mapRouter:Map[String, Router] = Map(
		"TOWN" -> new Router(new BroadcastRoutingLogic()), 
		"TOP_WORLD" -> new Router(new BroadcastRoutingLogic()), 
		"CASTLE_OF_DOOM" -> new Router(new BroadcastRoutingLogic()))
	val remoteNpc:Map[String, MapNPCs] = Map(
		"TOWN" -> MapNPCs("TOWN"), 
		"TOP_WORLD" -> MapNPCs("TOP_WORLD"),
	 	"CASTLE_OF_DOOM" -> MapNPCs("CASTLE_OF_DOOM"))

	var lastFrameTime = System.nanoTime()/1000000000d
	var checkPlayerAlive:Float = 3

	while(true){
		var delta = getDelta(System.nanoTime()/1000000000d).toFloat

		checkPlayerAlive -= delta
		if(checkPlayerAlive <= 0){
			players.foreach{player => 
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
			players.foreach{player => player.aliveFlag = false}
			checkPlayerAlive = 3f
		}
		remoteNpc.keys.foreach{mapNpc => remoteNpc(mapNpc).update(delta)}
		Thread.sleep(30)
	}

	def getDelta(currentNanoTime:Double):Double = {
		var delta = currentNanoTime - lastFrameTime
		if(delta > 1){
			delta = (currentNanoTime + 1000000) - lastFrameTime
		}
		lastFrameTime = currentNanoTime 
		delta
	}
}