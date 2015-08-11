package com.company.engine.village;

import com.company.engine.Engine;
import com.company.engine.building.Rallypoint;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class TroopSender {
    private final WebDriver driver;
    private final Map<Integer,Integer> troops;
    private final int sendType;
    private int x;
    private int y;
    private long timeleft;
    public TroopSender( WebDriver driver, Map<Integer, Integer> troops, int sendType ){
        this.driver = driver;
        this.troops = troops;
        this.sendType = sendType;
    }

    public TroopSender setX( int x ){
        this.x = x;
        return this;
    }

    public TroopSender setY( int y ){
        this.y = y;
        return this;
    }

    public TroopSender setTimeLeft( long timeLeft ){
        this.timeleft = timeLeft;
        return this;
    }

    public void prepare(){
        Engine.login( driver );
        Rallypoint.enterSendTroops( driver );
        troops.forEach( (id,count) -> driver.findElement( By.name( "t"+id ) ).sendKeys( count.toString() ) );
        switch ( sendType ){
            case 1:
                driver.findElement( By.xpath( "//*[@id=\"build\"]/div[2]/form/div[2]/label[1]" ) ).click();
                break;
            case 2:
                driver.findElement( By.xpath( "//*[@id=\"build\"]/div[2]/form/div[2]/label[2]" ) ).click();
                break;
            case 3:
                driver.findElement( By.xpath( "//*[@id=\"build\"]/div[2]/form/div[2]/label[3]" ) ).click();
                break;
            default:
                System.out.println(" sendType is not recognized");
                Engine.closeDriver( driver );
                return;
        }
        driver.findElement( By.name( "x" ) ).sendKeys( String.valueOf( x ) );
        driver.findElement( By.name( "y" ) ).sendKeys( String.valueOf( y ) );
        driver.findElement( By.xpath( "//*[@id=\"btn_ok\"]" ) ).click();
    }

    public void letsGo(){
        new Thread( ()-> {
                try {
                    Thread.sleep( timeleft * 1000 );
                    driver.findElement( By.xpath( "//*[@id=\"btn_ok\"]" ) ).click();
                    Engine.closeDriver( driver );
                }catch ( Exception e ){
                    e.printStackTrace();
                }
            } ).start();
    }
}
