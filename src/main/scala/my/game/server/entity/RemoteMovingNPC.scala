package my.game.server.entity

import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.utils.{Direction, State}
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._
import my.game.server.Server
import my.game.server.dictionary.ServerDictionary._

import scala.collection.mutable.Queue
import scala.util.Random

class RemoteMovingNPC(_uuid:String, _job:Job, _map:String, _x:Float, _y:Float, val rangeX:Float, val rangeY:Float) extends RemoteNPC(_uuid, _job, _map, Direction.RIGHT, _x, _y){
	
	val velocity = new Vector2(3f, 3f)
	var directionSequence = Queue(Direction.DOWN, Direction.LEFT, Direction.UP)
	var countDownRange:Float = rangeX
	var restTime:Float = 0
	var state = State.WALKING

	/**
	 * Update NPC status
	 * @param  delta:Float Delta time of the frame
	 */
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
				velocity.scl(delta)
				direction match{
					case Direction.LEFT =>
						x -= velocity.x
						countDownRange -= velocity.x
					case Direction.RIGHT =>
						x += velocity.x
						countDownRange -= velocity.x
					case Direction.UP =>
						y += velocity.y
						countDownRange -= velocity.y
					case Direction.DOWN =>
						y -= velocity.y
						countDownRange -= velocity.y
					case _ =>
				}
				velocity.scl(1 / delta)
				if(countDownRange <= 0){
					state = State.IDLE
					restTime = 1f + (Random.nextFloat % 5f)
				}

		}
	}
}

object RemoteMovingNPC{

	/**
	 * Apply method for creating RemoteMovingNPC
	 * @param  uuid:String     UUID of the Remote NPC
	 * @param  job:Job         Job of the NPC
	 * @param  map:String      Map of the NPC locate at
	 * @param  x:Float         X position of the NPC start with
	 * @param  y:Float         Y position of the NPC start with
	 * @param  rangeX:Float    X range of NPC will move maximum
	 * @param  rangeY:Float    Y range of NPC will move maximum
	 * @return RemoteMovingNPC New Instance of RemoteMovingNPC
	 */
	def apply(uuid:String, job:Job, map:String, x:Float, y:Float, rangeX:Float, rangeY:Float):RemoteMovingNPC = new RemoteMovingNPC(uuid, job, map, x, y, rangeX, rangeY)
}