package org.sen4ik.utils.selenium.utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.sen4ik.utils.selenium.base.SeleniumUtils;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Log4j2
public class WaiterUtil extends SeleniumUtils {

	/**
	 * Sets implicit wait to 0.
	 * @return
	 */
	public static void disableImplicitWaits() {
		log.info("CALLED: turnOffImplicitWaits()");
		getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	/**
	 * Sets implicit wait.
	 * @return
	 */
	public static void enableImplicitWaits(int seconds) {
		log.info("CALLED: turnOnImplicitWaits(" + seconds + ")");
		getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Waits for number of browser windows to equal to the specified number.
	 * The method uses default timeout set in SeleniumUtils.defaultTimeoutInSeconds.
	 * @param expectedNumberOfWindows
	 * @return
	 */
	public static boolean waitForNumberOfWindowsToEqual(int expectedNumberOfWindows) {
		return waitForNumberOfWindowsToEqual(expectedNumberOfWindows, SeleniumUtils.defaultTimeoutInSeconds);
	}

	/**
	 * Waits for number of browser windows to equal to the specified number.
	 * @param expectedNumberOfWindows
	 * @return
	 */
	public static boolean waitForNumberOfWindowsToEqual(int expectedNumberOfWindows, int timeoutInSeconds) {
		log.info("CALLED: waitForNumberOfWindowsToEqual(" + expectedNumberOfWindows + ", " + timeoutInSeconds + ")");
		long start = new Date().getTime();
		int timeout = timeoutInSeconds * 1000;

		while(true) {
			long end = new Date().getTime();
			long diff = end - start;

			if (diff >= timeout) {
				log.error("Timeout is reached while waiting for number of windows to be " + expectedNumberOfWindows);
				return false;
			}

			Set<String> handles = getDriver().getWindowHandles();
			log.info("Number of windows found: " + handles.size());
			int windowsCount = handles.size();

			if (expectedNumberOfWindows == windowsCount) {
				log.info("Was able to wait for the number of windows to be " + expectedNumberOfWindows);
				return true;
			}

			pauseMilliseconds(SeleniumUtils.defaultPollingPeriodInMilliseconds);
		}
	}

	public static void waitForAlert(int timeoutInSeconds) {
		log.info("CALLED: waitForAlert(" + timeoutInSeconds + ")");
		new WebDriverWait(getDriver(), timeoutInSeconds)
        	.ignoring(NoAlertPresentException.class)
        	.until(ExpectedConditions.alertIsPresent());
	}

	public static boolean waitForAlertAndAccept(int timeOutInSeconds) {
		log.info("CALLED: waitForAlertAndAccept(" + timeOutInSeconds + ")");
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds, SeleniumUtils.defaultPollingPeriodInMilliseconds);
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			log.info("Alert text: " + alert.getText());
			alert.accept();
			log.info("Alert is accepted");
			return true;
		} catch (TimeoutException e) {
			log.info("Alert was NOT displayed within specified timeout");
			return false;
		}
	}

	public static boolean waitForDomToComplete(int timeoutInSeconds) {
		return waitForDomToComplete(timeoutInSeconds, SeleniumUtils.defaultPollingPeriodInMilliseconds);
	}

	public static boolean waitForDomToComplete(int timeoutInSeconds, int sleepInMillis) {
		return JavaScriptUtil.waitForDomToComplete(timeoutInSeconds, sleepInMillis);
	}

	/**
	 * Uses below JavaScript to determine if Angular finished executing:
	 * <code>
	 *      return (window.angular !== undefined)
	 * 		&& (angular.element(document.getElementById('ng-app')).injector() !== undefined)
	 * 		&& (angular.element(document.getElementById('ng-app')).injector().get('$http').pendingRequests.length === 0)
	 * </code>
	 * Current method will use default SeleniumUtils.defaultTimeoutInSeconds timeout and SeleniumUtils.defaultPollingPeriodInMilliseconds polling frequency.
	 * @return
	 */
	public static boolean waitForAngularToComplete() {
		return waitForAngularToComplete(SeleniumUtils.defaultTimeoutInSeconds, SeleniumUtils.defaultPollingPeriodInMilliseconds);
	}

	/**
	 * Uses below JavaScript to determine if Angular finished executing:
	 * <code>
	 *      return (window.angular !== undefined)
	 * 		&& (angular.element(document.getElementById('ng-app')).injector() !== undefined)
	 * 		&& (angular.element(document.getElementById('ng-app')).injector().get('$http').pendingRequests.length === 0)
	 * </code>
	 * @param timeoutInSeconds
	 * @param pollingSeconds
	 * @return
	 */
	public static boolean waitForAngularToComplete(int timeoutInSeconds, int pollingSeconds) {
		log.info("CALLED: waitForAngularToComplete(" + timeoutInSeconds + ", " + pollingSeconds + ")");
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeoutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(pollingSeconds, TimeUnit.MILLISECONDS).withMessage("Angular requests are still pending");
		return wait.until((ExpectedCondition<Boolean>) arg0 -> Boolean.valueOf(((JavascriptExecutor) arg0)
				.executeScript("return (window.angular !== undefined) "
						+ "	&& (angular.element(document.getElementById('ng-app')).injector() !== undefined) "
						+ "	&& (angular.element(document.getElementById('ng-app')).injector().get('$http').pendingRequests.length === 0);")
				.toString()));
	}

