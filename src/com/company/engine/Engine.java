package com.company.engine;

import com.company.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {
    public static List<String> expans = new ArrayList<>();
    public static List<WebDriver> drivers = new ArrayList<>();
    private static boolean initialised = false;
    public static WebDriverWait wait;

    public static void addExp(String expand){
        Integer index = expans.size()+1;
        if ( expand.contains( index.toString() ) ){
            expans.add( expand );
        }else {
            System.out.println("Error edding expand - engine.addExp");
        }
    }

    public static void w8alittle(){
        try {
            Random rr = new Random();
            int rvalue = rr.nextInt(5) + 1;
            Thread.sleep(rvalue * 1000);
        }catch (Exception e){
            System.out.println("Engine.w8alittle error : "+e);
            e.printStackTrace();
        }
    }

    public static WebDriver newDriver() throws IllegalStateException{
        if(!initialised){
            throw new IllegalStateException("engine is not initialised!");
        }
        WebDriver newD = new ChromeDriver();
        drivers.add( newD );
        return newD;
    }

    public static void init(){
        try {
            System.setProperty("webdriver.chrome.driver", "C:\\travian\\lib\\chromedriver.exe");
            initialised = true;
        }catch (Exception e){
            System.out.println("Cant init Engine");
            initialised = false;
        }
    }

    public static void login( WebDriver driver ){
        w8alittle();
        driver.get("http://ts2.travian.ru");
        WebElement loginInput = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/form/table/tbody/tr[1]/td[2]/input"));
        loginInput.sendKeys(Settings.login);
        WebElement passInput = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/form/table/tbody/tr[2]/td[2]/input"));
        passInput.sendKeys(Settings.password);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"s1\"]"));
        loginButton.click();
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"villageNameField\"]")));
    }

    public static void analyzeExps( WebDriver driver ){
        List<WebElement> expanList = driver.findElements(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul/li"));
        expanList.forEach( expDiv -> {
            String expname = expDiv.getText();
            expname = expname.replace("\n","");
            expans.add( expname );
        });
        for (int i = 0; i < expans.size(); i++) {
            currentExp.ince().changeExp(i+1, driver);
        }
    }

    public static void analyzeVillageBuildings( WebDriver driver ){
        if ( driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]")).size()>0 ){

        }
    }
}
