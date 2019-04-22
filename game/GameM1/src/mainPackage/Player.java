package mainPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
	Game game;
	private BufferedImage[] playerImage = new BufferedImage[13];
	Animation anim, anim2, anim3, anim4;
	Handler handler;

	public Player(int x, int y, ID id, Handler handler, SpriteSheet s1,Game game) {
		super(x, y, id, s1);
		this.handler = handler;
		this.game = game;

		playerImage[0] = s1.grabImage(1, 1, 32, 45);
		playerImage[1] = s1.grabImage(2, 1, 32, 45);
		playerImage[2] = s1.grabImage(3, 1, 32, 45);

		playerImage[3] = s1.grabImage(1, 3, 32, 45);
		playerImage[4] = s1.grabImage(2, 3, 32, 45);
		playerImage[5] = s1.grabImage(3, 3, 32, 45);

		playerImage[6] = s1.grabImage(1, 5, 32, 45);
		playerImage[7] = s1.grabImage(2, 5, 32, 45);
		playerImage[8] = s1.grabImage(3, 5, 32, 45);

		playerImage[9] = s1.grabImage(1, 7, 32, 45);
		playerImage[10] = s1.grabImage(2, 7, 32, 45);
		playerImage[11] = s1.grabImage(3, 7, 32, 45);

		anim = new Animation(10,playerImage[0],playerImage[1],playerImage[2]);
		anim2 = new Animation(10,playerImage[3],playerImage[4],playerImage[5]);
		anim3 = new Animation(10,playerImage[6],playerImage[7],playerImage[8]);
		anim4 = new Animation(10,playerImage[9],playerImage[10],playerImage[11]);
	}

	public void tick() {
		x += velX;
		y += velY;

		collision();
		// movement
		if (handler.isUp()) {
			velY = -5;
			anim4.runAnimation();
		} else if (!handler.isDown())
			velY = 0;

		if (handler.isDown()) {
			velY = 5;
			anim.runAnimation();
		} else if (!handler.isUp())
			velY = 0;

		if (handler.isRight()) {
			velX = 5;
			anim3.runAnimation();
		} else if (!handler.isLeft())
			velX = 0;

		if (handler.isLeft()) {
			velX = -5;
			anim2.runAnimation();
		} else if (!handler.isRight())
			velX = 0;

		// anim.runAnimation();
	}

	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Block) {
				if (getBounds().intersects(tempObject.getBounds())) {
					x += (velX * -1);
					y += (velY * -1);
					if(velX==0 && velY==0 || velX > 0 && velY > 0 ) {
						x=500;
						y=400;
					}
				}
			}
			if (tempObject.getId() == ID.EnemyPush) {
				if (getBounds().intersects(tempObject.getBounds())) {
					
					int push=20;
					
					if (velX == 0 && velY == 0) {
						
					    if(tempObject.velX==1 &&tempObject.velY==0) { // empurra esquerda
					    	x+=push;
					    }else if(tempObject.velX==0 &&tempObject.velY==-1) { // empurra cima
					    	y+=-push;
					    }else if(tempObject.velX==-1 &&tempObject.velY==0) { // empurra direita
					    	x+=-push;
					    }else if(tempObject.velX==0 &&tempObject.velY==1) { // empurra baixo
					    	y+=push;
					    }
					    }
					
					else if (velX == 0 && velY == 5 || velX == 0 && velY == -5) {//anda y
						y+=velY*-push;
				    }
				        
					else if (velX == 5 && velY == 0||velX == -5 && velY == 0) {//anda x
						x+=velX*-push;
					}
					
					else if (velX == 5 && velY == 5|| velX == -5 && velY == 5||velX == 5 && velY == -5||velX == -5 && velY == -5) {//anda dig sup direita
					    x+=velX*-push;
					    y+=velY*-push;	
					}


				}
			}

			if (tempObject.getId() == ID.EnemyPoint) {
				if (getBounds().intersects(tempObject.getBounds())) {
					game.score += -7;
					handler.object.remove(handler.object.get(i));
					System.out.println(game.score);
				}
			}
			if (tempObject.getId() == ID.Items) {
				if (getBounds().intersects(tempObject.getBounds())) {
					game.score += 5;
					handler.object.remove(handler.object.get(i));
					System.out.println(game.score);
					}
				}
			if (tempObject.getId() == ID.EnemyLife) {
				if (getBounds().intersects(tempObject.getBounds())) {
					game.life +=-1;
					handler.object.remove(handler.object.get(i));
					System.out.println(game.life);
			      	//x=500;
				    //y=400;
				    
					}
				}
			}
		}

	public void render(Graphics g) {

		if (velX == 0 && velY == 0)
			g.drawImage(playerImage[1], x, y, null);

		else if (velX == 0 && velY == -5)
			anim4.drawAnimation(g, x, y, 0);

		else if (velX == 0 && velY == 5)
			anim.drawAnimation(g, x, y, 0);

		else if (velX == -5 && velY == 0 || velX == -5 && velY == -5 || velX == -5 && velY == 5)

			anim2.drawAnimation(g, x, y, 0);

		else if (velX == 5 && velY == 0 || velX == 5 && velY == 5 || velX == 5 && velY == -5)
			anim3.drawAnimation(g, x, y, 0);
		// g.setColor(Color.blue);
		// g.fillRect(x+5,y,25,40);
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 5, y + 4, 25, 36);

	}

}
