package org.sen4ik.utils.selenium.utils;

import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.awt.event.KeyEvent;

@Log4j2
public class KeyboardUtil {

    public static void pressEnterButton() throws AWTException {
        log.info("CALLED: pressEnterButton()");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
    }

    public static void pressEscButton() throws AWTException {
        log.info("CALLED: pressEscButton()");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
    }

    public static void pressDeleteButton() throws AWTException {
        log.info("CALLED: pressDeleteButton()");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DELETE);
    }

    public static void pressBackspaceButton() throws AWTException {
        log.info("CALLED: pressBackspaceButton()");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
    }

    public static void pressF11Button() throws AWTException {
        log.info("CALLED: pressF11Button()");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_F11);
    }

    public static void pressCtrlButton() throws AWTException {
        log.info("CALLED: pressCtrlButton()");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
    }

}
