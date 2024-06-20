package com.hp.pom_pages;

import com.hp.utilMetods.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WorkdayJobsPage {

    public WorkdayJobsPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//input[@type='text']")//
    public WebElement searchInputBox;
//    data-automation-id="keywordSearchInput"
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement searchBtn;

@FindBy(xpath = "//a[@data-automation-id='jobTitle'")
    public WebElement firstJobTitle;




}
