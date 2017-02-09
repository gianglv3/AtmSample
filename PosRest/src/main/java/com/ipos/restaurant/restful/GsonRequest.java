/**
 * Copyright 2013 Mani Selvaraj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ipos.restaurant.restful;

import android.annotation.TargetApi;
import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ipos.restaurant.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


/**
 * Custom implementation of Request<T> class which converts the HttpResponse
 * obtained to Java class objects. Uses GSON library, to parse the response
 * obtained. Ref - JsonRequest<T>
 * 
 * @author Mani Selvaraj
 */

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class GsonRequest<T> extends VolleyRequest<T> {

	private final Listener<T> mListener;
	private final String mRequestBody;

	private Gson mGson;
	private Class<T> mJavaClass;

	public GsonRequest(int method, String url, Class<T> cls,
			String requestBody, Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		mGson = new Gson();
		mRequestBody = requestBody;
		mJavaClass = cls;
		mListener = listener;

	}

	@Override
	protected void deliverResponse(T response) {
		if (mListener != null)
			mListener.onResponse(response);
	}


	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					Charset.forName("UTF-8"));
		//	Log.i("GsonRequest.parseNetworkResponse()", "JSON  " + jsonString);
			T parsedGSON = mGson.fromJson(jsonString, mJavaClass);
			return Response.success(parsedGSON,
					HttpHeaderParser.parseCacheHeaders(response));

		} catch (JsonSyntaxException je) {
			return Response.error(new ParseError(je));
		}

	}

	@Override
	public byte[] getBody() {
		try {
			return mRequestBody == null ? super.getBody() : mRequestBody
					.getBytes(getParamsEncoding());
		} catch (UnsupportedEncodingException uee) {
			VolleyLog
					.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
							mRequestBody, getParamsEncoding());
			Log.i("GsonRequest.getBody()", " get byte null");
			return null;
		} catch (AuthFailureError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
