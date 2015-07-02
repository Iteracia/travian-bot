package com.company.engine.building;

import com.company.engine.CurrentExp;
import com.company.engine.Engine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;

public class Market {

    public static void sendResources( WebDriver driver , Map<String,String> params ){
        CurrentExp.ince().changeExp( Integer.parseInt( params.get( "from" ) ), driver );
        CurrentExp.ince().changeWindow( 3, driver );
        driver.findElement( By.xpath("//*[@id=\"build\"]/div[1]/div[2]/div[3]") ).click();
        Engine.wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"r1\"]" ) ) );
        WebElement woodInput = driver.findElement( By.xpath( "//*[@id=\"r1\"]" ) );
        WebElement clayInput = driver.findElement( By.xpath( "//*[@id=\"r2\"]" ) );
        WebElement ironInput = driver.findElement( By.xpath( "//*[@id=\"r3\"]" ) );
        WebElement grainInput = driver.findElement( By.xpath( "//*[@id=\"r4\"]" ) );
        woodInput.sendKeys( params.get( "wood" ) );
        clayInput.sendKeys( params.get( "clay" ) );
        ironInput.sendKeys( params.get( "iron" ) );
        grainInput.sendKeys( params.get( "grain" ) );
        driver.findElement( By.xpath( "//*[@id=\"xCoordInput\"]" ) ).sendKeys( params.get( "x" ) );
        driver.findElement( By.xpath( "//*[@id=\"yCoordInput\"]" ) ).sendKeys( params.get( "y" ) );
        Engine.w8alittle();
        driver.findElement( By.xpath( "//*[@id=\"enabledButton\"]/div/div[2]" ) ).click();

    }
}
