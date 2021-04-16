package game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Board extends JFrame {

	Board() {
		
		add(new Map());
		
		setResizable(false);
		pack();
		
	    Dimension frameSize = getSize();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 3);
	    
        setTitle("Snake");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void setDefaultCloseOperation(int operation) {
		Map.out = true;
		super.setDefaultCloseOperation(operation);
		
	}
}
