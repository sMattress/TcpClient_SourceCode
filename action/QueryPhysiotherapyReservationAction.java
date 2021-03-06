package action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.APIsMsg;
import wtf.socket.WTFSocketMsg;

/**
 * 查询理疗预约
 */
public class QueryPhysiotherapyReservationAction extends BaseAction {

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

        response.setFlag(1);

        switch (getSide(body)) {
            case 0:
                response.addParam(model.getRight().getIntensiveTemperatureSetting());
                return new WTFSocketMsg().setBody(response);
            case 1:
                response.addParam(model.getLeft().getIntensiveTemperatureSetting());
                return new WTFSocketMsg().setBody(response);
            case 2:
                response.addParam(model.getLeft().getIntensiveTemperatureSetting());
                response.addParam(model.getRight().getIntensiveTemperatureSetting());
                return new WTFSocketMsg().setBody(response);
            default:
                response.setFlag(0);
                response.setErrCode(ErrCodeType.INVALID_PARAM);
                response.setCause("the parameter <side> can only be => [0|1|2], actual => " + getSide(body));
                return new WTFSocketMsg().setBody(response);
        }
    }
}
