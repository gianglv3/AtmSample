package com.ipos.restaurant.restful;

import android.net.Uri;

/**
 * Build URl
 * 
 * @author gianglv3
 * 
 */
public class CreateResfulString {
	private StringBuilder baseUrl;

	public CreateResfulString(String baseUrl, boolean gen) {
		super();
		if (gen) {
			this.baseUrl = new StringBuilder(baseUrl).append("?");
		} else {
			this.baseUrl = new StringBuilder(baseUrl);
		}
	}

	public void AddParam(String key, Object value) {
		baseUrl.append(replateSpecialKey(key) + "=" + Uri.encode(replateSpecialKey(value.toString().trim()))).append(
				"&");
	}


	public void AddParamNonEncode(String key, Object value) {
		baseUrl.append(replateSpecialKey(key) + "=" + replateSpecialKey(value.toString().trim())).append(
				"&");
	}


	private String replateSpecialKey(String content) {
		content = content.replace("&", "\u0026");
		content = content.replace("#", "\u0023");
		content = content.replace("#", "\u003B");
		return content;

	}

	public CreateResfulString(String baseUrl) {
		if (!baseUrl.endsWith("?")) {
			this.baseUrl = new StringBuilder(baseUrl).append("?");
		}
	}

	@Override
	public String toString() {
		String encode = baseUrl.toString().substring(0, baseUrl.length() - 1);
		// Log.i("ResfulString.toString()", "gianglv3----> "+encode);
		return encode;
	}

}