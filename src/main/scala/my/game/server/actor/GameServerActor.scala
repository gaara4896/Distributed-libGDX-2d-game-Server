package my.game.server.actor

import akka.actor.Actor

import my.game.server.Server
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._
import my.game.pkg.entity.utils.Job._

import scala.collection.mutable.Set
import scala.util.control.Breaks._

class GameServerActor extends Actor{

	/**
	 * Called when received actor message
	 */
	def receive = {
		case Join(uuid, map) =>
			Server.mapRouter(map) = Server.mapRouter(map).addRoutee(sender())
			breakable{
				Server.players.foreach{player => 
					if(player.uuid.equals(uuid)){
						player.map = Option(map)
						player.actorRef ! Ping
						break
					}
				}
			}

		case Move(uuid, map, direction) => 
			Server.mapRouter(map).route(PlayerMove(uuid, direction), self)

		case StandStill(uuid, job, map, x, y) =>
			Server.mapRouter(map).route(PlayerStandStill(uuid, job, x, y), self)

		case ChangeMap(uuid, job, mapFrom, mapTo, x, y) =>
			Server.mapRouter(mapFrom) = Server.mapRouter(mapFrom).removeRoutee(sender())
			Server.mapRouter(mapFrom).route(KillPlayer(uuid), self)
			Server.mapRouter(mapTo).route(PlayerStandStill(uuid, job, x, y), self)
			Server.mapRouter(mapTo) = Server.mapRouter(mapTo).addRoutee(sender())
			breakable{
				Server.players.foreach{player => 
					if(player.uuid.equals(uuid)){
						player.map = Option(mapTo)
						break
					}
				}
			}

		case Alive(uuid, job, map, x, y, direction, state, frameTime) =>
			Server.mapRouter(map).route(Correction(uuid, job, x, y, direction, state, frameTime), self)
			breakable{
				Server.players.foreach{player => 
					if(player.uuid.equals(uuid)){
						player.aliveFlag = true
						player.actorRef ! Ping
						break
					}
				}
			}

		case NotAlive(uuid, map) => 
			Server.mapRouter(map).route(KillPlayer(uuid), self)
	}
}