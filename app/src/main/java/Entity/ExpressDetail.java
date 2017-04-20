package Entity;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class ExpressDetail extends DataSupport {

  /**
   * EBusinessID : 1284116 ShipperCode : YTO Success : true LogisticCode : 12345678 State : 2 Traces
   * : [{"AcceptTime":"2017-01-18 10:32:20","AcceptStation":"【安徽省合肥市政务区柏堰工业园公司】 派件人 : 王耐富 派件中
   * 派件员电话18755105463"},{"AcceptTime":"2017-01-18 10:52:16","AcceptStation":"【江西省赣州市赣县区公司】 派件人 : 曾鸣
   * 派件中 派件员电话18720121499"},{"AcceptTime":"2017-01-18 11:23:01","AcceptStation":"【云南省曲靖市宣威市公司】 派件人 :
   * 余凤芹 派件中 派件员电话15687439152"},{"AcceptTime":"2017-01-18 12:55:11","AcceptStation":"【黑龙江省鸡西市虎林市公司】
   * 派件人 : 付厚君 派件中 派件员电话"},{"AcceptTime":"2017-01-18 13:58:23","AcceptStation":"【河南省南阳市新野县施庵镇公司】 派件人
   * : 张彩平 派件中 派件员电话18338335206"}]
   */

  private String EBusinessID;
  private String ShipperCode;
  private boolean Success;
  private String LogisticCode;
  private String State;
  @SerializedName("Traces") private List<TracesBean> TracesX;

  public String getEBusinessID() {
    return EBusinessID;
  }

  public String getShipperCode() {
    return ShipperCode;
  }

  public void setShipperCode(String ShipperCode) {
    this.ShipperCode = ShipperCode;
  }

  public boolean isSuccess() {
    return Success;
  }

  public String getLogisticCode() {
    return LogisticCode;
  }

  public String getState() {
    return State;
  }

  public void setEBusinessID(String EBusinessID) {
    this.EBusinessID = EBusinessID;
  }

  public void setSuccess(boolean success) {
    Success = success;
  }

  public void setLogisticCode(String logisticCode) {
    LogisticCode = logisticCode;
  }

  public void setState(String state) {
    State = state;
  }


  public List<TracesBean> getTracesX() {
    return TracesX;
  }

  public void setTracesX(List<TracesBean> TracesX) {
    this.TracesX = TracesX;
  }

  public static class TracesBean {
    /**
     * AcceptTime : 2017-01-18 10:32:20
     * AcceptStation : 【安徽省合肥市政务区柏堰工业园公司】 派件人 : 王耐富 派件中 派件员电话18755105463
     */

    private String AcceptTime;
    private String AcceptStation;

    public String getAcceptTime() {
      return AcceptTime;
    }

    public void setAcceptTime(String AcceptTime) {
      this.AcceptTime = AcceptTime;
    }

    public String getAcceptStation() {
      return AcceptStation;
    }

    public void setAcceptStation(String AcceptStation) {
      this.AcceptStation = AcceptStation;
    }
  }
}
