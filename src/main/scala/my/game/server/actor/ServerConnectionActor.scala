package my.game.server.actor

import akka.actor.Actor

import my.game.server.Server
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._
import my.game.server.utils.PlayerRef
import my.game.pkg.entity.utils.Job
import my.game.pkg.entity.utils.Job._

import java.util.UUID

import scala.collection.mutable.Set
import scala.util.Random
import scala.util.control.Breaks._

class ServerConnectionActor extends Actor{

	/**
	 * Called when actor received message
	 */
	def receive = {
		case Connect => 
			val playerUUID = UUID.randomUUID().toString()
			var job:Job = null
			Random.nextInt(4) match {
				case 0 => job = Job.WARRIOR
				case 1 => job = Job.PALADIN
				case 2 => job = Job.ROGUE
				case _=> job = Job.ENGINEER
			}
			Server.players += PlayerRef(sender(), None, playerUUID)
			sender() ! Connected(playerUUID, job)
			
		case Quit(uuid) => Server.players.foreach{player =>
				if(player.actorRef == sender()){
					Server.players -= player
					
				} else {
					player.actorRef ! KillPlayer(uuid)
				}
			}
	}
}