package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import ca.humbermail.n01300070.automahome.R;

public class ConditionOrOperationView extends ConstraintLayout {
	private String conditionOrOperationId = null;
	private TextView mainTextView;
	private TextView typeTextView;
	private ImageView dragHandleImageVIew;
	
	int type;
	String conditionOrOperationType;
	
	public ConditionOrOperationView(Context context) {
		super(context);
		inflateViews(context);
	}
	
	public ConditionOrOperationView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflateViews(context);
	}
	
	public ConditionOrOperationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateViews(context);
	}
	
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_operation_or_condition, this);
		
		mainTextView = findViewById(R.id.textView_operationOrCondition_main);
		typeTextView = findViewById(R.id.textView_operationOrCondition_type);
		dragHandleImageVIew = findViewById(R.id.imageView_operationOrCondition_dragHandle);
	}
	
	public String getConditionOrOperationId() {
		return conditionOrOperationId;
	}
	
	public void setConditionOrOperationId(String conditionOrOperationId) {
		this.conditionOrOperationId = conditionOrOperationId;
	}
	
	public CharSequence getMainText() {
		return mainTextView.getText();
	}
	
	public void setMainText(CharSequence text) {
		mainTextView.setText(text);
	}
	
	public CharSequence getTypeText() {
		return typeTextView.getText();
	}
	
	public void setTypeText(CharSequence text) {
		typeTextView.setText(text);
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
	
	public void setDragHandleVisible(boolean visible) {
		if (visible) {
			dragHandleImageVIew.setVisibility(View.VISIBLE);
		} else {
			dragHandleImageVIew.setVisibility(View.GONE);
		}
	}
}
