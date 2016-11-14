package model;

/**
 * 恒温理疗预约设置
 */
public class HeatReservation {

    private Integer modeSwitch = 0;
    private Integer targetTemperature = 35;
    private Integer protectTime = 2100;
    private Integer startTime = 28800;
    private Integer autoTemperatureControl = 0;
    private Integer side = 0;

    public Integer getModeSwitch() {
        return modeSwitch;
    }

    public void setModeSwitch(Integer modeSwitch) {
        this.modeSwitch = modeSwitch;
    }

    public Integer getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Integer targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public Integer getProtectTime() {
        return protectTime;
    }

    public void setProtectTime(Integer protectTime) {
        this.protectTime = protectTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getAutoTemperatureControl() {
        return autoTemperatureControl;
    }

    public void setAutoTemperatureControl(Integer autoTemperatureControl) {
        this.autoTemperatureControl = autoTemperatureControl;
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }
}
