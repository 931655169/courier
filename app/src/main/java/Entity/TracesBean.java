package Entity;

import org.litepal.crud.DataSupport;

public class TracesBean extends DataSupport {
  /**
   * AcceptTime : 2017-01-18 10:32:20
   * AcceptStation : 【安徽省合肥市政务区柏堰工业园公司】 派件人 : 王耐富 派件中 派件员电话18755105463
   */
  private String AcceptTime;
  private String AcceptStation;
  private ExpressDetail expressDetail;

  public String getAcceptTime() {
    return AcceptTime;
  }

  public String getAcceptStation() {
    return AcceptStation;
  }

  public void setAcceptTime(String acceptTime) {
    AcceptTime = acceptTime;
  }

  public void setAcceptStation(String acceptStation) {
    AcceptStation = acceptStation;
  }

  public ExpressDetail getExpressDetail() {
    return expressDetail;
  }

  public void setExpressDetail(ExpressDetail expressDetail) {
    this.expressDetail = expressDetail;
  }
}
