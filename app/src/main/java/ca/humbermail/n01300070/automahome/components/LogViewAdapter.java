package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.data.LogViewData;

public class LogViewAdapter extends RecyclerView.Adapter<LogViewAdapter.LogViewHolder> {

    private Context context;
    private ArrayList<LogViewData> dataList;


    public class LogViewHolder extends RecyclerView.ViewHolder
    {
        public LogView logView;

        public LogViewHolder(LogView logView) {
            super(logView);
            this.logView = logView;
        }
    }

    public LogViewAdapter(Context context, ArrayList<LogViewData> dataList)
    {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogView logView = new LogView(parent.getContext());
        logView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        logView.setOrientation(LinearLayout.VERTICAL);
        return new LogViewHolder(logView);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        LogViewData data = dataList.get(position);

        holder.logView.setMainText(data.getMainText());
        holder.logView.setTimeText(data.getTimeText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
