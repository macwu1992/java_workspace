//此版本增加功能：

//使代表坦克的圆相应上下左右键

import java.awt.*;
import java.awt.List;
import java.util.*;
import java.awt.event.*;

public class TankClient extends Frame{
	int x = 50;
	int y = 50;
	
	Image offScreenImage = null;
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank tank = new Tank(x, y, true, this);
	//Tank enemyTank = new Tank(x+x, y+y, false, this);
	
	ArrayList<Tank> tanks = new ArrayList<Tank> ();
	ArrayList<Missile> missiles = new ArrayList<Missile> ();
	
	public void paint(Graphics g) {
		g.drawString("Missiles count:"+missiles.size(), 10, 50);
		g.drawString("EnemyTanks count:"+tanks.size(), 10, 30);
		tank.draw(g);
		for(int k = 0; k<tanks.size(); k++){
			Tank t = tanks.get(k);
			if(!t.isLive) tanks.remove(t);
			if(t.isLive) t.draw(g);

		}
		
		for(int i=0; i<missiles.size();i++){
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			if(!m.isMissileLive()) missiles.remove(m);
			m.draw(g);
		}
	}
	
	
	
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.GREEN);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage ,0 ,0 ,null);
	}



	public void lauchFrame(){
		for(int i = 0; i<5; i++){
			tanks.add(new Tank(x+(i+1)*x, y, false, this));
		}
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setBackground(Color.GREEN);
		this.setResizable(false);
		
		this.addKeyListener(new KeyMonitor());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}
	
	private class PaintThread implements Runnable{
		public void run(){
			while(true){
				repaint();
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			tank.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			tank.keyReleased(e);
		}
		
		
	}

}
