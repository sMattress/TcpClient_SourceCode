package action;

import com.alibaba.fastjson.JSONObject;
import model.APIsMsg;
import model.HeatReservation;
import wtf.socket.WTFSocketMsg;

/**
 * 更新加热预约
 */
public class UpdateHeatReservationAction extends BaseAction {

    @Override
    public WTFSocketMsg execute(JSONObject body) {

        APIsMsg response = new APIsMsg();

        ErrCode errCode = verifyParams(body, "side");
        if (errCode.getCode() != ErrCodeType.NONE) {
            response.setFlag(0);
            response.setErrCode(errCode.getCode());
            response.setCause(errCode.getCause());
            return new WTFSocketMsg().setBody(response);
        }

        HeatReservation param = getParam(body, HeatReservation.class);

        // switch = [0 | 1]
        if (param.getModeSwitch() != 0 && param.getModeSwitch() != 1) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <switch> can only be => [0|1], actual => " + param.getModeSwitch());
            return new WTFSocketMsg()
                    .setBody(response);
        }
        // 25 <= targetTemperature <= 45
        if (param.getTargetTemperature() > 45 || param.getTargetTemperature() < 25) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <targetTemperature> range is => [25, 45], actual => " + param.getTargetTemperature());
            return new WTFSocketMsg()
                    .setBody(response);
        }
        // autoTemperatureControl = [0 | 1]
        if (param.getAutoTemperatureControl() != 0 && param.getAutoTemperatureControl() != 1) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <autoTemperatureControl> can only be => [0|1], actual => " + param.getAutoTemperatureControl());
            return new WTFSocketMsg()
                    .setBody(response);
        }
        // 30min <= protectTime <= 3h
        if (param.getProtectTime() < 30 * 60 || param.getProtectTime() > 3 * 60 * 60) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <protectTime> range is => [30 *60, 3 * 60 * 60], actual => " + param.getProtectTime());
            return new WTFSocketMsg()
                    .setBody(response);
        }
        // 00:00:00 <= startTime <= 23:59:59
        if (param.getStartTime() < 0 || param.getStartTime() > 24 * 60 * 60 - 1) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <startTime> range is => [0, 24 * 60 * 60 - 1], actual => " + param.getStartTime());
            return new WTFSocketMsg()
                    .setBody(response);
        }

        switch (param.getSide()) {
            case 0:
                model.getRight().setConstantTemperatureSetting(param);
                response.setFlag(1);
                return new WTFSocketMsg().setBody(response);
            case 1:
                model.getLeft().setConstantTemperatureSetting(param);
                response.setFlag(1);
                return new WTFSocketMsg().setBody(response);
            case 2:
                param.setSide(0);
                model.getRight().setConstantTemperatureSetting(param);
                param.setSide(1);
                model.getLeft().setConstantTemperatureSetting(param);
                response.setFlag(1);
                return new WTFSocketMsg().setBody(response);
            default:
                response.setFlag(0);
                response.setErrCode(ErrCodeType.INVALID_PARAM);
                response.setCause("the parameter <side> can only be => [0|1|2], actual => " + getSide(body));
                return new WTFSocketMsg().setBody(response);
        }
    }
}
