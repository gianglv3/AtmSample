package com.ipos.restaurant.bussiness;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.FoodBookTracker;
import com.ipos.restaurant.GA;
import com.ipos.restaurant.R;
import com.ipos.restaurant.activities.NotificationActivity;
import com.ipos.restaurant.model.DmPush;
import com.ipos.restaurant.util.SharedPref;
import com.ipos.restaurant.util.Utilities;


public class NotificationBussiness {
	private PowerManager.WakeLock wakeLock;
	private NotificationManager nm;
	private Context mContext;
	public static int ID_LOCAL_PUSh_11h30 = 110;
	private AlarmManager am;
	private SharedPref pref;
	private ImageLoaderBussiness mImageloader;
	public NotificationBussiness(Context context) {
		this.mContext = context;
		pref = new SharedPref(mContext);
		nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		 am = (AlarmManager) mContext
					.getSystemService(Context.ALARM_SERVICE);
		 
		 ApplicationFoodBook app =(ApplicationFoodBook) context.getApplicationContext();
		 mImageloader=app.getImageLoader();
	}
	
//
//	@SuppressWarnings("deprecation")
//	public void runRegistimeLocal11h30() {
//		Date timeBroad = new Date();
//		timeBroad.setDate(timeBroad.getDate() + 1);
//		timeBroad.setHours(11);
//		timeBroad.setMinutes(30);
//		timeBroad.setSeconds(00);
//
//		Intent intent2 = new Intent(mContext, LocalNotifiReceiver.class);
//		intent2.putExtra(Constants.KEY_TYPE, ID_LOCAL_PUSh_11h30);
//		PendingIntent pi2 = PendingIntent.getBroadcast(mContext, 0, intent2, 0);
//
//		am.setRepeating(AlarmManager.RTC_WAKEUP, timeBroad.getTime(),
//				AlarmManager.INTERVAL_DAY, pi2);
//	}

	
	public void runPush(final DmPush item) {
			initRemoteView(item);
	}
	
	private void initRemoteView(DmPush item) {
		
//		RemoteViews views = new RemoteViews(mContext.getPackageName(),
//				R.layout.notification_local);
//
//		views.setTextViewText(R.id.textView, item.getTitle());
//		   if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
//	            Bitmap bitmapFromURL = mImageloader.getCache(item.getImage_path_Thumb());
//	            if(bitmapFromURL!=null){
//	                views.setImageViewBitmap(R.id.image, bitmapFromURL);
//	            }
//	        }else {
//	            views.setImageViewResource(R.id.image, R.drawable.ic_launcher);
//
//	        }
//		   genNotification(views, item);;
	}
	
	
	private void genNotification(RemoteViews views,DmPush item) {
	
	 

		Intent i = new Intent(mContext, NotificationActivity.class);
		i.putExtra(Constants.KEY_DATA,item);
//		switch (item.getPush_type()) {
//		case DmPush.PUSH_UPDATE_STATUS_ORDER_ONLINE:
//				i = new Intent(mContext, MeActivity.class);
//				i.putExtra(Constants.KEY_POSITION, MeActivity.TAB_HISTORY);
//				Intent intentBroad = new Intent();
//				intentBroad.setAction(Constants.ACTION_UPDATE_STATUS_FROM_PUSH);
//				mContext.sendBroadcast(intentBroad);
//				break;
//		case DmPush.PUSH_UPDATE:
//			i = Utilities.genIntentGoMarket(mContext);
//			break;
//		case DmPush.PUSH_IPOS:
//		case DmPush.PUSH_ITEM:
//		case DmPush.PUSH_PROMO:
//			 i = new Intent(mContext,RestaurantActivty.class);
//			 DmPos pos = new DmPos();
//			 pos.setId(item.getPos_id());
//			 pos.setImagePath(item.getImage_path_Thumb());;
//			 pos.setDescription(item.getTitle());
//			 i.putExtra(Constants.KEY_DATA, pos);
//			break;
//		case DmPush.PUSH_TYPE_RATE_ORDER:
//			i = new Intent(mContext, RateOrderActivity.class);
//			i.putExtra(Constants.KEY_DATA,item);
//			break;
//		case DmPush.PUSH_TYPE_UPDATE_STATUS_ORDER_ONLINE_WITH_URL:
//		case DmPush.PUSH_TYPE_OPEN_URL:
//				i = new Intent(mContext, WebViewActivity.class);
//				i.putExtra(Constants.KEY_DATA,item.getUrl());
//				break;
//		default:
//			break;
//		}

		PendingIntent detailsIntent = PendingIntent.getActivity(mContext,
				(int) System.currentTimeMillis(), i,
				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				mContext);

		builder.setTicker(item.getTitle());
		Uri alarmSound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		builder.setSound(alarmSound);
		builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		builder.setContentIntent(detailsIntent);
		builder.setContent(views);
		builder.setLights(Color.BLUE, 1500, 1000);
		builder.setSmallIcon(R.drawable.icon_notificaiton);
		builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
		acquireWakeLock(mContext);

		releaseWakeLock();

		Utilities.showBadge(1);
		FoodBookTracker.sendEvent(GA.NOTIFICATION,
				GA.NOTIFICATION_PUSH, item.getTitle(), (long) 0);
		nm.notify((int) item.getPush_type(), builder.build());
	}

