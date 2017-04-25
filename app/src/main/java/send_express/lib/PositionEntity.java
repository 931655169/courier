/**  
 * Project Name:Android_Car_Example  
 * File Name:PositionEntity.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午9:50:28  
 *  
 */

package send_express.lib;

/**
 * ClassName:PositionEntity <br/>
 * Function: 封装的关于位置的实体 <br/>
 * Date: 2015年4月3日 上午9:50:28 <br/>
 * 
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class PositionEntity {

	public double latitue;

	public double longitude;

	public String address;
	
	public String city;

	public PositionEntity() {
	}

	public PositionEntity(double latitude, double longtitude, String address,String city) {
		this.latitue = latitude;
		this.longitude = longtitude;
		this.address = address;
	}
	public PositionEntity(double longitude,double latitue,String address){
		this.address=address;
		this.latitue=latitue;
		this.longitude=longitude;
	}
	public double getLatitue() {
		return latitue;
	}

	public void setLatitue(double latitue) {
		this.latitue = latitue;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
