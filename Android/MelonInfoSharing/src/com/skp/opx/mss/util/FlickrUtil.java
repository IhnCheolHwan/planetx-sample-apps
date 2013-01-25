package com.skp.opx.mss.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.skp.opx.mss.ui.R;

public class FlickrUtil {

	private static HttpClient 		mClient;                                      
	private static HttpGet 			mGetMethod;
	public static ImageLoader 		imageLoader = ImageLoader.getInstance();
	private static List<String> title 				= new ArrayList<String>();          	// ����Ʈ�� ����� ���� 
	public static List<String> thumbnailUrl 		= new ArrayList<String>(); 		// ����� URL ���� ������� �����ϱ� ���� ��ü
	public static List<String> videoUrl 			= new ArrayList<String>();			// ����� ���� ������ URL �� ������� ����
	public static DisplayImageOptions 	options;

	public static void imageCacheIn (Context context) {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPoolSize(3)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.memoryCacheSize(1500000) // 1.5 Mb
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.enableLogging() // Not necessary in common
		.build();
		// �̹��� �δ� �ʱ�ȭ
		ImageLoader.getInstance().init(config);	
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.stub_image)
		.showImageForEmptyUri(R.drawable.image_for_empty_url)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
	}

	public static void getYoutubeData(String keyword) {          
		String encodeKeyword = "";		

		try {
			//���� keyword �� ��Ʃ��� ������ ���� utf-8 �� ���ڵ�
			encodeKeyword = URLEncoder.encode(keyword, "UTF-8");      
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 

		String uri = "http://gdata.youtube.com/feeds/api/videos?vq=" + encodeKeyword + "&max-results=48";  
		mClient = new DefaultHttpClient();          
		mGetMethod = new HttpGet();  
		HttpResponse resp = null;

		try {       
			// ��Ʃ�꿡 �˻��� ��û�ϱ� ���� URI �� �غ��ϰ�
			mGetMethod.setURI(new URI(uri));              
		} catch (URISyntaxException e) {  
			e.printStackTrace(); 
		}  

		try {              
			 // ��Ʃ��� �˻��� ��û(request) �Ѵ�.
			resp = mClient.execute(mGetMethod);      
		} catch (ClientProtocolException e) {               
			e.printStackTrace();           
		} catch (IOException e) {              
			e.printStackTrace();          
		}                      

		if (resp.getStatusLine().getStatusCode() == 200) {    
			try {   
				//Response Stream
				InputStream is = resp.getEntity().getContent();  
				try {
					title.clear();
					thumbnailUrl.clear();
					videoUrl.clear();

					//Stream Parsing...
					URL url = new URL(uri);
					is = url.openConnection().getInputStream();

					String nameSpace = null; 
					String parserName = null;                       //  Youtube XML �������� �� �κе��� �̸�
					String value;                                        //  Youtube XML �������� �� �κе��� ��
					Integer depth = 0;       
					XmlPullParser parser = Xml.newPullParser();
					parser.setInput(new InputStreamReader(is)); 
					// boolean bThumbnail = false;
					String szTempTitle = null;
					String szOrigTitle = null;

					for (int e = parser.getEventType(); e != parser.END_DOCUMENT; e = parser.next()) {           
						depth = parser.getDepth();                      // xml �������� ������ �����ɴϴ�.
						switch(e) {               
						case XmlPullParser.START_TAG:           	   // START_TAG �κ��� ���       
							parserName = parser.getName();  

							if (depth == 4) {
								// ������ 1���� 3-4���� thumbnail. ���� 1���� thumbnail�� ����
								if (parserName.equals("thumbnail")) {
									//������ thumbnail ����. �������� Ÿ��Ʋ�� �ٲ��� ������ �� ���� �������̶�� ����.
									if(szTempTitle != szOrigTitle) {
										//�� �ȿ� ���Դٸ� ������ ������̶� ���̹Ƿ� �� �� ������ �����մϴ�.    
										szTempTitle = szOrigTitle;                 
										// ����� �ּҸ� �޾Ƽ�  
										String urlth = parser.getAttributeValue(nameSpace, "url");
										//����� List �� �����մϴ�.
										thumbnailUrl.add(urlth);                        
									}        
								} else if (parserName.equals("player")) {                       
									String urlp = parser.getAttributeValue(nameSpace, "url");   // �������ּ�
									videoUrl.add(urlp);
								}                     
							}              
							break;              

						case XmlPullParser.TEXT: // XML ���� ������ ���� �� �ƴ� �ؽ�Ʈ �κе�
							value = null;        
							value = parser.getText();  
							if (depth == 3) {                   
								if (parserName.equals("published")) { 
									int a = 0; 
								} else if (parserName.equals("updated")) {
									int a = 0; 
								}
								
								if (parserName.equals("title")) {
									szOrigTitle = value;
									title.add(value);
								}                 
							}              
							break;         

						case XmlPullParser.END_TAG:
							parserName = parser.getName();      
							if (parserName.equals("entry")) {                    
								//retEntries.add(entry);   
							}
							break; 
						}
					}  

				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}     
				is.close();

			} catch (IllegalStateException e) {                   
				e.printStackTrace();               
			} catch (IOException e) {             
				e.printStackTrace();               
			}     
		}  
	}
}
