/**
 * 
 */
package com.weibo.sdk.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.weibo.sdk.android.demo.R.color;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * @author lei
 * 
 */
public class StringUtil {

	static List<HashMap<String, Integer>> highLightList = new ArrayList<HashMap<String, Integer>>();
	private static final String TOPIC = "#.+?#";
	private static final String NAME = "@[\u4E00-\u9FA5A-Za-z0-9_-]*";

	/**
	 * @param thumbnail_pic
	 * @return
	 */
	public static boolean isEmpty(String string) {
		// TODO Auto-generated method stub
		return (string == null || string.equals("")) ? true : false;
	}

	public static SpannableString getSpannableString(String string) {
		SpannableString spannableString = new SpannableString(string);

	
		getStartAndEnd(Pattern.compile(TOPIC), string);
		getStartAndEnd(Pattern.compile(NAME), string);
		for (HashMap<String, Integer> map : highLightList) {
			ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);//必须写在for循环中
			spannableString.setSpan(span, map.get("start"), map.get("end"),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		}
		highLightList.clear();
		return spannableString;

	}

	public static void getStartAndEnd(Pattern pattern, String string) {
		Matcher matcher = pattern.matcher(string);

		
		
		while (matcher.find()) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("start", matcher.start());
			map.put("end", matcher.end());

			highLightList.add(map);
		}

	}
	public void hightLight() {
		ForegroundColorSpan span = new ForegroundColorSpan(color.blue);
	}

}
