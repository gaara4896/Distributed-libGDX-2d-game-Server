package my.game.server.entity.npc

import my.game.server.entity.{RemoteNPC, RemoteMovingNPC}
import my.game.pkg.entity.utils.{Direction, Job}

import java.util.UUID

import scala.collection.mutable.ListBuffer

class TownNPCs extends MapNPCs {

	val npcs = ListBuffer(
		RemoteNPC(UUID.randomUUID().toString(), Job.WARRIOR, "TOWN", Direction.DOWN, 11.6835f, 23.3770f),
		RemoteNPC(UUID.randomUUID().toString(), Job.PALADIN, "TOWN", Direction.DOWN, 4.0811f, 26.3357f)
	)

	val movingNpcs = ListBuffer(
		RemoteMovingNPC(UUID.randomUUID().toString(), Job.ROGUE, "TOWN", 8, 20, 11, 3),
		RemoteMovingNPC(UUID.randomUUID().toString(), Job.ENGINEER, "TOWN", 9.7f, 8f, 1f, 5.1467f)
	)

  }

object TownNPCs{

	/**
	 * Apply method for creating TownNPCs
	 * @return TownNPCs New instance of TownNPCs
	 */
	def apply():TownNPCs = new TownNPCs
}
