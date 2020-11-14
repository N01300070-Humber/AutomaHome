package ca.humbermail.n01300070.automahome.ui.favorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.CategorizedDeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewCategoryPadding;

public class FavoritesFragment extends Fragment {
	
	private Context context;
	private RecyclerView recyclerView;
	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;
	
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
		final View root = inflater.inflate(R.layout.fragment_favorites, container, false);
		context = getActivity().getApplicationContext();
		
		recyclerView = root.findViewById(R.id.recyclerView_favorites);
		
		categoryOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO: Replace placeholder on click function with navigation to the correct Control Device or Edit Task Activity
				DeviceOrTaskButtonView deviceOrTaskButtonView = (DeviceOrTaskButtonView) view;
				deviceOrTaskButtonView.setExtraText("Clicked");
			}
		};
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter(context,
				FavoritesViewModel.generatePlaceholderCategoryDataList(context,
						categoryOnClickListener) );
		
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding(
				(int) getResources().getDimension(R.dimen.activity_vertical_margin) ));
		
		return root;
	}
	
}