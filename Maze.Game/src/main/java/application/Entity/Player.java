// The only subclass the fully utilizes the
// Entity superclass (no other class requires
// movement in a tile based map).
// Contains all the gameplay associated with
// the Player.

package application.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import application.GameState.PlayState;
import application.Manager.Content;
import application.Manager.GameStateManager;
import application.TileMap.TileMap;

public class Player extends Entity {

	// sprites
	private BufferedImage[] downSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] rightSprites;
	private BufferedImage[] upSprites;
	private BufferedImage[] downBoatSprites;
	private BufferedImage[] leftBoatSprites;
	private BufferedImage[] rightBoatSprites;
	private BufferedImage[] upBoatSprites;
	
	// animation
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWNBOAT = 4;
	private final int LEFTBOAT = 5;
	private final int RIGHTBOAT = 6;
	private final int UPBOAT = 7;

	// gameplay
	public int endgame;
	public int Health;
	public static int numGold;
	private int totalGold;
	private boolean hasBoat;
	private boolean hasSword;
	private boolean onWater;
	private long ticks;

	public Player(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 12;
		cheight = 12;
		
		moveSpeed = 2;
		
		numGold = 0;
		Health = 100; // reason for high health is due to the fact that player cannot kill enemies yet.
		downSprites = Content.PLAYER[0];
		leftSprites = Content.PLAYER[1];
		rightSprites = Content.PLAYER[2];
		upSprites = Content.PLAYER[3];
		downBoatSprites = Content.PLAYER[4];
		leftBoatSprites = Content.PLAYER[5];
		rightBoatSprites = Content.PLAYER[6];
		upBoatSprites = Content.PLAYER[7];
		
		animation.setFrames(downSprites);
		animation.setDelay(10);
		endgame = 0;
	}
	
	private void setAnimation(int i, BufferedImage[] bi, int g) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(g);
	}
	
	public void collectGold() { numGold++; }
	public int numGold() { return numGold; }
	public int getTotalGold() { return totalGold; }
	public void setTotalGold(int i) { totalGold = i; }
	
	public void gotBoat() { hasBoat = true; tileMap.replace(22, 4); }
	public void gotSword() { hasSword = true; }
	public boolean hasBoat() { return hasBoat; }
	public boolean hasSword() { return hasSword; }
	
	// Used to update time.
	public long getTicks() { return ticks; }
	
	// Keyboard input. Moves the player.
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
	
	// Keyboard input.
	// If Player has Broken Sword, breakable enemy in front
	// of the Player will be broken down down.


	public void setAction() {
		if(hasSword) {
			if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 21) {
				tileMap.setTile(rowTile - 1, colTile, 1);
			}
			if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 21){
				tileMap.setTile(rowTile + 1, colTile, 2);
			}
			if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 23){
				tileMap.setTile(rowTile + 1, colTile, 2);
				endgame = 1;
			}
			if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 21) {
				tileMap.setTile(rowTile, colTile - 1, 1);
			}
			if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 21) {
				tileMap.setTile(rowTile, colTile + 1, 2);
			}
		}
	}

	public void update() {
		ticks++;
		// check if on water
		boolean current = onWater;
		if(tileMap.getIndex(ydest / tileSize, xdest / tileSize) == 4) {
			onWater = true;
		}
		else {
			onWater = false;
		}

		// set animation
		if(down) {
			if(onWater && currentAnimation != DOWNBOAT) {
				setAnimation(DOWNBOAT, downBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != DOWN) {
				setAnimation(DOWN, downSprites, 10);
			}
		}
		if(left) {
			if(onWater && currentAnimation != LEFTBOAT) {
				setAnimation(LEFTBOAT, leftBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != LEFT) {
				setAnimation(LEFT, leftSprites, 10);
			}
		}
		if(right) {
			if(onWater && currentAnimation != RIGHTBOAT) {
				setAnimation(RIGHTBOAT, rightBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != RIGHT) {
				setAnimation(RIGHT, rightSprites, 10);
			}
		}
		if(up) {
			if(onWater && currentAnimation != UPBOAT) {
				setAnimation(UPBOAT, upBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != UP) {
				setAnimation(UP, upSprites, 10);
			}
		}
		
		// update position
		super.update();
		
	}
	
	// Draw Player.
	public void draw(Graphics2D g) {
		super.draw(g);
	}


}

