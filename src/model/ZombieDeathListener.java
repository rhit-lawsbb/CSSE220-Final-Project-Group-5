package model;

/**
 * Interface: ZombieDeathListener
 * @author Group 5
 * <br>Purpose: when zombie dies, allowes other classes to respond to it
 * <br>Restrictions: when a class implements, the class must decide what happens when the zombie dies
 * <br>For Example:
 * <pre>
 * 		public class GameModel implements ZombieDeathListener{
 * 			public void onZombieDied(){
 * 				startZombieRespawn();
 * 			}
 * 		}
 * </pre>
 */

public interface ZombieDeathListener {
	void onZombieDied();
}
