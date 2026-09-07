package com.parasoft.testScenarios;


import com.parasoft.annotations.WorkItem;
import com.parasoft.page.CategoriesPage;
import com.parasoft.page.LocalhostPage;
import com.parasoft.page.OrderWizardPage;
import com.parasoft.page.PARASOFTDEMOAPPPage;

import static com.parasoft.annotations.WorkItem.Type.REQ;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class OrderWizardScenarioTests {

    /**
     * Parasoft auto generated base URL
     * Use -DBASE_URL=http://localhost:8080 from command line
     * or use System.setProperty("BASE_URL", "http://localhost:8080") to change base URL at run time.
     */
    private static final String BASE_URL = "http://localhost:8080";

    private WebDriver driver;

    @BeforeEach
    public void beforeTest() {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--start-maximized");
        opts.addArguments("--disable-geolocation");
        opts.addArguments("--incognito");
        opts.addArguments("--enable-strict-powerful-feature-restrictions");
        driver = new ChromeDriver(opts);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Name: MasterOrderWizardScenario
     * Recording file: MasterOrderWizardScenario.json
     * <p>
     * Parasoft recorded Selenium test on Thu Sep 08 2022 07:38:05 GMT+0800 (China Standard Time)
     */
    @Test
    @WorkItem(type= REQ, id="KAN-80", url="https://parasoftindia86.atlassian.net/browse/KAN-80")
    public void testOrderWizardScenario() throws Throwable {
        driver.get(System.getProperty("BASE_URL", BASE_URL) + "/loginPage");


        PARASOFTDEMOAPPPage pARASOFTDEMOAPPPage = new PARASOFTDEMOAPPPage(driver);
        pARASOFTDEMOAPPPage.setUsernameField("purchaser");
        pARASOFTDEMOAPPPage.setPasswordField("password");
        pARASOFTDEMOAPPPage.clickSIGNINButton();


        LocalhostPage localhostPage = new LocalhostPage(driver);
        localhostPage.clickWebElement();


        CategoriesPage categoriesPage = new CategoriesPage(driver);
        categoriesPage.clickADDTOCARTButton();
        categoriesPage.clickWebElement();
        categoriesPage.clickWebElement2();
        categoriesPage.clickPROCEEDTOSUBMISSIONButton();


        OrderWizardPage orderWizardPage = new OrderWizardPage(driver);
        orderWizardPage.selectSelectedAreaDropdown("Western General Store");
        orderWizardPage.setPositionIdField("name");
        orderWizardPage.clickGETLOCATIONButton();
        orderWizardPage.clickINVOICEASSIGNMENTButton();
        orderWizardPage.setCampaignIdField("number");
        orderWizardPage.setCampaignNumberField("order");
        orderWizardPage.clickGOTOREVIEWButton();
        orderWizardPage.clickSUBMITFORAPPROVALButton();
        orderWizardPage.clickPurchaser();
        orderWizardPage.clickSignOutLink();
    }
}