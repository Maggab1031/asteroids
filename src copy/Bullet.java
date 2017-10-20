import objectdraw.*;
import java.awt.*;

//Name:
//Comments:

public class Bullet extends ActiveObject {

	// static variables
	private static int BULLET_SPEED = 5;
	private static int PAUSE = 10;
	private static int SIZE = 5;

	// the visible parts of the ship
	private FilledOval casing;
	private SpaceShip ship;
	private double dx, dy;
	private boolean hasVelocity;

	// parameters passed through
	private Asteroid[] field;
	private ScoreKeeper keeper;
	private DrawingCanvas canvas;
	private double angle;
	private Location loc;

	public Bullet(Location loc, double angle, Asteroid[] field,
			ScoreKeeper keeper, DrawingCanvas canvas) {
		// sets boolean up
		hasVelocity = true;
		// passes parameters through
		this.field = field;
		this.canvas = canvas;
		this.loc = loc;
		this.angle = angle;
		// creates visible bullet
		casing = new FilledOval(loc, SIZE, SIZE, canvas);
		// runs animation
		start();
	}

	// animation of bullet moving
	public void run() {
		// the changes in X and Y of the bullet
		dx = BULLET_SPEED * (Math.cos(angle));
		dy = BULLET_SPEED * (-Math.sin(angle));
		// checks to see if bullet hasn't been stopped
		while (hasVelocity) {
			// moves the casing in the direction of the barrel
			casing.move(dx, dy);
			// checks to see if the bullet has hit any active asteroid as it
			// moves
			for (int i = 0; i < field.length; i++) {
				// if so
				if (field[i].active() && field[i].Contains(casing)) {
					// hide bullet
					casing.hide();
					// destroy asteroid that is active
					field[i].Destroy(i);
					// makes the bullet no longer move
					hasVelocity = false;
				}
			}
			// adds a pause to the bullet
			pause(PAUSE);
		}
		// removes casing from canvas
		casing.removeFromCanvas();
	}

	//returns the angle the bullet is sent out at
	public double getAngle() {
		return angle;
	}

}
