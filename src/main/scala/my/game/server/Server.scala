package my.game.server

import akka.actor.{ActorSystem, Props, ActorRef}

import my.game.server.actor.ServerConnectionActor
import my.game.server.utils.PlayerRef

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

object Server extends App{
	val system = ActorSystem("bludbourne")
	val serverConnectionActor = system.actorOf(Props[ServerConnectionActor], "serverconenction")
	val players = new ArrayBuffer[PlayerRef]

	while(true){
		if(StdIn.readLine("Enter [quit] to terminate the server").equals("quit")){
			system.terminate
			System.exit(0)
		}
	}
}