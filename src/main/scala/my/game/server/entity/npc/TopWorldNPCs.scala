package my.game.server.entity.npc

import my.game.server.entity.RemoteMovingNPC
import my.game.pkg.entity.utils.Job

import java.util.UUID

import scala.collection.mutable.ListBuffer

class TopWorldNPCs extends MapNPCs {

	val npcs = ListBuffer()

	val movingNpcs = ListBuffer(
		RemoteMovingNPC(UUID.randomUUID().toString(), Job.WARRIOR, "TOP_WORLD", 47.1329f, 68.2196f, 0.5f, 5f),
		RemoteMovingNPC(UUID.randomUUID().toString(), Job.WARRIOR, "TOP_WORLD", 16.6086f, 23.1235f, 1f, 4.5292f),
		RemoteMovingNPC(UUID.randomUUID().toString(), Job.ROGUE, "TOP_WORLD", 45.7690f, 30.3483f, 7.3f, 2f),
		RemoteMovingNPC(UUID.randomUUID().toString(), Job.PALADIN, "TOP_WORLD", 15.2256f, 34.9951f, 22f, 9.5f)
	)

}

object TopWorldNPCs{

	/**
	 * Apply method for creating TopWorldNPCs
	 * @return TopWorldNPCs New instance of TopWorldNPCs
	 */
	def apply():TopWorldNPCs = new TopWorldNPCs
}
