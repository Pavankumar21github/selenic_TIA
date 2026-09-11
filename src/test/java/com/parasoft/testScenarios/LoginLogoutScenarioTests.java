package com.parasoft.testScenarios;


import com.parasoft.annotations.WorkItem;
import static com.parasoft.annotations.WorkItem.Type.*;
import com.parasoft.page.LocalhostPage;
import com.parasoft.page.PARASOFTDEMOAPPPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class LoginLogoutScenarioTests {

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
     * Name: MasterLoginAndLogoutScenario
     * Recording file: MasterLoginAndLogoutScenario.json
     * <p>
     * Parasoft recorded Selenium test on Thu Sep 08 2022 07:36:45 GMT+0800 (China Standard Time)
     */
 /**   @Test
    @WorkItem(type= REQ, id="KAN-80", url="https://parasoftindia86.atlassian.net/browse/KAN-80")
    public void testLoginAndLogoutScenario() throws Throwable {
        driver.get(System.getProperty("BASE_URL", BASE_URL) + "/loginPage");


        PARASOFTDEMOAPPPage pARASOFTDEMOAPPPage = new PARASOFTDEMOAPPPage(driver);
        pARASOFTDEMOAPPPage.setUsernameField("purchaser");
        pARASOFTDEMOAPPPage.setPasswordField("password");
        pARASOFTDEMOAPPPage.clickSIGNINButton();


        LocalhostPage localhostPage = new LocalhostPage(driver);
        localhostPage.clickPurchaser();
        localhostPage.clickSignOutLink(); */
        
    }
}
