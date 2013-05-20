/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.factory;

import mscproject.graph.controller.LinkController;
import mscproject.graph.model.LinkModel;
import mscproject.graph.model.LinkModel.LinkType;
import mscproject.graph.model.NodeModel;
import mscproject.graph.view.EqualLinkView;
import mscproject.graph.view.LinkView;
import mscproject.graph.view.ProductLinkView;
import mscproject.graph.view.SumLinkView;

/**
 *
 * @author Andy
 */
public class LinkFactory {
    

    
    public LinkFactory() {
        
    }
    
    /**
     * Creates a new link of the defined linkType, the model and view are added
     * to the controller before a reference to the controller is returned.
     * 
     * The factory assumes that the link is to be added to node1 and node2 and so
     * calls the add() method for you.
     * 
     * @param node1
     * @param node2
     * @param linkType
     * @return 
     */
    public LinkController makeLink(NodeModel node1, NodeModel node2, LinkType linkType) {
        LinkModel model;
        LinkView view;
        LinkController controller;
        
        switch (linkType) {
            default:
            case SIMPLE: view =  new LinkView();
                break;
            case ADDITION: view =  new SumLinkView();
                break;
            case MULTIPLICATION: view =  new ProductLinkView();
                break;
            case EQUALS: view =  new EqualLinkView();
                break;                
        }
        model = new LinkModel(node1, node2, linkType, view).add();
        controller = new LinkController(model);
        return controller;
    }
    
}
