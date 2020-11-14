package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.R;

@SuppressWarnings("rawtypes")
public class CategoryView extends LinearLayout {
	
	private TextView headerTextView;
	private RecyclerView itemsRecyclerView;
	
	private CharSequence headerText;
	private int headerTextAppearance;
	boolean initialized = false;
	
	// Constructors
	public CategoryView(Context context) {
		super(context);
		inflateViews(context);
	}
	
	public CategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttributes(context, attrs);
		inflateViews(context);
		setAttributes();
	}
	
	public CategoryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getAttributes(context, attrs);
		inflateViews(context);
		setAttributes();
	}
	
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_category, this);
		
		headerTextView = findViewById(R.id.textView_category_header);
		itemsRecyclerView = findViewById(R.id.recyclerView_category_items);
	}
	
	private void getAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CategoryView);
		
		headerText = attributes.getText(R.styleable.CategoryView_android_text);
		headerTextAppearance = attributes.getResourceId(R.styleable.CategoryView_android_textAppearance,
				R.style.TextAppearance_MaterialComponents_Headline6);
		
		attributes.recycle();
	}
	
	private void setAttributes() {
		setHeaderText(headerText);
		setHeaderTextAppearance(headerTextAppearance);
	}
	
	public CharSequence getHeaderText() {
		return headerTextView.getText();
	}
	
	public void setHeaderText(CharSequence headerText) {
		headerTextView.setText(headerText);
	}
	
	public void setHeaderTextAppearance(int headerTextAppearance) {
		headerTextView.setTextAppearance(headerTextAppearance);
	}
	
	public void setRecyclerViewLayoutManager(RecyclerView.LayoutManager layoutManager) {
		itemsRecyclerView.setLayoutManager(layoutManager);
	}
	
	public void setRecyclerViewAdapter(RecyclerView.Adapter adapter) {
		itemsRecyclerView.setAdapter(adapter);
	}
	
	public void addRecyclerViewItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
		itemsRecyclerView.addItemDecoration(itemDecoration);
	}
	
	public void setHeaderPadding(int paddingSize) {
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) headerTextView.getLayoutParams();
		params.setMargins(paddingSize, 0, paddingSize, 0);
		headerTextView.setLayoutParams(params);
	}
}
