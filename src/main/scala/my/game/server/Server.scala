package my.game.server

import akka.actor.{ActorSystem, Props, ActorRef}

import my.game.server.actor.{ServerConnectionActor, GameServerActor}
import my.game.server.utils.PlayerRef
import my.game.server.dictionary.ServerDictionary._

import scala.collection.mutable.ListBuffer
import scala.io.StdIn

object Server extends App{
	val system = ActorSystem("bludbourne")
	val serverConnectionActor = system.actorOf(Props[ServerConnectionActor], "serverconenction")
	val gameServerActor = system.actorOf(Props[GameServerActor], "gameserver")
	val players = new ListBuffer[PlayerRef]

	while(true){
		for(player <- players){
			player.aliveFlag = false
		}
		Thread.sleep(3000)
		for(player <- players){
			if(!player.aliveFlag){
				player.map match{
					case Some(somemap) => 
						gameServerActor ! NotAlive(player.uuid, somemap)
						players -= player
					case None => 
				}
			}
		}
	}
}