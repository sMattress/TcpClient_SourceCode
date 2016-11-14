package action;

import com.alibaba.fastjson.JSONObject;
import model.APIsMsg;
import wtf.socket.WTFSocketMsg;

/**
 * 无效操作操作
 */
public class NullAction extends BaseAction {

    @Override
    public WTFSocketMsg execute(JSONObject body) {

        APIsMsg response = new APIsMsg();
        response.setFlag(0);
        response.setErrCode(ErrCodeType.INVALID_CMD);

        return new WTFSocketMsg().setBody(response);
    }
}
