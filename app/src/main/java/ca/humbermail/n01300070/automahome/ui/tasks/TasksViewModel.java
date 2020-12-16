package ca.humbermail.n01300070.automahome.ui.tasks;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.NonScrollingGridLayoutManager;
import ca.humbermail.n01300070.automahome.components.RecyclerViewItemDivider;
import ca.humbermail.n01300070.automahome.data.model.CategoryData;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.data.model.Task;

public class TasksViewModel extends ViewModel {
	private static final String[] HEADERS = {"Manual", "Automatic"};
	
	private final MutableLiveData<String> mText;
	
	public TasksViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is the task fragment");
	}
	
	public LiveData<String> getText() {
		return mText;
	}
	
	public ArrayList<CategoryData> getCategorizedTaskDataList(Context context, List<Task> tasks, View.OnClickListener onClickListener) {
		ArrayList<ArrayList<DeviceOrTaskButtonData>> categoryItemDataList = getCategoryItemDataList(context, tasks);
		ArrayList<CategoryData> categoryDataList = new ArrayList<>(HEADERS.length);
		
		for (int i = 0; i < HEADERS.length; i++) {
			CategoryData categoryData = new CategoryData();
			
			categoryData.setHeaderText(HEADERS[i]);
			categoryData.setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6);
			categoryData.setHeaderSidePadding((int) context.getResources().getDimension(R.dimen.activity_horizontal_margin));
			
			categoryData.setLayoutManager(new NonScrollingGridLayoutManager(context, 2));
			categoryData.setItemDecoration(new RecyclerViewItemDivider(
					(int) context.getResources().getDimension(R.dimen.recycler_divider_space),
					(int) context.getResources().getDimension(R.dimen.category_divider_space),
					(int) context.getResources().getDimension(R.dimen.activity_horizontal_margin)));
			
			DeviceOrTaskButtonRecyclerViewAdapter categoryAdapter =
					new DeviceOrTaskButtonRecyclerViewAdapter(context, onClickListener);
			categoryAdapter.setItemDataList(categoryItemDataList.get(i));
			categoryData.setViewAdapter(categoryAdapter);
			
			categoryDataList.add(categoryData);
		}
		
		return categoryDataList;
	}
	
	private ArrayList<ArrayList<DeviceOrTaskButtonData>> getCategoryItemDataList(Context context, List<Task> tasks) {
		ArrayList<ArrayList<DeviceOrTaskButtonData>> categoryItemDataList = new ArrayList<>(2);
		ArrayList<DeviceOrTaskButtonData> manualTaskDataList = new ArrayList<>();
		ArrayList<DeviceOrTaskButtonData> automaticTaskDataList = new ArrayList<>();
		
		for (Task task : tasks) {
			DeviceOrTaskButtonData taskData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_TASK,
					task.getId(),
					task.getName(),
					task.getNote(),
					task.getCategory(),
					ContextCompat.getDrawable(context, R.drawable.ic_task),
					context.getString(R.string.content_description_type_task),
					context.getColor(R.color.primary_200)
			);
			
			automaticTaskDataList.add(taskData); // TODO: Add check to determine if task is automatic or manual
		}
		
		categoryItemDataList.add(manualTaskDataList);
		categoryItemDataList.add(automaticTaskDataList);
		return categoryItemDataList;
	}
}