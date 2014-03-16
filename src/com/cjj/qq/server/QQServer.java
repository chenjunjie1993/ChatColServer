package com.cjj.qq.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class QQServer extends JFrame
{
	private JTextArea ta;
	private JScrollPane sp;
	private ServerSocket server;
	private Thread serverThread;
	
	public QQServer()
	{	
		// 配置界面
		ta = new JTextArea();
		sp = new JScrollPane(ta);
		ta.setEditable(false);
		getContentPane().add(sp);
		
		setTitle("QQ服务器");
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		startServer();
	}
	
	
	/**
	 * 开启服务器
	 */
	public void startServer()
	{
		if (server != null)
		{
			ta.append("服务器已经是开启状态\n");
			return;
		}
		try
		{
			server = new ServerSocket(8888);
		} catch (IOException e)
		{
			ta.append("服务器打开失败\n");
			e.printStackTrace();
		}
		try
		{
			ta.append("服务器已打开\n" + InetAddress.getLocalHost().getHostAddress() + '\n');
		} catch (UnknownHostException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		serverThread = new ServerThread(server, ta);
		serverThread.start();
	}
	
	public void closeServer()
	{
		try
		{
			serverThread.stop();
			server.close();
			server = null;
			ta.append("服务器已关闭\n");
		} catch (IOException e)
		{
			ta.append("服务器关闭失败\n");
		}
	}
}
