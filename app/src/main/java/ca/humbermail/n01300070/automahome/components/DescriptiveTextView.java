package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ca.humbermail.n01300070.automahome.R;

public class DescriptiveTextView extends LinearLayout {
    private TextView mainTextView;
    private TextView descriptionTextView;

    public DescriptiveTextView(Context context) {
        super(context);
        inflateViews(context);
    }

    public DescriptiveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateViews(context);
    }

    public DescriptiveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateViews(context);
    }

    private void inflateViews(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_log, this);

        mainTextView = findViewById(R.id.textView_log_main);
        descriptionTextView = findViewById(R.id.textView_log_time);
    }

    public CharSequence getMainText()
    {
        return mainTextView.getText();
    }
    
    public void setMainText(CharSequence text)
    {
        mainTextView.setText(text);
    }
    
    public CharSequence getDescriptionText()
    {
        return descriptionTextView.getText();
    }
    
    public void setDescriptionText(CharSequence text)
    {
        descriptionTextView.setText(text);
    }
}
