package my.game.server.actor

import akka.actor.Actor
import my.game.server.Server
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._
import my.game.server.utils.PlayerRef
import java.util.UUID

import scala.collection.mutable.Set
import scala.util.Random
import scala.util.control.Breaks._

class ServerConnectionActor extends Actor{

	val r = Random
	/**
	 * Called when actor received message
	 */
	def receive = {
		case Connect => 
			val playerUUID = UUID.randomUUID().toString()
			val patch = r.nextInt(5)
			Server.players += PlayerRef(sender(), None, playerUUID)
			sender() ! Connected(playerUUID, patch)
			
		case Quit(uuid) => Server.players.foreach{player =>
				if(player.actorRef == sender()){
					Server.players -= player
					
				} else {
					player.actorRef ! KillPlayer(uuid)
				}
			}
	}
}