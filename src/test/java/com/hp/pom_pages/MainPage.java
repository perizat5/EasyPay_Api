package com.hp.pom_pages;

import com.hp.utilMetods.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    public MainPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(linkText = "About H&P")
    public WebElement aboutHP;


    @FindBy(linkText = "Careers") //
    public WebElement careers;
//li[@data-level='1']/following-sibling::li[6]//li[2]

}
