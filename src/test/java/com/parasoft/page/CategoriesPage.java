package com.parasoft.page;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class CategoriesPage {

    @FindBy(xpath = "/descendant::button[normalize-space(.)='ADD TO CART'][1]")
    private WebElement aDDTOCARTButton;

    @FindBy(css = "#confirm_button > .ng-scope")
    private WebElement webElement;

    @FindBy(css = "[title='Cart']")
    private WebElement webElement2;

    @FindBy(xpath = "/descendant::button[normalize-space(.)='PROCEED TO SUBMISSION']")
    private WebElement pROCEEDTOSUBMISSIONButton;

    private WebDriver driver;

    private static final int DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT = 15;

    private static final String[] TITLE_WORDS = {"PARASOFT", "DEMO", "APP"};

    public CategoriesPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
        wait.ignoring(StaleElementReferenceException.class);
        Arrays.stream(TITLE_WORDS).forEach(word -> {
            wait.until(attributeContains(By.tagName("title"), "innerHTML", word));
        });
        PageFactory.initElements(driver, this);
    }

    private WebElement waitFor(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
        wait.ignoring(StaleElementReferenceException.class);
        return wait.until(elementToBeClickable(element));
    }

    private WebElement scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
        return element;
    }

    protected WebElement click(WebElement element) {
        WebElement webElement = scrollTo(waitFor(element));
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
        return wait.ignoring(ElementClickInterceptedException.class).until(webDriver -> {
            webElement.click();
            return webElement;
        });
    }

    public void clickADDTOCARTButton() {
        click(aDDTOCARTButton);
    }


    public void clickWebElement() {
        click(webElement);
    }


    public void clickWebElement2() {
        click(webElement2);
    }


    public void clickPROCEEDTOSUBMISSIONButton() {
        click(pROCEEDTOSUBMISSIONButton);
    }


}