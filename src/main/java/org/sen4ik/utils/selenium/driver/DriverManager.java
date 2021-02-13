package org.sen4ik.utils.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    private static ThreadLocal<WebDriver> thread = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver() {
        return thread.get();
    }

    public static WebDriver createInstance() {
        WebDriver driver = new ChromeDriver();
        thread.set(driver);
        return driver;
    }
}
