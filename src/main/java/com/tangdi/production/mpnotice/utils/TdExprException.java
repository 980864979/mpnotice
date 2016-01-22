package com.tangdi.production.mpnotice.utils;

public class TdExprException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public TdExprException()
  {
  }

  public TdExprException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public TdExprException(String paramString)
  {
    super(paramString);
  }

  public TdExprException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}