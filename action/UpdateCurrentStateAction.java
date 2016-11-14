package action;

import com.alibaba.fastjson.JSONObject;
import model.APIsMsg;
import model.CurrentState;
import wtf.socket.WTFSocketMsg;

/**
 * 更新当前状态
 */
public class UpdateCurrentStateAction extends BaseAction {
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

        int i = 0;
        CurrentState param = getParam(body, CurrentState.class, i++);

        while (param != null) {

            // 25 <= targetTemperature <= 45
            if (param.getTargetTemperature() > 45 || param.getTargetTemperature() < 25) {
                response.setFlag(0);
                response.setErrCode(ErrCodeType.INVALID_PARAM);
                response.setCause("parameter <targetTemperature> must range => [25, 45], actual => " + param.getTargetTemperature());
                return new WTFSocketMsg()
                        .setBody(response);
            }

            switch (param.getSide()) {
                case 0:
                    model.getRight().setCurrentState(param);
                    break;
                case 1:
                    model.getLeft().setCurrentState(param);
                    break;
                default:
                    response.setFlag(0);
                    response.setErrCode(ErrCodeType.INVALID_PARAM);
                    response.setCause("the parameter <side> can only be => [0|1], actual => " + getSide(body));
                    return new WTFSocketMsg().setBody(response);
            }
            param = getParam(body, CurrentState.class, i++);
        }

        response.setFlag(1);
        return new WTFSocketMsg().setBody(response);
    }
}
