package my.game.server.actor

import akka.actor.Actor
import akka.remote.DisassociatedEvent

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
	 * Override config on prestart
	 * @return Unit Return nothing
	 */
	override def preStart():Unit = {
		context.system.eventStream.subscribe(self, classOf[akka.remote.DisassociatedEvent])
	}

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
			
		case Quit(uuid) => 
			breakable{
				for(player <- Server.players){
					if(player.actorRef == sender()){
						player.map match {
							case Some(x) => 
								Server.mapRouter(x) = Server.mapRouter(x).removeRoutee(player.actorRef)
								Server.mapRouter(x).route(KillPlayer(uuid), self)
						}
						Server.players -= player
						break
					}
				}
			}
		case DisassociatedEvent(localAddress, remoteAddress, _) => 
			for(player <- Server.players){
				if(player.actorRef.path.address == remoteAddress){
					player.map match {
						case Some(somemap) => 
							Server.mapRouter(somemap) = Server.mapRouter(somemap).removeRoutee(player.actorRef)
							Server.gameServerActor ! NotAlive(player.uuid, somemap)
							Server.players -= player
						case None => Server.players -= player
					}
				}
			}
	}
}