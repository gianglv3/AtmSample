package com.ipos.restaurant.model;

import com.google.gson.annotations.SerializedName;

public class DmPush extends AbsModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2467895233163397691L;
	public static final int PUSH_LOCAL = -1;//update
	public static final int PUSH_UPDATE = 1;//update
	public static final int PUSH_IPOS = 2;//NHAHANG
	public static final int PUSH_ITEM = 3;//ITEM
	public static final int PUSH_PROMO = 4;//PROMO
	public static final int PUSH_UPDATE_STATUS_ORDER_ONLINE = 5;//
	public static final int PUSH_TYPE_RATE_ORDER = 6;//
	public static final int PUSH_TYPE_MESSAGE = 7;
	public static final int PUSH_TYPE_OPEN_URL = 8;
	public static final int PUSH_TYPE_BOOKING_REMINDER = 9;
	public static final int PUSH_TYPE_UPDATE_STATUS_ORDER_ONLINE_WITH_URL = 10;
	public static final int PUSH_TYPE_VOUCHER = 11;
	
	@SerializedName("Id")
	private long id;

	// Cac truong trong ban tin Push co ban

	@SerializedName("Push_Type")
	private int push_type;



	@SerializedName("Pos_Name")
	private String posName="";

	@SerializedName("Title")
	private String title;

	@SerializedName("Content_Simple")
	private String content_simple;

	@SerializedName("Image_Path")
	private String image_path;

	@SerializedName("Image_Path_Thumb")
	private String image_path_Thumb;

	// Cac truong lay ra trong push_detail

	@SerializedName("Content_Full")
	private String content_full;

	@SerializedName("Order_Id")
	private String order_id;

	@SerializedName("Tran_Id")
	private String tranId;

	@SerializedName("Url")
	private String url;

	// A NEW POS, new Promotion, new item

	@SerializedName("Pos_id")
	private String posId;

	@SerializedName("Pos_Parent")
	private String posParent="";

	@SerializedName("Promotion_id")
	private long promotion_id;

	@SerializedName("Item_id")
	private long item_id;
	private boolean haveShareLink;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPush_type() {
		return push_type;
	}

	public void setPush_type(int push_type) {
		this.push_type = push_type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent_simple() {
		return content_simple;
	}

	public void setContent_simple(String content_simple) {
		this.content_simple = content_simple;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getImage_path_Thumb() {
		return image_path_Thumb;
	}

	public void setImage_path_Thumb(String image_path_Thumb) {
		this.image_path_Thumb = image_path_Thumb;
	}

	public String getContent_full() {
		return content_full;
	}

	public void setContent_full(String content_full) {
		this.content_full = content_full;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public long getPromotionId() {
		return promotion_id;
	}

	public void setPromotionId(long promotion_id) {
		this.promotion_id = promotion_id;
	}

	public long getItemId() {
		return item_id;
	}

	public void setItemId(long item_id) {
		this.item_id = item_id;
	}

	public String getOrderId() {
		return order_id;
	}

	public void setOrderId(String order_id) {
		this.order_id = order_id;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public boolean hasUrl() {
		if (url!=null&&url.contains("http")) return true;
		return false;
	}

	public String getPosParent() {
		return posParent;
	}

	public void setPosParent(String posParent) {
		this.posParent = posParent;
	}

	public void setHaveShareLink(boolean haveShareLink) {
		this.haveShareLink = haveShareLink;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	@Override
	public String toString() {
		return "DmPush{" +
				"id=" + id +
				", push_type=" + push_type +
				", posName='" + posName + '\'' +
				", title='" + title + '\'' +
				", content_simple='" + content_simple + '\'' +
				", image_path='" + image_path + '\'' +
				", image_path_Thumb='" + image_path_Thumb + '\'' +
				", content_full='" + content_full + '\'' +
				", order_id='" + order_id + '\'' +
				", url='" + url + '\'' +
				", posId='" + posId + '\'' +
				", promotion_id=" + promotion_id +
				", item_id=" + item_id +
				'}';
	}

	public boolean isHaveShareLink() {
		if (url!=null&&url.contains("http")) return true;
		return  false;
	}
}
