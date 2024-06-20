package com.hp.step_d;
import com.hp.utilMetods.DataBase_Utils;
import com.hp.pom_pages.CareersPage;
import com.hp.pom_pages.WorkdayJobsPage;
import com.hp.utilMetods.Config_Reader;
import com.hp.utilMetods.Driver;
import com.hp.utilMetods.FrontendUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class UI_StepDef {
    CareersPage careersPage = new CareersPage();
    WorkdayJobsPage workday = new WorkdayJobsPage();
    Map<String, Object> dbMap;
    String firstWindow;
    String expectedSearchData;

    @Given("user is on home page")
    public void user_is_on_home_page() {
        Driver.getDriver().get(Config_Reader.getProperty("url"));
    }

    @When("user clicks to About H&P")
    public void user_clicks_to_about_h_p() {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(careersPage.aboutHP).perform();
    }

    @When("user clicks to the Careers")
    public void user_clicks_to_the_careers() {
        careersPage.careers.click();
        FrontendUtils.sleep(4);
    }

    @Then("validate following info displayed on right navbar")
    public void validate_following_info_displayed_on_right_navbar(List<String> expectedRightNavBar) {
        String rightNavBarText = careersPage.rightNavBar.getText();
        String[] rightNavBarItems = rightNavBarText.split("\\n");
        for (int i = 0; i < rightNavBarItems.length; i++) {
            rightNavBarItems[i] = rightNavBarItems[i].trim();
        }
        List<String> actualNavBar = Arrays.asList(rightNavBarItems);
        Assert.assertEquals(expectedRightNavBar, actualNavBar);
    }

    @When("user navigates to the APPLY NOW")
    public void user_navigates_to_the_apply_now() {
        FrontendUtils.sleep(4);
        careersPage.applyNow.click();
        firstWindow = Driver.getDriver().getWindowHandle();
    }

    @When("user enters {string} into the search box")
    public void user_enters_into_the_search_box(String searchData) {
     Set<String> windows = Driver.getDriver().getWindowHandles();
     List<String> windowHandles = new ArrayList<>(windows);
        for (String eachWindow : windowHandles) {
            if (!(eachWindow.equals(firstWindow))){
                Driver.getDriver().switchTo().window(eachWindow);
            }
            System.out.println("eachWindow = " + eachWindow);
        }
        System.out.println("current sessionId"+Driver.getDriver().getWindowHandle());
        FrontendUtils.sleep(2);
        workday.searchInputBox.sendKeys(searchData);
        expectedSearchData=searchData;
        System.out.println("expectedSearchData = " + expectedSearchData);
    }

    @When("user clicks the Search button")
    public void user_clicks_the_search_button() {
        FrontendUtils.sleep(1);
        workday.searchBtn.click();
    }

    @When("user retrieves the actual list of open jobs from the database")
    public void user_retrieves_the_actual_list_of_open_jobs_from_the_database() {
//        DataBase_Utils.runQuery("select * from JobPosting where job_category='Hotshot Driver'");
//        dbMap = DataBase_Utils.getRowMap(1);
    }

    @Then("validate the actual result with the data retrieved form the database")
    public void validate_the_actual_result_with_the_data_retrieved_form_the_database() {
       String dbFirstJobTitle = (String) dbMap.get("job_title");
       assertEquals(expectedSearchData, dbFirstJobTitle);
    }


}
