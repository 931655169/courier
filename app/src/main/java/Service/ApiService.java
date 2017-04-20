package Service;

import Network.ApiStores;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;



public class ApiService {
  public static ApiStores createApiStores() {
    return retrofit().create(ApiStores.class);
  }
  private static Retrofit retrofit() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://api.kdniao.cc/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit;
  }
}
