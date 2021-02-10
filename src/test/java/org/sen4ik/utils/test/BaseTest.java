package org.sen4ik.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.sen4ik.utils.selenium.base.SeleniumUtils;
import org.sen4ik.utils.selenium.driver.DriverFactory;
import org.testng.annotations.*;

@Slf4j
public class BaseTest {

    @BeforeClass
    public void bc(){
        SeleniumUtils.init(DriverFactory::getDriver);
    }

    @BeforeMethod
    public void bm(){
        DriverFactory.getDriver();
        DriverFactory.getDriver().get("http://google.com");
    }

    @AfterMethod
    public void at() {
        DriverFactory.quitDriver();
    }

    @AfterClass
    public void ac() {
        DriverFactory.quitAllDrivers();
    }
}
