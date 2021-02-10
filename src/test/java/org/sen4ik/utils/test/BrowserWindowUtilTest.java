package org.sen4ik.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchWindowException;
import org.sen4ik.utils.selenium.utils.BrowserWindowUtil;
import org.sen4ik.utils.selenium.utils.JavaScriptUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Slf4j
public class BrowserWindowUtilTest extends BaseTest {

    String vfbTitle = "VerseFromBible.com";
    String googleTitle = "Google";

    @Test
    public void getTitleAndIsWindowPresent() {
        assertTrue(BrowserWindowUtil.getTitle().equals(googleTitle));
        assertTrue(BrowserWindowUtil.isWindowPresent(googleTitle));
    }

    @Test(expectedExceptions = NoSuchWindowException.class, priority = 1000)
    public void switchToWindow_negativeOne() {
        BrowserWindowUtil.switchToWindow(999);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, priority = 1001)
    public void switchToWindow_negativeTwo() {
        BrowserWindowUtil.switchToWindow(-1);
    }

    @Test(priority = 999)
    public void switchToWindow() {
        JavaScriptUtil.openNewTabAndSwitch("http://versefrombible.com");
        JavaScriptUtil.openNewTabAndSwitch("http://yahoo.com");
        BrowserWindowUtil.switchToWindowWhereTitleEquals(vfbTitle);
        assertTrue(BrowserWindowUtil.getTitle().equals(vfbTitle));
        BrowserWindowUtil.switchToWindow(1);
        assertTrue(BrowserWindowUtil.getTitle().equals(googleTitle));
        BrowserWindowUtil.switchToWindowWhereTitleContains("FromBible");
        assertTrue(BrowserWindowUtil.getTitle().equals(vfbTitle));
        BrowserWindowUtil.switchToWindow(1);
        assertTrue(BrowserWindowUtil.getTitle().equals(googleTitle));
    }

    @Test
    public void temp() {
        JavaScriptUtil.openNewTabAndSwitch("http://versefrombible.com");
        JavaScriptUtil.openNewTabAndSwitch("http://yahoo.com");
        log.info(BrowserWindowUtil.getWindowCount() + "");

        BrowserWindowUtil.closeCurrentWindow();
        log.info(BrowserWindowUtil.getWindowCount() + "");
        BrowserWindowUtil.switchToWindow(1);

        JavaScriptUtil.openNewTabAndSwitch("http://habr.com");
        BrowserWindowUtil.closeAllWindows();
    }

}
