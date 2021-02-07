package org.sen4ik.utils.selenium.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DriverThread {

    private WebDriver driver = null;

    public WebDriver getDriver() {
        if (this.driver == null) {
            instantiateWebDriver();
        }
        return this.driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void instantiateWebDriver() {
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
        driver = new ChromeDriver(options);
    }

}
