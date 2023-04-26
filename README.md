# HelltakerPathFinder

This is a path finding algorithm written in Java 20/21 for the game Helltaker. Specifically, this project solves the non-DLC levels of Helltaker.

Helltaker is a puzzle game, where the player is on a 2D Grid, and must get to the goal with only a limited number of steps.

* The player can only move North/South/East/West, each costing 1 step
* If there is a movable obstacle in their way (with no obstacles behind it), the player can push it, but the push costs as a step too
  * So, 1 step to push the obstacle out of the way
  * Another 1 step to actually move to where that obstacle used to stand
* If the player steps on spikes, the spike costs as a step too
  * Some obstacles will be destroyed if they are on top of spikes
* The grid may contain a lock with a matching key to unlock it
  * To grab the key or unlock the lock, simply step onto the cell that they are on
* And finally, the player only has to stand adjacent to the goal in order for it to count as a win
  * Adjacent meaning to stand N/S/E/W of the goal
