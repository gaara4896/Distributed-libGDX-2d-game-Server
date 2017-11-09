package my.game.server.actor

import akka.actor.Actor

import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

class ServerConnectionActor extends Actor{

	def receive = {
		case Connect => 
			println("Received")
			sender() ! Connected
		case _ => println("Unknown")
	}
}