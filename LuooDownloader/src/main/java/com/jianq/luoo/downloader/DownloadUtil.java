package com.jianq.luoo.downloader;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author longchaozhong
 * @date 2014年8月2日 下午3:58:06
 * @describe
 */
public class DownloadUtil implements Runnable {
	private final static String	BASE_URL		= "http://www.luoo.net/music/";
	private final static String	DOWNLOAD_URL	= "http://luoo.800edu.net/low/luoo/radio";
	private String				radioNum;
	private String				savePath		= "E:\\luoo\\";
	private JTextArea			textArea		= new JTextArea();

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getRadioNum() {
		return radioNum;
	}

	public void setRadioNum(String radioNum) {
		this.radioNum = radioNum;
	}

	public void download() {
		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(BASE_URL + (radioNum.length() < 3?("0"+radioNum):radioNum));
		httpGet.getParams().setContentCharset("UTF-8");
		String title = null;
		try {
			client.executeMethod(httpGet);
			byte[] responsBody = httpGet.getResponseBody();
			Pattern p = Pattern
					.compile("<a href=\"javascript:;\" rel=\"nofollow\" class=\"trackname btn-play\">(.*?)</a>");
			Matcher m = p.matcher(new String(responsBody,"UTF-8"));

			// download picture
			Pattern p2 = Pattern
					.compile("<img src=\"(.*?)\" alt=\"(.*?)\" class=\"vol-cover\">");
			Matcher m2 = p2.matcher(new String(responsBody,"UTF-8"));
			String picUrl = null;
			
			if (m2.find()) {
				picUrl = m2.group(1);
				title = m2.group(2);
			}
			textArea.append(">>>本期名称:《" + title + "》\n");
			while (m.find()) {
				String downloadName = m.group(1).substring(0,
						m.group(1).indexOf("."))
						+ ".mp3";
				String realName = m.group(1).substring(
						m.group(1).indexOf(".") + 2)
						+ ".mp3";
				textArea.append(">>>开始下载《" + realName + "》\n");
				title = title.replace(".", "");
				File saveDir = new File(savePath + (radioNum.length() < 3?("0"+radioNum):radioNum) + "." + title);
				if (!saveDir.exists()) {
					saveDir.mkdirs();
				}
				new Donwload().down(DOWNLOAD_URL + (radioNum.charAt(0)== '0'? radioNum.substring(1):radioNum)+ "/"
						+ downloadName, savePath + (radioNum.length() < 3?("0"+radioNum):radioNum) + "." + title
						+ "\\" + realName.replace("?", ""));
				textArea.append(">>>《" + realName + "》下载完成\n");
			}
			textArea.append(">>>开始下载封面图片\n");
			new Donwload().down(picUrl, savePath + (radioNum.length() < 3?("0"+radioNum):radioNum) + "." + title
					+ "\\" + title + ".jpg");
			textArea.append(">>>封面图片下载完成\n");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		textArea.append(">>>第【" + radioNum + "】期《"+title+"》下载完成\n");
	}

	public void run() {
		download();
	}

}