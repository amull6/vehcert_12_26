package com.cqcca.jni;
//package QR;

public class QR

{
	{
		try{
			System.loadLibrary("QRCom_x64");
		}catch (Exception e) {
			System.out.println("QRCom_x64.dll!");
		}
	}
	
	public native String  GenQR(String in);
	public native String  DESEncrypt(String in);
	public native String  DESDecrypt(String in);


	public static void main(String[] args)
	{
		QR c = new QR();
		String strRet = c.GenQR("1234");
		strRet = c.DESEncrypt("12345678");
		System.out.print(strRet);
		strRet = c.DESDecrypt(strRet);
		System.out.print(strRet);
	}
}