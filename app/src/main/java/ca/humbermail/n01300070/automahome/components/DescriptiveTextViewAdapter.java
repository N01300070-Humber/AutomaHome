package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.data.model.DescriptiveTextData;

public class DescriptiveTextViewAdapter extends RecyclerView.Adapter<DescriptiveTextViewAdapter.DescriptiveTextViewHolder> {

    private Context context;
    private ArrayList<DescriptiveTextData> dataList;


    public class DescriptiveTextViewHolder extends RecyclerView.ViewHolder
    {
        public DescriptiveTextView descriptiveTextView;

        public DescriptiveTextViewHolder(DescriptiveTextView descriptiveTextView) {
            super(descriptiveTextView);
            this.descriptiveTextView = descriptiveTextView;
        }
    }

    public DescriptiveTextViewAdapter(Context context, ArrayList<DescriptiveTextData> dataList)
    {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DescriptiveTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DescriptiveTextView logView = new DescriptiveTextView(parent.getContext());
        logView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        logView.setOrientation(LinearLayout.VERTICAL);
        return new DescriptiveTextViewHolder(logView);
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptiveTextViewHolder holder, int position) {
        DescriptiveTextData data = dataList.get(position);

        holder.descriptiveTextView.setMainText(data.getMainText());
        holder.descriptiveTextView.setDescriptionText(data.getDescriptionText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