	/**
	 * Uses below JavaScript to determine if jQuery finished executing:
	 * <code>
	 * 		return (window.jQuery!= null) && (jQuery.active === 0)
	 * </code>
	 * The method uses default timeout and default polling period.
	 * @return
	 */
	public static boolean waitForJQueryToFinishProcessing() {
		return waitForJQueryToFinishProcessing(SeleniumUtils.defaultTimeoutInSeconds, SeleniumUtils.defaultPollingPeriodInMilliseconds);
	}

	/**
	 * Uses below JavaScript to determine if jQuery finished executing:
	 * <code>
	 * 		return (window.jQuery!= null) && (jQuery.active === 0)
	 * </code>
	 * @param timeout
	 * @return
	 */
	public static boolean waitForJQueryToFinishProcessing(int timeout, int pollingSeconds) {
		log.info("CALLED: waitForJQueryToFinishProcessing()");
		// Dirty wait to make sure jQuery had enough time to start executing
		pauseMilliseconds(SeleniumUtils.defaultPollingPeriodInMilliseconds);
		WebDriverWait wait = new WebDriverWait(getDriver(), timeout, pollingSeconds);
		return wait.until((ExpectedCondition<Boolean>) driver -> (Boolean) ((JavascriptExecutor) driver)
				.executeScript("return (window.jQuery!= null) && (jQuery.active === 0);"));
	}

	/**
	 * Waits for element to be present on page and to be clickable within the specified timeout.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public static boolean waitForElement(By locator, int timeout) {
		log.info("CALLED: waitForElement()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeout, SeleniumUtils.defaultPollingPeriodInMilliseconds);
		try{
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			log.info("Element is present");
			return true;
		}
		catch(TimeoutException te){
			log.info("Timeout is reached. Element is NOT present");
			return false;
		}
	}

	public static boolean waitForElementToBePresent(By locator, int timeoutInSeconds) {
		log.info("CALLED: waitForElementToBecomePresent()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds, SeleniumUtils.defaultPollingPeriodInMilliseconds);
		try{
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			log.info("Element is present");
			return true;
		}
		catch(TimeoutException te){
			log.info("Timeout is reached. Element is NOT present");
			return false;
		}
	}

	public static boolean waitForElementToBeClickable(By locator, int timeoutInSeconds) {
		log.info("CALLED: waitForElementToBeClickable()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds, SeleniumUtils.defaultPollingPeriodInMilliseconds);
		try{
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			log.info("Element is clickable");
			return true;
		}
		catch(TimeoutException te){
			log.info("Timeout is reached. Element is NOT clickable");
			return false;
		}
	}

	/*
	public static boolean waitForElementToBecomeVisible(WebElement webElement, int timeoutInSeconds) {
		log.info("CALLED: waitForElementToBecomeVisible()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(webElement));
		return webElement.isEnabled();
	}
	 */

	public static boolean waitForElementToBecomeVisible(WebElement webElement, int timeoutInSeconds) {
		log.info("CALLED: waitForElementToBecomeVisible()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds);
		try{
			wait.until(ExpectedConditions.visibilityOf(webElement));
			log.info("Element is visible");
			return true;
		}
		catch(TimeoutException te){
			log.info("Timeout is reached. Element is NOT visible");
			return false;
		}
	}

	public static boolean waitForElementToBecomeVisible(By locator, int timeoutInSeconds) {
		return waitForElementToBecomeVisible(getDriver().findElement(locator), timeoutInSeconds);
	}

	public static boolean waitForElementAttributeToContainValue(WebElement webElement, String attribute, String value, int timeoutInSeconds) {
		log.info("CALLED: waitForElementAttributeToContainValue()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds);
		wait.until(ExpectedConditions.attributeContains(webElement, attribute, value));
		return webElement.getAttribute(attribute).contains(value);
	}

	public static boolean waitForElementToBecomeStale(WebElement webElement, int timeoutInSeconds) {
		log.info("CALLED: waitForElementToBecomeStale()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds, SeleniumUtils.defaultPollingPeriodInMilliseconds);
		return wait.until(ExpectedConditions.stalenessOf(webElement));
	}

	/**
	 * Waits for element to disappear from page withing specified timeout.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public static boolean waitForElementToDisappear(By locator, int timeout) {
		log.info("CALLED: waitForElementToDisappear()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeout, SeleniumUtils.defaultPollingPeriodInMilliseconds);
		try{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			return true;
		}
		catch(TimeoutException te){
			return false;
		}
	}

	/**
	 * Refreshes page until element is present on a page.
	 * @param locator
	 * @param timeoutInSeconds
	 * @return
	 */
	public static boolean refreshPageUntilElementIsPresent(By locator, int timeoutInSeconds) {
		log.info("CALLED: refreshPageUntilElementIsPresent()");

		int timeoutInMilliseconds = 1000 * timeoutInSeconds;
		long start = new Date().getTime();

		while(true) {
			long end = new Date().getTime();
			long diff = end - start;

			if (diff >= timeoutInMilliseconds) {
				log.info("Timeout " + timeoutInSeconds + " is reached");
				return false;
			}

			boolean isPresent = DriverUtil.isElementPresent(locator);
			if(isPresent){
				return true;
			}

			pauseMilliseconds(SeleniumUtils.defaultPollingPeriodInMilliseconds);
			DriverUtil.pageRefresh();
		}
	}

	public static void pause(int seconds) {
		org.sen4ik.utils.WaiterUtil.pause(seconds);
	}

	public static void pauseMilliseconds(int milliseconds) {
		org.sen4ik.utils.WaiterUtil.pauseMilliseconds(milliseconds);
	}

}
