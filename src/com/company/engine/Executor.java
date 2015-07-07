package com.company.engine;

import com.company.NodeListWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import com.company.Settings;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Executor implements Runnable{
    private static Executor ourInstance = new Executor();
    public static Executor ince() {
        return ourInstance;
    }
    private final long updateDelta; // delta time before ask whether the executeList is empty
    private List<Task> tasks = new ArrayList<>();
    private final WebDriver localDriver;

    public WebDriver getLocalDriver() {
        return localDriver;
    }

    private Executor() {
        Engine.init();
        localDriver = Engine.newDriver();
        updateDelta = 1000;
    }

    public synchronized void addTask ( Task task ){
        tasks.add( task );
    }
    private synchronized void clearTasks(){
        tasks.clear();
    }
    @Override
    public void run() {
        Engine.login( localDriver );
        analyzeAndExecuteDefault();
        loadCommands();
        analyzeCommands();
        readCommand();
        Engine.closeDrivers();
    }

    private synchronized void execute(){
        tasks.forEach( task ->{
            task.driver = localDriver;
            task.run();
        } );
        clearTasks();
    }

    private void loadCommands(){
        try {
            File loads = new File( "loadCommands.xml" );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( loads );
            doc.getDocumentElement().normalize();
            List<Node> commands = NodeListWrapper.takeThis( doc.getElementsByTagName( "command" ) );
            commands.forEach( node -> {
                Task newTask = TasksManager.manage( node.getAttributes().getNamedItem( "type" ).getNodeValue(), node.getAttributes().getNamedItem( "id" ).getNodeValue(), null );
                newTask.driver( localDriver ).run();
            } );
        }catch ( Exception e ){
            System.out.println( "executor.loadCommands error" );
        }
    }

    private void analyzeAndExecuteDefault(){
        try {
            File defaultTasks = new File( "defaultCommands.xml" );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( defaultTasks );
            doc.getDocumentElement().normalize();
            List<Node> commands = NodeListWrapper.takeThis( doc.getElementsByTagName( "command" ) );
            commands.forEach( node -> {
                Task newTask = TasksManager.manage( node.getAttributes().getNamedItem( "type" ).getNodeValue(), node.getAttributes().getNamedItem( "id" ).getNodeValue(), null );
                addTask( newTask );
            });
            Engine.analyzeExps( localDriver, tasks );
            clearTasks();
        }catch ( Exception e ){
            System.out.println("executor.analyzeDefault error");
            e.printStackTrace();
        }
    }

    private void analyzeCommands(){
        try {
            File taskFile = new File( "commands.xml" );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( taskFile );
            doc.getDocumentElement().normalize();
            List<Node> commands = NodeListWrapper.takeThis( doc.getElementsByTagName( "command" ) );
            commands.forEach( node -> {
                Map<String,String> params = parseParamsForNode( (Element)node );
                Task newTask = TasksManager.manage( node.getAttributes().getNamedItem( "type" ).getNodeValue(), node.getAttributes().getNamedItem( "id" ).getNodeValue(), params );
                if ( newTask!=null ) {
                    tasks.add( newTask );
                }
            });
            if ( Settings.deleteXMLCommandsOnRead ){
                FileWriter writer = new FileWriter( taskFile );
                writer.write( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<commands xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "          xsi:noNamespaceSchemaLocation=\"./resources/command.xsd\">\n" +
                        "</commands>" );
                writer.flush();
                writer.close();
            }
            execute();
        }catch ( Exception e ){
            System.out.println("executor.analyzeCommands error");
            e.printStackTrace();
        }
    }
    private Map<String,String> parseParamsForNode(Element node){
        Map<String,String> params = new HashMap<>(  );
        NodeListWrapper.takeThis( node.getChildNodes() ).forEach( nodeCh ->{
            if ( nodeCh.getChildNodes().getLength()>0 )
                params.putAll( parseParamsForNode( (Element)nodeCh ) );
        } );
        if(node.getChildNodes().getLength() == 1) {
            params.put( node.getNodeName(), node.getTextContent().replace( "\n","" ) );
        }
        return params;
    }

    private Map<String,String> parseParamsForConsole(String string){
        Map<String,String> params = new HashMap<>();
        List<String> splited = Arrays.asList( string.split( " " ) );
        splited.forEach( param -> {
            String[] spl = param.split( "=" );
            params.put( spl[0], spl[1] );
        } );
        return params;
    }

    private void readCommand(){
        String command = read();
        while ( command != "exit" ) {
            Map<String, String> params = parseParamsForConsole( command );
            try {
                addTask( TasksManager.manage( params.get( "ComType" ), params.get( "ComId" ), params ) );
            } catch ( NullPointerException e ) {
                System.out.println( " Command input error probably " );
            }
            execute();
            command = read();
        }
    }
    private String read(){
        try{
            System.out.println(" < Enter command to execute ( ComType ComId is required )");
            System.out.printf( " > " );
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        }catch ( Exception e ){
            return "";
        }
    }
}
