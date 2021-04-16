package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Snake {

	private static BufferedReader br;
	private static PrintWriter pw;
	private static Socket clientSocket;
	
	public static void main(String[] args) {
		new Board();
		init();
	}

	public static void init() {
		try {
			clientSocket = new Socket("localhost", 8981);
			System.out.println("Server Connect");
			
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw = new PrintWriter(clientSocket.getOutputStream());
			
			while(!Map.out) {
				
				try {
					Thread.sleep(1);
				} catch (Exception e) {}
				if(Map.end) {
					pw.println(Map.len);
					pw.flush();
					
					System.out.println("from Server > " + br.readLine());
					Map.end = false;
				}
			}
			clientSocket.close();
		} catch (Exception e) {
		}
	}
}
