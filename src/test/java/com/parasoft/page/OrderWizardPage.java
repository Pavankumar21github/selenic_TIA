package com.parasoft.page;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class OrderWizardPage {

    @FindBy(name = "selectedArea")
    private WebElement selectedAreaDropdown;

    @FindBy(name = "positionId")
    private WebElement positionIdField;

    @FindBy(xpath = "/descendant::button[normalize-space(.)='GET LOCATION']")
    private WebElement gETLOCATIONButton;

    @FindBy(xpath = "/descendant::button[normalize-space(.)='INVOICE ASSIGNMENT']")
    private WebElement iNVOICEASSIGNMENTButton;

    @FindBy(id = "campaign_id")
    private WebElement campaignIdField;

    @FindBy(id = "campaign_number")
    private WebElement campaignNumberField;

    @FindBy(xpath = "/descendant::button[normalize-space(.)='GO TO REVIEW']")
    private WebElement gOTOREVIEWButton;

    @FindBy(xpath = "/descendant::button[normalize-space(.)='SUBMIT FOR APPROVAL']")
    private WebElement sUBMITFORAPPROVALButton;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='purchaser']")
    private WebElement purchaser;

    @FindBy(linkText = "Sign out")
    private WebElement signOutLink;

    private WebDriver driver;

    private static final int DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT = 15;

    private static final String[] TITLE_WORDS = {"PARASOFT", "DEMO", "APP"};

    public OrderWizardPage(WebDriver driver) {
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

    public void selectSelectedAreaDropdown(String text) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(webdriver -> new Select(selectedAreaDropdown).getOptions().stream()
                .anyMatch(element -> text.equals(element.getText())));
        Select dropdown = new Select(selectedAreaDropdown);
        dropdown.selectByVisibleText(text);
    }


    public void setPositionIdField(String text) {
        waitFor(positionIdField).clear();
        positionIdField.sendKeys(text);
    }


    public void clickGETLOCATIONButton() {
        click(gETLOCATIONButton);
    }


    public void clickINVOICEASSIGNMENTButton() {
        click(iNVOICEASSIGNMENTButton);
    }


    public void setCampaignIdField(String text) {
        waitFor(campaignIdField).clear();
        campaignIdField.sendKeys(text);
    }


    public void setCampaignNumberField(String text) {
        waitFor(campaignNumberField).clear();
        campaignNumberField.sendKeys(text);
    }


    public void clickGOTOREVIEWButton() {
        click(gOTOREVIEWButton);
    }


    public void clickSUBMITFORAPPROVALButton() {
        click(sUBMITFORAPPROVALButton);
    }


    public void clickPurchaser() {
        click(purchaser);
    }


    public void clickSignOutLink() {
        click(signOutLink);
    }


}