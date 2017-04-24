package Service;

import Entity.ExpressDetail;
import Entity.TracesBean;
import Network.ApiStores;
import android.content.SharedPreferences;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.List;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KdniaoTrackQueryAPI {
  private String mShipperCode;
  private String mLogisticCode;
  public KdniaoTrackQueryAPI() {
  }

  /**
   * 向指定 URL 发送POST方法的请求
   */
  @SuppressWarnings("unused")
  public void sendPost(final String ShipperCode, String LogisticCode) throws Exception {
    mShipperCode = ShipperCode;
    mLogisticCode = LogisticCode;

    final String requestData = "{'OrderCode':'','ShipperCode':'"
        + mShipperCode
        + "','LogisticCode':'"
        + mLogisticCode
        + "'}";
    String dataSign = encrypt(requestData, AppKey, "UTF-8");
    ApiStores apiStores = ApiService.createApiStores();
    Call<ExpressDetail> call = apiStores.getExpressDetail(
        urlEncoder(dataSign, "UTF-8"), "2", EBusinessID, urlEncoder(requestData, "UTF-8"), "1002"
    );
    call.enqueue(new Callback<ExpressDetail>() {
      @Override
      public void onResponse(Call<ExpressDetail> call, final Response<ExpressDetail> response) {
        response.body().saveAsync().listen(new SaveCallback() {
          @Override public void onFinish(boolean success) {

          }
        });
        if (response.body().isSuccess()){
            DataSupport.deleteAll(TracesBean.class);
          for (int i = 0; i < response.body().getTracesX().size(); i++) {
            TracesBean tracesBean = new TracesBean();
            tracesBean.setAcceptStation(response.body().getTracesX().get(i).getAcceptStation());
            tracesBean.setAcceptTime(response.body().getTracesX().get(i).getAcceptTime());
            tracesBean.save();
          }
        }
      }
      @Override public void onFailure(Call<ExpressDetail> call, Throwable t) {
        Log.d("调用", t.getMessage() + "网络请求失败");
      }
    });
  }

  private String EBusinessID = "1284116";
  private String AppKey = "e0fd3b17-ea19-4e71-a068-f9997a890a8e";
  private String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";

  /**
   * MD5加密
   *
   * @param str 内容
   * @param charset 编码方式
   * @throws Exception
   */
  @SuppressWarnings("unused")
  private String MD5(String str, String charset) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(str.getBytes(charset));
    byte[] result = md.digest();
    StringBuffer sb = new StringBuffer(32);
    for (int i = 0; i < result.length; i++) {
      int val = result[i] & 0xff;
      if (val <= 0xf) {
        sb.append("0");
      }
      sb.append(Integer.toHexString(val));
    }
    return sb.toString().toLowerCase();
  }

  /**
   * base64编码
   *
   * @param str 内容
   * @param charset 编码方式
   * @throws UnsupportedEncodingException
   */
  private String base64(String str, String charset) throws UnsupportedEncodingException {
    String encoded = base64Encode(str.getBytes(charset));
    return encoded;
  }

  @SuppressWarnings("unused")
  private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
    String result = URLEncoder.encode(str, charset);
    return result;
  }

  /**
   * 电商Sign签名生成
   *
   * @param content 内容
   * @param keyValue Appkey
   * @param charset 编码方式
   * @return DataSign签名
   * @throws UnsupportedEncodingException ,Exception
   */
  @SuppressWarnings("unused")
  private String encrypt(String content, String keyValue, String charset) throws
      UnsupportedEncodingException, Exception {
    if (keyValue != null) {
      return base64(MD5(content + keyValue, charset), charset);
    }
    return base64(MD5(content, charset), charset);
  }

  private static char[] base64EncodeChars = new char[] {
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
      'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
      'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
      'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
      'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
      'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
      'w', 'x', 'y', 'z', '0', '1', '2', '3',
      '4', '5', '6', '7', '8', '9', '+', '/'};

  public static String base64Encode(byte[] data) {
    StringBuffer sb = new StringBuffer();
    int len = data.length;
    int i = 0;
    int b1, b2, b3;
    while (i < len) {
      b1 = data[i++] & 0xff;
      if (i == len) {
        sb.append(base64EncodeChars[b1 >>> 2]);
        sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
        sb.append("==");
        break;
      }
      b2 = data[i++] & 0xff;
      if (i == len) {
        sb.append(base64EncodeChars[b1 >>> 2]);
        sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
        sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
        sb.append("=");
        break;
      }
      b3 = data[i++] & 0xff;
      sb.append(base64EncodeChars[b1 >>> 2]);
      sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
      sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
      sb.append(base64EncodeChars[b3 & 0x3f]);
    }
    return sb.toString();
  }
}

