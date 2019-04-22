package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Items extends GameObject {

	private BufferedImage itemsPoint;
	Handler handler;
	public Items(int x, int y, ID id,Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		itemsPoint = ss.grabImage(1,1,48,48);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;	
	}

	@Override
	public void render(Graphics g) {
		//g.setColor(Color.PINK);
		//g.fillRect(x, y, 32,32);
		g.drawImage(itemsPoint,x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,48,48);
	}
	
	

}
