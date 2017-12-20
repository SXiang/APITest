package webdamdb.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 * @author Steve Xiang
 * @date 10/31/2017
 */

public class APIHelper {
	// Update CLIENT_ID and CLIENT_SECRET if they are expired
	private static final String CLIENT_ID = "4";
	private static final String CLIENT_SECRET = "4ovGa5yXfHnWR47wGRVUfKlDTBxC3WQtnkmO5sgs";
	private static final String TEST_URL = "http://interview-testing-api.webdamdb.com";
	private static final String TOKEN_PATH = "/oauth/token";
	private static final String ENCODING = "UTF-8";
	private static final String CONTENT_TYPE = "application/json";

	private static HttpClient client;
	private static String token = "";
	private static String getClientJsonForToken(){
		String contentToPost = "{\"grant_type\":\"client_credentials\","+
				"\"client_id\":\""+CLIENT_ID+"\","+
				"\"client_secret\":\""+CLIENT_SECRET+"\"}";
		return contentToPost;
	}

	public static void setHttpClient() throws ClientProtocolException, IOException{
		// Set token verification for debugging purpose.
		if(isValidToken()){
			return;
		}
		refreshClientToken();
	}

	public static void refreshClientToken() throws ClientProtocolException, IOException{
		HttpResponse httpResponse = sendPost(TOKEN_PATH, getClientJsonForToken());
		String bodyText = getBodyText(httpResponse);
		token = JSONHelper.getString(bodyText, "token_type") +
				" "+JSONHelper.getString(bodyText, "access_token");
		System.out.println("[Updated]Authorization: "+token);
		setClientDefaultHeader();
	}

	private static boolean isValidToken() throws ClientProtocolException, IOException{
		setClientDefaultHeader();
		HttpResponse httpResponse = sendHead("/api/v1/login");
		return httpResponse.getStatusLine().getStatusCode()==202;
	}

	private static void setClientDefaultHeader(){
		List<Header> headers = new ArrayList<>();
		Header content_type = new BasicHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
		Header accept = new BasicHeader(HttpHeaders.ACCEPT, CONTENT_TYPE);
		Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, token);		
		headers.add(content_type);
		headers.add(accept);
		headers.add(authorization);
		client = HttpClients.custom().setDefaultHeaders(headers).build();
	}

	public static HttpResponse sendPost(String path) throws ClientProtocolException, IOException{
		return sendPost(path,"");
	}

	public static HttpResponse sendGet(String path) throws ClientProtocolException, IOException{
		return sendGet(path, "");
	}

	public static HttpResponse sendHead(String path) throws ClientProtocolException, IOException{
		return sendHead(path, "");
	}

	public static HttpResponse sendPost(String path, String jsonContent) throws ClientProtocolException, IOException{
		StringEntity entity = new StringEntity(jsonContent);
		HttpPost httpPost = new HttpPost(TEST_URL+path);
		if(!jsonContent.isEmpty()){
			httpPost.setEntity(entity);
		}
		return client.execute(httpPost);
	}

	public static HttpResponse sendGet(String path, String queryString) throws ClientProtocolException, IOException{
		HttpGet httpGet = new HttpGet(TEST_URL+path+queryString);
		return client.execute(httpGet);
	}

	public static HttpResponse sendHead(String path, String queryString) throws ClientProtocolException, IOException{
		HttpHead httpHead = new HttpHead(TEST_URL+path+queryString);
		return client.execute(httpHead);
	}

	public static HttpResponse sendHeadWithURL(String url) throws ClientProtocolException, IOException{
		HttpHead httpHead = new HttpHead(url);
		return client.execute(httpHead);
	}

	public static String getBodyText(HttpResponse httpResponse) throws UnsupportedOperationException, IOException{
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream in = httpEntity.getContent();
		String body = IOUtils.toString(in, ENCODING);
		return body;		
	}

}
