/**
 * 
 */
package com.ruoogle.nova.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 刘飞
 * E-mail:liufei_it@126.com
 * 3:39:53 PM Sep 29, 2013
 */
public class DateUtil {

	public static String formatDate(String sFormat, Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		return formatter.format(date);
	}
}