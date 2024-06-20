package com.hp.pom_pages;

import com.hp.utilMetods.Driver;
import io.cucumber.core.cli.Main;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CareersPage extends MainPage {
    public CareersPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div[@class='way-finder']/ul") //className = "way-finder"
    public WebElement rightNavBar;

    @FindBy(linkText = "View open positions at our corporate offices.")
    public WebElement openJobs; // View open positions at our corporate offices.

    @FindBy(linkText = "APPLY NOW")
    public WebElement applyNow; // View open positions at our corporate offices.
    @FindBy(id = "logo")
     public WebElement logo;
}
