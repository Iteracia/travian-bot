package com.company.engine.building;

import com.company.Settings;
import com.company.engine.CurrentExp;
import com.company.engine.Engine;
import com.company.engine.Executor;
import com.company.engine.village.TroopSender;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.InvalidParameterException;
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
            // ComType=RallyPoint ComId=6 findAround=10:38
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
            driver.get( "http://www.gettertools.com/" + Settings.world.replace( "http://", "" ) + ".3/42-Search-inactives" );
            WebDriverWait wait = new WebDriverWait( driver, 10 );
            wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//*[@id=\"contentInner\"]/div/div/div/form/table/tbody/tr[14]/td[2]/button" ) ) );
            // fill center coords
            driver.findElement( By.xpath( "//*[@id=\"xyX\"]" ) ).sendKeys( cX.toString() );
            driver.findElement( By.xpath( "//*[@id=\"xyY\"]" ) ).sendKeys( cY.toString() );
            //fill radius
            driver.findElement( By.xpath( "//*[@id=\"range\"]" ) ).clear();
            driver.findElement( By.xpath( "//*[@id=\"range\"]" ) ).sendKeys( "20" );
            // population
            String popMin = "2";
            String popMax = "200";
            driver.findElement( By.xpath( "//*[@id=\"minSpielerEW\"]" ) ).clear();
            driver.findElement( By.xpath( "//*[@id=\"minSpielerEW\"]" ) ).sendKeys( popMin );
            driver.findElement( By.xpath( "//*[@id=\"maxSpielerEW\"]" ) ).clear();
            driver.findElement( By.xpath( "//*[@id=\"maxSpielerEW\"]" ) ).sendKeys( popMax );
            // population Grows
            String populationGrows = "20";
            driver.findElement( By.xpath( "//*[@id=\"contentInner\"]/div/div/div/form/table/tbody/tr[6]/td[2]/input" ) ).clear();
            driver.findElement( By.xpath( "//*[@id=\"contentInner\"]/div/div/div/form/table/tbody/tr[6]/td[2]/input" ) ).sendKeys( populationGrows );
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
            String listId = "765";
            wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"list"+listId+"\"]/form/div[2]/div[1]/div[2]/button" ) ) );
            WebDriverWait dialogw8 = new WebDriverWait( driver, 3 );
            //String coordsS="22:48";
            coordTable.forEach( coordsS -> {
                String split[] = coordsS.split( ":" );
                Engine.w8alittle();
                driver.findElement( By.xpath( "//*[@id=\"list"+listId+"\"]/form/div[2]/div[1]/div[2]/button" ) ).click();
                wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"xCoordInput\"]" ) ) );
                driver.findElement( By.xpath( "//*[@id=\"xCoordInput\"]" ) ).clear();
                driver.findElement( By.xpath( "//*[@id=\"yCoordInput\"]" ) ).clear();
                driver.findElement( By.xpath( "//*[@id=\"xCoordInput\"]" ) ).sendKeys( split[0] );
                Engine.fastW8();
                driver.findElement( By.xpath( "//*[@id=\"yCoordInput\"]" ) ).sendKeys( split[1] );
                // tier of soldier
                driver.findElement( By.xpath( "//*[@id=\"t3\"]" ) ).clear();
                driver.findElement( By.xpath( "//*[@id=\"t3\"]" ) ).sendKeys( "3" );
                driver.findElement( By.xpath( "//*[@id=\"save\"]" ) ).click();
                try {
                    dialogw8.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "/html/body/div[5]/div/div/div[10]/form/div[6]/button/div/div[2]" ) ) );
                    driver.get( Settings.world + "/build.php?tt=99&id=39" );
                }catch ( TimeoutException e ){

                }
                Engine.w8alittle();
            } );
        }catch ( Exception e ){
            System.out.println( "fillFarmList error" );
            e.printStackTrace();
        }
    }

    public static void copyFarmList( Map<String,String> params ) throws InvalidParameterException{
        // ComType=RallyPoint ComId=7 from=859 to=1175 unitTier=5
        // from and to are farmLists` ids
        String fromListId = params.get( "from" );
        String toListId = params.get( "to" );
        String unitTier = params.get( "unitTier" );
        WebDriver driver = Engine.newDriver();
        WebDriverWait wait = new WebDriverWait( driver, 10 );
        Engine.login( driver );
        driver.get( Settings.world+"/build.php?tt=99&id=39" );
        List<WebElement> fromRows = driver.findElements( By.xpath( "//*[@id=\"list"+fromListId+"\"]/form/div[2]/div[1]/table/tbody/tr" ) );//td2 a
        List<String> coordsList = new ArrayList<>(  );
        fromRows.forEach( row -> {
            // get FromInfo
            WebElement villageLink = row.findElement( By.xpath( "./td[2]/a" ) );
            String href = villageLink.getAttribute( "href" );
            String pattern = ".*x=([0-9]*).*";
            Pattern patternX = Pattern.compile( pattern );
            pattern = ".*y=([0-9]*).*";
            Pattern patternY = Pattern.compile( pattern );
            Matcher matcher = patternX.matcher( href );
            String xCoord, yCoord;
            if ( matcher.matches() ){
                xCoord = matcher.group( 1 ).replace( "x=","" );
            } else {
                throw new InvalidParameterException("cant parse x coord in RallyPoint.copyFarmList");
            }
            matcher = patternY.matcher( href );
            if ( matcher.matches() ){
                yCoord = matcher.group( 1 ).replace( "y=","" );
            } else {
                throw new InvalidParameterException("cant parse y coord in RallyPoint.copyFarmList");
            }
            coordsList.add( xCoord+":"+yCoord );
        });
        coordsList.forEach( element -> {
            // add new
            String[] split = element.split( ":" );
            Engine.w8alittle();
            driver.findElement( By.xpath( "//*[@id=\"list"+toListId+"\"]/form/div[2]/div[1]/div[2]/button" ) ).click();
            wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"xCoordInput\"]" ) ) );
            driver.findElement( By.xpath( "//*[@id=\"xCoordInput\"]" ) ).clear();
            driver.findElement( By.xpath( "//*[@id=\"yCoordInput\"]" ) ).clear();
            driver.findElement( By.xpath( "//*[@id=\"xCoordInput\"]" ) ).sendKeys( split[0] );
            Engine.fastW8();
            driver.findElement( By.xpath( "//*[@id=\"yCoordInput\"]" ) ).sendKeys( split[1] );
            // tier of soldier
            driver.findElement( By.xpath( "//*[@id=\"t"+unitTier+"\"]" ) ).clear();
            driver.findElement( By.xpath( "//*[@id=\"t"+unitTier+"\"]" ) ).sendKeys( "2" );
            driver.findElement( By.xpath( "//*[@id=\"save\"]" ) ).click();
            try {
                wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "/html/body/div[5]/div/div/div[10]/form/div[6]/button/div/div[2]" ) ) );
                driver.get( Settings.world + "/build.php?tt=99&id=39" );
            }catch ( TimeoutException e ){

            }
            Engine.w8alittle();
        } );
    }
}
