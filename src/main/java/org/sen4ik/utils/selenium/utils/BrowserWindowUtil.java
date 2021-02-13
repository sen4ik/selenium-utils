package org.sen4ik.utils.selenium.utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.sen4ik.utils.GraphicsDeviceUtil;
import org.sen4ik.utils.selenium.base.SeleniumUtils;
import org.sen4ik.utils.selenium.enums.MobileScreenSizes;
import org.sen4ik.utils.selenium.enums.ScreenSizes;

import java.awt.Rectangle;
import java.util.Set;

@Log4j2
public class BrowserWindowUtil extends SeleniumUtils {

    public static String getTitle() {
        log.info("CALLED: getTitle()");
        String t = getDriver().getTitle();
        log.info("The current title: " + t);
        return t;
    }

    public static int getWindowCount() {
        log.info("CALLED: getWindowCount()");
        int count = getDriver().getWindowHandles().size();
        log.info("Number of windows opened: " + count);
        return count;
    }

    /**
     * Closes the current window.
     * NOTE: You will need to switch to another tab after closing the current.
     */
    public static void closeCurrentWindow() {
        log.info("CALLED: closeCurrentWindow()");
        getDriver().close();
    }

    /**
     * Closes all browser windows.
     */
    public static void closeAllWindows() {
        log.info("CALLED: closeAllWindows()");
        Set<String> handles = getDriver().getWindowHandles();
        for (String handle : handles) {
            getDriver().switchTo().window(handle);
            getDriver().close();
        }
    }

    public static String getCurrentWindowHandle() {
        log.info("CALLED: getCurrentWindowHandle()");
        String h = getDriver().getWindowHandle();
        log.info("Current window handle: " + h);
        return h;
    }

    public static Set<String> getWindowHandles() {
        log.info("CALLED: getWindowHandles()");
        Set<String> h = getDriver().getWindowHandles();
        log.info("Window handles: " + h.toString());
        return h;
    }

    public static boolean isWindowWithHandlePresent(String handle) {
        log.info("CALLED: isWindowPresent(\"" + handle + "\")");
        Set<String> handles = getDriver().getWindowHandles();
        if (handles.contains(handle)) {
            log.info("Window with handle " + handle + " is present");
            return true;
        } else {
            log.info("Window with handle " + handle + " is NOT present");
            return false;
        }
    }

    public static boolean isWindowPresent(String title) {
        log.info("CALLED: isWindowPresent(\"" + title + "\")");

        String currentWindowHandle = getDriver().getWindowHandle();

        boolean found = false;
        Set<String> handles = getDriver().getWindowHandles();
        for (String handle : handles) {
            getDriver().switchTo().window(handle);
            String currentTitle = getDriver().getTitle().trim();
            if(currentTitle.equalsIgnoreCase(title)){
                log.info("Found window using title " + title);
                found = true;
                break;
            }
        }

        if(!found){
            log.warn("Window is not found using title " + title + ". Switching back to the initial window.");
            getDriver().switchTo().window(currentWindowHandle);
        }

        return found;
    }

    public static void maximizeCurrentWindow() {
        log.info("CALLED: maximizeCurrentWindow()");
        getDriver().manage().window().maximize();
    }

    public static void moveBrowserWindow(int x, int y) {
        log.info("CALLED: moveBrowserWindow(" + x + ", " + y + ")");
        getDriver().manage().window().setPosition(
                new Point(x, y)
        );
    }

    public static void resizeBrowserWindow(int width, int height) {
        log.info("CALLED: maximizeCurrentWindow(" + width + ", " + height + ")");
        getDriver().manage().window().setSize(
                new Dimension(width, height)
        );
    }

    public static void resizeBrowserWindow(ScreenSizes screenSizes) {
        resizeBrowserWindow(screenSizes.width, screenSizes.height);
    }

    public static void resizeBrowserWindow(MobileScreenSizes mobileScreenSizes) {
        resizeBrowserWindow(mobileScreenSizes.width, mobileScreenSizes.height);
    }

    public static Dimension getBrowserWindowSize() {
        log.info("CALLED: getBrowserWindowSize()");
        return getDriver().manage().window().getSize();
    }

    public static Point getBrowserWindowPosition() {
        log.info("CALLED: getBrowserWindowPosition()");
        return getDriver().manage().window().getPosition();
    }

    /*
    // TODO: Rethink this. There are cases when the last opened tab wont be the last in the list of handles that the
    // getDriver() returns.
    public static void switchToWindowThatWasOpenedLast() {
        log.info("CALLED: switchToWindowThatWasOpenedLast()");
        Set<String> handles = getDriver().getWindowHandles();
        String lastHandle = null;
        for (String handle : handles) {
            lastHandle = handle;
        }
        getDriver().switchTo().window(lastHandle);
    }
    */

    public static boolean switchToWindow(int index) throws NoSuchWindowException{
        log.info("CALLED: switchToWindow(\"" + index + "\")");

        Set<String> handles = getDriver().getWindowHandles();
        int windowCount = handles.size();
        log.info("Number of currently shown windows: " + windowCount);

        if(index > windowCount){
            throw new NoSuchWindowException("index argument is greater than the current window count");
        }
        if(index <= 0){
            throw new IllegalArgumentException("index argument must be a positive integer");
        }

        boolean windowFound = false;
        int i = 1;
        for (String handle : handles) {
            if (i == index) {
                getDriver().switchTo().window(handle);
                log.info("Switched to window with index=" + index);
                windowFound = true;
                break;
            }
            i++;
        }

        return windowFound;
    }

    public static boolean switchToWindowWhereTitleEquals(String title) {
        return switchToWindow(title, true);
    }

    public static boolean switchToWindowWhereTitleContains(String title) {
        return switchToWindow(title, false);
    }

    private static boolean switchToWindow(String title, boolean equals) {
        log.info("CALLED: switchToWindow(\"" + title + "\", " + equals + ")");

        String currentWindowHandle = getDriver().getWindowHandle();

        boolean found = false;
        Set<String> handles = getDriver().getWindowHandles();
        log.info("Number of windows: " + handles.size());
        for (String handle : handles) {
            getDriver().switchTo().window(handle);
            String currentTitle = getDriver().getTitle().trim();
            if((equals && currentTitle.equalsIgnoreCase(title))
                    || (!equals && currentTitle.toLowerCase().contains(title.toLowerCase()))){
                log.info("Found window using title " + title);
                found = true;
                break;
            }
        }

        if(!found){
            getDriver().switchTo().window(currentWindowHandle);
        }

        return found;
    }

    public static boolean waitForNumberOfWindowsToEqual(int expectedNumberOfWindows, int timeoutInSeconds){
        return WaiterUtil.waitForNumberOfWindowsToEqual(expectedNumberOfWindows, timeoutInSeconds);
    }

    /**
     * Moves the browser window to another screen.
     * The first screen will have index 0.
     * When there is no screen present for the provided index, there will be no attempt to move the browser.
     * @param screenIndex
     */
    public static void moveBrowserToAnotherScreen(int screenIndex) {
        log.info("CALLED: moveBrowserToAnotherScreen(" + screenIndex + ")");
        Rectangle r = GraphicsDeviceUtil.getDisplayBounds(screenIndex);
        if(r != null){
            Double x = r.getX();
            Double y = r.getY();
            moveBrowserWindow(x.intValue(), y.intValue());
        }
    }

}
