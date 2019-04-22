package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyPush extends GameObject {

	private BufferedImage mummyImage;
	Handler handler;
	public EnemyPush(int x, int y, ID id,Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		mummyImage = ss.grabImage(1,1,45,45);
	}

	public void tick() {
		x+=velX;
		y+=velY;
	}

	public void render(Graphics g) {
		//g.setColor(Color.GRAY);
		//g.fillRect(x, y, 32,32);
		g.drawImage(mummyImage,x, y, null);
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,45,45);
	}


}
