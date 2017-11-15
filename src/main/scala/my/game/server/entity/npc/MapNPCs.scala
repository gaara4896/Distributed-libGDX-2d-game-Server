package my.game.server.entity.npc

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._
import my.game.server.entity.{RemoteNPC, RemoteMovingNPC}

import scala.collection.mutable.ListBuffer

abstract class MapNPCs{

	val npcs:ListBuffer[RemoteNPC]
	val movingNpcs:ListBuffer[RemoteMovingNPC]

	/**
	 * Update NPCs
	 * @param delta:Float Delta time of the frame
	 */
	def update(delta:Float){
		movingNpcs.foreach{npc => npc.update(delta)}
	}

	def getNPCList():ListBuffer[(String, Job, Direction, Float, Float, Float)] = {
		val npcList = new ListBuffer[(String, Job, Direction, Float, Float, Float)]()
		npcs.foreach{npc => npcList += ((npc.uuid, npc.job, npc.direction, npc.x, npc.y, 0))}
		movingNpcs.foreach{npc => npcList += ((npc.uuid, npc.job, npc.direction, npc.x, npc.y, npc.countDownRange))}
		npcList
	}
}

object MapNPCs{

	/**
	 * Apply method for creating MapNPCs
	 * @param  mapName:String Map name of the NPCs to be created
	 * @return MapNPCs New instance of MapNPCs
	 */
	def apply(mapName:String):MapNPCs = {
		mapName match{
			case "TOWN" => TownNPCs()
			case "TOP_WORLD" =>  TopWorldNPCs()
			case "CASTLE_OF_DOOM" => CastleOfDoomNPCs()
			case _ => TownNPCs()
		}
	}
}
