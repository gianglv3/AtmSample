package com.ipos.restaurant.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ipos.restaurant.R;

public class ToastUtil {

	public static void makeText(Context context, String text) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Call toast.xml file for toast layout
		View toastRoot = inflater.inflate(R.layout.toast_layout, null);
		TextView tv = (TextView) toastRoot.findViewById(R.id.text);
		tv.setText(text);
		Toast toast = new Toast(context);
		toast.setView(toastRoot);

		 toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER,
		 0,250);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

	public static void makeText(Context context, String text, int duration) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Call toast.xml file for toast layout
		View toastRoot = inflater.inflate(R.layout.toast_layout, null);
		TextView tv = (TextView) toastRoot.findViewById(R.id.text);
		tv.setText(text);
		Toast toast = new Toast(context);
		toast.setView(toastRoot);

		 toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER,
		 0,250);
		toast.setDuration(duration);
		toast.show();
	}

	public static void makeText(Context context, int text) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Call toast.xml file for toast layout
		View toastRoot = inflater.inflate(R.layout.toast_layout, null);
		TextView tv = (TextView) toastRoot.findViewById(R.id.text);
		tv.setText(text);
		Toast toast = new Toast(context);

		toast.setView(toastRoot);


		 toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER,
		 0,250);

		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

	public static void makeText(Context context, int text, int duration) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Call toast.xml file for toast layout
		View toastRoot = inflater.inflate(R.layout.toast_layout, null);
		TextView tv = (TextView) toastRoot.findViewById(R.id.text);
		tv.setText(text);
		Toast toast = new Toast(context);

		toast.setView(toastRoot);

		 toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER,
		 0,250);
		

		toast.setDuration(duration);
		toast.show();
	}

}
