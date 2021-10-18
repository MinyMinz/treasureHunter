// The main playing GameState.
// Contains everything you need for gameplay:
// Player, TileMap, Gold, etc.
// Updates and draws all game objects.

package application.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import application.Entity.*;
import application.Manager.Data;
import application.Manager.GameStateManager;
import application.Manager.Keys;
import application.TileMap.TileMap;
import application.HUD.Hud;
import application.Main.GamePanel;

public class PlayState extends GameState {

	// player
	private Player player;
	private int xPos;
	private int yPos;

	// enemy
	private Enemy enemy;
	private ArrayList<Enemy> mob;
	private int xePos;
	private int yePos;

	// tilemap
	private TileMap tileMap;

	// gold
	private ArrayList<Gold> golds;

	// items
	private ArrayList<Item> items;

	// sparkles
	private ArrayList<Sparkle> sparkles;

	// camera position
	private int xsector;
	private int ysector;
	private int sectorSize;

	// hud
	private Hud hud;

	// events
	private boolean blockInput;
	private boolean eventStart;
	private boolean eventFinish;
	private int eventTick;

	// transition box
	private ArrayList<Rectangle> boxes;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {

		// create lists
		golds = new ArrayList<Gold>();
		sparkles = new ArrayList<Sparkle>();
		items = new ArrayList<Item>();
		mob = new ArrayList<Enemy>();

		// load map
		tileMap = new TileMap(16);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/mazeone.map");

		// create player
		player = new Player(tileMap);

		// Place Gold, Items
		populateCoins();
		populateItems();

		// Place player, Inventory
		int max = tileMap.getNumCols() - 1;
		for (int i = 0; i < max; i++) {
			xPos = (int) (Math.random() * max) + 1;
			yPos = (int) (Math.random() * max) + 1;
			xPos=28;
			yPos=21;
			if (tileMap.getIndex(xPos, yPos) <= 3) {
				player.setTilePosition(xPos, yPos);
			}
		}
		player.setTotalGold(golds.size());

		// Creating and placing enemies
        Enemy foe;
		int xtemp = 0;
		int ytemp = 0;
		int LoopNo =0;
		int numberofenemies = 20; // change this to increase enemy spawn

		while (LoopNo<=numberofenemies){
			xePos = (int) (Math.random() * max) + 1;
			yePos = (int) (Math.random() * max) + 1;
			if (tileMap.getIndex(xePos, yePos) <= 3) {
				if (LoopNo == 0) {
					foe = new Enemy(tileMap);
					foe.setTilePosition(xePos, yePos);
					mob.add(foe);
					xtemp = xePos;
					ytemp = yePos;
                    LoopNo++;
				}
				else if (LoopNo >= 1) {
					if (xtemp != xePos && ytemp != yePos) {
                        foe = new Enemy(tileMap);
                        foe.setTilePosition(xePos, yePos);
                        mob.add(foe);
                        xtemp = xePos;
                        ytemp = yePos;
                        LoopNo++;
					}
				}

			}
		}
		System.out.println(mob.size());
		// set up camera position
		sectorSize = GamePanel.WIDTH;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);

		// load hud
		hud = new Hud(player, golds);

