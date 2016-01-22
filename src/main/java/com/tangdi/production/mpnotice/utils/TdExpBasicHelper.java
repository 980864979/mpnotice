package com.tangdi.production.mpnotice.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.apache.commons.lang.StringUtils;

public class TdExpBasicHelper
{
  static String formatDouble(double val, int precision)
  {
    if (precision == 0) {
      return String.valueOf(val);
    }

    String pattern = "0." + StringUtils.repeat("0", precision);
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
    df.applyPattern(pattern);
    return df.format(val);
  }

  public static final String tosbc(String str)
  {
    StringBuffer outStr = new StringBuffer();
    String tStr = "";
    byte[] b = null;

    for (int i = 0; i < str.length(); i++) {
      try {
        tStr = str.substring(i, i + 1);
        b = tStr.getBytes("unicode");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      if (b[3] != -1) {
        b[2] = (byte)(b[2] - 32);
        b[3] = -1;
        try {
          outStr.append(new String(b, "unicode"));
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      } else {
        outStr.append(tStr);
      }
    }
    return outStr.toString();
  }

  public static final String todbc(String str)
  {
    StringBuffer outStr = new StringBuffer();
    String tStr = "";
    byte[] b = null;

    for (int i = 0; i < str.length(); i++) {
      try {
        tStr = str.substring(i, i + 1);
        b = tStr.getBytes("unicode");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      if (b[3] == -1) {
        b[2] = (byte)(b[2] + 32);
        b[3] = 0;
        try {
          outStr.append(new String(b, "unicode"));
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      } else {
        outStr.append(tStr);
      }
    }
    return outStr.toString();
  }
}