package com.ipos.restaurant.util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.R;

import com.ipos.restaurant.activities.WebViewActivity;

import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.leolin.shortcutbadger.ShortcutBadger;

public class Utilities {

	public static void hideKeyboard(View focusingView, Activity context) {
		try {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (focusingView != null) {
				imm.hideSoftInputFromWindow(focusingView.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			} else {
				imm.hideSoftInputFromWindow(context.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	public static void showKeyboard(View focusingView, Context context) {
		focusingView.requestFocus();
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(focusingView, InputMethodManager.SHOW_IMPLICIT);
	}

	public static String getDecode(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String readHTMLerror(Context ctx) {

		return readFileContent(ctx, "ketnoi.html");
	}

	public static boolean gotoApps(Context ctx, String paka) {
		Intent intent;
		intent = new Intent(Intent.ACTION_MAIN);

		try {

			PackageManager manager = ctx.getPackageManager();
			intent = manager.getLaunchIntentForPackage(paka);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ctx.startActivity(intent);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Xu ly khi muon vao URL
	 *
	 * @param ctx
	 */
	public static void gotoUrl(Context ctx, String url) {
		Intent intent;

		Log.i("Utilities.gotoUrl()", "GiangLV----> " + url);
		intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		try {
			ctx.startActivity(intent);
		} catch (Exception e) {

			Log.i("Utilities.gotoUrl()", "GiangLV----> " + e.getMessage());
		}
	}

	public static void gotoUrlMyApp(Context ctx, String url) {
		Intent intent = null;

		Log.i("Utilities.gotoUrl()", "GiangLV----> " + url);
		intent = new Intent(ctx, WebViewActivity.class);
		intent.putExtra(Constants.KEY_DATA, url);

		try {
			ctx.startActivity(intent);
		} catch (Exception e) {

			Log.i("Utilities.gotoUrl()", "GiangLV----> " + e.getMessage());
		}
	}


	public String ReadFileContent(Context ctx) {
		String line;
		String result = "";

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(ctx
					.getAssets().open("ketnoi.html")));

			while ((line = in.readLine()) != null) {
				result = result + line;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}

	public static String readFileContent(Context ctx, String filename) {
		String line;
		String result = "";

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(ctx
					.getAssets().open(filename)));

			while ((line = in.readLine()) != null) {
				result = result + line;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Vao ypdate
	 *
	 * @param context
	 */
	public static void gotoMarket(Context context) {
		String appName = context.getPackageName();
		try {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("market://details?id=" + appName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://play.google.com/store/apps/details?id="
							+ appName)));
		}

	}

	public static Intent genIntentGoMarket(Context context) {
		String appName = context.getPackageName();
		return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
				+ appName));

	}

	/**
	 * Vao ypdate
	 *
	 * @param context
	 */
	public static void gotoMarket(Context context, String paka) {
		String appName = paka;
		try {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("market://details?id=" + appName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://play.google.com/store/apps/details?id="
							+ appName)));
		}

	}

	/**
	 * Function to change progress to timer
	 *
	 * @param progress
	 *            -
	 * @param totalDuration
	 *            returns current duration in milliseconds
	 * */
	public static int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / Constants.ONE_SECOND);
		currentDuration = (int) ((((double) progress) / 100) * totalDuration);
		return currentDuration * Constants.ONE_SECOND;
	}

	/**
	 * Function to convert milliseconds time to Timer Format
	 * Hours:Minutes:Seconds
	 * */
	public static String milliSecondsToTimer(long milliseconds) {

		// Convert total duration into time
		int hours = (int) (milliseconds / (Constants.ONE_SECOND * 60 * 60));
		int minutes = (int) (milliseconds % (Constants.ONE_SECOND * 60 * 60))
				/ (Constants.ONE_SECOND * 60);
		int seconds = (int) ((milliseconds % (Constants.ONE_SECOND * 60 * 60))
				% (Constants.ONE_SECOND * 60) / Constants.ONE_SECOND);
		StringBuilder sb = new StringBuilder();
		if (hours > 0) {
			sb.append(twoDigit(hours)).append(':');
		}
		sb.append(twoDigit(minutes)).append(':');
		sb.append(twoDigit(seconds));
		return sb.toString();
	}

	static public String twoDigit(int d) {
		NumberFormat formatter = new DecimalFormat("#00");
		return formatter.format(d);
	}

	public static int getProgressPercentage(long currentDuration,
											long totalDuration) {
		Double percentage = (double) 0;

		long currentSeconds = (int) (currentDuration / Constants.ONE_SECOND);
		long totalSeconds = (int) (totalDuration / Constants.ONE_SECOND);

		// calculating percentage
		percentage = (((double) currentSeconds) / totalSeconds) * 100;

		// return percentage
		return percentage.intValue();
	}


	public static String getIMEI(Context context) {
		try {
			SharedPref pref = new SharedPref(context);
			String tmDevice = pref.getString(Constants.GETIMEI, "");
			if (!tmDevice.equals("")) {
				return tmDevice;
			}
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			tmDevice = "" + tm.getDeviceId();
			if (tmDevice == null || tmDevice.equals("000000000000000") || tmDevice.equals("null")) {
				WifiManager wifiMan = (WifiManager) context
						.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInf = wifiMan.getConnectionInfo();
				String macAddr = wifiInf.getMacAddress();
				Log.i("Utilities.getIMEI()", "gianglv3---->MAC " + macAddr);
				return macAddr;
			}

			Log.i("Utilities.getIMEI()", "gianglv3---->Imei " + tmDevice);
			pref.putString(Constants.GETIMEI, tmDevice);
			return tmDevice;
		} catch (Exception e) {
			e.printStackTrace();
			String androidId = ""
					+ Settings.Secure.getString(
					context.getContentResolver(),
					Settings.Secure.ANDROID_ID);
			Log.i("Utilities.getIMEI() error", e.getMessage() + "/ gianglv3----> " + androidId);
			return androidId;
		}
	}

	public static String getMac(Context context) {// ANDROID MAC WIFI
		String base = "com.viettel.tinngan";
		SharedPref pref = new SharedPref(context);
		String getMac = pref.getString(Constants.GETMAC, "");
		if (!getMac.equals("")) {

			return getMac;
		}
		try {
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			String macAddr = "";
			if (wifiManager.isWifiEnabled()) {
				// WIFI ALREADY ENABLED. GRAB THE MAC ADDRESS HERE
				WifiInfo info = wifiManager.getConnectionInfo();
				macAddr = info.getMacAddress();

				// NOW DISABLE IT AGAIN
				// wifiManager.setWifiEnabled(true);
			} else {
				// ENABLE THE WIFI FIRST
				wifiManager.setWifiEnabled(true);

				// WIFI IS NOW ENABLED. GRAB THE MAC ADDRESS HERE
				WifiInfo info = wifiManager.getConnectionInfo();
				macAddr = info.getMacAddress();

				// NOW DISABLE IT AGAIN
				wifiManager.setWifiEnabled(false);
			}
			base = macAddr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.i("Utilities.getDeviceID()", "MAC " + base);
		getMac = base;
		pref.putString(Constants.GETMAC, getMac);
		return getMac;
	}

	/**
	 * Get IP address from first non-localhost interface
	 *
	 * @param ipv4
	 *            true=return ipv4, false=return ipv6
	 * @return address or empty string
	 */
	@SuppressLint("DefaultLocale")
	public static String getIPAddress(boolean useIPv4) {
		try {
			List<NetworkInterface> interfaces = Collections
					.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf
						.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						if (useIPv4) {
							if (isIPv4)
								return sAddr;
						} else {
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 port
								// suffix
								return delim < 0 ? sAddr : sAddr.substring(0,
										delim);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}

	// lam cho sdt co dang 84xxxxxxxxx
	public static String fixPhoneNumbTo84(String str) {
		if (str == null || str.equals("") || str.length() < 3)
			return "";

		String x = "0123456789";
		for (int i = 0; i < str.length(); i++) {

			if (x.indexOf("" + str.charAt(i)) < 0) {

				str = str.replace("" + str.charAt(i), "");
				i--;
			}
		}

		if (str.startsWith("084")) {
			str = str.substring(1);
		} else if (str.startsWith("0")) {
			str = "84" + str.substring(1);
		} else if (!str.startsWith("84")) {
			str = "84" + str;
		}

		return str.trim();
	}

	public static String fixPhoneNumb(String str) {
		String fixPhoneNumbTo84 = fixPhoneNumbTo84(str);
		if (fixPhoneNumbTo84.length() < 3) {
			return "";
		}

		return fixPhoneNumbTo84.substring(2);
	}

	public static String fixPhoneNumbTo0(String str) {
		String fixPhoneNumb = fixPhoneNumb(str);

		return "0" + fixPhoneNumb;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String toMD5(String str) {
		String hashtext = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(Charset.forName("UTF8")));
			final byte[] resultByte = messageDigest.digest();
			BigInteger bigInt = new BigInteger(1, resultByte);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashtext;
	}

	public static void saveFavoriteAndLike(String json, String filename) {
		String path = android.os.Environment.getExternalStorageDirectory()
				+ "/" + Constants.ROOT_FOLDER + "/";

		File createFolder = new File(path);
		if (!createFolder.exists())
			createFolder.mkdirs();

		try {
			// Create file
			Log.i("Utilities.saveFavorite()", "GiangLV----> " + path);
			FileWriter fstream = new FileWriter(path + "/" + filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(json);
			// Close the output stream
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static String getFaveoriteAndLike(String filename) {
		String path = android.os.Environment.getExternalStorageDirectory()
				+ "/" + Constants.ROOT_FOLDER + "/" + filename;

		String line;
		String result = "";
		File f = new File(path);
		if (!f.exists()) {
			return result;
		}
		try {
			String name = path;
			Log.i("Utilities.getFaveorite()", "GiangLV----> " + name);
			FileReader file = new FileReader(name);
			BufferedReader in = new BufferedReader(file);

			while ((line = in.readLine()) != null) {
				result = result + line;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Luu cache
	 *
	 * @param str
	 *            : noi dung
	 * @param filename
	 *            : ten file
	 */
	public static void saveCache(String str, String filename) {
		String path = android.os.Environment.getExternalStorageDirectory()
				+ "/" + Constants.ROOT_FOLDER + "/" + Constants.CACHE_FOLDER
				+ "/";

		File createFolder = new File(path);
		if (!createFolder.exists())
			createFolder.mkdirs();

		try {
			// Create file
			Log.i("Utilities.writeToCache()", "GiangLV----> " + path);
			FileWriter fstream = new FileWriter(path + "/" + filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(str);
			// Close the output stream
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Doc cache
	 *
	 * @param filename
	 * @return
	 */
	public static String getCache(String filename) {
		String path = android.os.Environment.getExternalStorageDirectory()
				+ "/" + Constants.ROOT_FOLDER + "/" + Constants.CACHE_FOLDER
				+ "/" + filename;

		String line;
		String result = "";
		File f = new File(path);
		if (!f.exists()) {
			return result;
		}
		try {
			String name = path;
			Log.i("Utilities.readFileCache()", "GiangLV----> " + name);
			FileReader file = new FileReader(name);
			BufferedReader in = new BufferedReader(file);

			while ((line = in.readLine()) != null) {
				result = result + line;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}

	public boolean appInstalledOrNot(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}


	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public static String getAppPackage(Context context) {

		return context.getPackageName();
	}


	public static String getDirectionsUrl(String origin, String dest) {

		// Origin of route
		String str_origin = "origin=" + origin;

		// Destination of route
		String str_dest = "destination=" + dest;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	public static Bitmap convertStringTobitmap(String encodedImage) {
		byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		return decodedByte;
	}

	
	public static void showOnMap(Context context, String lat, String lng,String labelName) {
		try {
			Log.i("TAG","SHOW ON MAP "+labelName);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+lat+","+lng+"?q="+lat+","+lng+"(" + Uri.encode(labelName)+")"));
		context.startActivity(intent);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void gotodriectMap(Context contex, String lat, String lng,
			 String mTitle) {
		try {

			String mapkace = "com.google.android.apps.maps";
			String geoUri = "http://maps.google.com/maps?daddr=" + lat + ","
					+ lng ;
			Log.i("gotodriectMap", "Driect Location " + lat + "/" + lng + " : "
					+ "/ " + geoUri);
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));

			if (gotoApps(contex, mapkace)) {
				i.setPackage(mapkace);
			}
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			contex.startActivity(i);
			;

			// Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," +
			// lng);
			// Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
			// mapIntent.setPackage("com.google.android.apps.maps");
			// contex.startActivity(mapIntent);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static String convert(String org) {
		// convert to VNese no sign. @haidh 2008
		char arrChar[] = org.toCharArray();
		char result[] = new char[arrChar.length];
		for (int i = 0; i < arrChar.length; i++) {
			switch (arrChar[i]) {
			case '\u00E1':
			case '\u00E0':
			case '\u1EA3':
			case '\u00E3':
			case '\u1EA1':
			case '\u0103':
			case '\u1EAF':
			case '\u1EB1':
			case '\u1EB3':
			case '\u1EB5':
			case '\u1EB7':
			case '\u00E2':
			case '\u1EA5':
			case '\u1EA7':
			case '\u1EA9':
			case '\u1EAB':
			case '\u1EAD':
			case '\u0203':
			case '\u01CE': {
				result[i] = 'a';
				break;
			}
			case '\u00E9':
			case '\u00E8':
			case '\u1EBB':
			case '\u1EBD':
			case '\u1EB9':
			case '\u00EA':
			case '\u1EBF':
			case '\u1EC1':
			case '\u1EC3':
			case '\u1EC5':
			case '\u1EC7':
			case '\u0207': {
				result[i] = 'e';
				break;
			}
			case '\u00ED':
			case '\u00EC':
			case '\u1EC9':
			case '\u0129':
			case '\u1ECB': {
				result[i] = 'i';
				break;
			}
			case '\u00F3':
			case '\u00F2':
			case '\u1ECF':
			case '\u00F5':
			case '\u1ECD':
			case '\u00F4':
			case '\u1ED1':
			case '\u1ED3':
			case '\u1ED5':
			case '\u1ED7':
			case '\u1ED9':
			case '\u01A1':
			case '\u1EDB':
			case '\u1EDD':
			case '\u1EDF':
			case '\u1EE1':
			case '\u1EE3':
			case '\u020F': {
				result[i] = 'o';
				break;
			}
			case '\u00FA':
			case '\u00F9':
			case '\u1EE7':
			case '\u0169':
			case '\u1EE5':
			case '\u01B0':
			case '\u1EE9':
			case '\u1EEB':
			case '\u1EED':
			case '\u1EEF':
			case '\u1EF1': {
				result[i] = 'u';
				break;
			}
			case '\u00FD':
			case '\u1EF3':
			case '\u1EF7':
			case '\u1EF9':
			case '\u1EF5': {
				result[i] = 'y';
				break;
			}
			case '\u0111': {
				result[i] = 'd';
				break;
			}
			case '\u00C1':
			case '\u00C0':
			case '\u1EA2':
			case '\u00C3':
			case '\u1EA0':
			case '\u0102':
			case '\u1EAE':
			case '\u1EB0':
			case '\u1EB2':
			case '\u1EB4':
			case '\u1EB6':
			case '\u00C2':
			case '\u1EA4':
			case '\u1EA6':
			case '\u1EA8':
			case '\u1EAA':
			case '\u1EAC':
			case '\u0202':
			case '\u01CD': {
				result[i] = 'A';
				break;
			}
			case '\u00C9':
			case '\u00C8':
			case '\u1EBA':
			case '\u1EBC':
			case '\u1EB8':
			case '\u00CA':
			case '\u1EBE':
			case '\u1EC0':
			case '\u1EC2':
			case '\u1EC4':
			case '\u1EC6':
			case '\u0206': {
				result[i] = 'E';
				break;
			}
			case '\u00CD':
			case '\u00CC':
			case '\u1EC8':
			case '\u0128':
			case '\u1ECA': {
				result[i] = 'I';
				break;
			}
			case '\u00D3':
			case '\u00D2':
			case '\u1ECE':
			case '\u00D5':
			case '\u1ECC':
			case '\u00D4':
			case '\u1ED0':
			case '\u1ED2':
			case '\u1ED4':
			case '\u1ED6':
			case '\u1ED8':
			case '\u01A0':
			case '\u1EDA':
			case '\u1EDC':
			case '\u1EDE':
			case '\u1EE0':
			case '\u1EE2':
			case '\u020E': {
				result[i] = 'O';
				break;
			}
			case '\u00DA':
			case '\u00D9':
			case '\u1EE6':
			case '\u0168':
			case '\u1EE4':
			case '\u01AF':
			case '\u1EE8':
			case '\u1EEA':
			case '\u1EEC':
			case '\u1EEE':
			case '\u1EF0': {
				result[i] = 'U';
				break;
			}

			case '\u00DD':
			case '\u1EF2':
			case '\u1EF6':
			case '\u1EF8':
			case '\u1EF4': {
				result[i] = 'Y';
				break;
			}
			case '\u0110':
			case '\u00D0':
			case '\u0089': {
				result[i] = 'D';
				break;
			}
			default:
				result[i] = arrChar[i];
			}
		}
		return new String(result);
	}

	public static int countLine(TextView tv, final String text,
			final int deviceWidth) {
		final String check = text.replace(" ", "_");
		float textWidth = tv.getPaint().measureText(check);

		int line = (int) (textWidth / deviceWidth);

		return line + 1;
	}


	
	public static boolean checNokGPS() {

		LocationManager lm = null;
		boolean gps_enabled = false, network_enabled = false;
		if (lm == null)
			lm = (LocationManager) ApplicationFoodBook.getInstance()
					.getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = lm
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}

		if (!gps_enabled && !network_enabled) {
			return true;
		}
		return false;
	}

	public static boolean checkAndGoGPS(final Context context) {

		LocationManager lm = null;
		boolean gps_enabled = false, network_enabled = false;
		if (lm == null)
			lm = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = lm
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}

		if (!gps_enabled && !network_enabled) {
			try {
				AlertDialog.Builder dialog = new AlertDialog.Builder(context);
				dialog.setMessage(context.getResources().getString(
						R.string.gps_network_not_enabled));
				dialog.setPositiveButton(
						context.getResources().getString(
								R.string.open_location_settings),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								// TODO Auto-generated method stub
								Intent myIntent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								context.startActivity(myIntent);
								// get gps
							}
						});
				dialog.setNegativeButton(context.getString(R.string.btn_boqua),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								// TODO Auto-generated method stub

							}
						});
				dialog.show();
			} catch (Exception e) {
				e.printStackTrace();;
			}
			return true;
		}
		return false;
	}



	public static void setUnderLine(TextView mTextView) {

		String mystring = new String(mTextView.getText().toString());
		SpannableString content = new SpannableString(mystring);
		content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
		mTextView.setText(content);
	}

	public static void setTexHtml(TextView mTextView) {

		String mystring = new String(mTextView.getText().toString());
		try {
			mTextView.setText(Html.fromHtml(mystring));
		} catch (Exception e){

		}
	}


	public static void setUnderLine(TextView mTextView,String text) {

		String mystring = new String(text);
		SpannableString content = new SpannableString(mystring);
		content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
		mTextView.setText(content);
	}



	public  static boolean isVersionAndroidOver10() {
		if (Build.VERSION.SDK_INT > 10) {
		return true;
		}
		return  false;
	}


	public  static boolean isVersionAndroidOver5() {
		if (Build.VERSION.SDK_INT > 19) {
			return true;
		}
		return  false;
	}

	public static void showBadge(int badgeCount) {
		ShortcutBadger.with(ApplicationFoodBook.getInstance()).count(badgeCount);
	}
	/** Returns the consumer friendly device name */
	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		}

		return capitalize(manufacturer) + " " + model;
	}
	private static String capitalize(String str) {
		if (TextUtils.isEmpty(str)) {
			return str;
		}
		char[] arr = str.toCharArray();
		boolean capitalizeNext = true;
		String phrase = "";
		for (char c : arr) {
			if (capitalizeNext && Character.isLetter(c)) {
				phrase += Character.toUpperCase(c);
				capitalizeNext = false;
				continue;
			} else if (Character.isWhitespace(c)) {
				capitalizeNext = true;
			}
			phrase += c;
		}
		return phrase;
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}

	public static String encrypt(String input) {
		// Simple encryption, not very strong!
		return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
	}

	public static String decrypt(String input) {
		return new String(Base64.decode(input, Base64.DEFAULT));
	}

	public static String releaseOrDebug() {

		if (Constants.IS_LOG) return  "Debug";
		else return  "Release";
	}


	public static Intent getOpenFacebookIntent(Context context) {

		try {
			context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
			return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+Constants.FANPAGEID));
		} catch (Exception e) {
			e.printStackTrace();
			return new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FANPAGE));
		}
	}
}
