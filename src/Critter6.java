/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Aditya Kharosekar
 * amk3587
 * 16465
 * Rahul Jain
 * rj8656
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment5;

import assignment5.Critter.CritterShape;

/**
 * Created by Aditya Kharosekar
 * This critter's fight() function will only return true against algae.
 * It only runs, never walks, and it only moves in 1 direction - west.
 * Every other instance of Critter3 will reproduce and its babies will always move west as well.
 * It's toString() representation is "6"
 */

public class Critter6 extends Critter {

    public static int count = 0;
    Critter6() {
        count++;
    }
    public boolean fight(String fightStatus) {
        if (fightStatus.equals("@")) {
            return true;
        }
        return false;
    }
    public void doTimeStep() {
    	if(this.look(0, true) != null){
    		walk(0);
    	}
        run(0);
        if (count%2==0) {
            Critter6 baby = new Critter6();
            reproduce(baby, 0);
        }

    }
    public String toString() {
        return "6";
    }
    @Override
    public CritterShape viewShape() { return CritterShape.STAR; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.YELLOW; }
}
