package my.game.server.actor

import akka.actor.Actor

import my.game.server.Server
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._
import my.game.server.utils.PlayerRef

import java.util.UUID
import scala.collection.mutable.Set
import scala.util.control.Breaks._

class ServerConnectionActor extends Actor{

	/**
	 * Called when actor received message
	 */
	def receive = {
		case Connect => 
			val playerUUID = UUID.randomUUID().toString()
			Server.players += PlayerRef(sender(), None, playerUUID)
			sender() ! Connected(playerUUID)
		case Quit => breakable{ Server.players.foreach{player =>
				if(player.actorRef == sender()){
					Server.players -= player
					break
				}
			}}
	}
}