package com.company.engine.building;

import com.company.Settings;
import com.company.engine.CurrentExp;
import com.company.engine.Engine;
import com.company.engine.Executor;
import com.company.engine.village.TroopSender;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rallypoint {
    public static void enterOverView( WebDriver driver ){
        CurrentExp.ince().changeWindow( 4, driver );
        driver.get( Settings.world+"/build.php?tt=1&gid=16" );
    }
    public static void enterSendTroops( WebDriver driver ){
        CurrentExp.ince().changeWindow( 4, driver );
        driver.get( Settings.world+"/build.php?tt=2&id=39" );
    }

    public static void sendTroops( Map<String,String> params ){
        Map<Integer,Integer> troops = new HashMap<>();
        String unitPattern = "^id[0-9]*$";
        Pattern pattern = Pattern.compile( unitPattern );
        params.forEach( (k,v) -> {
            Matcher m = pattern.matcher( k );
            if (m.matches()) {
                int id = Integer.parseInt( k.replace( "id","" ) );
                int count = Integer.parseInt( v );
                troops.put( id, count );
            }
        } );
        CurrentExp.ince().changeExp( Integer.parseInt( params.get( "from" ) ) ,Executor.ince().getLocalDriver() );
        TroopSender ts = new TroopSender( Engine.newDriver(), troops, Integer.parseInt( params.get( "SendType" ) ) );
        ts.setX( Integer.parseInt( params.get( "x" ) ) );
        ts.setY( Integer.parseInt( params.get( "y" ) ) );
        ts.prepare();
        Engine.w8alittle();
        String timeNowS = LocalDateTime.now().format( DateTimeFormatter.ofPattern( "HH:mm:ss" ) );
        long timeNow = Engine.timeToSecs( timeNowS );
        long timeToGo = Engine.timeToSecs( params.get( "time" ) );
        long timeLeft = timeToGo - timeNow;
        ts.setTimeLeft( timeLeft );
        ts.letsGo();
    }
    public static void sendFarmList( Map<String,String> params ){
        WebDriver localWindow = Engine.newDriver();
        Engine.login( localWindow );
        localWindow.get( Settings.world+"/build.php?tt=99&id=39" );
        Engine.w8alittle();
        localWindow.findElement( By.xpath( "//*[@id=\"raidListMarkAll66\"]" ) ).click();
        //localWindow.findElement( By.xpath( "//*[@id=\"button55ca1ddc5e756\"]" ) ).click();
    }

    public static void fillFarmList( Map<String,String> params ){
        try {
            //ComType=RallyPoint ComId=6 findAround=21:48
            // open gettertoolstable
            Integer cX = 0;
            Integer cY = 0;
            String center = params.get( "findAround" );
            if ( center == null ) {
                System.out.println( "center is not avaliable" );
            }else {
                String[] spltd = center.split( ":" );
                cX = Integer.parseInt( spltd[0] );
                cY = Integer.parseInt( spltd[1] );
            }
            WebDriver driver = Engine.newDriver();
            driver.get( "http://www.gettertools.com/" + Settings.world.replace( "http://", "" ) + ".5/42-Search-inactives" );
            WebDriverWait wait = new WebDriverWait( driver, 10 );
            wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//*[@id=\"contentInner\"]/div/div/div/form/table/tbody/tr[14]/td[2]/button" ) ) );
            // fill center coords
            driver.findElement( By.xpath( "//*[@id=\"xyX\"]" ) ).sendKeys( cX.toString() );
            driver.findElement( By.xpath( "//*[@id=\"xyY\"]" ) ).sendKeys( cY.toString() );
            //fill radius
            driver.findElement( By.xpath( "//*[@id=\"range\"]" ) ).sendKeys( "60" );
            // no noob protection
            driver.findElement( By.xpath( "//*[@id=\"antiNoob\"]" ) ).click();
            // go!
            driver.findElement( By.xpath( "//*[@id=\"contentInner\"]/div/div/div/form/table/tbody/tr[14]/td[2]/button" ) ).click();
            wait.until( ExpectedConditions.presenceOfAllElementsLocatedBy( By.xpath( "//*[@id=\"contentInner\"]/div/div/div/div[2]/div/div/div/table/tbody/tr" ) ) );
            List<String> coordTable = new ArrayList<>();
            // make List of coords
            List<WebElement> rows = driver.findElements( By.xpath( "//*[@id=\"contentInner\"]/div/div/div/div[2]/div/div/div/table/tbody/tr" ) );
            rows.remove( 0 );
            rows.forEach( row -> {
                String coords = row.findElement( By.xpath( "./td[1]" ) ).getText();
                coords = coords.replace( "|",":" ).replace( "\n","" ).replace( "(","" ).replace( ")","" ).replace( " ","" );
                coordTable.add( coords );
            } );
            // add list
            Engine.login( driver );
            driver.get( Settings.world+"/build.php?tt=99&id=39" );
            wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"list66\"]/form/div[2]/div[1]/div[2]/button" ) ) );
            coordTable.forEach( coordsS -> {
                String split[] = coordsS.split( ":" );
                Engine.w8alittle();
                driver.findElement( By.xpath( "//*[@id=\"list66\"]/form/div[2]/div[1]/div[2]/button" ) ).click();
                wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"xCoordInput\"]" ) ) );
                driver.findElement( By.xpath( "//*[@id=\"xCoordInput\"]" ) ).clear();
                driver.findElement( By.xpath( "//*[@id=\"yCoordInput\"]" ) ).clear();
                driver.findElement( By.xpath( "//*[@id=\"xCoordInput\"]" ) ).sendKeys( split[0] );
                Engine.fastW8();
                driver.findElement( By.xpath( "//*[@id=\"yCoordInput\"]" ) ).sendKeys( split[1] );
                driver.findElement( By.xpath( "//*[@id=\"t1\"]" ) ).clear();
                driver.findElement( By.xpath( "//*[@id=\"t1\"]" ) ).sendKeys( "2" );
                driver.findElement( By.xpath( "//*[@id=\"save\"]" ) ).click();
                Engine.w8alittle();
            } );


        }catch ( Exception e ){
            System.out.println( "fillFarmList error" );
            e.printStackTrace();
        }
    }
}
