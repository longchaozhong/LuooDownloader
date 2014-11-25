package com.jianq.luoo.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author longchaozhong
 * @date 2014年8月2日 下午5:26:31
 * @describe 
 */
public class Donwload {
	public void down(String downLoadUrl ,String savePath){
		HttpClient client = new HttpClient();
		GetMethod httpGet2 = new GetMethod(downLoadUrl);
		try {
			client.executeMethod(httpGet2);
			InputStream in = httpGet2.getResponseBodyAsStream();
			File saveDir = new File(savePath);
			FileOutputStream out = new FileOutputStream(saveDir);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			httpGet2.releaseConnection();
		}
	}
}
