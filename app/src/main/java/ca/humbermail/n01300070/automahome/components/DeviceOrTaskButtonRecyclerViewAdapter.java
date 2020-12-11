package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;

public class DeviceOrTaskButtonRecyclerViewAdapter extends RecyclerView.Adapter<DeviceOrTaskButtonRecyclerViewAdapter.ItemViewHolder> {
	
	private final Context context;
	private final ArrayList<DeviceOrTaskButtonData> itemDataList;
	private final View.OnClickListener onClickListener;
	
	/**
	 * ViewHolder for category items
	 */
	public static class ItemViewHolder extends RecyclerView.ViewHolder {
		public DeviceOrTaskButtonView deviceButtonView;
		
		public ItemViewHolder(DeviceOrTaskButtonView deviceOrTaskButtonView) {
			super(deviceOrTaskButtonView);
			this.deviceButtonView = deviceOrTaskButtonView;
		}
	}
	
	public DeviceOrTaskButtonRecyclerViewAdapter(Context context, ArrayList<DeviceOrTaskButtonData> itemDataList, View.OnClickListener onClickListener) {
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
		DeviceOrTaskButtonData data = itemDataList.get(position);
		
		holder.deviceButtonView.setType(data.getType());
		holder.deviceButtonView.setDeviceType(data.getDeviceType());
		holder.deviceButtonView.setName(data.getName());
		String extraText = data.getExtraText();
		if (extraText != null && !extraText.isEmpty()) {
			holder.deviceButtonView.setExtraText(extraText);
			holder.deviceButtonView.setExtraTextVisible(data.isExtraTextVisible());
		}
		else {
			holder.deviceButtonView.setExtraTextVisible(false);
		}
		holder.deviceButtonView.setIcon(data.getIcon());
		holder.deviceButtonView.setIconContentDesc(data.getContentDescription());
		holder.deviceButtonView.setBackgroundColour(data.getBackgroundColour());
		holder.deviceButtonView.setCornerRadius(context.getResources().getDimension(R.dimen.device_or_task_button_corner_radius));
		holder.deviceButtonView.setElevation(context.getResources().getDimension(R.dimen.device_or_task_button_elevation));
	}
	
	@Override
	public int getItemCount() {
		return itemDataList.size();
	}
	
	public void addItem(int position, DeviceOrTaskButtonData deviceOrTaskData) {
		itemDataList.add(position, deviceOrTaskData);
		notifyItemInserted(position);
	}
	
	public void removeItem(int position) {
		itemDataList.remove(position);
		notifyItemRemoved(position);
	}
	
	public DeviceOrTaskButtonData getItemData(int position) {
		return itemDataList.get(position);
	}
	
	public void setItemData(int position, DeviceOrTaskButtonData data) {
		itemDataList.set(position, data);
		notifyItemChanged(position);
	}
	
	public void setAllExtraTextVisible(boolean visible) {
		for (DeviceOrTaskButtonData itemData : itemDataList) {
			itemData.setExtraTextVisible(visible);
		}
		notifyDataSetChanged();
	}
}
