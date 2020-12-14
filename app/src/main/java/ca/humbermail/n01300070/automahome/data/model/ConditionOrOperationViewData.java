package ca.humbermail.n01300070.automahome.data.model;

public class ConditionOrOperationViewData {
	public static final int TYPE_CONDITION = 0;
	public static final int TYPE_OPERATION = 1;
	
	public static final String ARG_CONDITION = "ConditionType";
	public static final String CONDITION_SCHEDULE = "Schedule";
	public static final String CONDITION_MOVEMENT = "Movement";
	public static final String CONDITION_TEMPERATURE = "Temperature";
	
	public static final String ARG_OPERATION = "OperationType";
	public static final String OPERATION_LIGHTS = "Lights";
	public static final String OPERATION_THERMOSTAT = "Thermostat";
	
	
	private String conditionOrOperationId;
	private int type;
	private String conditionOrOperationType;
	private String mainText;
	private String typeText;
	private boolean dragHandleVisible;
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationId, String conditionOrOperationType) {
		this(type, conditionOrOperationId, conditionOrOperationType, null, null, true);
	}
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationId, String conditionOrOperationType, String mainText) {
		this(type, conditionOrOperationId, conditionOrOperationType, mainText, null, true);
	}
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationId, String conditionOrOperationType, String mainText, String typeText) {
		this(type, conditionOrOperationId, conditionOrOperationType, mainText, typeText, true);
	}
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationId, String conditionOrOperationType, String mainText, String typeText, boolean dragHandleVisible) {
		this.type = type;
		this.conditionOrOperationId = conditionOrOperationId;
		this.conditionOrOperationType = conditionOrOperationType;
		this.mainText = mainText;
		this.typeText = typeText;
		this.dragHandleVisible = dragHandleVisible;
	}
	
	
	public String getConditionOrOperationId() {
		return conditionOrOperationId;
	}
	
	public void setConditionOrOperationId(String conditionOrOperationId) {
		this.conditionOrOperationId = conditionOrOperationId;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getConditionOrOperationType() {
		return conditionOrOperationType;
	}
	
	public void setConditionOrOperationType(String conditionOrOperationType) {
		this.conditionOrOperationType = conditionOrOperationType;
	}
	
	public String getMainText() {
		return mainText;
	}
	
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}
	
	public String getTypeText() {
		return typeText;
	}
	
	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}
	
	public boolean isDragHandleVisible() {
		return dragHandleVisible;
	}
	
	public void setDragHandleVisible(boolean dragHandleVisible) {
		this.dragHandleVisible = dragHandleVisible;
	}
}
