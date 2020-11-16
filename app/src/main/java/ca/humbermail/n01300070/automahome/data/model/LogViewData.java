package ca.humbermail.n01300070.automahome.data.model;

public class LogViewData {

    private String mainText;
    private String timeText;

    public LogViewData()
    {

    }

    public LogViewData(String mainText, String timeText)
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

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

}
