package com.avit.itdap.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CommonUtil {
	
	

	/**
	 * 获取文件编码格式
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
    public static String getCharset(File fileName) throws IOException {

        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        byte[] b = new byte[10];
        bin.read(b, 0, b.length);

        String first = toHex(b);

        String code = null;
        if (first.startsWith("EFBBBF")) {
            code = "UTF-8";
        } else if (first.startsWith("FEFF00")) {
            code = "UTF-16BE";
        } else if (first.startsWith("FFFE")) {
            code = "Unicode";
        } else if (first.startsWith("FFFE")) {
            code = "Unicode";
        } else {
            code = "GBK";
        }
        return code;
    }
    
    public static String toHex(byte[] byteArray) {
        int i;
        StringBuffer buf = new StringBuffer("");
        int len = byteArray.length;

        for (int offset = 0; offset < len; offset++) {

            i = byteArray[offset];

            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");

            buf.append(Integer.toHexString(i));
        }

        return buf.toString().toUpperCase();
    }
    
    public static long getDuration(String durationString)
    {
    	
    	char [] durationArray=durationString.toCharArray();
		int timeLine=1;
		long duration=0l;
		for(int i=durationArray.length-1;i>=0;i--)
		{
			if(i==durationArray.length-1)
			{
				timeLine=1;
			}else if(i%2==0)
			{
				timeLine=timeLine*10;
			}else
			{
				timeLine=timeLine*6;
			}
			duration+=timeLine*Integer.valueOf(String.valueOf(durationArray[i]));
		}
		return duration;
    }
    public static void main(String[] args) {
    	System.out.println(getDuration("013000"));;
	}

}
