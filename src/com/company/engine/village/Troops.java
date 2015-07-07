package com.company.engine.village;

import com.company.engine.Engine;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class Troops {
    /*
     * Inner helper class
     */
    public static class troop implements Serializable{
        private int count;
        private String name;

        troop( int count, int id){
            this.count = count;
            this.name = troopNameMap.getK( id );
        }

        @Override
        public String toString() {
            return count+"  "+name;
        }
    }

    /*              ***
            == class itself ==
                    ***
     */
    private static KVmap<String, Integer> troopNameMap = new KVmap<>();
    private static Map<Integer, Integer> tropsSpeedMap = new HashMap<>();

    private List<troop> army;

    public Troops(){
        army = new ArrayList<>( 11 );
        army.add( 0, new troop( 0 , 1 ));
        army.add( 1, new troop( 0 , 2 ));
        army.add( 2, new troop( 0 , 3 ));
        army.add( 3, new troop( 0 , 4 ));
        army.add( 4, new troop( 0 , 5 ));
        army.add( 5, new troop( 0 , 6 ));
        army.add( 6, new troop( 0 , 7 ));
        army.add( 7, new troop( 0 , 8 ));
        army.add( 8, new troop( 0 , 9 ));
        army.add( 9, new troop( 0 , 10 ));
        army.add( 10, new troop( 0 , 11 ));
    }

    static {
        troopNameMap.put( "Легионеров",1 );
        troopNameMap.put( "Преторианцев",2 );
        troopNameMap.put( "Империанцев",3 );
        troopNameMap.put( "Конных разведчиков",4 );
        troopNameMap.put( "Конниц императора",5 );
        troopNameMap.put( "Конниц Цезаря",6 );
        troopNameMap.put( "Таранов",7 );
        troopNameMap.put( "Огненных катапульт",8 );
        troopNameMap.put( "Сенаторов",9 );
        troopNameMap.put( "Поселенцев",10 );
        troopNameMap.put( "Герой",11 );

    }


    public Troops addTroop( int count, String name){
        int id = troopNameMap.getV( name );
        return addTroop( count, id+1 );
    }
    public Troops addTroop( int count, int id){
        army.get( id-1 ).count += count;
        return this;
    }
    public Troops clearTroops(){
        army.clear();
        return this;
    }

    public boolean isEmpty(){
        return army.size()==0;
    }

    public List<troop> getArmy() {
        return army;
    }

    public void setArmy( List<troop> army ) {
        this.army = army;
    }

    public static void copyToClipboardForGetterTools(){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = builderFactory.newDocumentBuilder();
            String filePath = "./resources/troopsTable.xml";
            String outPath = "./resources/troops.html";
            Document document = builder.parse( filePath );
            document.getDocumentElement().normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();

            for ( int i = 1; i <= Engine.villages.size(); i++ ) {
                for ( int j = 1; j < 12; j++ ) {
                    String expression = "//*[@id=\"troops\"]/tbody/tr["+i+"]/td["+j+"]";
                    Node element = (Node) xPath.compile( expression ).evaluate( document, XPathConstants.NODE );
                    element.setTextContent( String.valueOf( Engine.villages.get( i-1 ).troops().army.get( j-1 ).count ) );
                }
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty( OutputKeys.METHOD, "html" );
            transformer.setOutputProperty( OutputKeys.INDENT, "no" );
            DOMSource source = new DOMSource( document );
            StreamResult result = new StreamResult( new File( outPath ) );
            transformer.transform( source, result );
            Engine.newDriver().get( "file:///C:/travian/resources/troops.html" );
            System.out.println(" < Window with troops is opened");
        }catch ( Exception e ){
            e.printStackTrace();
            System.exit( 0 );
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        army.forEach( troop -> builder.append( troop.toString() ).append("\n"));
        return builder.toString();
    }

}