	public void removeNotification(int id) {
		nm.cancel(id);
	}

	public void genNotificationLocal11h30() {
//		long timeCurrent =System.currentTimeMillis();
//		long  timeOnApp = pref.getLong(Constants.KEY_ON_APP, timeCurrent);
//
//		if (timeOnApp+Constants.ON_DAY >timeCurrent) {
//			Log.i("genNotificationLocal11h30 ", "genNotificationLocal11h30 " + timeCurrent + "/ " + timeOnApp);
//			return;
//		}
//
//		RemoteViews views = new RemoteViews(mContext.getPackageName(),
//				R.layout.notification_local);
//		String title = mContext.getString(R.string.noti_local_11h30);
//		views.setTextViewText(R.id.textView, title);
//
//		Intent i = new Intent(mContext, NotificationActivity.class);
//		DmPush item = new DmPush();
//		item.setTitle(title);
//		item.setPush_type(DmPush.PUSH_LOCAL);
//		i.putExtra(Constants.KEY_DATA,item);
//
//		PendingIntent detailsIntent = PendingIntent.getActivity(mContext,
//				(int) System.currentTimeMillis(), i,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//
//		NotificationCompat.Builder builder = new NotificationCompat.Builder(
//				mContext);
//
//		builder.setTicker(title);
//		Uri alarmSound = RingtoneManager
//				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//		builder.setSound(alarmSound);
//		builder.setAutoCancel(true);
//		builder.setWhen(System.currentTimeMillis());
//		builder.setContentIntent(detailsIntent);
//		builder.setContent(views);
//		builder.setLights(Color.BLUE, 1500, 1000);
//		builder.setSmallIcon(R.drawable.icon_notificaiton);
//		builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
//		acquireWakeLock(mContext);
//
//		releaseWakeLock();
//		FoodBookTracker.sendEvent(GA.NOTIFICATION,
//				GA.NOTIFICATION_PUSH, "Local " + title, (long) 0);
//		nm.notify(ID_LOCAL_PUSh_11h30, builder.build());
	}

//	public void getNotificationOrder(String token, long timeout, DmPos mDmPos) {
//
//
//
//		RemoteViews views = new RemoteViews(mContext.getPackageName(),
//				R.layout.notification_local);
//		String title = mContext.getString(R.string.notifi_order_offline);
//        title=title.replace("#time",""+(timeout/60));
//        title=title.replace("#token",token);
//		views.setTextViewText(R.id.textView, title);
//        views.setViewVisibility(R.id.time, View.VISIBLE);
//
//        String time =DateTimeUtil.formatTimeHHmm();
//        views.setTextViewText(R.id.time,time);
//
//		Intent i = new Intent(mContext, NotificationActivity.class);
//
//		DmPush item = new DmPush();
//		item.setTitle(title);
//        item.setPosId(mDmPos.getId());
//        item.setPosName(mDmPos.getPosName());
//		item.setPush_type(DmPush.PUSH_IPOS);
//		i.putExtra(Constants.KEY_DATA,item);
//
//		PendingIntent detailsIntent = PendingIntent.getActivity(mContext,
//				(int) System.currentTimeMillis(), i,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//
//		NotificationCompat.Builder builder = new NotificationCompat.Builder(
//				mContext);
//
//		builder.setTicker(title);
//		Uri alarmSound = RingtoneManager
//				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//		builder.setSound(alarmSound);
//		builder.setAutoCancel(true);
//		builder.setWhen(System.currentTimeMillis());
//		builder.setContentIntent(detailsIntent);
//		builder.setContent(views);
//		builder.setLights(Color.BLUE, 1500, 1000);
//		builder.setSmallIcon(R.drawable.icon_notificaiton);
//		builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
//
//		acquireWakeLock(mContext);
//
//		releaseWakeLock();
//		FoodBookTracker.sendEvent(GA.NOTIFICATION,
//				GA.NOTIFICATION_PUSH, "Local " + title, (long) 0);
//		nm.notify(token.hashCode(), builder.build());
//	}

	@SuppressLint("Wakelock")
	@SuppressWarnings("deprecation")
	public void acquireWakeLock(Context context) {

		if (wakeLock != null)
			wakeLock.release();

		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);

		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "WakeLock");

		wakeLock.acquire();
	}

	public void releaseWakeLock() {
		if (wakeLock != null)
			wakeLock.release();
		wakeLock = null;
	}


}
