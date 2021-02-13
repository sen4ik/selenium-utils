package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.sen4ik.utils.selenium.base.SeleniumUtils;
import org.sen4ik.utils.selenium.driver.DriverManager;
import org.testng.annotations.*;

public class BaseTest {

    @BeforeClass
    public void bc(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void bm(){
        DriverManager.createInstance();
        SeleniumUtils.init(DriverManager::getDriver);
        DriverManager.getDriver().get("http://google.com");
    }

    @AfterMethod
    public void am(){
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }

}
