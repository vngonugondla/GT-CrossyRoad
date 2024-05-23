package crossy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//class Logs & variable log
public class Boats {
	private int x, y, w, h, speed;
	private Rectangle boat;
	private BufferedImage image;
	
	Boats(int x, int y, int w, int h, int speed){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.speed = speed;
		try {
			image= ImageIO.read(getClass().getResourceAsStream("img/log.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		boat = new Rectangle(x, y, w, h);
	}

	//render()
	public void graphic(Graphics g){
		Graphics2D g2d= (Graphics2D)g;
		g2d.drawImage(image, boat.x, boat.y,w,h, null);
		move();
        //isOut()
		if(OutofBounds()){
			if(boat.x >= Display.WIDTH/2)
				boat.x = -170;
			else if(boat.x < Display.WIDTH/2)
				boat.x = Display.WIDTH;
		}
	}
	public boolean OutofBounds(){
		return boat.getMaxX() <= 0 || boat.getMinX() >= Display.WIDTH;
	}
	public void move(){
		boat.x += speed;
	}
    //getLog()
	public Rectangle getBoat() {
		return boat;
	}
	public void setBoat(Rectangle boat) {
		this.boat = boat;
	}
	public int getSpeed() {
		return speed;
	}
}
