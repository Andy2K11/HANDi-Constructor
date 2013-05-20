/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.factory;

import mscproject.graph.controller.NodeController;
import mscproject.graph.model.NodeModel;
import mscproject.graph.view.NodeView;

/**
 *
 * @author Andy
 */
public class NodeFactory {
    
    // These node types are temporary, but can easily be changed later.
    public enum NodeType {SIMPLE, MVC}   
    
    // Default constructor
    public NodeFactory() {
        
    }
    
    public NodeController makeNode(NodeType type) {
        NodeModel model;
        NodeView view;
        NodeController controller;
        switch (type) {
            default:
            case MVC:
                
                view = new NodeView();
                model = new NodeModel(view);
                controller = new NodeController(model);
                break;
        }
        return controller;
    }

}
