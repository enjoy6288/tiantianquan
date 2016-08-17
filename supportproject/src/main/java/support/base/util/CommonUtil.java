package support.base.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtil {
	public static void VoToPo(Object vo, Object desc) {
		try {
			Class descClass = desc.getClass();
			Class voClass = vo.getClass();
			Field[] voFields = voClass.getDeclaredFields();

			Method getMethod = null;
			Method setMethod = null;
			String fileType = null;
			String fileName = null;
			Field declaredField = null;
			for (Field field : voFields) {
				fileName = field.getName();
				declaredField = descClass.getDeclaredField(fileName);
				fileType = declaredField.getGenericType().toString();
				if (fileName.length() >= 2
						&& Character.isLowerCase(fileName.charAt(1))) {
					fileName = Character.toUpperCase(fileName.charAt(0))
							+ fileName.substring(1);
				}
				getMethod = vo.getClass().getMethod("get" + fileName);
				if (fileType.equals("class java.lang.Long")) {
					setMethod = descClass.getMethod("set" + fileName,
							new Class[] { Long.class });
					Object object = getMethod.invoke(vo);
					if (object != null && !StringUtils.isEmpty(object)) {
						Long value = new Long(object + "");
						setMethod.invoke(desc, value);
					}
				} else if (fileType.equals("class java.lang.String")) {
					setMethod = descClass.getMethod("set" + fileName,
							new Class[] { String.class });
					String value = null;
					if (getMethod.invoke(vo) != null) {
						value = getMethod.invoke(vo) + "";
					}
					setMethod.invoke(desc, value);
				} else if (fileType.equals("class java.lang.Integer")) {
					setMethod = descClass.getMethod("set" + fileName,
							new Class[] { Integer.class });
					Object object = getMethod.invoke(vo);
					if (object != null && !StringUtils.isEmpty(object)) {
						Integer value = new Integer(object + "");
						setMethod.invoke(desc, value);
					}
				} else if (fileType.equals("class java.lang.Byte")) {
					setMethod = descClass.getMethod("set" + fileName,
							new Class[] { Byte.class });
					Object object = getMethod.invoke(vo);
					if (object != null && !StringUtils.isEmpty(object)) {
						Byte value = new Byte(getMethod.invoke(vo) + "");
						setMethod.invoke(desc, value);
					}
				} else if (fileType.equals("class java.util.Date")) {
					setMethod = descClass.getMethod("set" + fileName,
							new Class[] { Date.class });
					// String value = (String) getMethod.invoke(src);
					Object object = getMethod.invoke(vo);
					if (object != null) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						Date date = sdf.parse(object + " 08:00");
						setMethod.invoke(desc, date);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static String sendMsg(String phoneNum,String msg){
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {  
        	String msgUrl = SpringPropertyUtil.getContextProperty(Constant.MSG_URL);
            HttpPost httpPost = new HttpPost(msgUrl);
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk"); 
            List<NameValuePair> params=new ArrayList<NameValuePair>();
    		//建立一个NameValuePair数组，用于存储欲传送的参数
    		params.add(new BasicNameValuePair("userId","JC2195"));
    		params.add(new BasicNameValuePair("password","232110"));
    		params.add(new BasicNameValuePair("pszMobis",phoneNum));
    		params.add(new BasicNameValuePair("pszMsg",msg));
    		params.add(new BasicNameValuePair("iMobiCount","1"));
    		params.add(new BasicNameValuePair("pszSubPort","*"));
    		params.add(new BasicNameValuePair("MsgId","9113362036854775807"));
    		//添加参数
    		httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
            //执行请求 
            CloseableHttpResponse response = httpclient.execute(httpPost);  
            StatusLine statusLine = response.getStatusLine();
            msg=statusLine.toString();
            response.close();  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
		return msg;
		
	}
	

	public static String upload(MultipartFile img,String position) {
		String imgPath = "";
		String realPath = SpringPropertyUtil.getContextProperty(position);
		try {
			if (img != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy_MM_dd_HH_mm_ss");
				String format = sdf.format(new Date());
				imgPath =format + ".jpg";
				realPath=realPath+imgPath;
				imgPath=SpringPropertyUtil.getContextProperty(Constant.IMG_PREFIX)+imgPath;
				File localFile = new File(realPath);
				if (!localFile.exists()) {
					localFile.mkdirs();
				}
				// 写文件到本地
				img.transferTo(localFile);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return imgPath;
	}
}
