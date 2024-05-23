package crossy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Buzz extends JPanel implements KeyListener{
	private int x, y, w, h;
	private Rectangle buzz;
	private BufferedImage image;
	private String sprite;
	

	Buzz(int x,int y,int w,int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		buzz = new Rectangle(x, y, w, h);
		changeSprite("img/BuzzBack.png");
		
	}
	public void graphic(Graphics g){
		Graphics2D g2d=(Graphics2D)g;
		g2d.drawImage(image, buzz.x, buzz.y,null);
	}
	public void mover(int speed){
		buzz.x += speed;
	}
	public void stopMove(){
		mover(0);
	}
	public int roundTo(int number){
		return number - (number % 50);
	}
	
	public void changeSprite(String sprite){
		this.sprite = sprite;
		try {
			image= ImageIO.read(getClass().getResourceAsStream(sprite));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean state(){
		return Display.state == Display.STATE.GAME;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(state()){
			int key=e.getKeyCode();
			if (key==KeyEvent.VK_RIGHT) {
				if(buzz.getMaxX()+1*Display.GRID<Display.WIDTH&&sprite=="img/BuzzRight.png"){
					buzz.x = buzz.x+1*Display.GRID;
					buzz.setLocation(buzz.x, buzz.y);			
				}
				else{
					changeSprite("img/BuzzRight.png");
				}
			}
			else if(key==KeyEvent.VK_LEFT) {
				if(buzz.getMaxX()-1*Display.GRID>0&&sprite=="img/BuzzLeft.png"){
					buzz.x = buzz.x-1*Display.GRID;
					buzz.setLocation(buzz.x, buzz.y);
				}
				else{
					changeSprite("img/BuzzLeft.png");
				}
			}
			else if(key==KeyEvent.VK_UP) {
				if(buzz.getMaxY()-1*Display.GRID>0)
					buzz.y=buzz.y-1*Display.GRID;
				if(buzz.x%50==0)
					buzz.setLocation(buzz.x, buzz.y);
				else{
					buzz.setLocation(roundTo(buzz.x), buzz.y);
				}
				changeSprite("img/BuzzBack.png");

			}
			else if(key==KeyEvent.VK_DOWN) {
				if(buzz.getMaxY()+1*Display.GRID<Display.HEIGHT)
					buzz.y=buzz.y+1*Display.GRID;
				if(buzz.x%50==0)
					buzz.setLocation(buzz.x, buzz.y);
				else{
					buzz.setLocation(roundTo(buzz.x), buzz.y);
				}
				changeSprite("img/BuzzBack.png");

			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	public Rectangle getBuzz() {
		return buzz;
	}
	public void setBuzz(Rectangle buzz) {
		this.buzz = buzz;
	}
	
}
