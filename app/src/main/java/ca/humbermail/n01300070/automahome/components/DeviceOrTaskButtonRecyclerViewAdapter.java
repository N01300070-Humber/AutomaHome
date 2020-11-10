package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;

public class DeviceOrTaskButtonRecyclerViewAdapter extends RecyclerView.Adapter<DeviceOrTaskButtonRecyclerViewAdapter.ItemViewHolder> {
	
	Context context;
	private final ArrayList<DeviceOrTaskData> itemDataList;
	private final View.OnClickListener onClickListener;
	
	/**
	 * ViewHolder for category items
	 */
	public static class ItemViewHolder extends RecyclerView.ViewHolder {
		public DeviceOrTaskButtonView deviceButtonView;
		
		public ItemViewHolder(DeviceOrTaskButtonView deviceButtonView) {
			super(deviceButtonView);
			this.deviceButtonView = deviceButtonView;
		}
	}
	
	public DeviceOrTaskButtonRecyclerViewAdapter(Context context, ArrayList<DeviceOrTaskData> itemDataList, View.OnClickListener onClickListener) {
		this.context = context;
		this.itemDataList = itemDataList;
		this.onClickListener = onClickListener;
	}
	
	@NonNull
	@Override
	public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		DeviceOrTaskButtonView deviceButtonView = new DeviceOrTaskButtonView(parent.getContext());
		deviceButtonView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		
		deviceButtonView.setOnClickListener(onClickListener);
		
		return new ItemViewHolder(deviceButtonView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
		DeviceOrTaskData itemData = itemDataList.get(position);
		
		holder.deviceButtonView.setName(itemData.getName());
		String extraText = itemData.getExtraText();
		if (extraText != null && !extraText.isEmpty()) {
			holder.deviceButtonView.setExtraText(itemData.getExtraText());
			holder.deviceButtonView.setExtraTextVisible(true);
		}
		else {
			holder.deviceButtonView.setExtraTextVisible(false);
		}
		holder.deviceButtonView.setIcon(itemData.getIcon());
		holder.deviceButtonView.setBackgroundColour(itemData.getBackgroundColour());
		holder.deviceButtonView.setCornerRadius(context.getResources().getDimension(R.dimen.device_or_task_button_corner_radius));
		holder.deviceButtonView.setElevation(context.getResources().getDimension(R.dimen.device_or_task_button_elevation));
	}
	
	@Override
	public int getItemCount() {
		return itemDataList.size();
	}
	
	public void addItem(int position, DeviceOrTaskData deviceOrTaskData) {
		itemDataList.add(position, deviceOrTaskData);
		notifyItemInserted(position);
	}
	
	public void removeItem(int position) {
		itemDataList.remove(position);
		notifyItemRemoved(position);
	}
}
