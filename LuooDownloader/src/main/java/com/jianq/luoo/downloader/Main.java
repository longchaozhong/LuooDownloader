package com.jianq.luoo.downloader;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author longchaozhong
 * @date 2014年8月2日 下午4:17:46
 * @describe
 */
public class Main {
	public static void main(String[] args) {
		GridLayout numLayout = new GridLayout(1, 2, 10, 10);

		JFrame mainFrame = new JFrame("Luuo Downloader");
		mainFrame.setSize(500, 350);
		mainFrame.setLocation(500, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel numPanel = new JPanel();
		numPanel.setLayout(numLayout);
		final JTextField numFiled = new JTextField();
		JLabel numLabel = new JLabel();
		numLabel.setText("期数");
		numPanel.add(numLabel);
		numPanel.add(numFiled);

		final JTextArea logsArea = new JTextArea();
		JScrollPane sp = new JScrollPane(logsArea);

		JButton startDownload = new JButton("Download");
		startDownload.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String num = numFiled.getText();
				try {
					logsArea.append("开始第【" + num + "】期下载........\n");
					DownloadUtil downloadUtil = new DownloadUtil();
					downloadUtil.setRadioNum(num);
					downloadUtil.setTextArea(logsArea);
					Thread downloader = new Thread(downloadUtil);
					downloader.start();
				} catch (Exception e1) {
					logsArea.append("下载期数为：【" + num + "】出错啦........\n");
				}
			}
		});

		mainFrame.add(numPanel,"North");
		mainFrame.add(sp,"Center");
		mainFrame.add(startDownload,"South");

		mainFrame.setVisible(true);
	}
}
