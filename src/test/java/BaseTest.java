import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.sen4ik.utils.selenium.base.SeleniumUtils;
import org.sen4ik.utils.selenium.driver.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

@Slf4j
public class BaseTest {

    public WebDriver driver;

    @BeforeClass
    public void bc(){
        SeleniumUtils.init(DriverFactory::getDriver);
    }

    @BeforeMethod
    public void bm(){
        driver = DriverFactory.getDriver();
        driver.get("http://google.com");
    }

    @AfterMethod
    public void at() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }

}
