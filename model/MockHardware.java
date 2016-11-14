package model;

import action.*;
import com.alibaba.fastjson.JSONObject;
import wtf.socket.WTFSocketLogUtils;
import wtf.socket.WTFSocketMsg;

/**
 * 模拟硬件对象
 */
public class MockHardware {

    // 左侧参数
    private SideSetting right = new SideSetting(0);
    // 右侧参数
    private SideSetting left = new SideSetting(1);

    public MockHardware() {
        right.getCurrentState().setCurrentTemperature(30);
        left.getCurrentState().setCurrentTemperature(25);
    }

    /**
     * 获取左侧参数
     *
     * @return 左侧参数 SideSetting
     */
    public SideSetting getLeft() {
        return left;
    }

    /**
     * 获取右侧参数
     *
     * @return 右侧参数 SideSetting
     */
    public SideSetting getRight() {
        return right;
    }

    /**
     * 解析命令
     *
     * @param body 消息对象
     * @return 反馈消息对象
     */
    public WTFSocketMsg parseCmd(JSONObject body) {

        return actionsMap(body.getIntValue("cmd")).setModel(this).execute(body);

    }

    /**
     * 解析错误代码
     *
     * @param body 消息对象
     * @return 反馈消息对象
     */
    public WTFSocketMsg parseErrCode(JSONObject body) {

        if (body.containsKey("errCode")) {

            WTFSocketLogUtils.err(String.format("parse errCode => %d", body.getIntValue("errCode")));
        }

        return null;
    }

    /**
     * 查找命令执行对象
     *
     * @param cmd 消息对象
     * @return 反馈消息对象
     */
    private BaseAction actionsMap(int cmd) {
        switch (cmd) {
            case 16:
                return new UpdateCurrentStateAction();
            case 17:
                return new UpdateHeatReservationAction();
            case 18:
                return new UpdatePhysiotherapyReservationAction();
            case 32:
                return new QueryCurrentStateAction();
            case 33:
                return new QueryHeatReservationAction();
            case 34:
                return new QueryPhysiotherapyReservationAction();
            default:
                return new NullAction();
        }
    }
}
