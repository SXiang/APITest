package api.testcase;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import api.helper.APIHelper;
import api.helper.JSONHelper;

import static org.junit.Assert.*;
import java.util.List;
import org.apache.http.*;

/**
 * @author Steve Xiang
 * @date 10/31/2017
 */

@RunWith(DataProviderRunner.class)
public class BugVerification {

	private static final String LOGIN_PATH = "/api/v1/login";
	private static final String LOGOUT_PATH = "/api/v1/logout";	
	private static final String SEARCH_PATH = "/api/v1/search";	
	private static final String ASSET_PATH = "/api/v1/asset";
	private static HttpResponse httpResponse;

	@BeforeClass  
	public static void setUpBeforeClass() throws Exception {
		// Setup HttpClient with a valid token
		APIHelper.setHttpClient();  
	}  

	@DataProvider
	public static Object[][] assetId(){
		return new Object[][]{{"12341"},{"12342"},{"12343"},{"12344"},{"12345"}};
	}

	/* Before running the test, make sure your are using valid CLIENT_ID and CLIENT_SECRET in webdamdb.helper.APIHelper.java
	 * private static String CLIENT_ID = "client id";
	 * private static String CLIENT_SECRET = "client secret";
	 * OR update token if you have a valid one
	 *    private static String token = "a valid token"
	 */

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(BugVerification.class);
		System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~ Test Failures ~~~~~~~~~~~~~~~~~~~~~");
		int num = 0;
		for (Failure failure : result.getFailures()) {
			System.out.println(++num+". "+failure.toString());
		}
	}

	@Test
	@UseDataProvider("assetId")
	public void validModifiedTimeAndCreatedTimeShouldbeAddedToAssets(final String asset_id) throws Exception{
		String queryString = "/"+asset_id;
		httpResponse = APIHelper.sendGet(ASSET_PATH, queryString);
		String bodyText = APIHelper.getBodyText(httpResponse);
		int dateModified = JSONHelper.getListIntegerFromArray(bodyText, "date_modified").get(0);
		int dateCreated = JSONHelper.getListIntegerFromArray(bodyText, "date_created").get(0);
		assertTrue("date_modified should be added to asset ", dateModified > 0);
		assertTrue("date_created should be added to asset ", dateCreated > 0);
		assertTrue("Modified time should be greater or equal to created time",dateModified >= dateCreated);
	}

	@Test
	public void validThumbnailLinkShouldBeAddedToAsset() throws Exception{
		httpResponse = APIHelper.sendGet(ASSET_PATH);
		String bodyText = APIHelper.getBodyText(httpResponse);
		List<String> thumbnailLinks = JSONHelper.getListStringFromArray(bodyText, "thumbnail");
		String err = "";
		for(int i=0; i<thumbnailLinks.size(); i++){
			String link = thumbnailLinks.get(i);
			httpResponse = APIHelper.sendHeadWithURL(link);
			if(httpResponse.getStatusLine().getStatusCode()!=200){
				err += "\n\t\t"+link;
			}
		}
		if(!err.isEmpty()){
			fail("Invalid link to thumbnail found for asset(s): "+err);
		}
	}

	@Test(timeout = 10000)
	public void searchQueryShouldBeHandledInAReasonableTime() throws Exception{
		String queryString = "?query=apple%20and%20a%3Da";// Encoded from "?query=apple and a=a";
		httpResponse = APIHelper.sendGet(SEARCH_PATH, queryString);
	}

}