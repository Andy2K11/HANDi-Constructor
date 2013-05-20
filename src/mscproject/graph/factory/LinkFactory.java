/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.factory;

import mscproject.graph.controller.LinkController;
import mscproject.graph.model.LinkModel;
import mscproject.graph.model.LinkModel.LinkType;
import mscproject.graph.model.NodeModel;
import mscproject.graph.view.AbstractLinkView;
import mscproject.graph.view.EqualLinkView;
import mscproject.graph.view.LinkView;
import mscproject.graph.view.ProductLinkView;
import mscproject.graph.view.Routable;
import mscproject.graph.view.SumLinkView;

/**
 *
 * @author Andy
 */
public class LinkFactory {
    private LinkModel model;
    private AbstractLinkView view;
    private LinkController controller;    

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
        setViewPositions(node1, node2);
        model = new LinkModel(node1, node2, linkType, view).add();
        controller = new LinkController(model);
        return controller;
    }
    
    /*
     * Helper method to set initial positions of link
     */
    private void setViewPositions(NodeModel node1, NodeModel node2) {
        double startX = node1.getView().getLayoutX();
        double startY = node1.getView().getLayoutY();
        double endX = node2.getView().getLayoutX();
        double endY = node2.getView().getLayoutY();
        view.setStartPosition(startX, startY);
        view.setEndPosition(endX, endY);
        if (view instanceof Routable) {
            ((Routable)view).calculateControlPosition();
        }       
    }
}
