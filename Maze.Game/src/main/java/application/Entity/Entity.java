// The game object superclass.
// This class has all the logic required
// to move around a tile based map.

package application.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import application.Manager.Keys;
import application.TileMap.Tile;
import application.TileMap.TileMap;
import application.Manager.GameStateManager;

public abstract class Entity {

	//Game State
	private GameStateManager gsm;

	// dimensions
	protected int width;
	protected int height;
	protected int cwidth;
	protected int cheight;
	
	// position
	protected int x;
	protected int y;
	protected int xdest;
	protected int ydest;
	protected int rowTile;
	protected int colTile;
	
	// movement
	protected boolean moving;
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	
	// attributes
	protected int moveSpeed;
	
	// tilemap
	protected TileMap tileMap;
	protected int tileSize;
	protected int xmap;
	protected int ymap;
	
	// animation
	protected Animation animation;
	protected int currentAnimation;
	
	public Entity(TileMap tm) {
		tileMap = tm;
		tileSize = tileMap.getTileSize();
		animation = new Animation();
	}

	public int getx() { return x; }
	public int gety() { return y; }
	public int getRow() { return rowTile; }
	public int getCol() { return colTile; }
	
	public void setPosition(int i1, int i2) {
		x = i1;
		y = i2;
		xdest = x;
		ydest = y;
	}

	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}

	public void setTilePosition(int i1, int i2) {
		y = i1 * tileSize + tileSize / 2;
		x = i2 * tileSize + tileSize / 2;
		xdest = x;
		ydest = y;
	}
	
	public void setLeft() {
		if(moving) return;
		left = true;
		moving = validateNextPosition();
	}

	public void setRight() {
		if(moving) return;
		right = true;
		moving = validateNextPosition();
	}

	public void setUp() {
		if(moving) return;
		up = true;
		moving = validateNextPosition();
	}

	public void setDown() {
		if(moving) return;
		down = true;
		moving = validateNextPosition();
	}
	
	public boolean intersects(Entity o) {
		return getRectangle().intersects(o.getRectangle());
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, cwidth, cheight);
	}
	
	// Returns whether or not the entity can
	// move into the next position.
	public boolean validateNextPosition() {
		if(moving) return true;
		
		rowTile = y / tileSize;
		colTile = x / tileSize;
		
		if(left) {
			if(colTile == 0 || tileMap.getType(rowTile, colTile - 1) == Tile.BLOCKED) {
				return false;
			}
			else {
				xdest = x - tileSize;
			}
		}
		if(right) {
			if(colTile == tileMap.getNumCols() || tileMap.getType(rowTile, colTile + 1) == Tile.BLOCKED) {
				return false;
			}
			else {
				xdest = x + tileSize;
			}
		}
		if(up) {
			if(rowTile == 0 || tileMap.getType(rowTile - 1, colTile) == Tile.BLOCKED) {
				return false;
			}
			else {
				ydest = y - tileSize;
			}
		}
		if(down) {
			if(rowTile == tileMap.getNumRows() - 1 || tileMap.getType(rowTile + 1, colTile) == Tile.BLOCKED) {
				return false;
			}
			else {
				ydest = y + tileSize;
			}
		}
		
		return true;
		
	}
	
	// Calculates the destination coordinates.
	public void getNextPosition() {
		
		if(left && x > xdest) x -= moveSpeed;
		else left = false;
		if(left && x < xdest) x = xdest;
		
		if(right && x < xdest) x += moveSpeed;
		else right = false;
		if(right && x > xdest) x = xdest;
		
		if(up && y > ydest) y -= moveSpeed;
		else up = false;
		if(up && y < ydest) y = ydest;
		
		if(down && y < ydest) y += moveSpeed;
		else down = false;
		if(down && y > ydest) y = ydest;
		
	}

	public void handleAI() {
		int maxmove = 5;
		int moveDirection;
//		if (Keys.isPressed(Keys.PAUSE)) {
//			gsm.setPaused(true);
//		}
		moveDirection = (int) (Math.random() * maxmove) + 1;
		switch (moveDirection) {
			case 1:
				setDown();
				break;
			case 2:
				setUp();
				break;
			case 3:
				setLeft();
				break;
			case 4:
				setRight();
				break;
			default:
				// System.out.println("Do not move");
				break;
		}
	}

	public void update() {
		
		// get next position
		if(moving) getNextPosition();
		
		// check stop moving
		if(x == xdest && y == ydest) {
			left = right = up = down = moving = false;
			rowTile = y / tileSize;
			colTile = x / tileSize;
		}
		
		// update animation
		animation.update();
		
	}
	
	// Draws the entity.
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(
			animation.getImage(),
			x + xmap - width / 2,
			y + ymap - height / 2,
			null
		);
	}
	
}
