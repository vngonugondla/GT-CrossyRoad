package crossy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class Display extends JPanel implements Runnable{
	public static int GRID=50;
	public static int ERRORY=10;
	public static int ERRORX=16;
	public static int WIDTH=600+ERRORX;
	public static int HEIGHT=500-ERRORY;
	public enum STATE{
		MENU,
		GAME,
		HELP
	};
	public static STATE state=STATE.MENU;
	private Menu menu;
	private BufferedImage image;
	private Buzz buzz;
	private Vehicles v1[];
	private Vehicles v2[];
	private Boats b1[];
	private Boats b2[];
	private Boats b3[];
	private int deaths=0;
	private int score=0;
	Display(){
		buzz = new Buzz(250,HEIGHT-90,50,50);
		menu= new Menu();
		v1 = new Vehicles[2];
		v2 = new Vehicles[3];
		b1= new Boats[2];
		b2= new Boats[2];
		b3= new Boats[2];
		
		loadMap();
		initializeGame();
		start();
		
		this.addKeyListener(menu);
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.addKeyListener(buzz);
		setFocusable(true);
	}
	public void initializeGame(){
		for(int i=0;i<v1.length;i++){
			v1[i]= new Vehicles(0+i*290,HEIGHT-140,100,50,3);
		}
		for(int i=0;i<v2.length;i++){
			v2[i]= new Vehicles(0+i*270,HEIGHT-190,100,50,-2);
		}
		for(int i=0;i<b1.length;i++){
			b1[i]= new Boats(0+i*250,HEIGHT-290,170,50,+2);
		}
		for(int i=0;i<b2.length;i++){
			b2[i]= new Boats(0+i*300,HEIGHT-340,170,50,-2);
		}
		for(int i=0;i<b3.length;i++){
			b3[i]= new Boats(0+i*350,HEIGHT-390,170,50,+3);
		}
	}
	public void didIntersectCar(){
		for(Vehicles v : v1){
			if(buzz.getBuzz().getBounds().intersects(v.getVehicle().getBounds())){
				reset();
			}
		}
		for(Vehicles v : v2){
			if(buzz.getBuzz().getBounds().intersects(v.getVehicle().getBounds())){
				reset();
			}
		}
	}
	public void isInsideLog(){
		Boats boatArray[][]=new Boats[][] {b1,b2,b3};

		for (int i = 0; i < boatArray.length; i++) {

			if(buzz.getBuzz().getCenterY()<HEIGHT-240-i*50&&buzz.getBuzz().getCenterY()>HEIGHT-290-i*50){
				if(!((buzz.getBuzz().getMinX()>boatArray[i][0].getBoat().getMinX()&&buzz.getBuzz().getMaxX()<boatArray[i][0].getBoat().getMaxX())||
						(buzz.getBuzz().getMinX()>boatArray[i][1].getBoat().getMinX()&&buzz.getBuzz().getMaxX()<boatArray[i][1].getBoat().getMaxX()))){
					reset();
				}
				else{
					buzz.mover(boatArray[i][1].getSpeed());
				}
			}
		}
	}
	public void loadMap(){
		try {
			image= ImageIO.read(getClass().getResourceAsStream("img/map.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void score(){
		if(buzz.getBuzz().getCenterY()<HEIGHT-390){
			score++;
			deaths--;
			reset();
		}
	}
	public void showInfo(Graphics g){
		Graphics2D g2d= (Graphics2D)g;
		g.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.PLAIN, 18));
		g2d.drawString("Deaths: "+Integer.toString(deaths), 15, 20);
		g2d.drawString("Score: "+Integer.toString(score), 105, 20);
	}
	public void reset(){
		deaths++;
		buzz.getBuzz().x=250;
		buzz.getBuzz().y=HEIGHT-90;
		JOptionPane.showMessageDialog(null, "Game Over", "Game Over!", JOptionPane.PLAIN_MESSAGE);
	}
	public void AntiAliasing(Graphics g){
		Graphics2D g2d= (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	public void renderGame(Graphics g){
		g.drawImage(image, 0, 0, null);
		for(Boats boat : b1)
			boat.graphic(g);
		for(Boats boat : b2)
			boat.graphic(g);
		for(Boats boat : b3)
			boat.graphic(g);
		buzz.graphic(g);
		for(Vehicles vehicle: v1)
			vehicle.graphic(g);
		for(Vehicles vehicle: v2)
			vehicle.graphic(g);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		AntiAliasing(g);
		if(state==STATE.MENU||state==STATE.HELP){
			menu.graphic(g);
		}else if(state==STATE.GAME){
		renderGame(g);
		score();
		showInfo(g);
		didIntersectCar();
		isInsideLog();
		}
	}

    public void start() {   			
        Thread thread = new Thread(this);
        thread.start();
    }
	@Override
	public void run() {
		while (true) {
            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
