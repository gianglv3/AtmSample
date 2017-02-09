package com.ipos.restaurant.bussiness;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.ipos.restaurant.ApplicationFoodBook;
import com.ipos.restaurant.Constants;
import com.ipos.restaurant.R;
import com.ipos.restaurant.paser.RestString;
import com.ipos.restaurant.restful.WSRestAuth;
import com.ipos.restaurant.util.Log;
import com.ipos.restaurant.util.SharedPref;
import com.ipos.restaurant.util.ToastUtil;

import org.json.JSONObject;

import java.util.Arrays;

public class ShareFacebookBussiness {
	// permison FB
	private static final String PUBLISH_PERMISSION = "publish_actions";
	private static final String PROFILE_PERMISSION = "public_profile";

	private static final String BIRTHDAY_PERMISSION = "user_birthday";
	protected static final int PENDING_NONE = 0;
	protected static final int PENDING_SHARE_POS = 1;
	protected static final int PENDING_SHARE_ORTHER_CONTENT = 2;// dung comment
 
	protected static final int PENDING_INVITE_FB = 5;// dung cho Event share FB
	protected static final int PENDING_INSTALL_APP = 6;// dung cho Event share
														// FB
	protected static final int PENDING_GET_INFO_USER = 7;// dung cho Event share
															// FB

	protected int pendingAction = PENDING_NONE;
	private Bundle posBundle;
	private ProgressDialog dialog;
	private OnConectFace mLisConectFace;

	private Activity mActivity;
	private String TAG = "ShareFacebookBussiness";
	private CallbackManager callbackManager;

	private ShareDialog shareDialog;

	public ShareFacebookBussiness(Activity app, Bundle savedInstanceState) {
		this.mActivity = app;
		new SharedPref(mActivity);
		callbackManager = CallbackManager.Factory.create();
		// fbInitOnCreat(savedInstanceState);
	}

	public void shareFacebook(String message, String link) {
		pendingAction = PENDING_SHARE_POS;
		
		posBundle = new Bundle();
		posBundle.putString("message", message);
		posBundle.putString("link", link);
		sharePos();
	}

	public void synInFoUser() {
		pendingAction = PENDING_GET_INFO_USER;
		getInfoFacebook();
	}

	/**
	 * share fb :
	 * 
	 * @param am
	 *            : bai hat
	 * @param mess
	 *            :message
	 */
	public void shareOtherContent(String message, String link) {

		posBundle = new Bundle();
		posBundle.putString("message", message);
		posBundle.putString("link", link);
		pendingAction = PENDING_SHARE_ORTHER_CONTENT;
		shareOtherContent();

	}

	public void showInviteFriend() {
		pendingAction = PENDING_INVITE_FB;
		Log.i("ShareFacebookBussiness.showInviteFriend()", "INVITE ");
		inviteFriend();

	}

	public void setChangeAvatarUtil(ChangeAvatarBussiness mUtil) {
	}

	public void setOnConectFace(OnConectFace mConectFace) {
		mLisConectFace = mConectFace;
	}

	private AccessToken getAccessToken(boolean public_action) {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		Log.i("ShareFacebookBussiness.onLoginFBSuccess()", "get token "
				+ accessToken);
		if (accessToken == null) {
			if (public_action) {
				registerLoginWithPublicAction();
			} else {
				registerLogin();
			}
			return accessToken;
		}

		if (public_action
				&& accessToken.getPermissions().contains(PUBLISH_PERMISSION)) {
			registerLoginWithPublicAction();

			return accessToken;
		}
		return accessToken;
	}

	protected void sharePos() {

		AccessToken toke = getAccessToken(false);

		Log.i("ShareFacebookBussiness.shareVideoAndSong()", "TOKEN NULL ");

		if (toke == null) {

			return;
		}
		shareDialog = new ShareDialog(mActivity);
		// this part is optional
		shareDialog.registerCallback(callbackManager,
				new FacebookCallback<Sharer.Result>() {

					@Override
					public void onSuccess(Result result) {

						if (dialog != null) {
							dialog.dismiss();
						}
						if (mLisConectFace!=null) {
							mLisConectFace.OnFBSuccess(null);
						}

					}

					@Override
					public void onError(FacebookException error) {
						ToastUtil.makeText(mActivity, R.string.fb_share_fail,
								Toast.LENGTH_LONG);

						if (mLisConectFace!=null) {
							mLisConectFace.OnError();
						}
					}

					@Override
					public void onCancel() {
						 
					}

				});
		String link =posBundle.getString("link");
		
		if (link==null||"".equals(link)) {
			
			link= Constants.URL_HOME;
		}


		
		if (ShareDialog.canShow(ShareLinkContent.class)) {
			ShareLinkContent linkContent = new ShareLinkContent.Builder()

			.setContentUrl(Uri.parse(link)).build();

			shareDialog.show(linkContent);
		} else {
//			WebDialog mWeb  = new WebDialog(mActivity, link);
//			mWeb.show();
		}

		validateSynFacebook();
	}

