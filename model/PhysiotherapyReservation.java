package model;

/**
 * 加强理疗预约设置
 */
public class PhysiotherapyReservation {

    private Integer modeSwitch = 0;
    private Integer workTime = 2700;
    private Integer startTime = 75800;
    private Integer overTime = 82800;
    private Integer side = 0;

    public Integer getModeSwitch() {
        return modeSwitch;
    }

    public void setModeSwitch(Integer modeSwitch) {
        this.modeSwitch = modeSwitch;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }
}
