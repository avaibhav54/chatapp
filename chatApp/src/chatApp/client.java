package chatApp;
import java.net.*;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import java.util.*;

import javax.swing.*;


public class client extends JFrame {
	Socket socket;

	BufferedReader br;
	PrintWriter out;
	private JLabel head=new JLabel("Client Area");
	private JTextArea message=new JTextArea();
	private JTextField input=new JTextField();
	
	
	public  client() {
		try {
			
			System.out.println("sending request to server");
			socket=new Socket("127.0.0.1",666);
			System.out.println("connection done");
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			
			creategui();
			handle();
		startread();
//			startwrit();
			
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
		}
		
	}

	private void handle() {
		// TODO Auto-generated method stub
		input.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode()==10) {
					String st=input.getText();
					message.append("me::"+st+"\n");

					out.println(st);
					out.flush();
					input.setText("");
					
				}
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void creategui() {
		// TODO Auto-generated method stub
		
		this.setTitle("client message");
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		message.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(head,BorderLayout.NORTH);
		JScrollPane js=new JScrollPane(message);
		this.add(js);
		this.add(input,BorderLayout.SOUTH);
		
		
		
		this.setVisible(true);
		
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
				
					System.out.println("server terminated the chat");
					input.setEnabled(false);
					JOptionPane.showMessageDialog(this,"server terminated the chat" );
					socket.close();
					break;
				}
				message.append("server::"+msg+"\n");
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
System.out.println("connection closed");		}
			
			
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

				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("connection closed");		
			}
			
			
		};
		new Thread(r2).start();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hlo this is client");
		new client();
		

	}
	

}
