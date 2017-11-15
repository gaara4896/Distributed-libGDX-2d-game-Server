package my.game.server.entity

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._

class RemoteNPC(val uuid:String, val job:Job, val map:String, var direction:Direction, var x:Float, var y:Float)

object RemoteNPC{
	def apply(uuid:String, job:Job, map:String, direction:Direction, x:Float, y:Float):RemoteNPC = new RemoteNPC(uuid, job, map, direction, x, y)
}