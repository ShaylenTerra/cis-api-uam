package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WORKFLOW_ACTION")
@SqlResultSetMapping(
		name = "findAllAutoDispatchResultSet",
		classes = @ConstructorResult(
				targetClass = AutoDispatchWorkflow.class,
				columns = {
						@ColumnResult(name = "processId"),
						@ColumnResult(name = "processName"),
						@ColumnResult(name = "actionRequiredDescription"),
						@ColumnResult(name = "workflowId"),
						@ColumnResult(name = "referenceNo"),
						@ColumnResult(name = "provinceId"),
						@ColumnResult(name = "turnaroundDuration"),
						@ColumnResult(name = "actionId"),
						@ColumnResult(name = "actionRequired"),
						@ColumnResult(name = "allocatedOn"),
						@ColumnResult(name = "assignedToUser"),
						@ColumnResult(name = "diariseDate"),
						@ColumnResult(name = "internalStatus"),
						@ColumnResult(name = "internalStatusCaption"),
						@ColumnResult(name = "externalStatus"),
						@ColumnResult(name = "externalStatusCaption"),
						@ColumnResult(name = "priorityFlag"),
						@ColumnResult(name = "priorityName"),
						@ColumnResult(name = "escalationTime"),
						@ColumnResult(name = "actionContext"),
						@ColumnResult(name = "triggeredOn"),
						@ColumnResult(name = "nodeId"),
						@ColumnResult(name = "parentProcessId")
				}
		)
)
@NamedNativeQuery(name = "findAllAutoDispatch",
		resultSetMapping = "findAllAutoDispatchResultSet",
		query="SELECT " +
				"    WF.PROCESSID as processId,\n" +
				"    WP.NAME as processName,\n" +
				"    WPM.DESCRIPTION AS actionRequiredDescription,\n" +
				"    WF.WORKFLOWID as workflowId,\n" +
				"    WF.REFERENCE_NO as referenceNo,\n" +
				"    WF.PROVINCEID as provinceId,\n" +
				"    WF.TRUNAROUND_DURATION as turnaroundDuration ,\n" +
				"    WA.ACTIONID as actionId,\n" +
				"    WA.ACTIONREQUIRED as actionRequired,\n" +
				"    WA.ALLOCATEDON as allocatedOn,\n" +
				"    CONCAT ( CONCAT(U.FIRSTNAME, ' ') ,  U.SURNAME )  AS assignedToUser,\n" +
				"    WA.DIARISEDATE as diariseDate,\n" +
				"    WF.INTERNAL_STATUS_ID as internalStatus,\n" +
				"    INT_STATUS.DESCRIPTION as internalStatusCaption,\n" +
				"    WF.EXTERNAL_STATUS_ID as externalStatus,\n" +
				"    EXT_STATUS.DESCRIPTION as externalStatusCaption,\n" +
				"    WF.PRIORITY_FLAG as priorityFlag,\n" +
				"    FLAG.DESCRIPTION as priorityName,\n" +
				"    WA.ESCALATION_TIME as escalationTime,\n" +
				"    WA.CONTEXT as actionContext,\n" +
				"    WF.TRIGGEREDON as triggeredOn,\n" +
				"    WA.NODEID as nodeId ,\n" +
				"    WP.PARENT_PROCESSID as parentProcessId \n" +
				" FROM WORKFLOW_ACTION WA\n" +
				"          INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
				"          INNER JOIN WORKFLOWPROCESS WP  ON WF.PROCESSID = WP.PROCESSID\n" +
				"          INNER JOIN USERS U ON WA.USERID = U.USERID\n" +
				"          INNER JOIN WORKFLOWPROCESS_MATRIX  WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
				"          LEFT JOIN WORKFLOWPROCESS_MATRIX  INT_STATUS  on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
				"          LEFT JOIN WORKFLOWPROCESS_MATRIX  EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
				"          LEFT JOIN WORKFLOWPROCESS_MATRIX  FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
				"WHERE   (WA.ACTIONTAKEN IS NULL  and WF.STATUSID=1)\n" +
				"  and WP.PARENT_PROCESSID = 164 AND WA.WORKFLOWID = :workflowId")
public class WorkflowAction implements Serializable {

	@Id
	@Column(name = "ACTIONID")
	@GeneratedValue(generator = "workflow_action_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "workflow_action_seq", sequenceName = "WORKFLOW_ACTION_SEQ", allocationSize = 1)
	private Long actionId;

	@Column(name = "WORKFLOWID")
	private Long workflowId;

	@Column(name = "ACTIONREQUIRED")
	private Long actionRequired;

	@Column(name = "POSTEDON")
	private LocalDateTime postedOn;

	@Column(name = "USERID")
	private Long userId;

	@Column(name = "ALLOCATEDON")
	private Date allocatedOn;

	@Column(name = "ACTEDON")
	private LocalDateTime actedOn;

	@Column(name = "ACTIONTAKEN")
	private Long actionTaken;

	@Column(name = "TRANSACTIONID")
	private Long transactionId;

	@Column(name = "ACTIONTRANSACTIONID")
	private Long actionTransactionId;

	@Column(name = "LINKID")
	private Long linkId;

	@Column(name = "NODEID")
	private Long nodeId;

	@Column(name = "DATA_JSON")
	private String dataJson;

	@Column(name = "DIARISEDATE")
	private Date diariseDate;

	@Column(name = "CONTEXT")
	private String context;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "NOTIFICATION_TIME")
	private Long notificationTime;

	@Column(name = "ESCALATION_TIME")
	private Long escalationTime;

}
