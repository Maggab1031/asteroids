import objectdraw.*;

import java.awt.*;

//Name:
//Comments:

public class SpaceShip {

	// insert your code here.
	private static double FUSELAGE_SIZE = 20;
	private static double BARREL_SIZE = 15;

	// instance variables
	private Location shipLoc, barrelStart, barrelEnd, newEnd, bShipLoc,
			bBarrelStart, bBarrelEnd;
	private FilledOval fuselage;
	private Line barrel;
	private double angle;
	private boolean alive;
	public int bulletCount;

	// parameter variables
	private DrawingCanvas canvas;
	private Asteroid[] field;
	public ScoreKeeper keeper;

	// constructor for the spaceship
	public SpaceShip(Asteroid[] field, ScoreKeeper keeper, DrawingCanvas canvas) {
		// initalize variables
		this.field = field;
		this.canvas = canvas;
		this.keeper = keeper;
		// initalizes the number of bullets
		bulletCount = keeper.getClip();
		// determines the initial placement of the ship
		shipLoc = new Location((canvas.getWidth() / 2) - (FUSELAGE_SIZE / 2),
				(canvas.getHeight() / 2) - (FUSELAGE_SIZE / 2));
		// sets the ship to be alive
		alive = true;
		// sets the initial angles
		angle = 0;
		// creats the fuselage and makes it blue
		fuselage = new FilledOval(shipLoc, FUSELAGE_SIZE, FUSELAGE_SIZE, canvas);
		fuselage.setColor(Color.BLUE);
		// makes the start of the barrel
		barrelStart = new Location((shipLoc.getX() + (FUSELAGE_SIZE / 2)),
				(shipLoc.getY() + (FUSELAGE_SIZE / 2)));
		// makes the end of the barrel
		barrelEnd = new Location(barrelStart.getX()
				+ (BARREL_SIZE * Math.cos(angle)), barrelStart.getY()
				- BARREL_SIZE * (Math.sin(angle)));
		// creates the barrel
		barrel = new Line(barrelStart, barrelEnd, canvas);
	}

	// returns the location of the ship's fuselage
	public Location getLoc() {
		return fuselage.getLocation();
	}

	// rotates barrel to the left
	public void RotateLeft() {
		// if the ship is alive and so is the user
		if (alive && keeper.isActive()) {
			// creates a new angle to left by a sixteenth of a rotation
			angle = angle + Math.PI / 8;
			// the new end of the barrel
			newEnd = new Location(barrel.getStart().getX()
					+ (BARREL_SIZE * Math.cos(angle)), barrel.getStart().getY()
					- BARREL_SIZE * (Math.sin(angle)));
			// set the end points of the barrel to the new coordinates
			barrel.setEndPoints(barrel.getStart(), newEnd);
		}
	}

	public void RotateRight() {
		// if the ship is alive and so is the user
		if (alive && keeper.isActive()) {
			// creates a new angle to right by a sixteenth of a rotation
			angle = angle - Math.PI / 8;
			// the new end of the barrel
			newEnd = new Location(barrel.getStart().getX()
					+ (BARREL_SIZE * Math.cos(angle)), barrel.getStart().getY()
					- (BARREL_SIZE * (Math.sin(angle))));
			// set the end points of the barrel to the new coordinates
			barrel.setEndPoints(barrel.getStart(), newEnd);
		}

	}

	//returns the ship for the contains method
	public Drawable2DInterface getShip() {
		return fuselage;
	}

	//moves the ship forward in the direction of the gun
	public void moveForward() {
		// if the ship is alive and so is the user
		if (alive && keeper.isActive()) {
			//new fuselage loc
			bShipLoc = new Location(fuselage.getLocation().getX()
					+ FUSELAGE_SIZE * Math.cos(angle), fuselage.getLocation()
					.getY() - FUSELAGE_SIZE * Math.sin(angle));
			//new endpoint locs
			bBarrelEnd = new Location(barrel.getEnd().getX()
					+ (FUSELAGE_SIZE * Math.cos(angle)), barrel.getEnd().getY()
					- (FUSELAGE_SIZE * (Math.sin(angle))));
			bBarrelStart = new Location(barrel.getStart().getX()
					+ (FUSELAGE_SIZE * Math.cos(angle)), barrel.getStart()
					.getY() - (FUSELAGE_SIZE * (Math.sin(angle))));
			//moves both barrel and fuselage
			barrel.setEndPoints(bBarrelStart, bBarrelEnd);
			fuselage.moveTo(bShipLoc);
		}
	}

	public void moveBackward() {
		// if the ship is alive and so is the user
		if (alive && keeper.isActive()) {
			bShipLoc = new Location(fuselage.getLocation().getX()
					- FUSELAGE_SIZE * Math.cos(angle), fuselage.getLocation()
					.getY() + FUSELAGE_SIZE * Math.sin(angle));
			//new endpoint locs
			bBarrelEnd = new Location(barrel.getEnd().getX()
					- (FUSELAGE_SIZE * Math.cos(angle)), barrel.getEnd().getY()
					+ (FUSELAGE_SIZE * (Math.sin(angle))));
			bBarrelStart = new Location(barrel.getStart().getX()
					- (FUSELAGE_SIZE * Math.cos(angle)), barrel.getStart()
					.getY() + (FUSELAGE_SIZE * (Math.sin(angle))));
			//moves both barrel and fuselage
			barrel.setEndPoints(bBarrelStart, bBarrelEnd);
			fuselage.moveTo(bShipLoc);
		}
	}

	//shoots a bullet
	public void Shoot() {
		// if the ship is alive and so is the user, and bullets left
		if (alive && keeper.isActive() && bulletCount > 0) {
			//makes bullet coming from end of barrell
			new Bullet(barrel.getEnd(), angle, field, keeper, canvas);
			//decreases bullet counts
			bulletCount--;
			keeper.loseBullet();
		}
	}

	//kills the ship
	public void Die() {
		alive = false;
	}
}
