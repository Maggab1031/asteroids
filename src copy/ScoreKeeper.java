import objectdraw.*;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Name:
//Comments:

public class ScoreKeeper {

	private static double SCORE_INCREASE = 10;

	private Text display;
	private double score, width, height;
	private Location loc, scoreLoc;
	private JPanel panel;
	private JButton restart;
	private Asteroid[] field;
	private boolean active;
	private int bulletCount;

	// insert your code here.
	public ScoreKeeper(Asteroid[] field, DrawingCanvas canvas) {
		this.field = field;
		score = 0;
		width = 20;
		height = 20;
		bulletCount = 30;
		scoreLoc = new Location((canvas.getWidth() / 2) - (width),
				canvas.getHeight() - (height));
		display = new Text("Score: " + score + "     Bullets Left: "
				+ bulletCount, scoreLoc, canvas);
		active = true;
	}

	public void increaseScore(double size) {
		if (active) {
			score = score + SCORE_INCREASE * size;
			// adds a bullet to the Ship's clip
			gainBullet();
			// updates the display
			display.setText("Score: " + score + "     Bullets Left: "
					+ bulletCount);
			// if the score is over 200, the user wins and cannot interact any
			// more
			if (score >= 200) {
				win();
				active = false;
			}
		}
	}

	// the user loses
	public void lose() {
		// updates display text
		display.setText("You Lost! Score: " + score);
		// makes all asteroids inactive
		for (int i = 0; i < field.length; i++) {
			field[i].setActivityFalse();
		}
		// makes the score keeper inactive
		active = false;
	}

	// the user wins
	public void win() {
		// updates the display
		display.setText("You win! Final Score: " + score);
		// makes all asteroids inactive
		for (int i = 0; i < field.length; i++) {
			field[i].setActivityFalse();
		}
		// makes score keeper inactive
		active = false;
	}

	// checks to see if the scorekeeper is active
	public boolean isActive() {
		return active;
	}

	// returns the number of bullets the ship has left
	public int getClip() {
		return bulletCount;
	}

	// makes a bullet 'spent'
	public void loseBullet() {
		// decreases the bullet count
		bulletCount--;
		// updates display
		display.setText("Score: " + score + "     Bullets Left: " + bulletCount);
		// makes the user lose if bullet count drops below zero
		if (bulletCount <= 0) {
			lose();
		}
	}

	// makes the ship gain a bullet
	public void gainBullet() {
		// increases bullet count
		bulletCount++;
		// updates the display text
		display.setText("Score: " + score + "     Bullets Left: " + bulletCount);
	}
}