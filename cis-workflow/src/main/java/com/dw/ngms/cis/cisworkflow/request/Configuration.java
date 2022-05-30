package com.dw.ngms.cis.cisworkflow.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
public class Configuration {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Module")
    private int module;

    @JsonProperty("TurnAroundTime")
    private long turnAroundTime;

    @JsonProperty("NotificationTime")
    private long notificationTime;

    @JsonProperty("Prefix")
    private String prefix;

    @JsonProperty("ResetNumberEY")
    private String resetNumberEY;

    @JsonProperty("Separator")
    private String separator;

    @JsonProperty("PostfixType")
    private String postfixType;

    @JsonProperty("Postfix")
    private String postfix;

    @JsonProperty("Node")
    private List<Node> nodes;

    @JsonProperty("Context")
    private String context;

    @JsonProperty("ShowAnnouncement")
    private String showAnnouncement;

    @JsonProperty("AnnouncementText")
    private String announcementText;

    public Node getTriggerNode() {
        for (Node node : this.nodes) {
            if (node.getNodeType().equals("Trigger"))
                return node;
        }
        return null;
    }

    public Node getNextNode(Node node) {
        String actionBits = node.getActionBits();
        List<NodeActionBits> nodeActionBitsList = Node.getNodeActionBits(actionBits);
        int nextNodeID = ((NodeActionBits) nodeActionBitsList.get(0)).getNextNode();
        for (Node counterNode : this.nodes) {
            if (counterNode.getNodeID() == nextNodeID)
                return counterNode;
        }
        return null;
    }

    public Node getNode(Long id) {
        for (Node node : this.nodes) {
            if (node.getNodeID().equals(id))
                return node;
        }
        return null;
    }

    public Node getNextNodeForAction(Node node, Long action) {
        List<Outlink> outlinkList = node.getOutlinks();
        for (Outlink outlink : outlinkList) {
            if (outlink.getAction().equals(String.valueOf(action)))
                return getNode(outlink.getNextNodeID());
        }
        return null;
    }
}
