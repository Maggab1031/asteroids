import objectdraw.*;

import java.awt.*;

// Name:
// Comments:

public class Asteroid extends ActiveObject {

	// insert your code here.
	private static double PAUSE = 20;
	private static double SPEED = 6;
	private static double BASE_SIZE = 20;

	private Image img;
	private VisibleImage asteroidImage;
	private Location loc;
	private ScoreKeeper keeper;
	private double size, initDirection, dx, dy;
	private boolean alive;
	private SpaceShip ship;
	private DrawingCanvas canvas;
	private Asteroid[] field;
	private double speed;

	// constructs the
	public Asteroid(Image img, Location loc, ScoreKeeper keeper, double size,
			double initDirection, SpaceShip ship, double speed,
			Asteroid[] field, DrawingCanvas canvas) {
		this.field = field;
		this.canvas = canvas;
		this.keeper = keeper;
		alive = true;
		this.ship = ship;
		this.loc = loc;
		this.img = img;
		this.size = size;
		this.initDirection = initDirection;
		asteroidImage = new VisibleImage(img, loc, canvas);
		asteroidImage.setSize(size * BASE_SIZE, size * BASE_SIZE);
		start();

	}

	public void run() {
		// creates the DX and DYs of the asteroid
		dx = SPEED * (Math.cos(initDirection));
		dy = SPEED * (-Math.sin(initDirection));
		// while the asteroid has not been destroyed
		while (alive) {
			// move the asteroid in the direction that it had determined
			asteroidImage.move(dx, dy);
			// pause in movement
			pause(PAUSE);
			// if the asteroid comes into contact with a ship
			if (alive && Contains(ship.getShip())) {
				// kill it
				ship.Die();
				// the user loses the game
				keeper.lose();
			}
			// ensures that the asteroid moves from one side of the screen to
			// the other
			Wrap();
		}
	}

	public void Destroy(int i) {
		// break up the asteroid
		// if asteroid is alive
		if (alive) {
			// increase the score by a factor of the size of the asteroid
			keeper.increaseScore(size);
			// makes the asteroid be unactive
			setActivityFalse();
			// if the user is still alive
			if (keeper.isActive() == true) {
				// makes the next asteroid in the array appear and be active
				field[i + 1].setActivityTrue();
			}
		}

	}

	// set the asteroid to inactive
	public void setActivityFalse() {
		alive = false;
		// hide it from the canvas
		asteroidImage.hide();
	}

	// makes the asteroid to active
	public void setActivityTrue() {
		alive = true;
		// show the image on the canvas
		asteroidImage.show();
		// runs the animation for the asteroid chosen
		run();
	}

	// determines if asteroid is active or not
	public boolean active() {
		return alive;
	}

	// does the asteroid contain the points associated with a certain drawable
	// item
	public boolean Contains(Drawable2DInterface item) {
		// check if the asteroid contains a point
		return asteroidImage.overlaps(item);
	}

	public void Wrap() {
		// move the asteroid from one side of the screen to the other
		// if the asteroid is above the screen
		if (asteroidImage.getY() < 0 - asteroidImage.getHeight()) {
			pause(PAUSE);
			// move the asteroid to the same X coordinate on the bottom
			asteroidImage.moveTo(asteroidImage.getX(), canvas.getHeight());
		}
		// if the asteroid is below the screen
		if (asteroidImage.getY() > canvas.getHeight()) {
			pause(PAUSE);
			// move the asteroid to the same X coordinate on the top
			asteroidImage.moveTo(asteroidImage.getX(), 0);
		}
		// if the asteroid is to the right of the screen
		if (asteroidImage.getX() > canvas.getWidth()) {
			pause(PAUSE);
			// move the asteroid to the same Y coordinate on the left
			asteroidImage.moveTo(0, asteroidImage.getY());
		}
		// if the asteroid is to the left of the screen
		if (asteroidImage.getX() < 0 - asteroidImage.getWidth()) {
			pause(PAUSE);
			// move the asteroid to the same Y coordinate on the right
			asteroidImage.moveTo(canvas.getWidth(), asteroidImage.getY());
		}

	}
}
