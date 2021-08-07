import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;


public class FirefoxSite extends CrossBrowserBase{

    public FirefoxSite(String s){
        super(s);
    }

    private String FirefoxStatus = "******Firefox Test Completed******";
    public String runFirefoxTest() throws WebDriverException {
        ConfigureFirefoxDriver();
        runTests();
        return FirefoxStatus;
    }

}