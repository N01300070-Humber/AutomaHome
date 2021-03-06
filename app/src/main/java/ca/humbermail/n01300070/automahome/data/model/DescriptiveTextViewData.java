package ca.humbermail.n01300070.automahome.data.model;

public class DescriptiveTextViewData {

    private String mainText;
    private String timeText;

    public DescriptiveTextViewData()
    {

    }

    public DescriptiveTextViewData(String mainText, String timeText)
    {
        this.mainText = mainText;
        this.timeText = timeText;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getDescriptionText() {
        return timeText;
    }

    public void setDescriptionText(String timeText) {
        this.timeText = timeText;
    }

}
