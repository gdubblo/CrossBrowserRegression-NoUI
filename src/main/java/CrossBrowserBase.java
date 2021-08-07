import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.net.HttpURLConnection;
import java.net.URL;

public class CrossBrowserBase {

    protected String site;
    protected String testrailpass = "P@ss1word21Q3";
    protected String testrailuser = "111377";
    protected int numImages = 6;
    protected String expectedWelcomeTitle = "Welcome";
    protected int expectedLinks = 43;
    WebDriver driver;

    public CrossBrowserBase(String testSite) {
        this.site = testSite;
    }

    public void setDriver(WebDriver driver){
        this.driver = driver;
    }

    public void getDriver(){
    }

    public String getSite(){ return site; }

    public void ConfigureChromeDriver(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("headless");

        System.out.println("\n********************************");
        System.out.println("\n******Starting Chrome Test******");
        System.out.println("\n********************************");
        setDriver(driver = new ChromeDriver(chromeOptions));
    }

    public void ConfigureEdgeDriver(){
        WebDriverManager.edgedriver().setup();
        //Creating object of EdgeDriver
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("headless");


        System.out.println("\n********************************");
        System.out.println("\n******Starting Edge Test******");
        System.out.println("\n********************************");
        setDriver(driver = new EdgeDriver(options));
    }

    public void ConfigureFirefoxDriver(){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);

