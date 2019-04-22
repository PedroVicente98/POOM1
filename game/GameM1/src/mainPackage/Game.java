package mainPackage;
import java.awt.Canvas;
import java.util.Random;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private boolean spawn = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private SpriteSheet ss,s1,spider,snake,mummy,items;
	private int vel=1;
	public int score = 0,life=3,wave=0;
	
	private BufferedImage Level = null;
	private BufferedImage sprite_sheet = null,sprite_sheet2 = null,spiderEnemy = null,snakeEnemyLife = null,back = null;
	private BufferedImage mummyEnemy = null,itemsPoint = null;
	
	public Game() {
		new Window(1046,810,"Diamond Game",this);
		start();
		//System.out.println(score);
		handler = new Handler();
		camera = new Camera(0,0);
		this.addKeyListener(new KeyInput(handler));
		BufferedImageLoader loader = new BufferedImageLoader();
		Level = loader.loadImage("/wizard_level.png");	
		
		sprite_sheet = loader.loadImage("/sprite0.png");
		sprite_sheet2 = loader.loadImage("/personagem.png");
		spiderEnemy = loader.loadImage("/spider.png");
		snakeEnemyLife =  loader.loadImage("/snake.png");
		mummyEnemy =  loader.loadImage("/mummy.png");
		itemsPoint =  loader.loadImage("/items.png");
		
		ss = new SpriteSheet(sprite_sheet);
		s1 = new SpriteSheet(sprite_sheet2);
		spider = new SpriteSheet(spiderEnemy);
		snake = new SpriteSheet(snakeEnemyLife);
		mummy =  new SpriteSheet(mummyEnemy);
		items =  new SpriteSheet(itemsPoint);
		back = ss.grabImage(3,1,32,32);
		
		//handler.addObject(new Wizard(100,100, ID.player, handler));
		loadLevel(Level);
	}
	
	public void start() {
		isRunning =true;
		spawn = true;
		thread = new Thread(this);
		thread.start();
			
	}
	
	public void stop() {
		isRunning = false;
		//String path="C:/Users/6647871.UNIVALI.001/Desktop/game/GameM1/Pontos.txt";
		try {
			thread.join();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning){
		    long now = System.nanoTime();
		    delta += (now - lastTime) / ns;
		    lastTime = now;
		    while(delta>= 1) {
		    	tick();
		    	delta --;
		    }
		    render();
		    frames++;
		    if(System.currentTimeMillis() - timer > 1000) {
		    	timer += 1000;
		    	frames = 0;	
		    }
		}
			stop();
	}
	
	public void tick() {
		
		for(int i = 0; i < handler.object.size(); i++){
			if(handler.object.get(i).id == ID.player) {
				camera.tick(handler.object.get(i));
			}
		}
		if(life == 0) {
			FileWriter arquivo = null;
			
			try{
			arquivo= new FileWriter("C:\\Users\\6647871.UNIVALI.001\\Desktop\\game\\GameM1\\Pontos.txt",true);
			}catch(IOException ex){
				ex.printStackTrace();
			}
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.printf("score;wave"+"\n");
			gravar.printf(score + ";" + wave+"\n");
			System.out.println(gravar);
			try{
			 arquivo.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
			try {
			Thread.sleep(2000);
			}
			catch(InterruptedException ex){}
			System.exit(0);
		}
		handler.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		
		//////////////////////////////////
		
		for(int xx = 0; xx < 30*72; xx+=32){
			for(int yy = 0; yy <30*72; yy+=32){
			g.drawImage(back, xx, yy, null);
			}
		}
		
		//g2d.translate(-camera.getX(),-camera.getY());
		//g.setColor(Color.red);
		//g.fillRect(0,0,1050,800);
		
		handler.render(g);
		//g2d.translate(camera.getX(),camera.getY());
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,0,1050,35);
		g.setColor(Color.black);
		g.drawRect(0,0,1050,35);
		
		g.setColor(Color.GRAY);
		g.fillRect(690, 3, 32, 32);
		g.setColor(Color.GRAY);
		g.fillRect(653, 3, 32, 32);
		g.setColor(Color.GRAY);
		g.fillRect(616, 3, 32, 32);
		
		if(life>=3) {
			g.setColor(Color.GREEN);
			g.fillRect(690, 3, 32, 32);
			g.setColor(Color.red);
			g.drawRect(690, 3, 33, 33);
		}
		if(life>=2) {
			g.setColor(Color.GREEN);
			g.fillRect(653, 3, 32, 32);
			g.setColor(Color.red);
			g.drawRect(653, 3, 33, 33);
		}
		if(life>=1){
			g.setColor(Color.GREEN);
			g.fillRect(616, 3, 32, 32);
			g.setColor(Color.red);
			g.drawRect(616, 3, 33, 33);
		}
		g.setColor(Color.cyan); 	
		Font font = new Font("Verdana", Font.BOLD, 20);
	    g.setFont(font);
		g.drawString("Score: "+ score, 112, 22);
		g.drawString("Wave: "+ wave, 260, 22);
		g.drawString("Life: "+ life, 500, 22);
		
		if(life == 0) {
			g.setColor(Color.red);
			Font font1 = new Font("Verdana", Font.BOLD,60);
			g.setFont(font1);
				g.drawString("GAME OVER",300,400);
			}
 
		
		//////////////////////////////////
		g.dispose();
		bs.show();
		
	}
	
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		boolean hasDraw = false;
		
		for(int xx = 0; xx < w; xx++){
			for(int yy = 0; yy < h; yy++){
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				//int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255)
					handler.addObject(new Block(xx*16,yy*12,ID.Block,ss));
				if(blue == 255 && !hasDraw) {
            		handler.addObject(new Player(500,400,ID.player,handler,s1,this));
					hasDraw = true;
					}
				}
			}
		while(spawn){
			try {
				enemyspawn();
				thread.sleep(5000);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
			
	}
	public void spawnup(){
		//x=3 a 62, y=0
		Random gen = new Random();
		Random pos = new Random();
		
		int i, xx;
		i=gen.nextInt(4);
		xx= pos.nextInt(60)+3;
		vel = pos.nextInt(3)+1;
		
		//ID a = null;
		if(i==0) {
			handler.addObject(new EnemyLife(xx*16,0,ID.EnemyLife,handler,snake));
		}
		else if(i==1) {
			handler.addObject(new EnemyPoint(xx*16,0,ID.EnemyPoint,handler,spider));
		}
		else if(i==2) {
			handler.addObject(new EnemyPush(xx*16,0,ID.EnemyPush,handler,mummy));
		}
		else if(i==3) {
			handler.addObject(new Items(xx*16,0,ID.Items,handler,items));
		}
		handler.object.getLast().setVelY(vel);
	}
	public void spawndown(){
		//x=3 a 62,y=62	
		Random gen = new Random();
		Random pos = new Random();
		
		int i, xx;
		i=gen.nextInt(4);
		xx= pos.nextInt(60)+3;
		vel = pos.nextInt(3)+1;
		
		//ID a = null;
		if(i==0) {
			handler.addObject(new EnemyLife(xx*16,62*12,ID.EnemyLife,handler,snake));
		}
		else if(i==1) {
			handler.addObject(new EnemyPoint(xx*16,62*12,ID.EnemyPoint,handler,spider));
		}
		else if(i==2) {
			handler.addObject(new EnemyPush(xx*16,62*12,ID.EnemyPush,handler,mummy));
		}
		else if(i==3) {
			handler.addObject(new Items(xx*16,62*12,ID.Items,handler,items));
		}
		handler.object.getLast().setVelY(-vel);
		
	}
	public void spawnright() {
		//x=62,y=3 a 62
		Random gen = new Random();
		Random pos = new Random();
		
		int i, yy;
		i=gen.nextInt(4);
		yy= pos.nextInt(60)+3;
		vel = pos.nextInt(3)+2;
		
		//ID a = null;
		if(i==0) {
			handler.addObject(new EnemyLife(62*16,yy*12,ID.EnemyLife,handler,snake));
		}
		else if(i==1) {
			handler.addObject(new EnemyPoint(62*16,yy*12,ID.EnemyPoint,handler,spider));
		}
		else if(i==2) {
			handler.addObject(new EnemyPush(62*16,yy*12,ID.EnemyPush,handler,mummy));
		}
		else if(i==3) {
			handler.addObject(new Items(62*16,yy*12,ID.Items,handler,items));
		}
		handler.object.getLast().setVelX(-vel);
		
		
	}
	public void spawnleft(){
		//x=0,y=3 a 62
		Random gen = new Random();
		Random pos = new Random();
		
		int i, yy;
		i=gen.nextInt(4);
		yy= pos.nextInt(60)+3;
		vel = pos.nextInt(3)+1;
		
		//ID a = null;
		if(i==0) {
			handler.addObject(new EnemyLife(0,yy*12,ID.EnemyLife,handler,snake));
		}
		else if(i==1) {
			handler.addObject(new EnemyPoint(0,yy*12,ID.EnemyPoint,handler,spider));
		}
		else if(i==2) {
			handler.addObject(new EnemyPush(0,yy*12,ID.EnemyPush,handler,mummy));
		}
		else if(i==3) {
			handler.addObject(new Items(0,yy*12,ID.Items,handler,items));
		}
		handler.object.getLast().setVelX(vel);
		
	}
	public void enemyspawn() {
		Random rand = new Random();
		Random SpawNumber =new Random();
		int j=SpawNumber.nextInt(20)+10;
		
		for(int k=0; k<j;k++) {
			int i = rand.nextInt(4);
		switch(i) {
		case 0:
			spawnup();
			break;
		case 1:
			spawnright();
			break;
		case 2:
			spawndown();
			break;

		case 3:
			spawnleft();
			break;
			}
		
		}
		wave++;
	}

	public static void main (String args[]) {
		new Game();
	}
			
}
