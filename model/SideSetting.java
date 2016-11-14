package model;

/**
 * 左右侧设置
 */
public class SideSetting {

    private CurrentState currentState;
    private HeatReservation constantTemperatureSetting;
    private PhysiotherapyReservation intensiveTemperatureSetting;

    public SideSetting (int side) {
        constantTemperatureSetting = new HeatReservation();
        constantTemperatureSetting.setSide(side);

        intensiveTemperatureSetting = new PhysiotherapyReservation();
        intensiveTemperatureSetting.setSide(side);

        currentState = new CurrentState();
        currentState.setSide(side);
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

    public HeatReservation getConstantTemperatureSetting() {
        return constantTemperatureSetting;
    }

    public void setConstantTemperatureSetting(HeatReservation constantTemperatureSetting) {
        this.constantTemperatureSetting = constantTemperatureSetting;
    }

    public PhysiotherapyReservation getIntensiveTemperatureSetting() {
        return intensiveTemperatureSetting;
    }

    public void setIntensiveTemperatureSetting(PhysiotherapyReservation intensiveTemperatureSetting) {
        this.intensiveTemperatureSetting = intensiveTemperatureSetting;
    }

}
