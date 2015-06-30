package com.company.engine.building;

import com.company.engine.CurrentExp;
import org.openqa.selenium.WebDriver;

public class Market {

    public static void enterMarketWindow( WebDriver driver ){
        CurrentExp.ince().changeExp( 2, driver );
        CurrentExp.ince().changeWindow( 3, driver );

    }
}
