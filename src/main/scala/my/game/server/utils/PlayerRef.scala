package my.game.server.utils

import akka.actor.ActorRef

class PlayerRef(val actorRef:ActorRef, var map:Option[String], val uuid:String){
	var aliveFlag = true
}

object PlayerRef{

	/**
	 * Apply method for creating PlayRef
	 * @param  actorRef:ActorRef  Client ActorRef of the player
	 * @param  map:Option[String] Map of the user
	 * @param  uuid:String        UUID of the user
	 * @return PlayerRef          New instance of PlayerRef
	 */
	def apply(actorRef:ActorRef, map:Option[String], uuid:String):PlayerRef = new PlayerRef(actorRef, map, uuid)
}