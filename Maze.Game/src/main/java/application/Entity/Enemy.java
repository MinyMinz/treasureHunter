
// The only subclass the fully utilizes the
// Entity superclass (no other class requires
// movement in a tile based map).
// Contains all the gameplay associated with
// the Player.

package application.Entity;

import application.Manager.Content;
import application.TileMap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

	// sprites
	private BufferedImage[] downSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] rightSprites;
	private BufferedImage[] upSprites;
	private BufferedImage[] downflySprites;
	private BufferedImage[] leftflySprites;
	private BufferedImage[] rightflySprites;
	private BufferedImage[] upflySprites;

	// animation
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int FLYDOWN = 4;
	private final int FLYLEFT = 5;
	private final int FLYRIGHT = 6;
	private final int FLYUP = 7;

	// gameplay
	public static int EnemyHP;
	private long ticks;
	private boolean onWater;

	public Enemy(TileMap tm) {

		super(tm);

		width = 16;
		height = 16;
		cwidth = 12;
		cheight = 12;

		moveSpeed = 1;

		EnemyHP = 50;
		downSprites = Content.ENEMY[0];
		leftSprites = Content.ENEMY[1];
		rightSprites = Content.ENEMY[2];
		upSprites = Content.ENEMY[3];
		downflySprites = Content.ENEMY[4];
		leftflySprites = Content.ENEMY[5];
		rightflySprites = Content.ENEMY[6];
		upflySprites = Content.ENEMY[7];

		animation.setFrames(downSprites);
		animation.setDelay(25);
	}

	private void setAnimation(int i, BufferedImage[] bi, int g) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(g);
	}


	// Used to update time.
	public long getTicks() { return ticks; }

	//AI movement
	public void setDown() {
		super.setDown();
	}
	public void setLeft() {
		super.setLeft();
	}
	public void setRight() {
		super.setRight();
	}
	public void setUp() {
		super.setUp();
	}

	public void update() {

		ticks++;
		handleAI();
		// set animation
		boolean current = onWater;
		onWater = tileMap.getIndex(ydest / tileSize, xdest / tileSize) == 4;

		// set animation
		if(down) {
			if(onWater && currentAnimation != FLYDOWN) {
				setAnimation(FLYDOWN, downflySprites, 10);
			}
			else if(!onWater && currentAnimation != DOWN) {
				setAnimation(DOWN, downSprites, 10);
			}
		}
		if(left) {
			if(onWater && currentAnimation != FLYLEFT) {
				setAnimation(FLYLEFT, leftflySprites, 10);
			}
			else if(!onWater && currentAnimation != LEFT) {
				setAnimation(LEFT, leftSprites, 10);
			}
		}
		if(right) {
			if(onWater && currentAnimation != FLYRIGHT) {
				setAnimation(FLYRIGHT, rightflySprites, 10);
			}
			else if(!onWater && currentAnimation != RIGHT) {
				setAnimation(RIGHT, rightSprites, 10);
			}
		}
		if(up) {
			if(onWater && currentAnimation != FLYUP) {
				setAnimation(FLYUP, upflySprites, 10);
			}
			else if(!onWater && currentAnimation != UP) {
				setAnimation(UP, upSprites, 10);
			}
		}
		// update position
		super.update();
	}

	// Draw Enemy
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

