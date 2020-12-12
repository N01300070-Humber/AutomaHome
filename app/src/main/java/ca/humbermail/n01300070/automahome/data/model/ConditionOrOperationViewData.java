package ca.humbermail.n01300070.automahome.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ConditionOrOperationViewData implements Parcelable {
	public static final int TYPE_CONDITION = 0;
	public static final int TYPE_OPERATION = 1;
	
	public static final String ARG_CONDITION = "ConditionType";
	public static final String CONDITION_SCHEDULE = "Schedule";
	public static final String CONDITION_MOVEMENT = "Movement";
	public static final String CONDITION_TEMPERATURE = "Temperature";
	
	public static final String ARG_OPERATION = "OperationType";
	public static final String OPERATION_LIGHTS = "Lights";
	public static final String OPERATION_THERMOSTAT = "Thermostat";
	
	
	private int type;
	private String conditionOrOperationType;
	private String mainText;
	private String typeText;
	private boolean dragHandleVisible;
	
	public ConditionOrOperationViewData() {
		this(-1, null, null, null, true);
	}
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationType) {
		this(type, conditionOrOperationType, null, null, true);
	}
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationType, String mainText) {
		this(type, conditionOrOperationType, mainText, null, true);
	}
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationType, String mainText, String typeText) {
		this(type, conditionOrOperationType, mainText, typeText, true);
	}
	
	public ConditionOrOperationViewData(int type, String conditionOrOperationType, String mainText, String typeText, boolean dragHandleVisible) {
		this.type = type;
		this.conditionOrOperationType = conditionOrOperationType;
		this.mainText = mainText;
		this.typeText = typeText;
		this.dragHandleVisible = dragHandleVisible;
	}

	protected ConditionOrOperationViewData(Parcel in) {
		type = in.readInt();
		conditionOrOperationType = in.readString();
		mainText = in.readString();
		typeText = in.readString();
		dragHandleVisible = in.readByte() != 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(type);
		dest.writeString(conditionOrOperationType);
		dest.writeString(mainText);
		dest.writeString(typeText);
		dest.writeByte((byte) (dragHandleVisible ? 1 : 0));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ConditionOrOperationViewData> CREATOR = new Creator<ConditionOrOperationViewData>() {
		@Override
		public ConditionOrOperationViewData createFromParcel(Parcel in) {
			return new ConditionOrOperationViewData(in);
		}

		@Override
		public ConditionOrOperationViewData[] newArray(int size) {
			return new ConditionOrOperationViewData[size];
		}
	};

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
