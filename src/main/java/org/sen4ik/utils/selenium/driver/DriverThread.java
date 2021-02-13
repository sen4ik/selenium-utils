package org.sen4ik.utils.selenium.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DriverThread {

    private WebDriver driver = null;

    public WebDriver getDriver() {
        log.debug("CALLED: getDriver()");
        if (this.driver == null) {
            instantiateDriver();
        }
        return this.driver;
    }

    public void quitDriver() {
        log.debug("CALLED: quitDriver()");
        if (this.driver != null) {
            this.driver.quit();
            this.driver = null;
        }
    }

    private void instantiateDriver() {
        log.debug("CALLED: instantiateDriver()");
        ChromeOptions options = new ChromeOptions();

        List<String> arguments = new ArrayList<>();
        arguments.add("--no-sandbox");
        arguments.add("--start-maximized");
        arguments.add("--disable-infobars");
        // arguments.add("--disable-gpu");
        options.addArguments(arguments);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.merge(capabilities);

         WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver(options);
    }

}
