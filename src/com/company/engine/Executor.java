package com.company.engine;

import com.company.NodeListWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.company.engine.building.Rallypoint;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Executor implements Runnable{
    private static Executor ourInstance = new Executor();
    public static Executor ince() {
        return ourInstance;
    }
    private final long updateDelta; // delta time before ask whether the executeList is empty
    private static List<Task> tasks = new ArrayList<>();
    private final WebDriver localDriver;

    private Executor() {
        Engine.init();
        localDriver = Engine.newDriver();
        updateDelta = 1000;
    }

    public static void addTask ( Task task ){
        tasks.add( task );
    }
    @Override
    public void run() {
        Engine.login( localDriver );
        analyzeAndExecuteDefault();
        loadCommands();
        analyzeCommands();
        read();
        Engine.closeDrivers();
    }

    private void execute(){
        tasks.forEach( task ->{
            task.driver = localDriver;
            task.run();
        } );
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
                Task newTask = TasksManager.manage( node.getAttributes().getNamedItem( "type" ).getNodeValue(), node.getAttributes().getNamedItem( "id" ).getNodeValue() );
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
                Task newTask = TasksManager.manage( node.getAttributes().getNamedItem( "type" ).getNodeValue(), node.getAttributes().getNamedItem( "id" ).getNodeValue() );
                tasks.add( newTask );
            });
            Engine.analyzeExps( localDriver, tasks );
            tasks.clear();
        }catch ( Exception e ){
            System.out.println("executor.analyzeDefault error");
            e.printStackTrace();
        }
    }

    private void analyzeCommands(){
        try {
            File defaultTasks = new File( "commands.xml" );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( defaultTasks );
            doc.getDocumentElement().normalize();
            List<Node> commands = NodeListWrapper.takeThis( doc.getElementsByTagName( "command" ) );
            commands.forEach( node -> {
                Task newTask = TasksManager.manage( node.getAttributes().getNamedItem( "type" ).getNodeValue(), node.getAttributes().getNamedItem( "id" ).getNodeValue() );
                if ( newTask!=null ) {
                    tasks.add( newTask );
                }
            });
            execute();
        }catch ( Exception e ){
            System.out.println("executor.analyzeCommands error");
            e.printStackTrace();
        }
    }

    private void read(){
        try{
            System.in.read();
        }catch ( Exception e ){}
    }
}
