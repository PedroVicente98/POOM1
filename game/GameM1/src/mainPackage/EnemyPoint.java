package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyPoint extends GameObject {

	private BufferedImage spiderEnemy;
	
	Handler handler;
	public EnemyPoint(int x, int y, ID id,Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		 spiderEnemy = ss.grabImage(1,1,48,48);
	}

	public void tick() {
		x+=velX;
		y+=velY;
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.green);
		//g.fillRect(x, y, 32,32);
		g.drawImage(spiderEnemy,x, y, null);
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}

	

}
