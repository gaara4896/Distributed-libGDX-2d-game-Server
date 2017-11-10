package my.game.server.actor

import akka.actor.Actor

import my.game.server.Server
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

import scala.collection.mutable.Set

class GameServerActor extends Actor{

	def receive = {
		case Update(uuid, map, x, y, direction, frameTime) => 
			println("Received")
			Server.players.foreach{player =>
				if(player.uuid.equals(uuid)){
					println("self")
					player.map = Option(map)
				} else {
					player.map match{
						case Some(somemap) =>
							if(somemap.equals(map)){
								println("Send")
								player.actorRef ! UpdatePlayerStatus(uuid, x, y, direction, frameTime)
							}
					}
					println("Finish Send")
				}
			}
	}
}