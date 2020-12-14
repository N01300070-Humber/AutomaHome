package ca.humbermail.n01300070.automahome.ui.tasks;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Random;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.NonScrollingGridLayoutManager;
import ca.humbermail.n01300070.automahome.components.RecyclerViewItemDivider;
import ca.humbermail.n01300070.automahome.data.model.CategoryData;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;

public class TasksViewModel extends ViewModel {
	
	public TasksViewModel() {
	
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	public ArrayList<CategoryData> generatePlaceholderCategorizedTaskDataList(Context context, View.OnClickListener onClickListener) {
		Random random = new Random();
		String[] headers = {"Manual", "Automatic"};
		ArrayList<CategoryData> categoryDataList = new ArrayList<>(headers.length);
		
		for (int i = 0; i < headers.length; i++) {
			CategoryData categoryData = new CategoryData();
			
			categoryData.setHeaderText(headers[i]);
			categoryData.setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6);
			categoryData.setHeaderSidePadding( (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin) );
			
			int numTasks = random.nextInt(12)+1;
			categoryData.setLayoutManager(new NonScrollingGridLayoutManager(context, 2));
			categoryData.setViewAdapter(new DeviceOrTaskButtonRecyclerViewAdapter( context,
					generatePlaceholderTaskDataList(context, numTasks),
					onClickListener ));
			categoryData.setItemDecoration(new RecyclerViewItemDivider(
					(int) context.getResources().getDimension(R.dimen.recycler_divider_space),
					(int) context.getResources().getDimension(R.dimen.category_divider_space),
					(int) context.getResources().getDimension(R.dimen.activity_horizontal_margin) ));
			
			categoryDataList.add(categoryData);
		}
		
		return categoryDataList;
	}
	
	// TODO: Remove placeholder data generator function
	private ArrayList<DeviceOrTaskButtonData> generatePlaceholderTaskDataList(Context context, int numTasks) {
		ArrayList<DeviceOrTaskButtonData> taskDataList = new ArrayList<>(numTasks);
		
		for (int i = 0; i < numTasks; i++) {
			DeviceOrTaskButtonData taskData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_TASK,
					"Task Name",
					"Note",
					ContextCompat.getDrawable(context, R.drawable.ic_task),
					context.getString(R.string.content_description_type_task),
					context.getColor(R.color.primary_200)
			);
			
			taskDataList.add(taskData);
		}
		
		return taskDataList;
	}
}