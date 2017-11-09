package my.game.server

import akka.actor.{ActorSystem, Props}

import my.game.server.actor.ServerConnectionActor

import scala.io.StdIn

object Server extends App{
	val system = ActorSystem("bludbourne")
	val serverConnectionActor = system.actorOf(Props[ServerConnectionActor], "serverconenction")

	while(true){
		if(StdIn.readLine("Enter [quit] to terminate the server").equals("quit")){
			system.terminate
			System.exit(0)
		}
	}
}