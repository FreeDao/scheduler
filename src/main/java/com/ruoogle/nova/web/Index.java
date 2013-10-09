/**
 * 
 */
package com.ruoogle.nova.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 刘飞
 * E-mail:liufei_it@126.com
 * 8:35:04 PM Sep 29, 2013
 */
@RequestMapping("/index")
public class Index {
	
	@RequestMapping("/home.htm")
	public void home(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer data = new StringBuffer();
		data.append("Welcome Schedule System!\n");
		data.append("Ruoogle Schedule System Running...\n");
		print(response, data.toString());
	}
	
	private void print(HttpServletResponse response, String data) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(data);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
					out = null;
				} catch (Exception e2) {
					// 忽略
				}
			}
		}
	}
}
