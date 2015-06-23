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

    private int cvindex; // index of current village
    private currentExp() {
    }

    /**
     * Sets up the current village index
     * @param cei current exp index
     */
    public void init(int cei){
        cvindex = cei;
    }

    public void changeExp(int index, WebDriver driver){
        WebElement expand = driver.findElement(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul/li[" + index + "]"));
        cvindex = index;
        expand.click();
        Engine.wait.until( ExpectedConditions.textToBePresentInElement(By.xpath("//*[@id=\"villageNameField\"]"), Engine.expans.get(cvindex)) );
        Engine.w8alittle();
    }

    // useless
    private boolean currentVillageCheck(int index, WebDriver driver){
        try {
            WebElement activeVillageTitleDiv = driver.findElement(By.xpath("//*[@id=\"villageNameField\"]"));
            String activeVillageTitle = activeVillageTitleDiv.getText();
            return activeVillageTitle.equals(Engine.expans.get(cvindex));
        }catch (Exception e){
            System.out.println("Some problems... currentExp.currentVillageCheck : "+e);
            e.printStackTrace();
            return false;
        }
    }
}
