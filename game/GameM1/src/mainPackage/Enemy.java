package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Enemy extends GameObject  {
	
	private Handler handler;
	public Enemy(int x, int y, ID id,Handler handler,SpriteSheet s2) {
		super(x, y, id, s2);
	}

	public void tick() {
		x+=velX;
		y+=velY;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 32,32);
		
	}
	
	public void enemymovement( int direction) {
		
		if(direction==0) { // cima
			velY=-1;
		}
		else if(direction==1) { //direita
			velX=-1;
		}
		else if(direction==2) { // baixo
			velY=1;
		}
		else if(direction==3) { // esquerda
			velX=1;
		
	}
		}

	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}
}
