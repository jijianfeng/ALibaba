package com.jjf.crawler;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.PrintStream;
import java.net.MalformedURLException;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
import java.util.zip.GZIPInputStream;  
  







import org.apache.commons.io.IOUtils;  
import org.apache.http.Header;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
public class HttpClientRequestHandler {
	private static final int ERROR_CODE = 1;  
	  
    public static void main(String args[]) throws Exception{
    	String prixyIp = "112.227.50.183";
    	Integer proxyPort = 9999;
    	String html =  doGet("http://www.baidu.com/s?wd=ip",prixyIp,proxyPort).html();
//    	writeString(html,"C:/result.txt");
    }
	
	
    /** 
     * get��ʽ�ύ���� 
     */  
    public static Document doGet(String url, String proxyIp, Integer proxyPort) throws Exception{  
        //System.out.println("doGet��ʹ�ô���"+proxyIp+":"+proxyPort);  
        HttpClient client = HttpConnectionManager.getHttpClient();//getHttpClientWithProxy(proxyIp,proxyPort);  
        HttpGet httpGet = new HttpGet(url);  
        httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");  
        httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");  
//        httpGet.setHeader("User-Agent", HttpUserAgent.get()); 
          
        try{  
            //ִ��  
            HttpResponse response = client.execute(httpGet);  
            int statuCode = response.getStatusLine().getStatusCode();  
            if(statuCode == 200){  
                String html = formatResponse(response);  
                  
                if(html != null){  
                    return Jsoup.parse(html);  
                }  
                return null;  
                  
            } else {  
                throw new Exception(statuCode+"����URL��"+url+"����"+statuCode+"����");  
            }  
        } catch (Exception e){  
            throw new Exception(ERROR_CODE+e.getMessage());  
        } finally {  
            if(httpGet != null){  
                httpGet.abort();  
            }  
        }  
    }  
      
  
    /**  
     * post��ʽ�ύ  
     * @throws DataTaskException   
     */  
    public static Document doPost(String url, Map<String,String> paramaters, String proxyIp, Integer proxyPort) throws Exception{  
          
        HttpClient client = HttpConnectionManager.getHttpClient();  
          
        HttpPost request = new HttpPost(url);  
          
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");  
        request.setHeader("Accept-Encoding", "gzip, deflate");  
        request.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");  
        request.setHeader("Cache-Control", "no-cache");  
        request.setHeader("Connection", "keep-alive");  
        request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
  
        // ������/ֵ���б�  
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
        for(String key : paramaters.keySet()){  
            parameters.add(new BasicNameValuePair(key, paramaters.get(key)));  
        }  
          
        try {  
            // ����UrlEncodedFormEntity����  
            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters);  
            request.setEntity(formEntiry);  
              
            // ִ������  
            HttpResponse response = client.execute(request);  
            int statuCode = response.getStatusLine().getStatusCode();  
              
            if (statuCode == 200) {  
                String html = formatResponse(response);  
                  
                if(html != null){  
                    return Jsoup.parse(html);  
                }  
                          
                return null;  
  
            } else if (statuCode == 404) {  
                throw new Exception(ERROR_CODE+"����URL��"+url+"����404����");  
            }  
        } catch (Exception e) {  
              
            throw new Exception(ERROR_CODE+ e.getMessage());  
          
        } finally {  
            if(request != null){  
                request.abort();  
            }  
        }  
        return null;  
    }  
      
    /**  
     * ��ʽ��������  
     * @throws DataTaskException   
     */  
    private static String formatResponse(HttpResponse response) throws Exception {  
          
        ByteArrayInputStream bis = null;  
        try{  
            Header contentEncoding = response.getFirstHeader("Content-Encoding");  
              
            if(contentEncoding == null){  
                return EntityUtils.toString(response.getEntity(),"UTF-8");  
            } else {  
                  
                String charset = "utf-8";  
                Header contentType = response.getFirstHeader("Content-Type");  
                  
                if(contentType != null){  
                    String contentTypeStr = contentType.getValue();  
                    if(contentTypeStr != null && !"".equals(contentTypeStr)){  
                        charset = contentTypeStr.substring(contentTypeStr.indexOf("=") + 1,contentTypeStr.length());  
                          
                    }  
                }  
                  
                String contentEncodingType = contentEncoding.getValue();  
                if(contentEncodingType.equalsIgnoreCase("gzip")){  
                    if(response.toString().contains("soufun"))  
                        charset = "gb2312";  
                      
                    byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());  
                    bis = new ByteArrayInputStream(bytes);  
                      
                    return uncompress(bis ,charset);  
                }  
                  
            }  
              
        } catch(Exception e) {  
            throw new Exception(ERROR_CODE+"��ʽ��HttpResponse����");  
        } finally {  
            if(bis != null){  
                try {  
                    bis.close();  
                } catch (IOException e) {  
                    throw new Exception(ERROR_CODE+"��ʽ��HttpResponse����");  
                }  
            }  
        }  
          
        return null;  
    }  
  
      
    /** 
     * GZIP��ѹ 
     */  
    private static String uncompress(ByteArrayInputStream in, String charset) {  
  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
          
        try {  
            GZIPInputStream gunzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[256];  
            int n;  
            while((n = gunzip.read(buffer)) >=0 ){  
                out.write(buffer, 0, n);  
            }  
            return out.toString(charset);  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
}
