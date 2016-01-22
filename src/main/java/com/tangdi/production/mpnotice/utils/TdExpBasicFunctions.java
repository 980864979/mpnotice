package com.tangdi.production.mpnotice.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;


public class TdExpBasicFunctions
{
  private static String DIGIT_UPPER = "零壹贰叁肆伍陆柒捌玖";

  private static String AMT_UPPER = "分角元拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿";

  private static int[] ULEAD_MONTH_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  private static int[] LEAD_MONTH_DAYS = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  private static String EBCD_POSITIVE = "{ABCDEFGHI";

  private static String EBCD_NEGATIVE = "}JKLMNOPQR";

  public static String STRCAT(String[] args)
  {
    if (args.length < 2)
      throw new TdExprException("STRCAT表达式至少有两个参数");
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < args.length; i++) {
      result.append(args[i]);
    }
    return result.toString();
  }

  public static String ENCODEBASE64(String src) {
    return new String(Base64.encodeBase64(src.getBytes()));
  }

  public static String DECODEBASE64(String dst) {
    return new String(Base64.decodeBase64(dst.getBytes()));
  }

  public static long STRTOLONG(String args)
  {
    try
    {
      return Long.parseLong(args); } catch (NumberFormatException e) {
    }
   // throw new TdExprException("表达式STRTOLONG参数不合法", e);
	return 0;
  }

  public static int STRTOINT(String args)
  {
    try
    {
      return Integer.parseInt(args); } catch (NumberFormatException e) {
    }
   // throw new TdExprException("表达式STRTOINT参数不合法", e);
	return 0;
  }

  public static String STRNCAT(String args1, String args2, int len)
  {
    if (StringUtils.isEmpty(args1))
      throw new TdExprException("STRNCAT 参数不合法");
    StringBuffer result = new StringBuffer(args1);
    result.append(StringUtils.substring(args2, 0, len));
    return result.toString();
  }

  public static int STRCMP(String args1, String args2)
  {
    return args1.compareTo(args2);
  }

  public static int SUBCMP(String args1, int num1, int num2, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2)))
      throw new TdExprException("SUBCMP");
    int offset = num1 - 1;
    if (offset < 0)
      offset = 0;
    if (offset > args1.length()) {
      offset = args1.length() - 1;
    }
    if (num2 < 0) {
      num2 = 0;
    }

    int ret = StringUtils.substring(args1, offset, offset + num2).compareTo(args2);
    return ret;
  }

  public static long LONGPOWER(long[] param)
  {
    if (param.length < 2)
      throw new TdExprException("LONGPOWER");
    long result = param[0];
    for (int i = 1; i < param.length; i++) {
      result = (long) Math.pow(result, param[i]);
    }
    return result;
  }

  public static long SHORTPOWER(long[] param)
  {
    if (param.length < 2)
      throw new TdExprException("SHORTPOWER");
    long result = param[0];
    for (int i = 1; i < param.length; i++) {
      result = (long) Math.pow(result, param[i]);
    }
    return result;
  }

  public static int INTCMP(int args1, int args2, int args3)
  {
    int result = args1;
    int op = args2;
    switch (op) {
    case 1:
      if (result < args3) {
        return 1;
      }
      return 0;
    case 2:
      if (result <= args3) {
        return 1;
      }
      return 0;
    case 3:
      if (result == args3) {
        return 1;
      }
      return 0;
    case 4:
      if (result != args3) {
        return 1;
      }
      return 0;
    case 5:
      if (result >= args3) {
        return 1;
      }
      return 0;
    case 6:
      if (result > args3) {
        return 1;
      }
      return 0;
    }
    throw new TdExprException("INTCMP");
  }

  public static int DOUBLECMP(double args1, int args2, double args3)
  {
    double result = args1;
    int op = args2;
    switch (op) {
    case 1:
      if (result < args3) {
        return 1;
      }
      return 0;
    case 2:
      if (result <= args3) {
        return 1;
      }
      return 0;
    case 3:
      if (result == args3) {
        return 1;
      }
      return 0;
    case 4:
      if (result != args3) {
        return 1;
      }
      return 0;
    case 5:
      if (result >= args3) {
        return 1;
      }
      return 0;
    case 6:
      if (result > args3) {
        return 1;
      }
      return 0;
    }
    throw new TdExprException("DOUBLECMP 比较符参数不正确!");
  }

  public static boolean IS_NOEQUAL_STRING(String args1, String args2)
  {
    return !StringUtils.equals(args1, args2);
  }

  public static boolean IS_EQUAL_INT(int int1, int int2)
  {
    return int1 == int2;
  }

  public static boolean IS_EQUAL_DOUBLE(double d1, double d2)
  {
    return d1 == d2;
  }

  public static boolean IS_EQUAL_STRING(String args1, String args2)
  {
    return StringUtils.equals(args1, args2);
  }

  public static boolean ISNULL(String[] args)
  {
    if (args.length == 0) {
      throw new TdExprException("ISNULL 参数不正确!至少要求有一个参数");
    }
    for (int i = 0; i < args.length; i++) {
      if (!StringUtils.isEmpty(args[i]))
        return false;
    }
    return true;
  }

  public static boolean ISCHIN(String args)
  {
    if (StringUtils.isEmpty(args)) {
      throw new TdExprException("ISCHIN 参数不正确");
    }

    char[] buf = args.toCharArray();
    int sz = args.length();
    for (int i = 0; i < sz; i++) {
      if (CharUtils.isAscii(buf[i])) {
        return false;
      }
    }
    return true;
  }

  public static int STRLEN(String args)
  {
    if (args == null)
      throw new TdExprException("表达式 STRLEN 参数不能为空");
    return args.length();
  }

  public static String SUBSTR(String args, int pos, int len)
  {
    int beginIndex = pos - 1;
    if (beginIndex < 0)
      beginIndex = 0;
    if (beginIndex > args.length()) {
      beginIndex = args.length();
    }
    int length = len;
    if (length < 0) {
      length = 0;
    }
    if (beginIndex + length > args.length()) {
      length = args.length() - beginIndex;
    }
    byte[] bytes = args.getBytes();
    return new String(bytes, beginIndex, length);
  }

  public static String SUBRIGHT(String str, int len)
  {
    if (len < 0)
      len = 0;
    if (len > str.length())
      len = str.length();
    byte[] bytes = str.getBytes();
    return new String(bytes, str.length() - len, len);
  }

  public static boolean ISNUMBER(String[] args)
  {
    if (args.length == 0) {
      throw new TdExprException("ISNUMBER 至少要求有一个参数");
    }
    for (int i = 0; i < args.length; i++)
    {
      if (StringUtils.isEmpty(args[i])) {
        return false;
      }

      if (!StringUtils.isNumeric(args[i])) {
        return false;
      }
    }
    return true;
  }

  public static int GETCHARPOS(String args1, String args2, int len)
  {
    if (StringUtils.isEmpty(args1))
      return -1;
    if (len > args1.length())
      len = args1.length();
    int lenght = len - 1;
    if (lenght < 0) {
      return -1;
    }
    for (int i = 0; i < lenght; i++) {
      if (args1.charAt(i) == args2.charAt(0)) {
        return i;
      }
    }
    return -1;
  }

  public static int GETCHARPOSFROM(String args1, int index, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2))) {
      return -1;
    }
    int ret = StringUtils.indexOf(args1, args2, index);
    if (ret != -1) {
      ret++;
    }
    return ret;
  }

  public static int GETSTRPOS(String args1, String args2)
  {
    int idx = StringUtils.indexOf(args1, args2);
    if (idx == -1) {
      return -1;
    }
    return idx + 1;
  }

  public static String DELSPACE(String args1, String args2)
  {
    if ((args1 == null) || (StringUtils.isEmpty(args2)))
      throw new TdExprException("DELSPACE");
    args2 = args2.toUpperCase();

    if ((!args2.equals("LEFT")) && (!args2.equals("RIGHT")) && (!args2.equals("BOTH")) && (!args2.equals("ALL"))) {
      throw new TdExprException("DELSPACE第二个参数错误");
    }
    if (StringUtils.equalsIgnoreCase("LEFT", args2))
      return StringUtils.stripStart(args1, " ");
    if (StringUtils.equalsIgnoreCase("RIGHT", args2))
      return StringUtils.stripEnd(args1, " ");
    if (StringUtils.equalsIgnoreCase("BOTH", args2))
      return StringUtils.strip(args1);
    if (StringUtils.equalsIgnoreCase("ALL", args2)) {
      return StringUtils.replaceChars(args1, " \t\f\r\n　", "");
    }
    return args1;
  }

  public static String DELBOTHSPACE(String args)
  {
    if (args == null)
      throw new TdExprException("[DELBOTHSPACE 参数不允许为空]");
    return StringUtils.strip(args);
  }

  public static String DELRIGHTSPACE(String args)
  {
    if (args == null) {
      throw new TdExprException("DELRIGHTSPACE 参数不允许为空");
    }
    return StringUtils.stripEnd(args, " ");
  }

  public static String SPACE(int args, int len)
  {
    if (args == 0)
      throw new TdExprException("SPACE");
    switch (len) {
    case 0:
      return StringUtils.repeat(" ", args);
    case 1:
      return StringUtils.repeat("　", args);
    }
    throw new TdExprException("SPACE 参数产生空格的模式不正确，只能是[0]或者[1]");
  }

  public static String INTTOSTR(String args, int len)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("INTTOSTR");
    return StringUtils.leftPad(StringUtils.trim(args), len, '0');
  }

  public static String ADDCHAR(String args1, int len, String args2, String args3)
  {
    if ((args1 == null) || (args2 == null) || (args3 == null)) {
      throw new TdExprException("ADDCHAR 参数不允许为空!");
    }
    byte[] arg1 = args1.getBytes();
    byte[] chs = args2.getBytes();
    if (chs.length != 1) {
      throw new TdExprException("ADDCHAR 参数不合法，要填充的必须是字符！！");
    }
    int arglen = arg1.length;

    int strLen = args1.length();
    len -= arglen - strLen;
    if (len < 0) {
      throw new TdExprException("ADDCHAR 参数不合法，字符串的长度大于要补充后的长度！！");
    }
    switch (NumberUtils.toInt(StringUtils.trim(args3))) {
    case 0:
      return StringUtils.rightPad(args1, len, (char)chs[0]);
    case 1:
      return StringUtils.leftPad(args1, len, (char)chs[0]);
    }
    throw new TdExprException("ADDCHAR 补充字符方向不合法，必须为[0][1]中的一个");
  }

  public static String ADDCHAR2(String args1, int len, String args2, String args3)
  {
    switch (NumberUtils.toInt(StringUtils.trim(args3))) {
    case 0:
      return StringUtils.rightPad(StringUtils.trim(args1), len, StringUtils.trim(args2));
    case 1:
      return StringUtils.leftPad(StringUtils.trim(args1), len, StringUtils.trim(args2));
    }

    throw new TdExprException("ADDCHAR");
  }

  public static String CUTDATALENGTH(String args, int len)
  {
    return StringUtils.substring(StringUtils.trim(args), StringUtils.trim(args).length() - len);
  }

  public static String TOUPPER(String args)
  {
    if (StringUtils.isEmpty(args)) {
      throw new TdExprException("TOUPPER");
    }
    return StringUtils.upperCase(args);
  }

  public static String TOLOWER(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("TOLOWER");
    return StringUtils.lowerCase(args);
  }

  public static String REPSTR(String[] args)
  {
    if (args.length != 3) {
      throw new TdExprException("REPSTR");
    }
    return StringUtils.replaceOnce(args[0].trim(), args[1].trim(), args[2].trim());
  }

  public static String REPALLSTR(String[] args)
  {
    if (args.length != 3) {
      throw new TdExprException("REPALLSTR");
    }
    return StringUtils.replace(args[0], args[1], args[2]);
  }

  public static String DELCTRL(String args)
  {
    if (StringUtils.isEmpty(args)) {
      throw new TdExprException("DELCTRL");
    }
    String str = args.trim();
    if (StringUtils.isEmpty(str))
      throw new TdExprException("DELCTRL");
    char[] buf = str.toCharArray();
    for (int i = 0; i < buf.length; i++) {
      if (CharUtils.isAsciiControl(buf[i])) {
        str = StringUtils.replace(str, Character.toString(buf[i]), "");
      }
    }
    return str;
  }

  public static String TURNSPACIALCHAR(String args)
  {
    return args.replaceAll("'", "''");
  }

  public static String REPCTRL(String args1, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2))) {
      throw new TdExprException("REPCTRL");
    }
    String str = args1;
    String ch = args2;

    char[] buf = str.toCharArray();
    for (int i = 0; i < buf.length; i++) {
      if (CharUtils.isAsciiControl(buf[i])) {
        buf[i] = ch.charAt(0);
      }
    }
    return new String(buf);
  }

  public static String REVERSAL(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("REVERSAL");
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < args.length(); i++) {
      switch (args.charAt(i)) {
      case '0':
        result.append('1');
        break;
      case '1':
        result.append('0');
        break;
      default:
        result.append(args.charAt(i));
      }
    }

    return result.toString();
  }

  public static String LTRIM(String args1, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2))) {
      return args1;
    }
    for (int i = 0; i < args1.length(); i++) {
      if (args1.charAt(i) == args2.charAt(0)) {
        return args1.substring(i + 1);
      }
    }
    return args1;
  }

  public static String RTRIM(String args1, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2))) {
      return args1;
    }
    for (int i = args1.length() - 1; i > -1; i--) {
      if (args1.charAt(i) == args2.charAt(0)) {
        return args1.substring(0, i);
      }
    }

    return args1;
  }

  public static String TRIM(String args1, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2)))
      return args1;
    args2 = args2.toUpperCase();
    if ((!args2.equals("LEFT")) && (!args2.equals("RIGHT")) && (!args2.equals("BOTH")) && (!args2.equals("ALL"))) {
      throw new TdExprException("TRIM第二个参数错误");
    }
    if (StringUtils.equals(args2.trim(), "BOTH"))
      return StringUtils.trim(args1);
    if (StringUtils.equals(args2.trim(), "RIGHT"))
      return StringUtils.stripEnd(args1, "");
    if (StringUtils.equals(args2.trim(), "LEFT"))
      return StringUtils.stripStart(args1, "");
    if (StringUtils.equals(args2.trim(), "ALL")) {
      String[] buf3 = args1.split(" |　|\t|\n|\f");
      String ret = "";
      for (int j = 0; j < buf3.length; j++) {
        ret = ret + buf3[j];
      }
      return ret;
    }
    throw new TdExprException("TRIM");
  }

  public static String NullToEmpty(String args)
  {
    if (args == null) {
      return "";
    }
    return args;
  }

  public static String INSERTCHAR(String str, String ch, int times, int step)
  {
    if (str == null) {
      return null;
    }

    int iTimes = 1;

    int iStep = 0;
    if (iTimes < times) {
      iTimes = times;
    }
    if (iStep < step) {
      iStep = step;
    }
    StringBuffer result = new StringBuffer();

    int interval = str.length() - 1;
    for (int i = 0; i < interval; i++) {
      result.append(str.charAt(i));
      for (int j = 0; j < iTimes; j++) {
        result.append(ch);
      }
      iTimes += iStep;
    }
    result.append(str.charAt(interval));
    return result.toString();
  }

  public static String GETWORDDELIMITER(String str, String seperator, int len)
  {
    if (StringUtils.isEmpty(str))
      throw new TdExprException("GETWORDDELIMITER");
    String[] temp = StringUtils.splitByWholeSeparator(str, seperator);
    int seq = len;
    if (seq > temp.length)
      return "";
    return temp[(seq - 1)];
  }

  public static String LEFTSTR(String args, int n)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("LEFTSTR");
    return StringUtils.left(args.trim(), n);
  }

  public static String RIGHTSTR(String args, int n)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("RIGHTSTR");
    return StringUtils.right(args.trim(), n);
  }

  public static int STRGETCOUNT(String args1, String args2)
  {
    if (StringUtils.isEmpty(args1))
      throw new TdExprException("STRGETCOUNT");
    return StringUtils.countMatches(args1, args2);
  }

  public static String FABSAMT(String args, int len)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("FABSAMT");
    String str = args.trim();

    if ((!StringUtils.containsNone(str, ",.")) || (!NumberUtils.isNumber(str))) {
      throw new TdExprException("FABSAMT");
    }
    DecimalFormat ft = new DecimalFormat("#");

    double dNum = NumberUtils.toDouble(str);
    String ret = ft.format(Math.abs(dNum));

    if (ret.length() < len) {
      StringBuffer buf = new StringBuffer(ret);
      for (int i = 0; i < len - ret.length(); i++) {
        buf.insert(0, '0');
      }
      ret = buf.toString();
    }
    return ret;
  }

  public static int ISLEAPYEAR(int year)
  {
    if (year < 1900) {
      return -2;
    }
    if (year % 4 == 0) {
      if (year % 100 == 0) {
        if (year % 400 == 0) {
          return 1;
        }
        return -1;
      }

      return 1;
    }

    return -1;
  }

  public static String FMTDATE(String args, int type1, int type2)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("FMTDATE");
    String str = args.trim();
    if ((StringUtils.isEmpty(str)) || (type1 > 5) || (type1 < 0) || (type2 > 5) || (type2 < 0)) {
      throw new TdExprException("FMTDATE");
    }

    String pattern1 = ""; String pattern2 = "";
    String[] buf1 = { "yyyyMMdd", "yyyy/MM/dd", "MM/dd/yyyy", "yyyy.MM.dd", "yyyy-MM-dd", "yyyy年MM月dd天" };

    str = str.trim();
    pattern1 = buf1[type1];
    pattern2 = buf1[type2];

    if ((StringUtils.isEmpty(pattern1)) || (StringUtils.isEmpty(pattern2))) {
      throw new TdExprException("FMTDATE");
    }

    String[] pattern = { pattern1 };
    try
    {
      Date date = DateUtils.parseDate(str, pattern);
      return DateFormatUtils.format(date, pattern2); } catch (ParseException e) {
    }
    throw new TdExprException("FMTDATE");
  }

  public static String FMTTIME(String time, String old, String newParttern)
  {
    try
    {
      Date date = DateUtils.parseDate(time, new String[] { old });
      return DateFormatUtils.format(date, newParttern); } catch (ParseException e) {
    }
    throw new TdExprException("FMTTIME 格式不正确");
  }

  public static int CHECKDATE(String args)
  {
    String str = args.trim();
    if (StringUtils.isEmpty(str)) {
      throw new TdExprException("CHECKDATE");
    }

    try
    {
      str = str.trim();
      if (str.length() != 8) {
        return 0;
      }
      int iYear = Integer.parseInt(str.substring(0, 4));
      int iMonth = Integer.parseInt(str.substring(4, 6));
      int iDay = Integer.parseInt(str.substring(6, 8));

      if (iYear <= 1900)
        return 0;
      if ((iMonth < 1) || (iMonth > 12)) {
        return 0;
      }
      if (ISLEAPYEAR(iYear) == 1)
      {
        if ((iDay > 0) && (iDay <= LEAD_MONTH_DAYS[(iMonth - 1)])) {
          return 1;
        }
        return 0;
      }

      if ((iDay > 0) && (iDay <= ULEAD_MONTH_DAYS[(iMonth - 1)])) {
        return 1;
      }
      return 0;
    }
    catch (NumberFormatException e) {
    }
   // throw new TdExprException(e);
	return 0;
  }

  public static String GETDATETIME(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("GETDATETIME");
    String[] buf1 = { "YYYY", "YY", "MM", "DD", "HH", "MI", "SS" };
    String[] buf2 = { "yyyy", "yy", "MM", "dd", "HH", "mm", "ss" };
    String str = args.trim();
    for (int i = 0; i < buf1.length; i++) {
      str = StringUtils.replace(str, buf1[i], buf2[i]);
    }
    Calendar calendar = Calendar.getInstance();
    return DateFormatUtils.format(calendar.getTime(), str);
  }

  public static String GETDATETIME()
  {
    return GETDATETIME("yyyyMMDDHHmmss");
  }

  public static String GETDATE()
  {
    return GETDATETIME("yyyyMMdd");
  }

  public static long GETSECOND()
  {
    Date dt = new Date();
    long curSec = dt.getTime();
    curSec /= 1000L;
    return curSec;
  }

  public static String CALCTIME(String[] args)
  {
    if (args.length < 4)
      throw new TdExprException("CALCTIME");
    String datefmt = "yyyyMMddHHmmss";
    try
    {
      Date dt;
      //Date dt;
      if (args.length == 5) {
        datefmt = args[4];
        dt = DateUtils.parseDate(args[0], new String[] { args[4] });
      } else {
        dt = DateUtils.parseDate(args[0], new String[] { datefmt });
      }
      int num = NumberUtils.toInt(args[3]);
      if (num == 0) {
        return args[0];
      }
      int sign = args[1].equals("+") ? 1 : -1;
      num *= sign;
      if (args[2].equals("y"))
        dt = DateUtils.addYears(dt, num);
      else if (args[2].equals("M"))
        dt = DateUtils.addMonths(dt, num);
      else if (args[2].equals("D"))
        dt = DateUtils.addDays(dt, num);
      else if (args[2].equalsIgnoreCase("H"))
      {
        dt = DateUtils.addHours(dt, num);
      } else if (args[2].equals("m"))
        dt = DateUtils.addMinutes(dt, num);
      else if (args[2].equals("s"))
        dt = DateUtils.addSeconds(dt, num);
      else {
        throw new TdExprException(args[2]);
      }
      return DateFormatUtils.format(dt, datefmt); } catch (ParseException e) {
    }
    throw new TdExprException(args[0]);
  }

  public static String CALCDATE(String[] args)
  {
    int[] buf3 = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 3, 2, 1 };

    if (args.length != 4) {
      throw new TdExprException("CALCDATE");
    }
    String str = args[0].trim();
    String op = args[1].trim();
    String obj = args[2].trim();
    String num = args[3].trim();

    if (CHECKDATE(str) != 1) {
      throw new TdExprException("CALCDATE 输入的日期不合法");
    }
    if ((StringUtils.isEmpty(str)) || (str.length() != 8))
      throw new TdExprException("CALCDATE");
    try {
      String[] pattern = { "yyyyMMdd" };
      Date dt = DateUtils.parseDate(str, pattern);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dt);
      int iYear = calendar.get(1);
      int iMonth = calendar.get(2) + 1;
      int iDay = calendar.get(5);
      int iNum = NumberUtils.toInt(num);
      if (obj.toUpperCase().equals("Y")) {
        if (op.equals("+"))
          iYear += iNum;
        else if (op.equals("-")) {
          iYear -= iNum;
        }
        if ((iMonth == 2) && 
          (ISMONTHEND(str)))
          if (ISLEAPYEAR(iYear) == 1)
            iDay = 29;
          else
            iDay = 28;
      }
      else
      {
        if (obj.toUpperCase().equals("M")) {
          if (op.equals("+"))
            iMonth += iNum;
          else if (op.equals("-")) {
            iMonth -= iNum;
          }
          calendar.set(iYear, iMonth - 1, iDay);
          iYear = calendar.get(1);
          if (iYear < 1900) {
            throw new TdExprException("CALCDATE");
          }

          int index = iMonth;
          if (index <= 0) {
            index = buf3[(Math.abs(iMonth) % 12)];
          }
          else if (index % 12 == 0)
            index = 12;
          else {
            index %= 12;
          }

          if (ISLEAPYEAR(iYear) == 0) {
            if (iDay > LEAD_MONTH_DAYS[(index - 1)]) {
              iDay = LEAD_MONTH_DAYS[(index - 1)];
            }
          }
          else if (iDay > ULEAD_MONTH_DAYS[(index - 1)]) {
            iDay = ULEAD_MONTH_DAYS[(index - 1)];
          }

          calendar.set(iYear, index - 1, iDay);
          return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd");
        }if (obj.toUpperCase().equals("D")) {
          if (op.equals("+"))
            iDay += iNum;
          else if (op.equals("-")) {
            iDay -= iNum;
          }
        }
      }
      if (iYear < 1900)
        throw new TdExprException("CALCDATE");
      calendar.set(iYear, iMonth - 1, iDay);
      return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd"); } catch (ParseException e) {
    }
    throw new TdExprException("CALCDATE");
  }

  public static boolean ISYEAREND(String args)
  {
    String str = args.trim();
    if ((StringUtils.isEmpty(str)) || (CHECKDATE(str) != 1))
      return false;
    try {
      String[] pt = { "yyyyMMdd" };
      Date dt = DateUtils.parseDate(str, pt);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dt);
      int iMonth = calendar.get(2) + 1;
      int iDay = calendar.get(5);

      return (iMonth == 12) && (iDay == 31);
    }
    catch (ParseException e)
    {
    }

    throw new TdExprException("ISYEAREND");
  }

  public static boolean ISQUARTEREND(String args)
  {
    String str = args.trim();
    if ((StringUtils.isEmpty(str)) || (CHECKDATE(str) != 1))
      return false;
    try {
      String[] pt = { "yyyyMMdd" };
      Date dt = DateUtils.parseDate(str, pt);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dt);
      int iMonth = calendar.get(2) + 1;
      int iDay = calendar.get(5);
      if ((iMonth == 3) && (iDay == 31))
        return true;
      if ((iMonth == 6) && (iDay == 30))
        return true;
      if ((iMonth == 9) && (iDay == 30)) {
        return true;
      }
      return (iMonth == 12) && (iDay == 31);
    }
    catch (ParseException e)
    {
    }
    throw new TdExprException("ISQUATEREND");
  }

  public static boolean ISMONTHEND(String args)
  {
    String str = args.trim();
    if ((StringUtils.isEmpty(str)) || (CHECKDATE(str) != 1))
      return false;
    try {
      String[] pt = { "yyyyMMdd" };
      Date dt = DateUtils.parseDate(str, pt);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dt);
      int iYear = calendar.get(1);
      int iMonth = calendar.get(2) + 1;
      int iDay = calendar.get(5);

      if (ISLEAPYEAR(iYear) == 1)
      {
        return LEAD_MONTH_DAYS[(iMonth - 1)] == iDay;
      }

      return ULEAD_MONTH_DAYS[(iMonth - 1)] == iDay;
    }
    catch (ParseException e)
    {
    }
	return false;

   // throw new TdExprException("ISMONTHEND", e);
  
  }

  public static boolean ISWEEKEND(String args)
  {
    String str = args.trim();
    if ((StringUtils.isEmpty(str)) || (CHECKDATE(args) != 1))
      return false;
    try
    {
      Date dt = DateUtils.parseDate(str, new String[] { "yyyyMMdd" });
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dt);
      int week = calendar.get(7);

      return (week == 7) || (week == 1);
    }
    catch (ParseException e)
    {
    }

    throw new TdExprException("ISMONTHEND");
  }

  public static String DIFFDATE(String args1, String args2)
  {
    String date1 = args1.trim();
    String date2 = args2.trim();
    if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2)) || (CHECKDATE(args1) != 1) || (CHECKDATE(args2) != 1))
      throw new TdExprException("DIFFDATE Y");
    String[] pt = { "yyyyMMdd" };
    try {
      Date dt1 = DateUtils.parseDate(date1, pt);
      Date dt2 = DateUtils.parseDate(date2, pt);
      long diff = Math.abs(dt2.getTime() - dt1.getTime());
      return Long.toString(diff / 86400000L); } catch (ParseException e) {
    }
    throw new TdExprException("ISMONTHEND");
  }

  public static String CALCMONTH(String args1, String args2, int num)
  {
    int diff = 0;
    String date1 = args1.trim();
    String date2 = args2.trim();

    if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2)) || (CHECKDATE(args1) != 1) || (CHECKDATE(args2) != 1)) {
      throw new TdExprException("CALCMONTH");
    }
    try
    {
      String[] pt = { "yyyyMMdd" };
      Date dt1 = DateUtils.parseDate(date1, pt);
      Date dt2 = DateUtils.parseDate(date2, pt);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dt1);
      int iYear1 = calendar.get(1);
      int iMonth1 = calendar.get(2) + 1;
      int iDay1 = calendar.get(5);
      calendar.setTime(dt2);
      int iYear2 = calendar.get(1);
      int iMonth2 = calendar.get(2) + 1;
      int iDay2 = calendar.get(5);

      if (num == 0) {
        diff = Math.abs(iYear2 - iYear1);
        if (dt2.getTime() >= dt1.getTime()) {
          diff = diff * 12 + iMonth2 - iMonth1;
          if (iDay2 < iDay1)
            diff--;
        }
        else {
          diff = diff * 12 + iMonth1 - iMonth2;
          if (iDay1 < iDay2)
            diff--;
        }
      }
      else if (1 == num) {
        diff = Math.abs(iYear2 - iYear1);
        if (dt2.getTime() >= dt1.getTime())
          diff = diff * 12 + iMonth2 - iMonth1;
        else
          diff = diff * 12 + iMonth1 - iMonth2;
      }
      else {
        throw new TdExprException("CALCMONTH");
      }
      return Integer.toString(diff); } catch (ParseException e) {
    }
    throw new TdExprException("CALCMONTH");
  }

  public static String CALCYEAR(String args1, String args2, int num)
  {
    int diff = 0;
    String date1 = args1.trim();
    String date2 = args2.trim();
    int flag = num;

    if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2)) || (CHECKDATE(date1) != 1) || (CHECKDATE(date2) != 0))
      throw new TdExprException("CALCYEAR");
    try
    {
      String[] pt = { "yyyyMMdd" };
      Date dt1 = DateUtils.parseDate(date1, pt);
      Date dt2 = DateUtils.parseDate(date2, pt);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dt1);
      int iYear1 = calendar.get(1);
      int iMonth1 = calendar.get(2) + 1;
      int iDay1 = calendar.get(5);
      calendar.setTime(dt2);
      int iYear2 = calendar.get(1);
      int iMonth2 = calendar.get(2) + 1;
      int iDay2 = calendar.get(5);

      if (flag == 0) {
        diff = Math.abs(iYear2 - iYear1);
        if (dt2.getTime() >= dt1.getTime()) {
          if ((iMonth2 < iMonth1) || (iDay2 < iDay1)) {
            diff--;
          }
        }
        else if ((iMonth1 < iMonth2) || (iDay1 < iDay2)) {
          diff--;
        }
      }
      else if (flag == 1) {
        diff = Math.abs(iYear2 - iYear1);
      }
      return Integer.toString(diff);
    } catch (ParseException e) {
    }
    throw new TdExprException("CALCYEAR");
  }

  public static String NORMAL_TO_COBOL(String args1, int len)
  {
    String ret = "";
    String amt = args1.trim();

    if (StringUtils.isEmpty(amt)) {
      throw new TdExprException("NORMAL_TO_COBOL");
    }

    if ((StringUtils.contains(amt, ',')) || (!NumberUtils.isNumber(StringUtils.trim(amt)))) {
      throw new TdExprException("NORMAL_TO_COBOL");
    }
    int iLen = len;
    if (iLen <= 0) {
      throw new TdExprException("NORMAL_TO_COBOL");
    }
    double fAmt = NumberUtils.toDouble(amt);
    int flag = 0;

    if (amt.indexOf('-') != -1) {
      flag = -1;
    }

    DecimalFormat df = new DecimalFormat("#");
    ret = df.format(Math.abs(fAmt));

    if (ret.length() < iLen) {
      StringBuffer buf = new StringBuffer(ret);
      for (int i = 0; i < iLen - ret.length(); i++) {
        buf.insert(0, "0");
      }
      ret = buf.toString();
    }
    if (flag == -1) {
      char[] buf1 = ret.toCharArray();
      int i = 48;
      int j = 112;
      int k = buf1[(buf1.length - 1)];
      buf1[(buf1.length - 1)] = (char)(k - i + j);

      ret = new String(buf1);
    }
    return ret;
  }

  public static String COBOL_TO_NORMAL(String args, int len)
  {
    int flag = 1;
    String ret = "";
    if (args == "")
      throw new TdExprException("COBOL_TO_NORMAL");
    String amt = StringUtils.trim(args);
    if (amt == null) {
      return StringUtils.leftPad("", len, '0');
    }

    if ((amt.charAt(amt.length() - 1) >= 'p') && (amt.charAt(amt.length() - 1) <= 'y')) {
      char[] buf = amt.toCharArray();
      flag = -1;
      buf[(amt.length() - 1)] = (char)(buf[(amt.length() - 1)] - 'p' + 48);
      amt = new String(buf);
    }

    double dAmt = NumberUtils.toDouble(amt);
    DecimalFormat ft = new DecimalFormat("#");
    ret = ft.format(dAmt);
    StringBuffer buf1 = new StringBuffer(ret);
    if (ret.length() < len - 1) {
      for (int i = 0; i < len - 1 - ret.length(); i++) {
        buf1.insert(0, "0");
      }
      ret = buf1.toString();
    }
    if (flag == -1)
      buf1.insert(0, '-');
    else if (buf1.length() < len) {
      buf1.insert(0, '0');
    }

    return buf1.toString();
  }

  public static String NORMAL_TO_EBCD(String args, int len)
  {
    int flag = 1; int iLen = 0;
    String ret = "";
    double dNum = NumberUtils.toDouble(args);

    iLen = len;
    if (dNum < 0.0D)
      flag = -1;
    DecimalFormat ft = new DecimalFormat("#");
    ret = ft.format(Math.abs(dNum));
    char[] buf = ret.toCharArray();
    if (flag == -1) {
      if (buf[(buf.length - 1)] == '0')
        buf[(buf.length - 1)] = '}';
      else {
        buf[(buf.length - 1)] = (char)(buf[(buf.length - 1)] - '1' + 74);
      }
    }
    else if (buf[(buf.length - 1)] == '0')
      buf[(buf.length - 1)] = '{';
    else {
      buf[(buf.length - 1)] = (char)(buf[(buf.length - 1)] - '1' + 65);
    }

    ret = new String(buf);

    if (ret.length() < iLen) {
      StringBuffer retBuf = new StringBuffer(ret);
      for (int i = 0; i < iLen - ret.length(); i++) {
        retBuf.insert(0, '0');
      }
      ret = retBuf.toString();
    }

    return ret;
  }

  public static String AMTADDDOT(String args)
  {
    String str = args.trim();
    if ((StringUtils.isEmpty(args)) || (!NumberUtils.isNumber(str)))
      throw new TdExprException("AMTADDDOT");
    StringBuffer buf = new StringBuffer(str);
    int flag = 1;
    if (buf.charAt(0) == '-') {
      flag = -1;
      buf = buf.delete(0, 1);
    }
    if (buf.length() >= 3)
      buf.insert(buf.length() - 2, '.');
    else if (buf.length() == 2)
      buf.insert(0, "0.");
    else if (buf.length() == 1)
      buf.insert(0, "0.0");
    else if (buf.length() == 0) {
      buf.insert(0, '0');
    }
    if (flag == -1) {
      buf.insert(0, '-');
    }

    return buf.toString();
  }


  public static String AMTDELZERO(String agrs)
  {
    String amt = agrs.trim();
    if ((StringUtils.isEmpty(amt)) || (!StringUtils.isNumeric(amt))) {
      throw new TdExprException("AMTDELZERO");
    }
    double dou = NumberUtils.toDouble(amt);
    DecimalFormat ft = new DecimalFormat("#");
    return ft.format(dou);
  }

  public static String AMTFMT(String args)
  {
    String amt = args.trim();
    if ((StringUtils.isEmpty(amt)) || (!NumberUtils.isNumber(amt)))
      throw new TdExprException("AMTFMT");
    int flag = 1;

    amt = AMTDELZERO(amt);
    if (amt.charAt(0) == '-') {
      flag = -1;
      amt = amt.substring(1);
    }

    StringBuffer buf = new StringBuffer(amt);
    if (amt.length() < 3) {
      amt = AMTADDDOT(amt);
      buf.replace(0, buf.length(), amt);
      if (flag == -1) {
        buf.insert(0, '-');
      }
      return buf.toString();
    }

    int len = amt.length();
    int j;
    if ((len - 2) % 3 == 0)
      j = (len - 2) / 3 - 1;
    else {
      j = (len - 2) / 3;
    }

    for (int i = 0; i < j; i++) {
      buf.insert(len - 2 - (i + 1) * 3, ',');
    }
    buf.insert(buf.length() - 2, '.');
    if (flag == -1) {
      buf.insert(0, '-');
    }
    return buf.toString();
  }

  public static String AMTSIMPLEFMT(String args)
  {
    String amt = args.trim();
    if ((StringUtils.isEmpty(args)) || (!NumberUtils.isNumber(amt)))
      throw new TdExprException("AMTSIMPLEFMT");
    if ((StringUtils.contains(amt, '.')) || (StringUtils.contains(amt, ','))) {
      throw new TdExprException("AMTSIMPLEFMT 表达式不允许带有小数点或者逗号");
    }
    int flag = 1;
    amt = AMTDELZERO(amt);
    if (amt.charAt(0) == '-') {
      flag = -1;
      amt = amt.substring(1);
    }
    StringBuffer buf = new StringBuffer(amt);
    amt = AMTADDDOT(amt);
    buf = new StringBuffer(amt);
    if (flag == -1) {
      buf.insert(0, '-');
    }
    return buf.toString();
  }

  public static int CYCAMTFALSE(String args1, String args2)
  {
    String cyccod = args1.trim();
    String amt = args2.trim();
    if ((!NumberUtils.isNumber(amt)) || (StringUtils.isEmpty(cyccod))) {
      throw new TdExprException("CYCAMTFALSE");
    }
    int len = 0;
    int ret = 0;
    for (int i = 0; i < 2; i++) {
      if (amt.charAt(amt.length() - i - 1) != '0') {
        len++;
      }
    }
    if (((StringUtils.equals(cyccod, "ITL")) || (StringUtils.equals(cyccod, "JPY"))) && 
      (len != 0)) {
      ret = 1;
    }

    return ret;
  }

  public static String AMTPOWER(String args, int num)
  {
    if ((StringUtils.isEmpty(args)) || (!NumberUtils.isNumber(args))) {
      throw new TdExprException("AMTPOWER");
    }
    int idx = 1;
    for (int c = 0; c < num; c++)
      idx *= 10;
    long l = new BigDecimal(args.trim()).multiply(new BigDecimal(idx)).longValue();
    return String.valueOf(l);
  }

  public static String GETENV(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("GETENV");
    try
    {
      String value = System.getenv(args.trim());
      if (StringUtils.isEmpty(value)) {
        value = System.getProperty(args.trim());
      }
      return value; } catch (Throwable t) {
    }
    return System.getProperty(args.trim());
  }

  public static long ADDAMT(long args1, long args2)
  {
    return args1 + args2;
  }

  public static String DELCHAR(String args1, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2)))
      throw new TdExprException("DELCHAR");
    String value = args1;

    String delStr = TdConvHelper.asc2bin(args2);
    value = StringUtils.replaceOnce(value, delStr, "");
    return value;
  }

  public static String DELBOTHCHAR(String args1, String args2)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2))) {
      throw new TdExprException("DELBOTHCHAR");
    }
    String value = args1;

    String delStr = TdConvHelper.asc2bin(args2);
    value = StringUtils.removeStart(value, delStr);
    value = StringUtils.removeEnd(value, delStr);
    return value;
  }

  public static long SUBAMT(long args1, long args2)
  {
    return args1 - args2;
  }

  public static String HEX2STR(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("HEX2STR");
    try {
      return new String(Hex.decodeHex(args.toCharArray())); } catch (DecoderException e) {
    }
   // throw new TdExprException(e.getMessage());
	return args;
    
  }

  public static String STR2HEX(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("HEX2STR");
    return new String(Hex.encodeHex(args.getBytes()));
  }

  public static String REPCHAR(String args1, String args2, String args3)
  {
    if ((StringUtils.isEmpty(args1)) || (StringUtils.isEmpty(args2)) || (StringUtils.isEmpty(args3)))
      throw new TdExprException("REPCHAR");
    return StringUtils.replace(args1, args2, args3);
  }

  public static String EBCD_TO_NORMAL(String args, int len)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("EBCD_TO_NORMAL");
    if (StringUtils.isBlank(args)) {
      throw new TdExprException("novalid ebcd value[" + args + "]");
    }
    int outPutLen = len;

    String ebcd = args;

    char lab = ebcd.charAt(ebcd.length() - 1);
    String normal = ebcd.substring(0, ebcd.length() - 1);

    int index = EBCD_POSITIVE.indexOf(lab);
    if (index >= 0) {
      normal = normal + index;
      normal = StringUtils.leftPad(normal, outPutLen, '0');
      return normal;
    }

    index = EBCD_NEGATIVE.indexOf(lab);
    if (index >= 0) {
      normal = normal + index;

      normal = "-" + StringUtils.leftPad(normal, outPutLen - 1, '0');
      return normal;
    }

    throw new TdExprException("novalid ebcd value[" + ebcd + "]");
  }

  public static String BIN2HEX(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("BIN2HEX");
    try {
      return Integer.toHexString(Integer.valueOf(args.trim(), 2).intValue()); } catch (NumberFormatException e) {
    }
    throw new TdExprException("含有非二进制的数字");
  }

  public static String HEX2BIN(String args)
  {
    if (StringUtils.isEmpty(args))
      throw new TdExprException("HEX2BIN");
    String binStr = "";
    try {
      binStr = Integer.toBinaryString(Integer.valueOf(args.trim(), 16).intValue());
    } catch (NumberFormatException e) {
      throw new TdExprException("含有非十六进制的数字");
    }

    int tmpLen = 4 - binStr.length() % 4;
    if (tmpLen != 0) {
      binStr = StringUtils.repeat("0", tmpLen) + binStr;
    }
    return binStr;
  }

  public static String FADD(String[] args)
  {
    if ((args == null) || (args.length < 3)) {
      throw new TdExprException("FADD 参数个数不合法，表达式[FADD]至少有三个参数!");
    }
    int argNum = args.length;

    int precision = Integer.parseInt(args[(argNum - 1)].trim());
    double total = 0.0D;

    argNum--;
    for (int i = 0; i < argNum; i++) {
      total += Double.parseDouble(args[i].trim());
    }

    return TdExpBasicHelper.formatDouble(total, precision);
  }

  public static String FSUB(String[] args)
  {
    if (args.length < 3) {
      throw new TdExprException("FSUB");
    }
    int argNum = args.length;

    int precision = Integer.parseInt(args[(argNum - 1)]);
    double total = NumberUtils.toDouble(args[0]);

    argNum--;
    for (int i = 1; i < argNum; i++) {
      total -= NumberUtils.toDouble(args[i]);
    }

    return TdExpBasicHelper.formatDouble(total, precision);
  }

  public static String FMUL(String[] args)
  {
    if (args.length < 3) {
      throw new TdExprException("FMUL");
    }
    int argNum = args.length;

    int precision = NumberUtils.toInt(args[(argNum - 1)]);
    double total = NumberUtils.toDouble(args[0]);

    argNum--;
    for (int i = 1; i < argNum; i++)
    {
      total *= NumberUtils.toDouble(args[i]);
    }

    return TdExpBasicHelper.formatDouble(total, precision);
  }

  public static String FDIV(String[] args)
  {
    if (args.length < 3) {
      throw new TdExprException("FDIV");
    }
    int argNum = args.length;

    int precision = NumberUtils.toInt(args[(argNum - 1)]);
    double total = NumberUtils.toDouble(args[0]);

    argNum--;
    for (int i = 1; i < argNum; i++)
    {
      total /= NumberUtils.toDouble(args[i]);
    }

    return TdExpBasicHelper.formatDouble(total, precision);
  }

  public static String FPOW(String[] args)
  {
    if (args.length != 3)
      throw new TdExprException("FPOW");
    int precision = NumberUtils.toInt(args[2]);

    int times = NumberUtils.toInt(args[1]);
    double total = NumberUtils.toDouble(args[0]);
    double tol = 1.0D;

    for (int i = 0; i < times; i++) {
      tol *= total;
    }

    return TdExpBasicHelper.formatDouble(tol, precision);
  }

  public static String DATETOCAP(String args)
  {
    if ((StringUtils.isEmpty(args)) || (args.trim().length() != 8)) {
      throw new TdExprException("DATETOCAP 参数错误");
    }
    if (CHECKDATE(args) != 1) {
      throw new TdExprException(args.trim() + "不是合法的日期 ");
    }

    char[] dateArray = args.trim().toCharArray();
    int flag = 0;
    String year = ""; String month = ""; String day = "";
    for (int i = 0; i < 4; i++) {
      if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
        throw new TdExprException(args.trim());
      }
      flag = dateArray[i] - '0';
      year = year + DIGIT_UPPER.charAt(flag);
    }
    year = year + "年";

    for (int i = 4; i < 6; i++) {
      if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
        throw new TdExprException(args.trim());
      }
      flag = dateArray[i] - '0';
      month = month + DIGIT_UPPER.charAt(flag);
    }
    month = month + "月";

    for (int i = 6; i < 8; i++) {
      if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
        throw new TdExprException(args.trim());
      }
      flag = dateArray[i] - '0';
      day = day + DIGIT_UPPER.charAt(flag);
    }
    day = day + "日";
    return year + month + day;
  }

  public static String GETFILELINES(String[] args)
  {
    if (args.length < 1)
      throw new TdExprException("GETFILELINES");
    String file = args[0].trim();
    File filepath = new File(file);

    if (!filepath.isAbsolute()) {
      String root = GETENV("HOME");
      if (root != null) {
        if (root.endsWith("/"))
          file = file + root;
        else
          file = file + root + "/";
      }
      else {
        throw new TdExprException("HOME");
      }
    }

    int total = 0;
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      while (br.ready()) {
        br.readLine();
        total++;
      }
    } catch (FileNotFoundException e) {
      throw new TdExprException(file);
    } catch (IOException e) {
      throw new TdExprException(file);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return String.valueOf(total);
  }

  public static long GETFILESIZE(String args)
  {
    String file = args.trim();
    File filepath = new File(file);

    if (!filepath.isAbsolute()) {
      String root = GETENV("HOME");
      if (root != null)
        filepath = new File(root, file);
      else {
        throw new TdExprException("HOME");
      }
    }

    long total = 0L;

    total = filepath.length();

    return total;
  }

  public static String TODBC(String args)
  {
    if (args == null)
      return null;
    return TdExpBasicHelper.todbc(args);
  }

  public static String TOSBC(String args)
  {
    if (args == "")
      throw new TdExprException("TOSBC");
    if (args == null) {
      return null;
    }
    return TdExpBasicHelper.tosbc(args);
  }


  public static int IS_MATCH(String[] args)
  {
    if (args.length < 2)
      throw new TdExprException("IS_MATCH");
    boolean ignoreCase = true;
    if ((args.length == 3) && 
      ("1".equals(args[2]))) {
      ignoreCase = false;
    }

    Pattern p = null;

    if (ignoreCase)
      p = Pattern.compile(args[1]);
    else {
      p = Pattern.compile(args[1], 2);
    }
    Matcher m = p.matcher(args[0]);

    if (m.matches()) {
      return 1;
    }
    return 0;
  }

  public static String CONDITION3(String cond, String arg1, String arg2)
  {
    boolean r = cond.equals("1");

    return r ? arg1 : arg2;
  }

  public static int IsExistNode(String[] args)
  {
    if (args.length < 1)
      throw new TdExprException("IsExistNode");
    for (int i = 0; i < args.length; i++) {
      if ((args[i] == null) || ("".equals(args[i])))
        return 0;
    }
    return 1;
  }

  public static String RANDOM(int args1, String args2)
  {
    if (StringUtils.isEmpty(args2))
      throw new TdExprException("RANDOM");
    int len = args1;
    args2 = StringUtils.trim(args2);

    if (StringUtils.equals(args2, "0"))
      return RandomStringUtils.randomAlphanumeric(len);
    if (StringUtils.equals(args2, "1")) {
      return RandomStringUtils.randomAlphabetic(len);
    }
    return RandomStringUtils.randomNumeric(len);
  }

  public static int binary2Int(String bin)
    throws TdExprException
  {
    try
    {
      return Integer.valueOf(bin, 2).intValue();
    } catch (NumberFormatException e) {
    }
    throw new TdExprException("错误提示信息: 待转换的bin-[" + bin + "], 包含非法二进制形式.");
  }

  public static String binToAscStr(String binStr)
    throws TdExprException
  {
    return TdConvHelper.binToAscStr(binStr.getBytes());
  }

  public static String asc2bin(String strAsc)
    throws TdExprException
  {
    try
    {
      Integer deliInt = Integer.valueOf(strAsc);
      if ((deliInt.intValue() > 255) || (deliInt.intValue() < -128)) {
        throw new TdExprException("转换为bin有误, asc=[" + strAsc + "]");
      }

      byte[] asc = { deliInt.byteValue() };
      return new String(asc, "ISO-8859-1");
    }
    catch (NumberFormatException e)
    {
      throw new TdExprException("asc2bin执行出错, 一个ASCII值 + [" + strAsc + "],转为对应的字符时失败."); } catch (UnsupportedEncodingException e) {
    }
    throw new TdExprException("asc2bin执行出错, 不支持相应编码集.");
  }

  public static String asc2bin(int intAsc, int binLen)
    throws TdExprException
  {
    byte[] aryAsc = new byte[4];

    TdConvHelper.int2byte(aryAsc, 0, intAsc);
    String retStr = "";

    boolean start = true;
    for (int i = 0; i < 4; i++) {
      if ((aryAsc[i] == 0) && (start)) {
        continue;
      }
      start = false;
      retStr = retStr + (char)aryAsc[i];
    }

    if (retStr.length() < binLen) {
      char fill_asc = '\000';
      String fill_str = String.valueOf(fill_asc);

      retStr = StringUtils.repeat(fill_str, binLen - retStr.length()) + retStr;
    } else if (retStr.length() > binLen) {
      throw new TdExprException("ascii2bin, 超过指定的宽度 " + binLen);
    }

    return retStr;
  }

  public static String int2Binary(int val)
  {
    return Integer.toBinaryString(val);
  }

  public static String HexToDecimal(String val) {
    return String.valueOf(Integer.valueOf(val, 16));
  }

  public static String URLDECODER(String str) throws Exception {
    String res = URLDecoder.decode(str, "GBK");
    return res;
  }

  public static String URLECODERGBK(String str) throws Exception
  {
    return URLEncoder.encode(str, "GBK");
  }

  public static String URLDECODER(String str, String encoding) throws Exception {
    String res = URLDecoder.decode(str, encoding);
    return res;
  }

  public static String GETLENSTR(String len)
    throws IOException
  {
    len = len.trim();
    InputStream in = (InputStream)Context.peekInstance(InputStream.class);
    StringBuffer buf = new StringBuffer();
    int length = Integer.valueOf(len, 16).intValue() * 2;
    byte[] data = new byte[length];
    in.read(data);
    for (int i = 0; i < data.length; i++) {
      buf.append(String.format("%02X", new Object[] { Byte.valueOf(data[i]) }));
    }

    String result = HEX2STR(buf.toString());
    return result;
  }

  public static String PUTSTR2OUT(String str)
    throws IOException
  {
    OutputStream out = (OutputStream)Context.peekInstance(OutputStream.class);
    out.write(str.getBytes());
    return "";
  }
  
}