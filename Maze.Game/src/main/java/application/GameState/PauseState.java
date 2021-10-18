//activated by calling GameStateManager
//PausedState = true.

package application.GameState;

import java.awt.Graphics2D;


import application.Main.GamePanel;
import application.Manager.Content;
import application.Manager.GameStateManager;
import application.Manager.Keys;

public class PauseState extends GameState {
	
	public PauseState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		Content.drawRect(g,0,0, GamePanel.WIDTH,GamePanel.HEIGHT,27,2,39);
		Content.drawString(g, "paused", 40, 30);
		
		Content.drawString(g, "arrow", 12, 76);
		Content.drawString(g, "keys", 16, 84);
		Content.drawString(g, ":move", 52, 80);
		
		Content.drawString(g, "space", 12, 96);
		Content.drawString(g, ":action", 52, 96);
		
		Content.drawString(g, "M:", 44, 112);
		Content.drawString(g, "return", 60, 108);
		Content.drawString(g, "to", 60, 116);
		Content.drawString(g, "menu", 80, 116);
		
	}
	public void handleInput() {
		if(Keys.isPressed(Keys.PAUSE)) {
			gsm.setPaused(false);
		}
		if(Keys.isPressed(Keys.MENU)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);
		}
	}
	
}
