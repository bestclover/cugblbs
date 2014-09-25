package com.zd.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.zd.util.ProgressDialog;;

public class UploadUtil {
	private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 100*1000;   //��ʱʱ��
    private static final String CHARSET = "utf-8"; //���ñ���
    /**
     * android�ϴ��ļ���������
     * @param file  ��Ҫ�ϴ����ļ�
     * @param RequestURL  �����rul
     * @return  ������Ӧ������
     */
    public static String uploadFile(File file,String RequestURL,Handler handler)
    {
        String result = null;
        String  BOUNDARY =  UUID.randomUUID().toString();  //�߽��ʶ   �������
        String PREFIX = "--" , LINE_END = "\r\n"; 
        String CONTENT_TYPE = "multipart/form-data";   //��������
        
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //����������
            conn.setDoOutput(true); //���������
            conn.setUseCaches(false);  //������ʹ�û���
            conn.setRequestMethod("POST");  //����ʽ
            conn.setRequestProperty("Charset", CHARSET);  //���ñ���
            conn.setRequestProperty("connection", "keep-alive");   
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 
            
            if(file!=null)
            {
                /**
                 * ���ļ���Ϊ�գ����ļ���װ�����ϴ�
                 */
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * �����ص�ע�⣺
                 * name�����ֵΪ����������Ҫkey   ֻ�����key �ſ��Եõ���Ӧ���ļ�
                 * filename���ļ������֣�������׺����   ����:abc.png  
                 */
                
                sb.append("Content-Disposition: form-data; name=\"application\"; filename=\""+file.getName()+"\""+LINE_END); 
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                
                FileInputStream is = new FileInputStream(file);
                
                byte[] bytes = new byte[1024];
                int len = 0;
                
                long totalLen = 0;
                
                while((len=is.read(bytes))!=-1)	//���ļ����������϶�
                {
                    dos.write(bytes, 0, len);	//д����
                    
                    totalLen += len;
                    
//                    Log.i("<<<<<<<<<<<<<", "nowStaturs="+totalLen);
                    
                    Message msg = new Message();//�޸Ľ���Ľ�����
                    msg.what = 1;
                    msg.arg1 = (int) (ProgressDialog.MAX_PROGRESS*totalLen/is.getChannel().size());
                    handler.sendMessage(msg);
                    
                    
                }
                
                Log.i("<<<<<<<<<<<<<>>>>", ""+is.getChannel().size());
                Log.i("<<<<<<<<<<<<<>>>>", ""+file.length());
                Log.i("<<<<<<<<<<<<<>>>>", ""+totalLen);
                
                
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                dos.close();
                /**
                 * ��ȡ��Ӧ��  200=�ɹ�
                 * ����Ӧ�ɹ�����ȡ��Ӧ����  
                 */
                int res = conn.getResponseCode();  
                Log.e(TAG, "response code:"+res);
//                if(res==200)
//                {
                    Log.e(TAG, "request success");
                    InputStream input =  conn.getInputStream();
                    StringBuffer sb1= new StringBuffer();
                    int ss ;
                    while((ss=input.read())!=-1)
                    {
                        sb1.append((char)ss);
                    }
                    result = sb1.toString();
                    Log.e(TAG, "result : "+ result);
//                }
//                else{
//                    Log.e(TAG, "request error");
//                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
