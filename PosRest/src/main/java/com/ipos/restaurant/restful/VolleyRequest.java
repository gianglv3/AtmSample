package com.ipos.restaurant.restful;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GiangLV on 10/7/2015.
 */
public class VolleyRequest<T> extends Request<T> {

    public VolleyRequest(String url, Response.ErrorListener listener) {
        super(url, listener);
    }

    public VolleyRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> params = new HashMap<String, String>();

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        // Log.i("GsonRequest.getHeaders()", "get header");
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {


        return params;
    }

    public void setHeader(String title, Object content) {
        content = content == null ? "" : content;
        headers.put(replateSpecialKey(title), replateSpecialKey(content.toString()));
    }

    public void setParams(String title, Object content) {
        content = content == null ? "" : content;

        params.put(replateSpecialKey(title), replateSpecialKey(content.toString()));

    }

    private String replateSpecialKey(String content) {

        content=content.replace("&","\u0026");
        content=content.replace("#","\u0023");
        content=content.replace("#","\u003B");
        return  content;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(T response) {

    }
}
