package my.game.server.utils

import akka.actor.ActorRef

class PlayerRef(val actorRef:ActorRef, var map:Option[String], val uuid:String){
}

object PlayerRef{

	def apply(actorRef:ActorRef, map:Option[String], uuid:String):PlayerRef = new PlayerRef(actorRef, map, uuid)
}