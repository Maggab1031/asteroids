import objectdraw.*;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

//Name: Gabe Magee
//Comments: Extra Credit - limit number of bullets, makes the ship move, makes scoring depend on size of asteroid

public class AsteroidsGame extends WindowController implements KeyListener {
	// static variables
	private static int NUMB_ASTEROIDS = 20;

	// instance variables
	private Asteroid[] field;
	private SpaceShip ship;
	private VisibleImage asteroid;
	private Image asteroidImage;
	private int numberField;
	private double size, initDirection, speed;
	private Location loc, scoreLoc;
	private ScoreKeeper keeper;

	// random number generators
	private RandomIntGenerator locGeneratorX = new RandomIntGenerator(10,
			canvas.getWidth());
	private RandomIntGenerator locGeneratorY = new RandomIntGenerator(10,
			canvas.getHeight());
	private RandomIntGenerator sizeGenerator = new RandomIntGenerator(1, 3);
	private RandomDoubleGenerator angleGenerator = new RandomDoubleGenerator(0,
			2 * Math.PI);
	private RandomDoubleGenerator speedGenerator = new RandomDoubleGenerator(2,
			5);

	// when the program is initialized
	public void begin() {

		// getting ready to respond to user's key presses

		// loads the asteroid image
		asteroidImage = getImage("rock.gif");
		// creates a array of asteroid
		field = new Asteroid[NUMB_ASTEROIDS];
		// creates a ScoreKeeper to keep track of scores
		keeper = new ScoreKeeper(field, canvas);
		// creates a Space Ship
		ship = new SpaceShip(field, keeper, canvas);
		// initializes the number of Asteroids that the field has 'seen'
		numberField = 0;
		// creates a new Asteroid
		newAsteroid();

		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		canvas.addKeyListener(this);
	}

	// Methods required by KeyListener interface.
	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// makes ship shoot when Space is pressed
			ship.Shoot();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// makes ship to turn clockwise when right arrow is pressed
			ship.RotateRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// makes ship to turn counterclockwise when left arrow is pressed
			ship.RotateLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			// makes ship move in the direction of the barrel when up arrow is
			// pressed
			ship.moveForward();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			// makes ship move in the opposite direction of the barrel when down
			// arrow is pressed
			ship.moveBackward();
		}

	}

	public void newAsteroid() {
		// fills asteroid array with asteroids
		for (int j = numberField; j < NUMB_ASTEROIDS; j++) {
			// randomly generates Location
			loc = new Location(locGeneratorX.nextValue(),
					locGeneratorY.nextValue());
			// randomly generates size
			size = sizeGenerator.nextValue();
			// randomly generates direction
			initDirection = angleGenerator.nextValue();
			// randomly generates speed
			speed = speedGenerator.nextValue();
			// creates the asteroid
			field[j] = new Asteroid(asteroidImage, loc, keeper, size,
					initDirection, ship, speed, field, canvas);
		}
		// hides all asteroids save for the one at hand
		for (int j = numberField + 1; j < NUMB_ASTEROIDS; j++) {
			field[j].setActivityFalse();
		}
	}

	public void onMousePress(Location pt) {
		this.requestFocus();
	}

}
