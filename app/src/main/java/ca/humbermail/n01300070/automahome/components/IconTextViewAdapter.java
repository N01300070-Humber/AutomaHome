package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IconTextViewAdapter extends RecyclerView.Adapter<IconTextViewAdapter.IconTextViewHolder> {
	private Context context;
	
	private ArrayList<IconTextData> iconTextDataList;
	View.OnClickListener onClickListener;
	
	public class IconTextViewHolder extends RecyclerView.ViewHolder {
		public IconTextView iconTextView;
		
		public IconTextViewHolder(IconTextView iconTextView) {
			super(iconTextView);
			this.iconTextView = iconTextView;
		}
	}
	
	public IconTextViewAdapter(Context context, ArrayList<IconTextData> iconTextDataList, View.OnClickListener onClickListener) {
		this.context = context;
		this.iconTextDataList = iconTextDataList;
		this.onClickListener = onClickListener;
	}
	
	@NonNull
	@Override
	public IconTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		IconTextView iconTextView = new IconTextView(parent.getContext());
		
		iconTextView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ));
		iconTextView.setOrientation(LinearLayout.HORIZONTAL);
		
		return new IconTextViewHolder(iconTextView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull IconTextViewHolder holder, int position) {
		IconTextData data = iconTextDataList.get(position);
		
		holder.iconTextView.setText(data.getText());
		holder.iconTextView.setTextAppearance(data.getTextAppearance());
		holder.iconTextView.setIcon(data.getIcon());
		holder.iconTextView.setIconTint(data.getIconTint());
		holder.iconTextView.setIconContentDescription(data.getIconContentDescription());
		holder.iconTextView.setIconVisible(data.isIconVisible());
	}
	
	@Override
	public int getItemCount() {
		return iconTextDataList.size();
	}
}
