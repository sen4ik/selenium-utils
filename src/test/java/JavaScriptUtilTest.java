import org.sen4ik.utils.selenium.utils.BrowserWindowUtil;
import org.sen4ik.utils.selenium.utils.JavaScriptUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class JavaScriptUtilTest extends BaseTest {

    @Test
    public void openNewTabAndGetWindowCount() {
        JavaScriptUtil.openNewTab();
        assertTrue(BrowserWindowUtil.getWindowCount() == 2);
    }

    @Test
    public void openNewTabAndSwitch() {
        JavaScriptUtil.openNewTabAndSwitch();
        assertEquals(BrowserWindowUtil.getWindowCount(), 2);
        JavaScriptUtil.openNewTabAndSwitch("http://yahoo.com");
        assertEquals(BrowserWindowUtil.getWindowCount(), 3);
    }

}
