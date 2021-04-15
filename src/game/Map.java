package game;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

public class Map extends JPanel {
	
	private Queue<int[]> body;
	
	protected static boolean end;
	protected static boolean out = false;
	
	private Label end_msg = new Label("game over!");
	private Button restart = new Button("restart");
	
	private final int MAP_HEIGHT = 30;
	private final int MAP_WIDTH = 30;
	private final int ITEM_SIZE = 20;
	private final int SPEED = 50;
	
	private String direction;
	private String realDirection;
	
	private int p_x, p_y;
	
	private int[][] map;
	private int[] head = new int[2];
	private int[] tail = new int[2];
	protected static int len;
	
	private SnakeThread t;
	
	public Map() {
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(MAP_WIDTH * ITEM_SIZE, MAP_HEIGHT * ITEM_SIZE));
        
		addKeyListener(new SnakeAdapter());
		
		setLabelAndButton();
        start_game();
	}
	
	private void setLabelAndButton() {
		
		end_msg.setBackground(Color.BLACK);
		end_msg.setFont(new Font("Serif", Font.BOLD, 21));
		end_msg.setForeground(Color.blue);
		end_msg.setAlignment(Label.CENTER);
		end_msg.setBounds(MAP_WIDTH*ITEM_SIZE/2-50, 50, 100, 75);
		
		restart.setBounds(MAP_WIDTH*ITEM_SIZE/2-37, 130, 75, 30);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(end_msg);
				remove(restart);
				start_game();
			}
		});
	}
	
	private void start_game() {
		map = new int[MAP_HEIGHT][MAP_WIDTH];
		body = new LinkedList<>();
		
		direction = "up";
		realDirection = "up";

		len = 10;
		head = new int[] {MAP_WIDTH/2, (MAP_HEIGHT-10)-(len-1)};
		
        for(int i=0; i<len; i++) {
        	
        	tail = new int[] {MAP_WIDTH/2, MAP_HEIGHT-10-i};
        	
        	map[tail[0]][tail[1]] = 1;
        	body.add(tail);
        }

        creatPoint();
        
        end = false;
        
        t = new SnakeThread();
        t.start();
	}

	private void creatPoint() {
		do {
			p_x =  (int) (Math.random() * MAP_WIDTH);
			p_y =  (int) (Math.random() * MAP_HEIGHT);
		} while (map[p_x][p_y]==1);
	}
	
	private void moveAndCheckPoint() {
		
		realDirection = direction;
		
		switch(realDirection) {
		case "right":
			head[0]++;
			break;
		case "left":
			head[0]--;
			break;
		case "up":
			head[1]--;
			break;
		case "down":
			head[1]++;
			break;
		}
		
		body.add(new int[] {head[0], head[1]});
		
		if(!(p_x==head[0]&&p_y==head[1])) {
			tail = body.poll();
			map[tail[0]][tail[1]] = 0;
		}else {
			len++;
			creatPoint();
		}
	}
	
	private boolean check_over() {
		
		if(head[0]<0||head[0]>=MAP_WIDTH) 
			return true;
		
		if(head[1]<0||head[1]>=MAP_HEIGHT)
			return true;
		
		if(map[head[0]][head[1]]==1)
			return true;
		
		return false;
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.red);
        
        for(int i=0; i<MAP_WIDTH; i++) {
        	for(int j=0; j<MAP_HEIGHT; j++) {
        		if(map[j][i]==1) {
        			g.fillRect(j*ITEM_SIZE, i*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
        		}
        	}
        	        	
        }
        
        g.setColor(Color.yellow);
        g.fillRect(p_x*ITEM_SIZE, p_y*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
    }

    class SnakeThread extends Thread {
    	
    	@Override
    	public void run() {
    		while(!end) {
    			try {
					Thread.sleep(SPEED);
				} catch (Exception e) {}
    			
    			moveAndCheckPoint();
        		
        		if(!check_over()) {
        			map[head[0]][head[1]] = 1;
        		} else {
        			add(end_msg);
        			add(restart);
        			end = true;
        		}
        			
        		repaint();
    		}
    	}
    }

    class SnakeAdapter extends KeyAdapter {
    	
    	@Override
    	public void keyPressed(KeyEvent e) {
    		
            int key = e.getKeyCode();
            
            switch(key) {
            case KeyEvent.VK_RIGHT:
            	if(!realDirection.equals("left"))
            		direction = "right";
            	break;
            case KeyEvent.VK_LEFT:
            	if(!realDirection.equals("right"))
            		direction = "left";
            	break;
            case KeyEvent.VK_UP:
            	if(!realDirection.equals("down"))
            		direction = "up";
            	break;
            case KeyEvent.VK_DOWN:
            	if(!realDirection.equals("up"))
            		direction = "down";
            	break;
            }
    	}
    }
    
}
