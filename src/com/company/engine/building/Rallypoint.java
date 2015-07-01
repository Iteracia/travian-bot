package com.company.engine.building;

import com.company.engine.CurrentExp;
import com.company.engine.Engine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Rallypoint {
    public static void enterOverView( WebDriver driver ){
        CurrentExp.ince().changeWindow( 4, driver );
        //driver.findElement( By.xpath("//*[@id=\"button5593055702dd9\"]") ).click();
        Engine.wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"build\"]/div[2]/div[1]/div[10]/div[1]" ) ) );

    }
}
