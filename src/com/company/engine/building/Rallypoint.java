package com.company.engine.building;

import com.company.engine.CurrentExp;
import com.company.engine.Engine;
import com.company.engine.Executor;
import com.company.engine.village.TroopSender;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rallypoint {
    public static void enterOverView( WebDriver driver ){
        CurrentExp.ince().changeWindow( 4, driver );
        driver.get( "http://ts2.travian.ru/build.php?tt=1&gid=16" );
    }
    public static void enterSendTroops( WebDriver driver ){
        CurrentExp.ince().changeWindow( 4, driver );
        driver.get( "http://ts2.travian.ru/build.php?tt=2&id=39" );
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
}
