package my.game.server.entity

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._

class RemoteNPC(val uuid:String, val job:Job, val map:String, var direction:Direction, var x:Float, var y:Float)

object RemoteNPC{

	/**
	 * Apply method for creating RemoteNPC
	 * @param  uuid:String         UUID of the Remote NPC
	 * @param  job:Job             Job of the NPC
	 * @param  map:String          Map of the NPC locate at
	 * @param  direction:Direction Direction of the NPC
	 * @param  x:Float             X position of the NPC start with
	 * @param  y:Float             Y position of the NPC start with
	 * @return RemoteNPC           New Instance of RemoteNPC
	 */
	def apply(uuid:String, job:Job, map:String, direction:Direction, x:Float, y:Float):RemoteNPC = new RemoteNPC(uuid, job, map, direction, x, y)
}