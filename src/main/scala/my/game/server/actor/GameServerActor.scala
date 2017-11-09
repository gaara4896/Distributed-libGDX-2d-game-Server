package my.game.server.actor

import akka.actor.Actor

import my.game.server.Server
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

import scala.collection.mutable.Set

class GameServerActor extends Actor{

	def receive = {
		case Update(uuid, map, x, y, direction, frameTime) => 
			Server.players.foreach{player =>
				if(player.actorRef == sender()){
					player.map = Option(map)
				} else {
					if(player.map == map){
						player.actorRef ! UpdatePlayerStatus(uuid, x, y, direction, frameTime)
					}
				}
			}
	}
}