	protected void shareOtherContent() {

		AccessToken toke = getAccessToken(true);

		if (toke == null) {
			return;
		}




		GraphRequest.Callback callback = new GraphRequest.Callback() {
			public void onCompleted(GraphResponse response) {
				FacebookRequestError error = response.getError();
				if (error != null) {
					Log.i("ShareFacebookBussiness.shareOtherContent().",
							"error Share----> " + error.getErrorMessage());
					ToastUtil.makeText(mActivity,
							"" + mActivity.getString(R.string.fb_share_fail),
							Toast.LENGTH_LONG);
					if (mLisConectFace != null) {
						mLisConectFace.OnError();
					}
				} else {

					ToastUtil.makeText(mActivity,
							"" + mActivity.getString(R.string.fb_share_done),
							Toast.LENGTH_LONG);

					if (mLisConectFace != null) {
						mLisConectFace.OnFBSuccess(null);
					}
				}
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		};
		dialog = ProgressDialog.show(mActivity, "",
				"" + mActivity.getString(R.string.loading), true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(true);
		GraphRequest request = new GraphRequest(toke, "me/feed", posBundle,
				HttpMethod.POST, callback);
		GraphRequestAsyncTask task = new GraphRequestAsyncTask(request);
		task.execute();
		// TODO: syn FB
		validateSynFacebook();
	}

	private void getInfoFacebook() {
		AccessToken toke = getAccessToken(false);

		if (toke == null) {
			return;
		}
		Log.i("ShareFacebookBussiness.onLoginFBSuccess()",
				"get info start request");
		GraphRequest request = GraphRequest.newMeRequest(toke,
				new GraphRequest.GraphJSONObjectCallback() {
					@Override
					public void onCompleted(JSONObject jsonObject,
							GraphResponse graphResponse) {

						FacebookRequestError error = graphResponse.getError();
						if (error != null) {
							ToastUtil.makeText(mActivity,
									R.string.fb_share_fail);
						} else {

							// if (changeAvatar != null) {
							// changeAvatar.synAvatar(img_value);
							// }
							if (mLisConectFace != null) {
								mLisConectFace.OnFBSuccess(jsonObject);
							}

							synFbId(jsonObject);

							Log.i("ShareFacebookBussiness.onLoginFBSuccess()",
									"complete "+jsonObject);
						}

					}
				});
		Bundle parameters = new Bundle();
		parameters.putString("fields", "id,name,link,birthday, gender");
		request.setParameters(parameters);
		request.executeAsync();
	}

	private void inviteFriend() {

		// ToastUtil.makeText(getApplicationContext(), "showDialogInvite");
		String appLinkUrl = "", previewImageUrl = "";

		appLinkUrl = "https://fb.me/" + mActivity.getString(R.string.fb_app_id);
		// previewImageUrl =
		// "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xta1/t31.0-8/s720x720/11262395_1425756231077908_2952001743303617657_o.jpg";

		if (AppInviteDialog.canShow()) {
			AppInviteContent content = new AppInviteContent.Builder()
					.setApplinkUrl(appLinkUrl)
					.setPreviewImageUrl(previewImageUrl).build();
			AppInviteDialog.show(mActivity, content);

		}

	}

	private void onLoginFBSuccess() {
		Log.i("ShareFacebookBussiness.onLoginFBSuccess()", "share share "
				+ pendingAction);
		switch (pendingAction) {
		case PENDING_GET_INFO_USER:
			getInfoFacebook();
			break;
		case PENDING_INVITE_FB:
			inviteFriend();
			break;
		case PENDING_SHARE_POS:
			sharePos();
			break;

		case PENDING_SHARE_ORTHER_CONTENT:
			shareOtherContent();
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode, resultCode, data);
		// Session.getActiveSession().onActivityResult(mActivity, requestCode,
		// resultCode, data);
	}

	public void onSaveInstanceState(Bundle outState) {
		try {

			// / Session session = Session.getActiveSession();
			// / Session.saveSession(session, outState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onStart() {
		// Session.getActiveSession().addCallback(statusCallbackOnLogin);
	}

	public void onStop() {
		// Session.getActiveSession().removeCallback(statusCallbackOnLogin);
	}

	private void registerLoginWithPublicAction() {
		Log.i(TAG, "shareContentToFacebook do login to publish");
		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						Log.i(TAG, "onSuccess " + loginResult);
						onLoginFBSuccess();
					}

					@Override
					public void onCancel() {
						Log.i(TAG, "onCancel ");
					}

					@Override
					public void onError(FacebookException exception) {
						Log.i(TAG, "onError " + exception.getMessage());
						if (mLisConectFace != null) {
							mLisConectFace.OnError();
						}
					}
				});
		LoginManager.getInstance().logInWithPublishPermissions(mActivity,
				Arrays.asList(PUBLISH_PERMISSION));
	}

