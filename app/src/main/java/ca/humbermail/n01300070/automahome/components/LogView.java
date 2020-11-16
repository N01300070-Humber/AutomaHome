package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

import org.w3c.dom.Text;

import ca.humbermail.n01300070.automahome.R;

public class LogView extends LinearLayout {
    private TextView mainTextView;
    private TextView timeTextView;

    public LogView(Context context) {
        super(context);
        inflateViews(context);
    }

    public LogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateViews(context);
    }

    public LogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateViews(context);
    }

    private void inflateViews(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_log, this);

        mainTextView = findViewById(R.id.textView_log_main);
        timeTextView = findViewById(R.id.textView_log_time);
    }

    public CharSequence getMainText()
    {
        return mainTextView.getText();
    }

    public CharSequence getTimeText()
    {
        return timeTextView.getText();
    }

    public void setMainText(CharSequence text)
    {
        mainTextView.setText(text);
    }

    public void setTimeText(CharSequence text)
    {
        timeTextView.setText(text);
    }
}
