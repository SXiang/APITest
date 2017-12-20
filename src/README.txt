This is a JUNIT tests created for webdamdb API 
*   Test Url:  http://interview-testing-api.webdamdb.com
*   Tester Name: Steve Xiang
*   Email: sxiang099@gmail.com
   
IDE and libs used:
*  Eclipse IDE Version: Mars Release (4.5.0)
*  JRE System Library: JavaSE-1.8 
*  (Main)Referenced Libraries:
*    junit-4.12.jar
*    httpclient-4.5.2.jar
*    json-20171018.jar
*    junit-dataprovider-1.13.1.jar

Test Files:
*. JUNIT Test Class: webdamdb.testcase.BugVerification.java
*. Helper Classes:
          webdamdb.helper.APIHelper.java
          webdamdb.helper.JSONHelper.java
*. Bug Report: BugReport.csv

Import to Eclipse project from webdamdbTest.jar file
1.    Start Eclipse and navigate to your workspace.
2.    Create a new project named as 'JUnitAPITest' or any other name you choose.
4.    Right-click the project name and select Import…
5.    In the Import dialog, expand General and select Archive File. Click Next.
6.    Browse for the webdamdbTest.jar file and click Open.
7.     Click Finish and the project should import correctly.

Run Test:
Before running the test, checking/updating these 2 values in webdamdb.helper.APIHelper.java
 *	private static String clientId = " client id ";
 *	private static String clientSecret = "client secret";
 * OR update token if you have a valid one
 *    private static String token = "a valid token"
 
Option 1:  Run as Java application: webdamdb.testcase. BugVerification.java has a main method to run all the tests and output failures.
Option 2:  Run as JUIT test: with java IDE, choose run as Junit Test.


~~~~~~~~~~~~~~~~~~~~~ Test Failures ~~~~~~~~~~~~~~~~~~~~~
1. searchQueryShouldBeHandledInAReasonableTime(webdamdb.testcase.BugVerification): test timed out after 10000 milliseconds
2. validModifiedTimeAndCreatedTimeShouldbeAddedToAssets[0: 12341](webdamdb.testcase.BugVerification): Modified time should be greater or equal to created time
3. validModifiedTimeAndCreatedTimeShouldbeAddedToAssets[2: 12343](webdamdb.testcase.BugVerification): Modified time should be greater or equal to created time
4. validModifiedTimeAndCreatedTimeShouldbeAddedToAssets[3: 12344](webdamdb.testcase.BugVerification): date_created should be added to asset 
5. validThumbnailLinkShouldBeAddedToAsset(webdamdb.testcase.BugVerification): Invalid link to thumbnail found for asset(s): 
		http://interview-testing-api.webdamdb.com/images/apples.jpg