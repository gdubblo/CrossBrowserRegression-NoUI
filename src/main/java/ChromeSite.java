import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;


public class ChromeSite extends CrossBrowserBase {

    public ChromeSite(String s){
        super(s);
    }

    private String ChromeStatus = "******Chrome Test Completed******";
    public String runChromeTest() throws WebDriverException {
        ConfigureChromeDriver();
        runTests();
        return ChromeStatus;
    }


}

