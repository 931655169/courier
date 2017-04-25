package Utils;

import java.util.HashMap;

public class CompanyfromCodeUtils {

  public static String Companyformat(String CompanyName){
    HashMap<String,String> mCompanyName=new HashMap<String,String>();
    mCompanyName.put("顺丰","SF");
    mCompanyName.put("百世快递","HTKY");
    mCompanyName.put("中通","ZTO");
    mCompanyName.put("申通","STO");
    mCompanyName.put("圆通","YTO");
    mCompanyName.put("韵达","YD");
    mCompanyName.put("邮政平邮","YZPY");
    mCompanyName.put("EMS","EMS");
    mCompanyName.put("天天快递","HHTT");
    return mCompanyName.get(CompanyName);
  }
  public static String Codeformat(String CompanyCode){
    HashMap<String,String> mCompanyCode=new HashMap<String,String>();
    mCompanyCode.put("SF","顺丰");
    mCompanyCode.put("HTKY","百世快递");
    mCompanyCode.put("ZTO","中通");
    mCompanyCode.put("STO","申通");
    mCompanyCode.put("YTO","圆通");
    mCompanyCode.put("TD","韵达");
    mCompanyCode.put("YZPY","邮政平邮");
    mCompanyCode.put("EMS","EMS");
    mCompanyCode.put("HHTT","天天快递");
    return mCompanyCode.get(CompanyCode);
  }
}
