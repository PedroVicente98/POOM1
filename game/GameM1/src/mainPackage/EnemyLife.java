package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyLife extends GameObject {

	private BufferedImage snakeEnemy;
	
	Handler handler; 
	public EnemyLife(int x, int y, ID id,Handler handler,SpriteSheet ss) {
		super(x, y, id, ss);
		 snakeEnemy = ss.grabImage(1,6,60,75);
	}
	public void tick() {
		x+=velX;
		y+=velY;	
	}
	public void render(Graphics g) {
		//g.setColor(Color.MAGENTA);
		//.fillRect(x, y, 32,32);
		g.drawImage(snakeEnemy,x, y, null);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,60,75);
	}

}
