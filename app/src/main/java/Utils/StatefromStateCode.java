package Utils;

import java.util.HashMap;

public class StatefromStateCode {
  public static final String format(String StateCode){
    HashMap<String,String> mState=new HashMap<String,String>();
    mState.put("2","在途中");
    mState.put("3","签收");
    mState.put("4","问题件");
    return mState.get(StateCode);
  }
}
