package org.sen4ik.utils.selenium.base;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

@Log4j2
public class SeleniumUtils {

    private static Supplier supplier;

    public static int defaultTimeoutInSeconds = 60;
    public static int defaultPollingPeriodInMilliseconds = 500;

    /**
     * Sets supplier so utils know where to get the driver instance.
     * @param supplier
     */
    public static void init(Supplier supplier){
        log.debug("CALLED: init()");
        SeleniumUtils.supplier = supplier;
        log.debug("supplier: " + supplier);
    }

    public static WebDriver getDriver(){
        log.debug("CALLED: getDriver()");
        WebDriver driver = (WebDriver) supplier.get();
        log.debug("supplier: " + supplier + "; " + driver.toString());
        return driver;
    }

    public static int getDefaultTimeoutInSeconds() {
        log.debug("CALLED: getDefaultTimeoutInSeconds()");
        return defaultTimeoutInSeconds;
    }

    public static void setDefaultTimeoutInSeconds(int defaultTimeoutInSeconds) {
        log.debug("CALLED: setDefaultTimeoutInSeconds()");
        SeleniumUtils.defaultTimeoutInSeconds = defaultTimeoutInSeconds;
    }

    public static int getDefaultPollingPeriodInMilliseconds() {
        log.debug("CALLED: getDefaultPollingPeriodInMilliseconds()");
        return defaultPollingPeriodInMilliseconds;
    }

    public static void setDefaultPollingPeriodInMilliseconds(int defaultPollingPeriodInMilliseconds) {
        log.debug("CALLED: setDefaultPollingPeriodInMilliseconds()");
        SeleniumUtils.defaultPollingPeriodInMilliseconds = defaultPollingPeriodInMilliseconds;
    }

}
