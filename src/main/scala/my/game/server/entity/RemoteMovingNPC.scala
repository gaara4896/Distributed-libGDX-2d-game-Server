package my.game.server.entity

import my.game.pkg.entity.utils.{Direction, State}
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._
import my.game.server.Server
import my.game.server.dictionary.ServerDictionary._

import scala.collection.mutable.Queue
import scala.util.Random

class RemoteMovingNPC(_uuid:String, _job:Job, _map:String, _x:Float, _y:Float, val rangeX:Float, val rangeY:Float) extends RemoteNPC(_uuid, _job, _map, Direction.RIGHT, _x, _y){
	
	val speed:Float = 3f
	var directionSequence = Queue(Direction.DOWN, Direction.LEFT, Direction.UP)
	var countDownRange:Float = rangeX
	var restTime:Float = 0
	var state = State.WALKING

	def update(delta:Float){
		state match{
			case State.IDLE => 
				restTime -= delta
				if(restTime <= 0) {
					state = State.WALKING
					directionSequence += direction
					direction = directionSequence.dequeue()
					if(direction == Direction.RIGHT || direction == Direction.LEFT){
						countDownRange = rangeX
					} else {
						countDownRange = rangeY
					}
					Server.gameServerActor ! UpdateNPC(uuid, map, direction, x, y, countDownRange)
				}

			case State.WALKING => 
				direction match{
					case Direction.LEFT =>
						x -= speed * delta
						countDownRange -= speed * delta					
					case Direction.RIGHT =>
						x += speed * delta
						countDownRange -= speed * delta
					case Direction.UP =>
						y += speed * delta
						countDownRange -= speed * delta
					case Direction.DOWN =>
						y -= speed * delta
						countDownRange -= speed * delta
					case _ =>
				}
				if(countDownRange <= 0){
					state = State.IDLE
					restTime = 1f + (Random.nextFloat % 5f)
				}

		}
	}
}

object RemoteMovingNPC{

	def apply(uuid:String, job:Job, map:String, x:Float, y:Float, rangeX:Float, rangeY:Float):RemoteMovingNPC = new RemoteMovingNPC(uuid, job, map, x, y, rangeX, rangeY)
}