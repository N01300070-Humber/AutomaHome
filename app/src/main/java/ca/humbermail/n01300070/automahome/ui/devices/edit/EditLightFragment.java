package ca.humbermail.n01300070.automahome.ui.devices.edit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;

public class EditLightFragment extends Fragment {

    private CheckBox autoBrightness;
    private Context context;

    public EditLightFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_device_light, container, false);
        context = getActivity().getApplicationContext();

        autoBrightness = root.findViewById(R.id.checkBox_editLights_autoBrightness);

        return root;
    }
}