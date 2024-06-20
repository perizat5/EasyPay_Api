package com.hp.step_d;
import com.hp.utilMetods.Driver;
import com.hp.utilMetods.DataBase_Utils;
import com.hp.utilMetods.FrontendUtils;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.Duration;

public class Hooks {
    @Before("@frontEnd")
    public void setupMethod(){
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }


    @After("@frontEnd")
    public void teardownMethod(Scenario scenario){
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        FrontendUtils.sleep(2);
        Driver.closeDriver();
    }
    // Database connect.
    @Before("@dataBase")
    public void setupDB(){
        System.out.println("connection starts");
        DataBase_Utils.createConnection();
    }


    @After("@dataBase")
    public void closeDB(){
        System.out.println("connection is closing");
        DataBase_Utils.destroy();
    }


}