	private void registerLogin() {
		Log.i(TAG, "shareContentToFacebook do login to publish");
		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						Log.i(TAG, "onSuccess " + loginResult);
						onLoginFBSuccess();
					}

					@Override
					public void onCancel() {
						Log.i(TAG, "onCancel ");
					}

					@Override
					public void onError(FacebookException exception) {
						Log.i(TAG, "onCancel " + exception.getMessage());
					}
				});
		LoginManager.getInstance().logInWithReadPermissions(mActivity,
				Arrays.asList(PROFILE_PERMISSION, BIRTHDAY_PERMISSION));
	}

	/**
	 * get Facebook ID -> neu la Rong thi SÃ�
	 * 
	 * @param
	 */
	private void validateSynFacebook() {

		AccessToken toke = getAccessToken(false);

		if (toke == null) {
			return;
		}
		Log.i("ShareFacebookBussiness.onLoginFBSuccess()",
				"get info start request");
		GraphRequest request = GraphRequest.newMeRequest(toke,
				new GraphRequest.GraphJSONObjectCallback() {
					@Override
					public void onCompleted(JSONObject jsonObject,
											GraphResponse graphResponse) {

						FacebookRequestError error = graphResponse.getError();
						if (error != null) {
							ToastUtil.makeText(mActivity,
									R.string.fb_share_fail);
						} else {



							synFbId(jsonObject);

							Log.i("ShareFacebookBussiness.onLoginFBSuccess()",
									"complete "+jsonObject);
						}

					}
				});
		Bundle parameters = new Bundle();
		parameters.putString("fields", "id,name,link,birthday, gender");
		request.setParameters(parameters);
		request.executeAsync();

	}

	public interface OnConectFace {

		void OnFBSuccess(JSONObject json);

		void OnError();
	}

	private void synFbId(JSONObject jsonObject) {

		try {

//			String userId = jsonObject
//					.getString("id");
//
//			DmMember user = ApplicationFoodBook.getInstance().getmDmMember();
//
//			user.setFacebookId(userId);
//
//			String json =new Gson().toJson(user);
//
//			WSRestAuth rest = new WSRestAuth(mActivity);
//
//			Log.d(TAG,"FB ID "+userId);
//			rest.update(json, new Response.Listener<RestString>() {
//
//				@Override
//				public void onResponse(RestString response) {
//
////					if (response.getData(RestaurantActivty.this) != null) {
////
////						ToastUtil.makeText(RestaurantActivty.this, R.string.update_succ);
////					} else {
////						ToastUtil.makeText(RestaurantActivty.this, response.getError()
////								.getMessage());
////					}
//					Log.i("respons smstoken", "token " + response);
////					if (dialog!=null)
////						dialog.dismiss();
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Log.i("Error", "error " + error.getMessage());
//				//	ToastUtil.makeText(RestaurantActivty.this, R.string.error_network);
////					if (dialog!=null)
////						dialog.dismiss();
//				}
//			});
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void connectFace() {

		//
		// AccessToken toke = getAccessToken(false);
		//
		// if (toke == null) {
		// return;
		// }
		//
		// GraphRequest request = GraphRequest.newMeRequest(toke,
		// new GraphRequest.GraphJSONObjectCallback() {
		// @Override
		// public void onCompleted(JSONObject jsonObject,
		// GraphResponse graphResponse) {
		//
		// FacebookRequestError error = graphResponse
		// .getError();
		// if (error != null) {
		// if (mLisConectFace!=null) {
		// mLisConectFace.OnError();
		// }
		// ToastUtil.makeText(mActivity, R.string.error);
		// } else {
		//
		// try {
		// String userId = (String) jsonObject
		// .getString("id");
		// String email = Utilities
		// .getGoogleEmail(mActivity);
		//
		//
		// AutoSynFacebook.syncFacebookAction(
		// mActivity, userId, email);
		// if (mLisConectFace!=null) {
		// mLisConectFace.OnFBSuccess();
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		//
		// }
		// });
		// Bundle parameters = new Bundle();
		// parameters.putString("fields", "id,name,link,birthday, gender");
		// request.setParameters(parameters);
		// request.executeAsync();
	}
}
