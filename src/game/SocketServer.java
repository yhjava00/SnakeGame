package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	
	private static ServerSocket serverSocket;
	private static BufferedReader br;
	private static PrintWriter pw;
	private static Socket clientSocket;
	
	public static void main(String[] args) {
		init();
	}
	
	public static void init() { 
		try {
			serverSocket = new ServerSocket(8981);
			clientSocket = serverSocket.accept();
			
			System.out.println("Client has accepted");
			
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw = new PrintWriter(clientSocket.getOutputStream());
			
			String readData = "";
			
			while (!(readData = br.readLine()).equals(null)) {
				System.out.println("from Client > " + readData);
				
				String sendData = "";
				int score = Integer.parseInt(readData);
				
				if(score<=10) {
					sendData = "BAD";
				}else if(score<=20) {
					sendData = "SOSO";
				}else if(score<=30) {
					sendData = "GOOD";
				}else if(score<=40) {
					sendData = "NICE";
				}else if(score<=50) {
					sendData = "PERFECT";
				}else {
					sendData = "SUPER";
				}
				
				pw.println("your score is " + score + " > " + sendData);
				pw.flush();
			}
			clientSocket.close();
		} catch (Exception e) {
		}
		
	}

}
