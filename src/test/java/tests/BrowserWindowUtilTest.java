package tests;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchWindowException;
import org.sen4ik.utils.selenium.utils.BrowserWindowUtil;
import org.sen4ik.utils.selenium.utils.JavaScriptUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Log4j2
public class BrowserWindowUtilTest extends BaseTest {

    String vfbTitle = "VerseFromBible.com";
    String googleTitle = "Google";
    String yahooUrl = "http://yahoo.com";
    String vfbUrl = "http://versefrombible.com";

    @Test
    public void getTitleAndIsWindowPresent() {
        assertTrue(BrowserWindowUtil.getTitle().equals(googleTitle));
        assertTrue(BrowserWindowUtil.isWindowPresent(googleTitle));
    }

    @Test
    public void getWindowCount() {
        assertTrue(BrowserWindowUtil.getWindowCount() == 1);
        JavaScriptUtil.openNewTabAndSwitch(yahooUrl);
        assertTrue(BrowserWindowUtil.getWindowCount() == 2);
    }

    @Test
    public void closeCurrentWindow() {
        JavaScriptUtil.openNewTabAndSwitch(yahooUrl);
        assertTrue(BrowserWindowUtil.getWindowCount() == 2);
        BrowserWindowUtil.closeCurrentWindow();
        assertTrue(BrowserWindowUtil.getWindowCount() == 1);
    }

    @Test(expectedExceptions = NoSuchWindowException.class)
    public void switchToWindow_negativeOne() {
        BrowserWindowUtil.switchToWindow(999);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void switchToWindow_negativeTwo() {
        BrowserWindowUtil.switchToWindow(-1);
    }

    @Test
    public void switchToWindow() {
        JavaScriptUtil.openNewTabAndSwitch(vfbUrl);
        JavaScriptUtil.openNewTabAndSwitch(yahooUrl);
        BrowserWindowUtil.switchToWindowWhereTitleEquals(vfbTitle);
        assertTrue(BrowserWindowUtil.getTitle().equals(vfbTitle));
        BrowserWindowUtil.switchToWindow(1);
        assertTrue(BrowserWindowUtil.getTitle().equals(googleTitle));
        BrowserWindowUtil.switchToWindowWhereTitleContains("FromBible");
        assertTrue(BrowserWindowUtil.getTitle().equals(vfbTitle));
        BrowserWindowUtil.switchToWindow(1);
        assertTrue(BrowserWindowUtil.getTitle().equals(googleTitle));
    }

}
