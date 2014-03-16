package com.cjj.qq.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextArea;

public class ServerThread extends Thread
{
	private ServerSocket server;
	private JTextArea ta;
	private int clientCount = 1;
	//private ArrayList<Socket> clients;
	private Map<Integer, Socket> clients;
	
	public ServerThread(ServerSocket server, JTextArea textArea)
	{
		this.server = server;
		this.ta = textArea;
		//clients = new ArrayList<>();
		clients = new HashMap<>();
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				Socket client = server.accept();
				//clients.add(client);
				clients.put(clientCount, client);
				String info = "�ͻ���" + clientCount++ + "����������";
				ta.append(info + '\n');
				/*// ��ȡ���������
				final InputStream is = client.getInputStream();*/
				OutputStream os = client.getOutputStream();
				os.write("��������ӭ��".getBytes());
				os.flush();
				/*for(int i = 0; i < clients.size(); i++)
				{
					Socket s = clients.get(i);
					OutputStream oss = s.getOutputStream();
					oss.write(info.getBytes());
					oss.flush();
				}*/
				Collection<Socket> sockets = clients.values();
				for (Iterator<Socket> iterator = sockets.iterator(); iterator.hasNext();)
				{
					Socket socket = (Socket) iterator.next();
					OutputStream oss = socket.getOutputStream();
					oss.write(info.getBytes());
					oss.flush();
				}
				
				new Thread(new HandleClient(client, clients, clientCount)).start();
				
			} catch (IOException e)
			{
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
}