        System.out.println("\n********************************");
        System.out.println("\n******Starting Firefox Test******");
        System.out.println("\n********************************");
        setDriver(driver = new FirefoxDriver(options));
    }


    public void runTests()  {

        try {
            getDriver();
            //1. Launch site and maximize
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.get(getSite());


            //2. Test: Check login page title with the expected one and print
            WebElement loginBox = driver.findElement(By.xpath("/html/body/form/main/article/article/section/div/div/div[2]"));

            if (loginBox.isDisplayed()) {
                System.out.println("Login screen displays");
                addPassResultForTestCase();
            } else {
                System.out.println("Login screen does not display");
                addFailResultForTestCase();
            }

            //3. Decline cookie consent and continue
            System.out.println("\n******Validating Presence of Cookie Consent on Login Screen******");
            declineCookieConsent();

            //4. Check presence of images on login screen
            System.out.println("\n******Validating Images on Login Screen******");
            List<WebElement> total_images = driver.findElements(By.tagName("img"));
            System.out.println("Total Number of images found on page = " + total_images.size());
            if (total_images.size() == numImages) {
                System.out.println("Correct number of images on page: " + numImages);
                addPassResultForTestCase();
            }else {
                System.out.println("Incorrect number of images on page. Should be " + numImages);
                addFailResultForTestCase();
            }


            //5. Start login process
            System.out.println("\n******Start login process******");
            if(driver.findElement(By.id("CybotCookiebotDialogBody")).isDisplayed()){
                declineCookieConsent();
                loginProcess();
            }
            else {
                loginProcess();
            }

            //6. Test: Check Welcome page title with the expected one and print
            System.out.println("\n******Validating presence of Welcome page******");
            String actualWelcomeTitle = driver.findElement(By.xpath("/html/body/form/main/article/div/section/span")).getText();

            if (actualWelcomeTitle.contains(expectedWelcomeTitle)) {
                System.out.println("Correct Welcome page title displays: " + actualWelcomeTitle);
                addPassResultForTestCase();
            } else {
                System.out.println("Incorrect Welcome page title displays: " + actualWelcomeTitle);
                addFailResultForTestCase();
            }

            //7. Test: Header logo
            System.out.println("\n******Validating presence of client logo******");
            WebElement elementHeaderLogo= driver.findElement(By.xpath("/html/body/form/main/article/div/div[1]/header/div/a/img"));
            if (elementHeaderLogo.isDisplayed()) {
                System.out.println("Header logo displays");
                addPassResultForTestCase();
            }else{
                System.out.println("Header logo does not display");
                addFailResultForTestCase();
            }

            //8. Test: Check presence of site nav bar on Welcome page
            System.out.println("\n******Validating presence of site nav bar*****");
            WebElement elementSiteNav= driver.findElement(By.xpath("/html/body/form/main/article/div/div[2]/nav"));
            if (elementSiteNav.isDisplayed()) {
                System.out.println("Site nav bar displays");
                addPassResultForTestCase();
            }else{
                System.out.println("Site nav bar does not display");
                addFailResultForTestCase();
            }

            //9. Test: Check presence of plan selection container on Welcome page
            System.out.println("\n******Validating presence of plan selection container*****");
            WebElement elementPlanSelection= driver.findElement(By.xpath("/html/body/form/main/article/div/section/div"));
            if (elementPlanSelection.isDisplayed()) {
                System.out.println("Plan selection container displays");
                addPassResultForTestCase();
            }else{
                System.out.println("Plan selection container does not display");
                addFailResultForTestCase();
            }

            //10. Test: Check presence of Plan dropdown on Welcome page
            System.out.println("\n******Validating presence of Viewing Plan dropdown******");
            WebElement elementsWelcome= driver.findElement(By.xpath("/html/body/form/main/article/div/section/div/div/span/input"));
            if (elementsWelcome.isDisplayed()) {
                System.out.println("Plan dropdown field displays");
                addPassResultForTestCase();
            }else{
                System.out.println("Plan dropdown field displays");
                addFailResultForTestCase();
            }

            //11. Test: Check presence of page message section on Welcome page
            System.out.println("\n******Validating presence of page message section*****");
            WebElement elementPageMessage= driver.findElement(By.xpath("/html/body/form/main/article/div/section/section/section"));
            if (elementPageMessage.isDisplayed()) {
                System.out.println("Page message section displays");
                addPassResultForTestCase();
            }else{
                System.out.println("Page message section does not display");
                addFailResultForTestCase();
            }


             //12. Find total number of links
            // Storing all the 'a' tags in a Array List

            List<WebElement> Alllinks = driver.findElements(By.tagName("a"));

            //Displaying the total number of links in the Webpage
            //Test for matching number of links
            if (Alllinks.size() == expectedLinks) {
                System.out.println("Correct number of  total links on page: " + Alllinks.size());
                addPassResultForTestCase();
            } else {
                System.out.println("Incorrect number of total links on page: " + Alllinks.size() + " versus " + expectedLinks);
                addFailResultForTestCase();
            }


            //13. Test each url
            List<String> hrefs = new ArrayList<>();
            for(WebElement anchor : Alllinks){
                hrefs.add(anchor.getAttribute("href"));
            }

            for(String href : hrefs) {
                verifyLink(href);
            }

            //14. Test: Footer elements
            System.out.println("\n******Validating presence of Footer******");
            WebElement elementsFooter= driver.findElement(By.xpath("/html/body/form/main/div/footer"));
            if (elementsFooter.isDisplayed()) {
                System.out.println("Footer section displays");
                addPassResultForTestCase();
            }else{
                System.out.println("Footer section does not display");
                addFailResultForTestCase();
            }


            //15. Test: Logout
            System.out.println("\n******Starting logoff process******");
            Actions actions = new Actions(driver);
            WebElement categories = driver.findElement(By.xpath("/html/body/form/main/article/div/div[1]/header/nav/nav[1]/button/span"));
            actions.moveToElement(categories).click().perform();
            WebElement subLink = driver.findElement(By.xpath("/html/body/form/main/article/div/div[1]/header/nav/nav[1]/div/a[3]"));
            actions.moveToElement(subLink).click().perform();

            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.titleContains("Login - M Benefit Solutions"));

            if (driver.findElement(By.xpath("/html/body/form/main/article/article/section/div/div/div[2]/table")).isDisplayed()) {
                System.out.println("Login screen displays after logoff");
                addPassResultForTestCase();
            } else {
                System.out.println("Login screen does not display after logoff");
                addFailResultForTestCase();
            }

            //16. Test each url available on logoff page
            System.out.println("\n******Check links available on login page******");
            List<WebElement> loginLinks = driver.findElements(By.tagName("a"));
            List<String> hrefs1 = new ArrayList<>();
            for(WebElement anchor : loginLinks){
                hrefs1.add(anchor.getAttribute("href"));
            }

            for(String hrefLogin : hrefs1) {
                verifyLink(hrefLogin);
            }


            driver.close();
        } catch (Exception e) {
            System.out.println("Exception caught");
            e.printStackTrace();
            driver.close();
        }
    }

    public  void verifyLink(String urlLink) {

        try {
            //Use URL Class - Create object of the URL Class and pass the urlLink as parameter
            URL link = new URL(urlLink);
            // Create a connection using URL object (i.e., link)
            HttpURLConnection httpConn =(HttpURLConnection)link.openConnection();
            //Set the timeout for 2 seconds
            httpConn.setConnectTimeout(2000);
            //connect using connect method
            httpConn.connect();
            //use getResponseCode() to get the response code.
            if(httpConn.getResponseCode()== 200) {
                System.out.println(urlLink+" - "+httpConn.getResponseCode());
            }
            if(httpConn.getResponseCode()== 404) {
                System.out.println(urlLink+" - "+httpConn.getResponseCode());
            }
            if(httpConn.getResponseCode()== 400) {
                System.out.println(urlLink+" - "+httpConn.getResponseCode()); }
            if(httpConn.getResponseCode()== 500) {
                System.out.println(urlLink+" - "+httpConn.getResponseCode()); }
        }
        //getResponseCode method returns = IOException - if an error occurred connecting to the server.
        catch (Exception e) {
        }
    }

    public void declineCookieConsent(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html/body/div/div[2]/div[2]"))));
            if(driver.findElement(By.xpath("/html/body/div/div[2]/div[2]")).isDisplayed()){
                //if ( !driver.findElement(By.xpath("/html/body/div/div[2]/div[4]/a[1]")).isSelected() ) {
                driver.findElement(By.xpath("/html/body/div/div[2]/div[4]/a[1]")).click();
                wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("/html/body/div/div[2]/div[2]"))));
                System.out.println("Cookie consent visible - declined");
            }


        } catch(Exception e) {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            System.out.println("cookie-bot remains present");
        }
    }

    public void loginProcess(){
        WebElement element = driver.findElement(By.id("MainContent_MainContent_LoginCtrl_UserName"));
        element.sendKeys(testrailuser);

        WebElement elementPass = driver.findElement(By.id("MainContent_MainContent_LoginCtrl_Password"));
        elementPass.sendKeys(testrailpass);

        WebElement button = driver.findElement(By.id("MainContent_MainContent_LoginCtrl_LoginButton"));
        button.click();

    }

    public void addPassResultForTestCase() {
            try{
                System.out.println("Test Passed");
            }
            catch(Exception e){
            e.printStackTrace();
            System.err.println("Exception " + e + "Error in results");}
    //throws IOException, APIException{

    }

    public void addFailResultForTestCase() {
        try{
            System.out.println("Test Failed");
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println("Exception " + e + "Error in results");
            }
    }

}
