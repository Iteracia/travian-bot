package com.company;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.AbstractList;

public class NodeListWrapper {
    public static List<Node> takeThis(NodeList nodeList){
        return new AbstractList<Node>(){
            @Override
            public int size() {
                return nodeList.getLength();
            }

            @Override
            public Node get( int index ) {
                return nodeList.item( index );
            }
        };
    }
}
