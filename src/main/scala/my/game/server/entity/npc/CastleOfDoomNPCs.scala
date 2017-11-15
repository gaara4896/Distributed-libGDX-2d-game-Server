package my.game.server.entity.npc

import my.game.server.entity.RemoteNPC
import my.game.pkg.entity.utils.{Direction, Job}

import java.util.UUID

import scala.collection.mutable.ListBuffer

class CastleOfDoomNPCs extends MapNPCs {

	val npcs = ListBuffer(
		RemoteNPC(UUID.randomUUID().toString(), Job.ENGINEER, "CASTLE_OF_DOOM", Direction.RIGHT, 7.2145f, 4.0440f),
		RemoteNPC(UUID.randomUUID().toString(), Job.ENGINEER, "CASTLE_OF_DOOM", Direction.RIGHT, 7.2145f, 4.0440f),
		RemoteNPC(UUID.randomUUID().toString(), Job.ROGUE, "CASTLE_OF_DOOM", Direction.LEFT, 24.0559f, 4.0440f),
		RemoteNPC(UUID.randomUUID().toString(), Job.PALADIN, "CASTLE_OF_DOOM", Direction.RIGHT, 7.2145f, 10.0111f),
		RemoteNPC(UUID.randomUUID().toString(), Job.ENGINEER, "CASTLE_OF_DOOM", Direction.LEFT, 24.0559f, 10.0111f),
		RemoteNPC(UUID.randomUUID().toString(), Job.ROGUE, "CASTLE_OF_DOOM", Direction.RIGHT, 7.2145f, 16.0407f),
		RemoteNPC(UUID.randomUUID().toString(), Job.WARRIOR, "CASTLE_OF_DOOM", Direction.LEFT, 24.0559f, 16.0407f),
		RemoteNPC(UUID.randomUUID().toString(), Job.ROGUE, "CASTLE_OF_DOOM", Direction.RIGHT, 2.5f, 75.5f)
	)

	val movingNpcs = ListBuffer()

}

object CastleOfDoomNPCs{

	/**
	 * Apply method for creating CastleOfDoomNPCs
	 * @return CastlrOfDoomNPCs New instance of CastleOfDoomNPCs
	 */
	def apply():CastleOfDoomNPCs = new CastleOfDoomNPCs
}
