package com.company.engine;

import com.company.engine.building.Building;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CurrentExp {
    private static CurrentExp ourInstance = new CurrentExp();
    public static CurrentExp ince() {
        return ourInstance;
    }
    private CurrentExp() {
    }
    private final long w8timeout = 10000;

    /**
     *
     * @return index of current village
     */
    public int getCvindex() {
        return cvindex;
    }

    private int cvindex; // index of current village
    private int currentWindow = 1; // current window on village 1=fileds 2=village

    /**
     * Changes the current village into another one specified by index
     * @param index village to enter
     * @param driver WebDriver of window
     */
    public void changeExp(int index, WebDriver driver){
        WebElement expand = driver.findElement(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul/li[" + index + "]"));
        cvindex = index;
        expand.click();
        waitThisToBeCurrent( driver );
        Engine.w8alittle();
    }

    /**
     * Checks whether the current window`s village is current as we think
     * @param driver WebDriver of window
     * @return all is ok
     */
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

    /**
     * Waits w8timeout on driver while he opens new village that should be current
     * @param driver WebDriver of window
     */
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

    /**
     * Return the name of the current village
     * @return Name of village
     */
    public String getName(){
        return Engine.expansNames.get( cvindex-1 );
    }

    /**
     * Chanes the window state to a new one
     * 1 = resourse fields
     * 2 = village inner view (buildings)
     * 3 = market
     * 4 = rally point
     * @param windowId new window state Id
     * @param driver WebDriver of window
     */
    public void changeWindow(int windowId, WebDriver driver){
        switch (windowId){
            case 1:
                driver.findElement(By.xpath("//*[@id=\"n1\"]")).click();
                Engine.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"n1\"]/a")));
                WebElement element = driver.findElement(By.xpath("//*[@id=\"n1\"]/a"));
                if ( element.getAttribute("class").equals("active") ){
                    currentWindow = 1;
                    Engine.w8alittle();
                    break;
                }else {
                    Engine.w8alittle();
                    changeWindow( windowId, driver );
                }
            case 2:
                driver.findElement(By.xpath("//*[@id=\"n2\"]")).click();
                Engine.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"n2\"]/a")));
                element = driver.findElement(By.xpath("//*[@id=\"n2\"]/a"));
                if ( element.getAttribute("class").equals("active") ){
                    currentWindow = 2;
                    Engine.w8alittle();
                    break;
                }else {
                    Engine.w8alittle();
                    changeWindow( windowId, driver );
                }
            case 3:
                int oid = getBuildingId( "Рынок" );
                driver.get( "http://ts2.travian.ru/build.php?id="+oid );
                Engine.wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"build\"]/div[1]/div[2]/div[3]" ) ) );
                break;
            case 4:
                oid = getBuildingId( "Пункт сбора" );
                driver.get( "http://ts2.travian.ru/build.php?id="+oid );
                break;
            default:
                System.out.println("CurrentExp.changeWindow index out of range");
        }
    }

    /**
     * Check whether the current window state is one of the window ids
     * @param windowId list of windows ids to check
     * @return window is in one of the states of windowIds
     */
    public boolean checkWindow( int ... windowId ){
        for (int id : windowId){
            if ( id == currentWindow){
                return true;
            }
        }
        return false;
    }

    public int getCurrentWindow(){
        return currentWindow;
    }

    /**
     * Gest building id in currentVillage
     * @param buildName name of building to find
     * @return it`s id
     */
    private int getBuildingId( String buildName){
        List<Building> builds = Engine.getCurrentVillage().getBuildings();
        return builds.stream().filter( b -> b.getName().equals( buildName ) ).findFirst().get().getSlotId();
    }
}
