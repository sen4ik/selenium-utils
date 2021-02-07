package org.sen4ik.utils.selenium.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sen4ik.utils.selenium.base.SeleniumUtils;

@Slf4j
public class JavaScriptUtil extends SeleniumUtils {

    public static void openNewTab(){
        log.info("CALLED: openNewTab()");
        ((JavascriptExecutor) getDriver()).executeScript("window.open()");
        // ((JavascriptExecutor) getDriver()).executeScript("window.open('about:blank', '_blank')");
    }

    public static void openNewTabAndSwitch(){
        log.info("CALLED: openNewTabAndSwitch()");
        int windowCount = BrowserWindowUtil.getWindowCount();
        openNewTab();
        BrowserWindowUtil.switchToWindow(windowCount + 1);
    }

    public static void openNewTab(String url){
        log.info("CALLED: openNewTab(" + url + ")");
        ((JavascriptExecutor) getDriver()).executeScript("window.open('" + url + "')");
        // ((JavascriptExecutor) getDriver()).executeScript("window.open('" + url + "', '_blank')");
    }

    public static void openNewTabAndSwitch(String url){
        log.info("CALLED: openNewTabAndSwitch(" + url + ")");
        int windowCount = BrowserWindowUtil.getWindowCount();
        openNewTab(url);
        BrowserWindowUtil.switchToWindow(windowCount + 1);
    }

    public static void populateField(WebElement webElement, String text){
        log.info("CALLED: populateField(" + text + ")");
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].setAttribute('value','" + text + "')", webElement
        );
    }

    public static void populateField(String elementId, String text){
        log.info("CALLED: populateField(" + elementId + ", " + text + ")");
        ((JavascriptExecutor) getDriver()).executeScript(
                "document.getElementById('" + elementId + "').value='" + text + "';"
        );
    }

    public static void click(By locator) {
        click(
                getDriver().findElement(locator)
        );
    }

    /**
     * Uses below code to hover over an element.
     * <code>
     * 		if(document.createEvent){
     * 		    var evObj = document.createEvent('MouseEvents');
     * 		    evObj.initEvent('click', true, false);
     * 		    arguments[0].dispatchEvent(evObj);
     * 		}
     * 		else if(document.createEventObject) {
     * 		    arguments[0].fireEvent('onclick');
     * 		}
     * </code>
     * @param webElement
     */
    public static void click(WebElement webElement) {
        log.info("CALLED: click()");
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        String s = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
                + "evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);}"
                + "else if(document.createEventObject) { arguments[0].fireEvent('onclick');}";
        je.executeScript(s, webElement);
    }

    public static void hoverOverElement(By locator) {
        hoverOverElement(getDriver().findElement(locator));
    }

    /**
     * Uses below code to hover over an element.
     * <code>
     * 		if(document.createEvent){
     * 		    var evObj = document.createEvent('MouseEvents');
     * 		    evObj.initEvent('mouseover', true, false);
     * 		    arguments[0].dispatchEvent(evObj);
     * 		 }
     * 		else if(document.createEventObject) {
     * 		    arguments[0].fireEvent('onmouseover');
     * 		}
     * </code>
     * @param webElement
     */
    public static void hoverOverElement(WebElement webElement) {
        log.info("CALLED: hoverOverElement()");
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        String script = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
                + "evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);}"
                + "else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        je.executeScript(script, webElement);
    }

    public static void scrollElementIntoView(WebElement webElement) {
        log.info("CALLED: scrollElementIntoView()");
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView(true);", webElement
        );
    }

    /**
     * Uses the below code to scroll down the page:
     * <code>
     *     window.scrollBy(0, 500);
     * </code>
     * @param scrollTo defines how many pixels the page will be scrolled down.
     */
    public static void scrollDown(int scrollTo) {
        log.info("CALLED: scrollDown()");
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, " + scrollTo + ")");
    }

    /**
     * Using JavaScript check if an image is present on the page.
     * Taken from https://stackoverflow.com/questions/23932402/can-anyone-help-me-explaining-javascript-code-for-image-validation-using-seleniu
     * @param image
     * @return
     */
    public static Boolean isImagePresent(WebElement image) {
        log.info("CALLED: isImagePresent()");
        Boolean present = (Boolean) ((JavascriptExecutor) getDriver())
                .executeScript("return arguments[0].complete " +
                        "&& typeof arguments[0].naturalWidth != \"undefined\" " +
                        "&& arguments[0].naturalWidth > 0", image);
        return present;
    }

    /**
     * Uses below code to show an alert.
     * <code>
     * 		alert('text');
     * </code>
     * @param text
     */
    public static void initAlert(String text){
        log.info("CALLED: initAlert()");
        ((JavascriptExecutor) getDriver()).executeScript("alert('" + text + "');");
    }

    /**
     * Uses below code to refresh the browser window.
     * <code>
     * 		history.go(0);
     * </code>
     */
    public static void refreshBrowserWindow(){
        log.info("CALLED: refreshBrowserWindow()");
        ((JavascriptExecutor) getDriver()).executeScript("history.go(0)");
    }

    /**
     * Waits for DOM to have status complete. Uses JavaScript document.readyState to get the DOM status.
     * @param timeoutInSeconds
     * @param sleepInMillis
     * @return
     */
    public static boolean waitForDomToComplete(int timeoutInSeconds, int sleepInMillis) {
        log.info("CALLED: waitForDomToComplete(" + timeoutInSeconds + ", " + sleepInMillis + ")");
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds, sleepInMillis);
        return wait.until((ExpectedCondition<Boolean>) driver -> {
            String readyState = (String) ((JavascriptExecutor) getDriver())
                    .executeScript("return document.readyState;");
            log.info("readyState: " + readyState);
            if(readyState.equals("complete")) {
                return true;
            } else {
                return false;
            }
        });
    }

}
