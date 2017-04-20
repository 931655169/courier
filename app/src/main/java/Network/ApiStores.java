package Network;

import Entity.ExpressDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiStores {
  @FormUrlEncoded
  @POST("Ebusiness/EbusinessOrderHandle.aspx")
  Call<ExpressDetail> getExpressDetail(
      @Field("DataSign") String datasign,
      @Field("DataType") String datatype,
      @Field("EBusinessID")String ebusinessid,
      @Field("RequestData") String requestdata,
      @Field("RequestType") String requesttype
  );
}
