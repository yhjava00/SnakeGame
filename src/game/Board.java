package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		Map.out = true;
        		System.exit(1);
        	}
		});
	}
	
}
