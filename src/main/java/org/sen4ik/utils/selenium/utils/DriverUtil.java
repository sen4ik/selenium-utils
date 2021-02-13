package org.sen4ik.utils.selenium.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.sen4ik.utils.selenium.base.SeleniumUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DriverUtil extends SeleniumUtils {

    public static void disableImplicitWaits() {
        log.info("CALLED: disableImplicitWaits()");
        WaiterUtil.disableImplicitWaits();
    }

    public static void enableImplicitWaits(int seconds) {
        log.info("CALLED: enableImplicitWaits()");
        WaiterUtil.enableImplicitWaits(seconds);
    }

    public static String getPageSource() {
        log.info("CALLED: getPageSource()");
        return getDriver().getPageSource();
    }

    public static void deleteAllCookies() {
        log.info("CALLED: deleteAllCookies()");
        getDriver().manage().deleteAllCookies();
    }

    public static void addCookie(Cookie cookie) {
        log.info("CALLED: addCookie()");
        getDriver().manage().addCookie(cookie);
    }

    public static void pageRefresh() {
        log.info("CALLED: pageRefresh()");
        getDriver().navigate().refresh();
    }

    public static String getCurrentUrl() {
        log.info("CALLED: getCurrentUrl()");
        String url = getDriver().getCurrentUrl();
        log.info("The current URL: " + url);
        return url;
    }

    public static void navigateTo(String url) {
        log.info("CALLED: navigateTo(\"" + url + "\")");
        getDriver().get(url);
    }

    public static void navigateBack() {
        log.info("CALLED: navigateBack()");
        getDriver().navigate().back();
    }

    public static void navigateForward() {
        log.info("CALLED: navigateForward()");
        getDriver().navigate().forward();
    }

    public static boolean isAlertPresent() {
        log.info("CALLED: isAlertPresent()");
        try {
            getDriver().switchTo().alert();
            log.info("Alert is present");
            return true;
        } catch (NoAlertPresentException Ex) {
            log.info("Alert is NOT present");
            return false;
        }
    }

    public static String getAlertTextAndAccept() {
        log.info("CALLED: getAlertTextAndAccept()");
        String text = "";
        try {
            Alert alert = getDriver().switchTo().alert();
            text = alert.getText().trim();
            log.info("Alert found with text: " + text);
            alert.accept();
            log.info("Alert accepted");
        } catch (Exception e) {
            log.error("Error occurred. Exception message: " + e.getMessage());
        }
        return text;
    }

    public static String getAlertTextAndDismiss() {
        log.info("CALLED: getAlertTextAndDismiss()");
        String text = "";
        try {
            Alert alert = getDriver().switchTo().alert();
            log.info("Alert found with text: " + text);
            text = alert.getText();
            alert.dismiss();
            log.info("Alert dismissed");
        } catch (Exception e) {
            log.error("Error occurred. Exception message: " + e.getMessage());
        }
        return text;
    }

    public static boolean waitForAlertAndAccept(int timeOutInSeconds){
        return WaiterUtil.waitForAlertAndAccept(timeOutInSeconds);
    }

    public static boolean isElementStale(WebElement webElement) {
        log.info("CALLED: isElementStale()");
        try {
            webElement.isEnabled();
            log.info("Element is NOT stale");
            return false;
        } catch (StaleElementReferenceException expected) {
            log.info("Element is stale");
            return true;
        }
    }

    public static boolean isElementPresent(By locator) {
        log.info("CALLED: isElementPresent()");
        try {
            getDriver().findElement(locator);
            log.info("Element is present");
            return true;
        } catch (NoSuchElementException e) {
            log.info("Element is not present");
            return false;
        }
    }

    /**
     * Checks if element is present on page within another element.
     * @param elementToLookWithin
     * @param locator
     * @return Return true if elements is present within the elementToLookWithin, returns false otherwise.
     */
    public static boolean isElementPresent(WebElement elementToLookWithin, By locator) {
        log.info("CALLED: isElementPresent()");
        try {
            elementToLookWithin.findElement(locator);
            log.info("Element is present");
            return true;
        } catch (NoSuchElementException e) {
            log.info("Element is not present");
            return false;
        }
    }

    public static boolean isElementVisible(By locator) {
        log.info("CALLED: isElementVisible()");
        try {
            return getDriver().findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            log.info("Element is NOT visible");
            return false;
        }
    }

    public static boolean isElementVisible(WebElement webElement) {
        log.info("CALLED: isElementVisible()");
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            log.info("Element is NOT visible");
            return false;
        }
    }

    /**
     * Checks if elements are present on a page.
     * @param elements
     * @return Returns false if at least one element is not present on the page.
     */
    public static boolean areElementsPresent(List<By> elements) {
        log.info("CALLED: areElementsPresent()");
        boolean present = true;
        for (By locator : elements) {
            if (!isElementPresent(locator)) {
                present = false;
                log.error("Element " + locator + " is NOT present on a page");
            } else {
                log.info("Element " + locator + " is present on a page");
            }
        }
        return present;
    }

    /**
     * Attempts to pull locator from the element.
     * @author https://stackoverflow.com/a/51080338/1318411.
     * @param webElement
     * @return
     */
    protected String getLocator(WebElement webElement) {
        try {
            Object proxyOrigin = FieldUtils.readField(webElement, "h", true);
            Object locator = FieldUtils.readField(proxyOrigin, "locator", true);
            Object findBy = FieldUtils.readField(locator, "by", true);
            if (findBy != null) {
                return findBy.toString();
            }
        } catch (IllegalAccessException e) {

        }
        return "";
    }

    /**
     * Finds element and populates text to it.
     * @param locator
     * @param text
     */
    public static void populateField(By locator, String text) {
        log.info("CALLED: populateField()");
        populateField(getDriver().findElement(locator), text);
    }

    /**
     * Finds element and populates text to it.
     * @param webElement
     * @param text
     */
    public static void populateField(WebElement webElement, String text) {
        log.info("CALLED: populateField()");
        webElement.clear();
        webElement.sendKeys(text);
    }

    public static void clearInput(By locator) {
        log.info("CALLED: clearInput()");
        getDriver().findElement(locator).clear();
    }

    public static String getInputFieldValue(By locator) {
        log.info("CALLED: getInputFieldValue()");
        return getInputFieldValue(getDriver().findElement(locator));
    }

    public static String getInputFieldValue(WebElement webElement) {
        log.info("CALLED: getInputFieldValue()");
        String value = webElement.getAttribute("value").trim();
        log.info("value: " + value);
        return value;
    }

    /**
     * Checks if checkbox is checked.
     * @param locator
     * @return Returns true if the checkbox is checked.
     */
    public static boolean isCheckboxChecked(By locator) {
        log.info("CALLED: isCheckboxChecked()");
        boolean checked = getDriver().findElement(locator).isSelected();
        log.info("Checked: " + checked);
        return checked;
    }

    /**
     * Clicks on the checkbox regardless of its state.
     * @param locator
     */
    public static void clickCheckbox(By locator) {
        log.info("CALLED: clickCheckbox()");
        WebElement checkbox = getDriver().findElement(locator);
        checkbox.click();
    }

    /**
     * Clicks on the checkbox only if it is not selected.
     * @param locator
     */
    public static void selectCheckbox(By locator) {
        log.info("CALLED: clickCheckbox()");
        WebElement checkbox = getDriver().findElement(locator);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    /**
     * Deselects a checkbox only if its selected.
     * @param locator
     */
    public static void unselectCheckbox(By locator) {
        log.info("CALLED: clickCheckbox()");
        WebElement checkbox = getDriver().findElement(locator);
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }

    /**
     * Clicks an element and waits for an element to become stale.
     * @param locator
     * @param timeoutInSeconds
     */
    public static void clickAndWait(By locator, int timeoutInSeconds) {
        clickAndWait(getDriver().findElement(locator), timeoutInSeconds);
    }

    /**
     * Clicks an element and waits for element to become stale.
     * @param webElement
     * @param timeoutInSeconds
     */
    public static void clickAndWait(WebElement webElement, int timeoutInSeconds) {
        log.info("called: clickAndWait()");
        webElement.click();
        WaiterUtil.waitForElementToBecomeStale(webElement, timeoutInSeconds);
    }

    /**
     * Attempts to click an element and if WebDriverException is thrown, clicks the element with JavaScript.
     * @param webElement
     */
    public static void specialClick(WebElement webElement) {
        log.info("CALLED: specialClick()");
        try {
            webElement.click();
            log.info("Element is clicked");
        } catch (WebDriverException e) {
            log.error("WebDriverException is thrown. Will click using JavaScript");
            JavaScriptUtil.click(webElement);
        }
    }

    /**
     * Attempts to click an element and if WebDriverException is thrown, clicks the element with JavaScript.
     * @param locator
     */
    public static void specialClick(By locator) {
        specialClick(getDriver().findElement(locator));
    }

    /**
     * Hover over an element using Selenium built-in Actions.
     * @param webElement
     */
    public static void hoverOverElement(WebElement webElement) {
        log.info("CALLED: hoverOverElement()");
        new Actions(getDriver()).moveToElement(webElement).perform();
    }

    /**
     * Converts List<WebElement> to List<String> where each string is a text from an element.
     * @param webElementList
     * @return
     */
    public static List<String> toStringList(List<WebElement> webElementList) {
        log.info("CALLED: toStringList()");
        List<String> strList = new ArrayList<String>();
        for (WebElement webElement : webElementList) {
            strList.add(webElement.getText());
        }
        return strList;
    }

}
