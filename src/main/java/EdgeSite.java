import com.microsoft.edge.seleniumtools.EdgeDriver;
import org.openqa.selenium.WebDriverException;


public class EdgeSite extends CrossBrowserBase {

            public EdgeSite(String s){
                super(s);
            }

            private String EdgeStatus = "******Edge Test Completed******";
            public String runEdgeTest() throws WebDriverException {
                ConfigureEdgeDriver();
                runTests();
                return EdgeStatus;
            }

        }