package com.cjj.qq.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class HandleClient implements Runnable
{
	private Socket client;
	private Map<Integer, Socket> clients;
	private int num;
	
	public HandleClient(Socket client, Map<Integer, Socket> clients, int num)
	{
		this.client = client;
		this.clients = clients;
		this.num = num;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				InputStream is = client.getInputStream();
				byte[] bytes = new byte[1024];
				int len = is.read(bytes);
				String result = new String(bytes, 0, len);
				String[] strs = result.split("to");
				String head = strs[0];
				if (head.equals("0"))
				{
					// 群发
					Collection<Socket> sockets = clients.values();
					for (Iterator<Socket> iterator = sockets.iterator(); iterator.hasNext();)
					{
						Socket socket = (Socket) iterator.next();
						OutputStream oss = socket.getOutputStream();
						oss.write(("客户端" + num + "：" + strs[1]).getBytes());
						oss.flush();
					}
				}
				else
				{
					Integer toClient = new Integer(head);
					Socket socket = clients.get(toClient);
					OutputStream oss = socket.getOutputStream();
					oss.write(("客户端" + num + "：" + strs[1]).getBytes());
					oss.flush();
				}
				
			} catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
}
