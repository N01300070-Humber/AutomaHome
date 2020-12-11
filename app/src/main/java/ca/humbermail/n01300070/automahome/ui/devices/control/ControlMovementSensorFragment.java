package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.DescriptiveTextViewAdapter;
import ca.humbermail.n01300070.automahome.data.model.DescriptiveTextData;

public class ControlMovementSensorFragment extends Fragment
{
    private RecyclerView detectionLog;
    private DescriptiveTextViewAdapter adapter;
    private Context context;
    private ArrayList<DescriptiveTextData> logViewDataList;

    public ControlMovementSensorFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_control_movement_sensor, container, false);
        context = getActivity().getApplicationContext();

        detectionLog = root.findViewById(R.id.recyclerView_control_movementSensor);

        logViewDataList = generateLogViewDataList();
        adapter = new DescriptiveTextViewAdapter(context, logViewDataList);

        detectionLog.setLayoutManager(new LinearLayoutManager(context));
        detectionLog.setAdapter(adapter);

        detectionLog.addItemDecoration(new ListLinePadding((int) context.getResources().getDimension(R.dimen.recycler_divider_space)));
        detectionLog.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        return root;
    }


    //TODO replace with method that will receive real data
    private ArrayList<DescriptiveTextData> generateLogViewDataList() {
        ArrayList<DescriptiveTextData> logDataList = new ArrayList<>();
        String[] mainTextArray = {"Room 1 ➜ Room 2", "Room 2 ➜ Room 1"};

        /*String[] timeTextArray = {"Today 2:40pm", "Today 8:04am", "Yesterday 2:25pm",
                                        "Yesterday 8:07am", "Wednesday 2:43pm",
                                    "Wednesday 7:58am", "Tuesday 2:36pm", "Tuesday 8:02am"};*/

        //Modified it to where it shows the real time
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String[] timeTextArray = {sdf.format(new Date())};

        for(int i = 0; i < timeTextArray.length; i++)
        {
            DescriptiveTextData logData = new DescriptiveTextData();
            logData.setMainText(mainTextArray[i % 2]);
            logData.setDescriptionText(timeTextArray[i]);
            logDataList.add(logData);
        }
        return logDataList;
    }

}

