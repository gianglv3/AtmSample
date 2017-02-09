package com.ipos.restaurant.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private static final int EXCEPTION = 100;
	private static final int REQUEST_PARAM_EXCEPTION = 300;
	private static final int REQUEST_TOO_MANY_EXCEPTION = 301;

	private static final int ACCESS_TOKEN_INVALID = 1400;
	private static final int APPLICATION_TOKEN_INVALID = 1401;
	private static final int ACCOUNT_INVALID = 1402;
	private static final int ACCOUNT_LOGIN_FAIL = 1403;
	public static final int ACCOUNT_LOGIN_NOT_EXIST = 1404;
	//foodbook order
	private static final int REQUEST_LOCKED_BY_POS = 1501;
	private static final int TIME_IS_NOT_AVAIABLE = 1502;

	@SerializedName("message")
	protected String message;

	@SerializedName("code")
	protected int code;

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
//		//code = ErrorMessage.REQUEST_TOO_MANY_EXCEPTION;
//		switch (code) {
//			case ErrorMessage.EXCEPTION:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.exception);
//				break;
//			case ErrorMessage.REQUEST_PARAM_EXCEPTION:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.request_param_exception);
//				break;
//			case ErrorMessage.REQUEST_TOO_MANY_EXCEPTION:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.request_too_many_exception);
//				break;
//			case ErrorMessage.ACCESS_TOKEN_INVALID:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.access_token_invalid);
//				break;
//			case ErrorMessage.APPLICATION_TOKEN_INVALID:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.application_token_invalid);
//				break;
//			case ErrorMessage.ACCOUNT_INVALID:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.account_invalid);
//				break;
//			case ErrorMessage.ACCOUNT_LOGIN_FAIL:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.account_login_fail);
//				break;
//			case ErrorMessage.REQUEST_LOCKED_BY_POS:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.request_locked_by_pos);
//				break;
//			case ErrorMessage.TIME_IS_NOT_AVAIABLE:
//				message = ApplicationFoodBook.getInstance().getResources().getString(R.string.time_is_not_avaiable);
//				break;
//			case ErrorMessage.ACCOUNT_LOGIN_NOT_EXIST:
//				message= ApplicationFoodBook.getInstance().getResources().getString(R.string.account_not_exists);
//				break;
//			default:
//				//message = ApplicationFoodBook.getInstance().getResources().getString(R.string.exception);
//				break;
//
//		}
//		Log.i("ErrorMessage", toString());
		return message;
	}

	@Override
	public String toString() {
		return "ErrorMessage{[message = " + message + ", code=" + code + "}";
	}

}
