package com.company.engine;

import com.company.Settings;
import com.company.engine.village.building;
import com.company.engine.village.resources;
import com.company.engine.village.village;
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
    public static List<String> expansNames = new ArrayList<>();
    public static List<WebDriver> drivers = new ArrayList<>();
    public static WebDriverWait wait;
    public static List<village> villages = new ArrayList<>();
    private static boolean initialised = false;

    public static void addExp(String expand){
        Integer index = expansNames.size()+1;
        if ( expand.contains( index.toString() ) ){
            expansNames.add(expand);
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
        driver.get("http://ts2.travian.ru");
        WebElement loginInput = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/form/table/tbody/tr[1]/td[2]/input"));
        loginInput.sendKeys(Settings.login);
        WebElement passInput = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/form/table/tbody/tr[2]/td[2]/input"));
        passInput.sendKeys(Settings.password);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"s1\"]"));
        w8alittle();
        loginButton.click();
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"villageNameField\"]")));
    }

    public static void analyzeExps( WebDriver driver ){
        List<WebElement> expanList = driver.findElements(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul/li"));
        expanList.forEach( expDiv -> {
            String expname = expDiv.getText();
            expname = expname.replace("\n","").replace("\u202D","").replace("\u202C","");
            expansNames.add(expname);
            village newV = new village(expname);
            villages.add( newV );
        });
        for (int i = 0; i < expansNames.size(); i++) {
            currentExp.ince().changeExp(i+1, driver);
            analyzeVillage( driver );
            System.out.println( getCurrentVillage() );
        }
    }

    private static void analyzeVillage( WebDriver driver ){
        //ananyleResources( driver );
        //analyzeVillageBuildings( driver );
        analyzeTroops( driver );
    }

    private static void analyzeVillageBuildings( WebDriver driver ){
        if ( driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div[10]/ul/li")).size()>0 && currentExp.ince().checkWindow(1,2)){
            List<WebElement> builds = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div[10]/ul/li"));
            builds.forEach( element -> {
                String divText = element.findElement(By.xpath("./div[1]")).getText();
                String name = divText.substring(0, divText.indexOf("Уровень")-1);
                String level = divText.substring( divText.indexOf("Уровень")+8);
                String timeLeft = element.findElement(By.xpath("./div[2]/span")).getText();
                building build = building.Builder.ince().level(Integer.parseInt(level)).name(name).timeLeft(timeLeft).build();
                getCurrentVillage().addBInProc( build );
            } );
        }
    }

    private static void ananyleResources( WebDriver driver ){
        Float wood  = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l1\"]")).getText() );
        Float clay  = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l2\"]")).getText() );
        Float iron  = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l3\"]")).getText() );
        Float grain = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l4\"]")).getText() );
        village villageInfo = getCurrentVillage();
        villageInfo.resources().set(new resources(wood, clay, iron, grain));
    }

    private static void analyzeTroops( WebDriver driver ){
        if ( driver.findElements(By.xpath("//*[@id=\"troops\"]/tbody/tr")).size() >0 && currentExp.ince().checkWindow(1) ){
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"troops\"]/tbody/tr"));
            elements.forEach( element -> {
                int count = Integer.parseInt( element.findElement(By.xpath("./td[2]")).getText() );
                String type = element.findElement(By.xpath("./td[3]")).getText();
                getCurrentVillage().troops().addTroop( count, type );
            });
        }
    }

    public static village getCurrentVillage(){
        return villages.get( currentExp.ince().getCvindex()-1 );
    }
}
