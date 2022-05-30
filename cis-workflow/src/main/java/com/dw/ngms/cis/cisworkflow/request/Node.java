package com.dw.ngms.cis.cisworkflow.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {

    @JsonProperty("NodeID")
    private Long nodeID;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("NodeType")
    private String nodeType;

    @JsonProperty("RoundRobinID")
    private int roundRobinID;

    @JsonProperty("FormID")
    private String formID;

    private String FormName;

    @JsonProperty("Question")
    private String question;

    @JsonProperty("Heading")
    private String heading;

    @JsonProperty("ActionRequired")
    private String actionRequired;

    private String ActionRequiredCaption;

    @JsonProperty("RoundRobinType")
    private String roundRobinType;

    @JsonProperty("RoundRobinPreferUser")
    private boolean roundRobinPreferUser;

    @JsonProperty("FlagReminder")
    private String flagReminder;

    @JsonProperty("Escalation")
    private String escalation;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("DiagramID")
    private String diagramID;

    @JsonProperty("ActionBits")
    private String actionBits;

    @JsonProperty("Tag")
    private String tag;

    @JsonProperty("MailBits")
    private String mailBits;

    @JsonProperty("NotificationBits")
    private String notificationBits;

    @JsonProperty("XREFBits")
    private String xREFBits;

    @JsonProperty("LetterBits")
    private String letterBits;

    @JsonProperty("FormIDSpecified")
    private boolean formIDSpecified;

    @JsonProperty("EscalationTo")
    private String escalationTo;

    @JsonProperty("Role")
    private String role;

    @JsonProperty("OutLink")
    private List<Outlink> outlinks;

    public static List<NodeActionBits> getNodeActionBits(String nodeActionBits) {
        List<NodeActionBits> nodeActionBitsList = new ArrayList<>();
        String[] actionBits = nodeActionBits.split(":");
        for (String actionBit : actionBits) {
            NodeActionBits nodeActionBitsElement = new NodeActionBits();
            String[] bits = actionBit.split("\\.");
            nodeActionBitsElement.setAction(Integer.valueOf(bits[0]).intValue());
            nodeActionBitsElement.setNextNode(Integer.valueOf(bits[1]).intValue());
            nodeActionBitsElement.setXref(bits[2]);
            nodeActionBitsElement.setBlank(Integer.valueOf(bits[3]).intValue());
            nodeActionBitsElement.setInternalStatus(Integer.valueOf(bits[4]).intValue());
            nodeActionBitsElement.setExternalStatus(Integer.valueOf(bits[5]).intValue());
            nodeActionBitsList.add(nodeActionBitsElement);
        }
        return nodeActionBitsList;
    }

    public Outlink getNextNodeOutlink(Long nodeID) {
        for (Outlink outlink : this.outlinks) {
            if (outlink.getNextNodeID().equals(nodeID))
                return outlink;
        }
        return null;
    }
}
