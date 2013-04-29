package com.weibo.sdk.android.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Geo {

	// 经度坐标
	String longitube;
	// 维度坐标
	String latitude;
	// 所在城市的城市代码
	String city;
	// 所在省份的省份代码
	String province;
	// 所在城市的城市名称
	String city_name;
	// 所在省份的省份名称
	String province_name;
	// 所在的实际地址，可以为空
	String address;
	// 地址的汉语拼音。不是所有情况都会返回该字段
	String pinyin;
	// 更多信息。不是所有情况都会返回该字段
	String more;

	public Geo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Geo(String longitube, String latitude, String city, String province,
			String city_name, String province_name, String address) {
		super();
		this.longitube = longitube;
		this.latitude = latitude;
		this.city = city;
		this.province = province;
		this.city_name = city_name;
		this.province_name = province_name;
		this.address = address;
	}

	public Geo(String longitube, String latitude, String city, String province,
			String city_name, String province_name, String address,
			String pinyin, String more) {
		super();
		this.longitube = longitube;
		this.latitude = latitude;
		this.city = city;
		this.province = province;
		this.city_name = city_name;
		this.province_name = province_name;
		this.address = address;
		this.pinyin = pinyin;
		this.more = more;
	}

	public String getLongitube() {
		return longitube;
	}

	public void setLongitube(String longitube) {
		this.longitube = longitube;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	/**
	 * getGeoByJSON(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param jsonObject
	 * @return Geo
	 * @exception
	 * @since 1.0.0
	 */
	public static Geo getGeoByJSON(JSONObject jsonObject) {

		// TODO Auto-generated method stub
		if (jsonObject == null) {
			return null;
		}
		Geo geo = new Geo( );

		try {
			
			JSONObject object = jsonObject.getJSONObject("coordinates");
			geo.setLongitube(object.getString("longitude"));

			geo.setCity(jsonObject.getString("city"));
			geo.setCity_name(jsonObject.getString("city_name"));
			geo.setProvince(jsonObject.getString("province"));

			geo.setProvince_name(jsonObject.getString("address"));
			geo.setAddress(jsonObject.getString("address"));
			geo.setPinyin(jsonObject.getString("pinyin"));
			geo.setMore(jsonObject.getString("more"));
		}
		catch (JSONException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return geo;

	}

}
