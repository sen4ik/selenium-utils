package org.sen4ik.utils.selenium.utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sen4ik.utils.selenium.base.SeleniumUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DropdownUtil extends SeleniumUtils {

    /**
     * Selects item from a dropdown.
     * @param webElement
     * @param valueToSelect
     */
    public static void selectDropdownItem(WebElement webElement, String valueToSelect) {
        log.info("CALLED: selectDropdownItem()");
        new Select(webElement).selectByVisibleText(valueToSelect);
    }

    public static void selectDropdownItem(By locator, String valueToBeSelected) {
        log.info("CALLED: selectDropdownItem()");
        selectDropdownItem(getDriver().findElement(locator), valueToBeSelected);
    }

    /**
     * Selects item from a dropdown using index of an item.
     * @param locator
     * @param index
     */
    public static void selectDropdownItem(By locator, int index) {
        log.info("CALLED: selectDropdownItem()");
        new Select(getDriver().findElement(locator)).selectByIndex(index);
    }

    public static boolean isSelectOptionPresent(By locator, String optionText) {
        log.info("CALLED: isSelectOptionPresent()");
        List<WebElement> selectOptions = new Select(getDriver().findElement(locator)).getOptions();
        for (int i = 0; i < selectOptions.size(); i++) {
            String currentOptionText = selectOptions.get(i).getText().trim();
            if (currentOptionText.contains(optionText)) {
                log.info("Option is found");
                return true;
            }
        }
        log.info("Option is NOT found");
        return false;
    }

    public static boolean isSelectEmpty(By locator) {
        log.info("CALLED: isSelectEmpty()");
        Select sel = new Select(getDriver().findElement(locator));
        if (sel.getOptions().size() > 0) {
            log.info("Select is NOT empty");
            return false;
        } else {
            log.info("Select is empty");
            return true;
        }
    }

    /**
     * @param webElement
     * @return Returns currently selected dropdown value.
     */
    public static String getSelectedItemFromDropdown(WebElement webElement) {
        log.info("CALLED: getSelectedItemFromDropdown()");
        String value = new Select(webElement).getFirstSelectedOption().getText();
        log.info("Selected option: '" + value);
        return value;
    }

    /**
     * @param locator
     * @return Returns currently selected dropdown value.
     */
    public static String getSelectedItemFromDropdown(By locator) {
        log.info("CALLED: getSelectedItemFromDropdown()");
        return getSelectedItemFromDropdown(getDriver().findElement(locator));
    }

    /**
     * @param webElement
     * @return Returns dropdown items as a list.
     */
    public static ArrayList<String> getSelectOptions(WebElement webElement) {
        log.info("CALLED: getSelectOptions()");
        List<WebElement> selectOptions = new Select(webElement).getOptions();
        ArrayList<String> result = new ArrayList<String>();
        for (WebElement select : selectOptions) {
            String text = select.getText().trim();
            result.add(text);
        }
        log.info("Result: " + result.toString());
        return result;
    }

    /**
     * @param locator
     * @return Returns dropdown items as a list.
     */
    public static ArrayList<String> getSelectOptions(By locator) {
        log.info("CALLED: getSelectOptions()");
        return getSelectOptions(getDriver().findElement(locator));
    }

}
