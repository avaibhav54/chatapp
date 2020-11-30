package chatApp;

import java.net.*;

import java.io.*;

import java.util.*;


public class server {

	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	public server() {
		try {
			
			server =new ServerSocket(666);
			System.out.println("Server is ready to accept connection");
			socket=server.accept();	
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			startread();
			startwrit();
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
		}
		
	}
	private void startread() {
		// TODO Auto-generated method stub
		Runnable r1=()->{
			System.out.println("reader started.................");
			try {
				
			
			while(true) {
				
				String msg = null;
				
					try {
						msg = br.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				
				if(msg.equals("exit")) {
					
					System.out.println("client terminated the chat");
					socket.close();
					break;
				}
				System.out.println("client::"+msg);
				
				
			}
			} catch (Exception e) {
				// TODO: handle exception
System.out.println("connection closed");			}
			
			
		};
		new Thread(r1).start();
		
	}
	private void startwrit() {
		// TODO Auto-generated method stub
		Runnable r2=()->{
			System.out.println("writer started");
			try {
				while(true && !socket.isClosed()) {
			
					Scanner sc=new Scanner(System.in);
					String content=sc.nextLine();
					
				
					out.println(content);
					out.flush();
					if(content.equals("exit")) {
						socket.close();
						break;
					}
				} 
			}
		 catch (Exception e) {
			 System.out.println("connection closed");		

			//e.printStackTrace();
			// TODO: handle exception
		}
			
		};
		new Thread(r2).start();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hlo this is server ");
		new server();

	}

}
