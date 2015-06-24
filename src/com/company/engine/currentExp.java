package com.company.engine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class currentExp {
    private static currentExp ourInstance = new currentExp();
    public static currentExp ince() {
        return ourInstance;
    }
    private final long w8timeout = 10000;

    private int cvindex; // index of current village
    private currentExp() {
    }

    public void changeExp(int index, WebDriver driver){
        WebElement expand = driver.findElement(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul/li[" + index + "]"));
        cvindex = index;
        expand.click();
        waitThisToBeCurrent( driver );
        Engine.w8alittle();
    }

    // useless
    private boolean currentVillageCheck( WebDriver driver ){
        try {
            Engine.wait.until( ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"villageNameField\"]")));
            WebElement activeVillageTitleDiv = driver.findElement(By.xpath("//*[@id=\"villageNameField\"]"));
            String activeVillageTitle = activeVillageTitleDiv.getText();
            return activeVillageTitle.equals(Engine.expansNames.get(cvindex-1));
        }catch (Exception e){
            System.out.println("Some problems... currentExp.currentVillageCheck : "+e);
            e.printStackTrace();
            return false;
        }
    }

    private void waitThisToBeCurrent(WebDriver driver){
        long timeStart = System.currentTimeMillis();
        while ( !currentVillageCheck(driver) ){
            try{
                wait( 1000 );
                if( (timeStart + w8timeout) > System.currentTimeMillis() ){
                    return;
                }
            }catch (Exception e){}
        }
    }

    public String getName(){
        return Engine.expansNames.get( cvindex-1 );
    }
}
