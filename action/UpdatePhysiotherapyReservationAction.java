package action;

import com.alibaba.fastjson.JSONObject;
import model.APIsMsg;
import model.PhysiotherapyReservation;
import wtf.socket.WTFSocketMsg;

/**
 * 更新理疗预约
 */
public class UpdatePhysiotherapyReservationAction extends BaseAction {
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

        PhysiotherapyReservation param = getParam(body, PhysiotherapyReservation.class);

        // switch = [0|1]
        if (param.getModeSwitch() != 0 && param.getModeSwitch() != 1) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <switch> can only be => [0|1], actual => " + param.getModeSwitch());
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
        // 00:00:00 <= overTime < overTime <= 23:59:59
        if (param.getOverTime() < 0 || param.getOverTime() > 24 * 60 * 60 - 1 || param.getOverTime() <= param.getStartTime()) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <overTime> range is => [0, 24 * 60 * 60 - 1] and startTime < overTime, actual => " + param.getOverTime());
            return new WTFSocketMsg()
                    .setBody(response);
        }
        // workTime = [45min | 90min | 135min]
        if (param.getWorkTime() != 45 * 60 && param.getWorkTime() != 2 * 45 * 60 && param.getWorkTime() != 3 * 45 * 60) {
            response.setFlag(0);
            response.setErrCode(ErrCodeType.INVALID_PARAM);
            response.setCause("the parameter <workTime> can only be => [45 * 60|2 * 45 * 60|3 * 45 * 60], actual => " + param.getWorkTime());
            return new WTFSocketMsg()
                    .setBody(response);
        }

        switch (param.getSide()) {
            case 0:
                model.getRight().setIntensiveTemperatureSetting(param);
                response.setFlag(1);
                return new WTFSocketMsg().setBody(response);
            case 1:
                model.getLeft().setIntensiveTemperatureSetting(param);
                response.setFlag(1);
                return new WTFSocketMsg().setBody(response);
            case 2:
                param.setSide(0);
                model.getRight().setIntensiveTemperatureSetting(param);
                param.setSide(1);
                model.getLeft().setIntensiveTemperatureSetting(param);
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
