package com.company.engine;

import com.company.Settings;
import com.company.engine.building.Building;
import com.company.engine.village.Resources;
import com.company.engine.village.Village;
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
    public static List<Village> villages = new ArrayList<>();
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

    public static void closeDrivers(){
        drivers.forEach( WebDriver::close );
    }

    public static void init(){
        try {
            System.setProperty("webdriver.chrome.driver", "lib\\chromedriver.exe");
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

    public static void analyzeExps( WebDriver driver, List<Task> tasks ){
        List<WebElement> expanList = driver.findElements(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul/li"));
        expanList.forEach( expDiv -> {
            String expname = expDiv.getText();
            expname = expname.replace("\n","").replace("\u202D","").replace("\u202C","");
            expansNames.add(expname);
            Village newV = new Village(expname);
            villages.add( newV );
        });
        for (int i = 0; i < expansNames.size(); i++) {
            CurrentExp.ince().changeExp(i+1, driver);
            tasks.forEach( task -> task.driver(driver).run() );
            System.out.println( getCurrentVillage() );
        }
    }

    public static void analyzeVillageBuildingsInP( WebDriver driver ){
        if ( driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div[10]/ul/li")).size()>0 && CurrentExp.ince().checkWindow(1,2)){
            List<WebElement> builds = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div[10]/ul/li"));
            builds.forEach( element -> {
                String divText = element.findElement(By.xpath("./div[1]")).getText();
                String name = divText.substring(0, divText.indexOf("Уровень")-1);
                String level = divText.substring( divText.indexOf("Уровень")+8);
                String timeLeft = element.findElement(By.xpath("./div[2]/span")).getText();
                Building build = Building.Builder.ince().level(Integer.parseInt(level)).name(name).timeLeft(timeLeft).build();
                getCurrentVillage().addBInProc( build );
            } );
        }
    }

    public static void analyzeResources( WebDriver driver ){
        Float wood  = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l1\"]")).getText() );
        Float clay  = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l2\"]")).getText() );
        Float iron  = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l3\"]")).getText() );
        Float grain = Float.parseFloat( driver.findElement(By.xpath("//*[@id=\"l4\"]")).getText() );
        Village villageInfo = getCurrentVillage();
        villageInfo.resources().set(new Resources(wood, clay, iron, grain));
    }

    /**
     * Analyzes slotss for buildings(resource fields and buildings)
     */
    public static void analyzeSlots( WebDriver driver){
        int windowId = CurrentExp.ince().getCurrentWindow();
        // resource fields
        if ( !CurrentExp.ince().checkWindow( 1 ) ){
            CurrentExp.ince().changeWindow( 1, driver );
        }
        for ( int i = 1; i < 19; i++ ) {
            String elementValue = driver.findElement( By.xpath( "//*[@id=\"rx\"]/area["+i+"]" ) ).getAttribute( "alt" );
            String name = elementValue.substring( 0, elementValue.indexOf("Уровень")-1 );
            String level = elementValue.substring( elementValue.indexOf("Уровень")+8 );
            Building building = Building.Builder.ince().level( Integer.parseInt( level ) ).name( name ).slotId( i ).build();
            getCurrentVillage().addBuilding( building );
        }
        // buildings
        CurrentExp.ince().changeWindow( 2, driver );
        for ( int i = 1; i < 23; i++ ) {
            String elementValue = driver.findElement( By.xpath( "//*[@id=\"clickareas\"]/area["+i+"]" ) ).getAttribute( "alt" );
            if ( !elementValue.equals( "Стройплощадка" )) {
                String name = elementValue.substring( 0, elementValue.indexOf( "<span" ) - 1 );
                String level = elementValue.substring( elementValue.indexOf( "Уровень" ) + 8, elementValue.indexOf( "</span" ) );
                Building building = Building.Builder.ince().level( Integer.parseInt( level ) ).name( name ).slotId( i + 18 ).build();
                getCurrentVillage().addBuilding( building );
            }else {
                //todo
            }
        }
        CurrentExp.ince().changeWindow( windowId, driver );
    }

    public static void analyzeTroops( WebDriver driver ){
        if ( driver.findElements(By.xpath("//*[@id=\"troops\"]/tbody/tr")).size() >0 && CurrentExp.ince().checkWindow(1) ){
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"troops\"]/tbody/tr"));
            elements.forEach( element -> {
                int count = Integer.parseInt( element.findElement(By.xpath("./td[2]")).getText() );
                String type = element.findElement(By.xpath("./td[3]")).getText();
                getCurrentVillage().troops().addTroop( count, type );
            });
        }
    }

    public static Village getCurrentVillage(){
        return villages.get( CurrentExp.ince().getCvindex()-1 );
    }
}
