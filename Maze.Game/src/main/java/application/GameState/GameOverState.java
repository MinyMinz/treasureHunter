// Gives you a rank based on how long it took

package application.GameState;

import java.awt.*;

import application.Entity.Player;
import application.Main.GamePanel;
import application.Manager.Content;
import application.Manager.Data;
import application.Manager.GameStateManager;

public class GameOverState extends GameState {

	private Color color;

	private int rank;
	private long ticks;

	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		color = new Color(57, 46, 82);
		ticks = Data.getTime();
		if(ticks < 3600) rank = 1;
		else if(ticks < 5400) rank = 2;
		else if(ticks < 7200) rank = 3;
		else rank = 4;
	}

	public void update() {}

	public void draw(Graphics2D g) {

		g.setColor(color);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
		Content.drawString(g, "finish", 20, 36);
		Content.drawString(g, "time", 70, 36);
        Content.drawString(g,"You",15,16);
		Content.drawString(g,"left",43,16);
		Content.drawString(g,"with",79,16);
		Content.drawString(g,Player.numGold + "",40,25);
		Content.drawString(g,"Coins",56,25);

		int minutes = (int) (ticks / 1800);
		int seconds = (int) ((ticks / 30) % 60);
		if(minutes < 10) {
			if(seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, "0" + minutes + ":" + seconds, 44, 48);
		}
		else {
			if(seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, minutes + ":" + seconds, 44, 48);
		}

		Content.drawString(g, "rank", 48, 66);
		if(rank == 1) Content.drawString(g, "Master", 40, 78);
		else if(rank == 2) Content.drawString(g, "Adventurer", 32, 78);
		else if(rank == 3) Content.drawString(g, "Beginner", 32, 78);
		else if(rank == 4) Content.drawString(g, "Novice", 40, 78);
		Content.drawString(g, "Press ESC Key", 10, 110);
		}

	public void handleInput() {
	}

}