		// start event
		boxes = new ArrayList<Rectangle>();
		eventStart = true;
		eventStart();

	}

	private void populateCoins() {

		Gold g;
		// Coins that open passages if enemy is removed
		g = new Gold(tileMap);
		g.setTilePosition(20, 20);
		g.addChange(new int[]{23, 19, 1});
		g.addChange(new int[]{23, 20, 1});
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(12, 36);
		g.addChange(new int[]{31, 17, 1});
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(28, 4);
		g.addChange(new int[]{27, 7, 1});
		g.addChange(new int[]{28, 7, 1});
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(4, 34);
		g.addChange(new int[]{31, 21, 1});
		golds.add(g);

		// Normal Coins
		g = new Gold(tileMap);
		g.setTilePosition(28, 19);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(35, 26);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(37, 36);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(27, 28);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(20, 30);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(14, 25);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(4, 21);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(9, 14);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(4, 3);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(20, 14);
		golds.add(g);
		g = new Gold(tileMap);
		g.setTilePosition(13, 20);
		golds.add(g);

	}

	private void populateItems() {

		Item item;

		item = new Item(tileMap);
		item.setType(Item.SWORD);
		item.setTilePosition(xPos, yPos);
		items.add(item);

		item = new Item(tileMap);
		item.setType(Item.BOAT);
		item.setTilePosition(xPos, yPos);
		items.add(item);
	}

	public void update() {

		// check keys
		handleInput();

		// check events
		if (eventStart) eventStart();
		if (eventFinish) {
			eventFinish();
		}

		 //end game on exit passage
		if(player.endgame == 1){
			eventFinish = blockInput = true;
		}

		// update camera
		int oldxs = xsector;
		int oldys = ysector;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
		tileMap.update();
		
/*		if(oldxs != xsector || oldys != ysector) {
		}*/

		if (tileMap.isMoving()) return;

		// update player
		player.update();

		//player touches enemy
        for (Enemy enemy : mob) {
            enemy.update();
            if (player.intersects(enemy)) {
                player.Health -= 1;
                //System.out.println(player.Health);
                if (player.Health == 0) {
                    gsm.setState(1);
                }
            }
        }


		// update golds
		for (int i = 0; i < golds.size(); i++) {

			Gold coin = golds.get(i);
			coin.update();

			// player collects Gold
			if (player.intersects(coin)) {

				// remove from list
				golds.remove(i);
				i--;

				// increment amount of collected golds
				player.collectGold();

				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(coin.getx(), coin.gety());
				sparkles.add(s);

			}
		}

		// update sparkles
		for (int i = 0; i < sparkles.size(); i++) {
			Sparkle s = sparkles.get(i);
			s.update();
			if (s.shouldRemove()) {
				sparkles.remove(i);
				i--;
			}
		}

		// Give items instantly to player
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			items.remove(i);
			item.collected(player);
		}

//  	update items if placed and collected cannot use this as player is placed randomly

//		for(int i = 0; i < items.size(); i++) {
//			Item item = items.get(i);
//			if(player.intersects(item)) {
//				items.remove(i);
//				i--;
//				item.collected(player);
//				Sparkle s = new Sparkle(tileMap);
//				s.setPosition(item.getx(), item.gety());
//				sparkles.add(s);
//			}
//		}

	}

	public void draw(Graphics2D g) {

		// draw tilemap
		tileMap.draw(g);

		// draw enemies
        for (Enemy e : mob) {
            e.draw(g);
        }

		// draw player
		player.draw(g);

		// draw golds
		for (Gold d : golds) {
			d.draw(g);
		}

		// draw sparkles
		for (Sparkle s : sparkles) {
			s.draw(g);
		}

		// draw items
		for (Item i : items) {
			i.draw(g);
		}

		// draw hud
		hud.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for (Rectangle box : boxes) {
			g.fill(box);
		}

	}

	public void handleInput() {
		if (Keys.isPressed(Keys.PAUSE)) {
			gsm.setPaused(true);
		}
		if (blockInput) return;
		if (Keys.isDown(Keys.LEFT)) player.setLeft();
		if (Keys.isDown(Keys.RIGHT)) player.setRight();
		if (Keys.isDown(Keys.UP)) player.setUp();
		if (Keys.isDown(Keys.DOWN)) player.setDown();
		if (Keys.isPressed(Keys.ACTION)) player.setAction();
	}

	//===============================================

	private void eventStart() {
		eventTick++;
		if (eventTick == 1) {
			boxes.clear();
			for (int i = 0; i < 9; i++) {
				boxes.add(new Rectangle(0, i * 16, GamePanel.WIDTH, 16));
			}
		}
		if (eventTick > 1 && eventTick < 32) {
			for (int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if (i % 2 == 0) {
					r.x -= 4;
				} else {
					r.x += 4;
				}
			}
		}
		if (eventTick == 33) {
			boxes.clear();
			eventStart = false;
			eventTick = 0;
		}
	}

	private void eventFinish() {
		eventTick++;
		if (eventTick == 1) {
			boxes.clear();
			for (int i = 0; i < 9; i++) {
				if (i % 2 == 0) boxes.add(new Rectangle(-128, i * 16, GamePanel.WIDTH, 16));
				else boxes.add(new Rectangle(128, i * 16, GamePanel.WIDTH, 16));
			}
		}
		if (eventTick > 1) {
			for (int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if (i % 2 == 0) {
					if (r.x < 0) r.x += 4;
				} else {
					if (r.x > 0) r.x -= 4;
				}
			}
		}
		if (eventTick > 33) {
			Data.setTime(player.getTicks());
			gsm.setState(GameStateManager.GAMEOVER);
		}
	}
}