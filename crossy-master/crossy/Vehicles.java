package crossy; 

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Vehicles {
	private Rectangle vehicle;
	private int speed, x, y, w, h;
	private BufferedImage image;

    Vehicles(int x, int y, int w, int h, int speed){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.speed = speed;	
        setType();
        vehicle = new Rectangle(x, y, w, h);
    }

    public void setType(){
		Random random = new Random();
		int rand = random.nextInt(2);
		if(speed > 0){
			if(rand == 0)
				changeSprite("img/RamblingWreckRight.png");
			else 
				changeSprite("img/StingerRight.png");
		}
		else{
			if(rand == 0)
				changeSprite("img/StingerLeft.png");
			else 
				changeSprite("img/RamblingWreckLeft.png");
		}
	}
    public void changeSprite(String sprite){
		try {
			image = ImageIO.read(getClass().getResourceAsStream(sprite));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void graphic(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(image, vehicle.x, vehicle.y, null);
		move();
		if(OutOfBounds()){
			if(vehicle.x > 500)
				vehicle.x =- 100;
			else {
				vehicle.x = Display.WIDTH;
		    }
        }
	}
	public void move(){
		vehicle.x += speed;
	}
   
	public boolean OutOfBounds(){
		return vehicle.getMaxX() <= 0 || vehicle.getMinX() >= Display.WIDTH;
	}
 
	public Rectangle getVehicle() {
		return vehicle;
	}
 
	public void setVehicle(Rectangle vehicle) {
		this.vehicle = vehicle;
	}
}
