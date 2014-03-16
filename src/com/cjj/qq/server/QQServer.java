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
		// ���ý���
		ta = new JTextArea();
		sp = new JScrollPane(ta);
		ta.setEditable(false);
		getContentPane().add(sp);
		
		setTitle("QQ������");
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		startServer();
	}
	
	
	/**
	 * ����������
	 */
	public void startServer()
	{
		if (server != null)
		{
			ta.append("�������Ѿ��ǿ���״̬\n");
			return;
		}
		try
		{
			server = new ServerSocket(8888);
		} catch (IOException e)
		{
			ta.append("��������ʧ��\n");
			e.printStackTrace();
		}
		try
		{
			ta.append("�������Ѵ�\n" + InetAddress.getLocalHost().getHostAddress() + '\n');
		} catch (UnknownHostException e)
		{
			// TODO �Զ����ɵ� catch ��
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
			ta.append("�������ѹر�\n");
		} catch (IOException e)
		{
			ta.append("�������ر�ʧ��\n");
		}
	}
}
