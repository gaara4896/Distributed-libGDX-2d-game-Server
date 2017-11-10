package my.game.server

import akka.actor.{ActorSystem, Props, ActorRef}

import my.game.server.actor.{ServerConnectionActor, GameServerActor}
import my.game.server.utils.PlayerRef

import scala.collection.mutable.ListBuffer
import scala.io.StdIn

object Server extends App{
	val system = ActorSystem("bludbourne")
	val serverConnectionActor = system.actorOf(Props[ServerConnectionActor], "serverconenction")
	val gameServerActor = system.actorOf(Props[GameServerActor], "gameserver")
	val players = new ListBuffer[PlayerRef]

	while(true){
		if(StdIn.readLine("Enter [quit] to terminate the server").equals("quit")){
			system.terminate
			System.exit(0)
		}
	}
}