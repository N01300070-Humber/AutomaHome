package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;

public class ConditionOrOperationViewAdapter extends RecyclerView.Adapter<ConditionOrOperationViewAdapter.ConditionOrOperationViewHolder> {

    private Context context;
    private ArrayList<ConditionOrOperationViewData> dataList;
    private View.OnClickListener onClickListener;

    public class ConditionOrOperationViewHolder extends RecyclerView.ViewHolder
    {
        public ConditionOrOperationView conditionOrOperationView;

        public ConditionOrOperationViewHolder(ConditionOrOperationView conditionOrOperationView) {
            super(conditionOrOperationView);
            this.conditionOrOperationView = conditionOrOperationView;
        }
    }
    
    public ConditionOrOperationViewAdapter(Context context, View.OnClickListener onClickListener)
    {
        this(context, new ArrayList<ConditionOrOperationViewData>(), onClickListener);
    }
    
    public ConditionOrOperationViewAdapter(Context context, ArrayList<ConditionOrOperationViewData> dataList, View.OnClickListener onClickListener)
    {
        this.context = context;
        this.dataList = dataList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ConditionOrOperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConditionOrOperationView view = new ConditionOrOperationView(parent.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT) );
        
        view.setOnClickListener(onClickListener);
        
        return new ConditionOrOperationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConditionOrOperationViewHolder holder, int position) {
        ConditionOrOperationViewData data = dataList.get(position);

        holder.conditionOrOperationView.setType(data.getType());
        holder.conditionOrOperationView.setConditionOrOperationType(data.getConditionOrOperationType());
        holder.conditionOrOperationView.setMainText(data.getMainText());
        holder.conditionOrOperationView.setTypeText(data.getTypeText());
        holder.conditionOrOperationView.setDragHandleVisible(data.isDragHandleVisible());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    
    public void setDataList(ArrayList<ConditionOrOperationViewData> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
