package main;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.MockHardware;
import wtf.socket.*;

public class Main {

    public static void main(String[] args) {

        // 云服务器1
        String ip = "139.224.46.85";
//        String ip = "192.168.1.113";
        int port = 1234;

        String name = "MockHardware";

        // 启动参数
        if (args.length > 2) {
            ip = args[0];
            port = Integer.valueOf(args[1]);
            name = args[2];
        }

        // 建立模拟硬件模型
        final MockHardware mockHardware = new MockHardware();

        // 初始化SocketSessionFactory
        // 并设置处理逻辑
        WTFSocketSessionFactory.init(
                new WTFSocketConfig()
                .setIp(ip)
                .setPort(port)
                .setLocalName(name)
//                .setUseHeartbeat(true)
//                .setHeartbeatPeriod(3_000)
//                .setHeartbeatBreakTime(3)
        );

        // 添加新会话建立监听者
        WTFSocketSessionFactory.addEventListener(new WTFSocketEventListener() {
            @Override
            public void onNewSession(WTFSocketSession session, WTFSocketMsg msg) {
                WTFSocketLogUtils.info(String.format(
                        "create session from <%s> to <%s>\nmsg => %s",
                        session.getFrom(),
                        session.getTo(),
                        msg));
            }
        });

        // 添加连接断开监听者
        WTFSocketSessionFactory.addEventListener(new WTFSocketEventListener() {
            @Override
            public void onDisconnect() {
                WTFSocketSessionFactory.reInit();
            }
        });

        WTFSocketSessionFactory.addEventListener(new WTFSocketEventListener() {
            @Override
            public void onConnect() {
                register();
            }
        });

        // 设置WTFSocketSessionFactory默认响应方法
        WTFSocketSessionFactory.setDefaultResponse(

                new WTFSocketHandler() {
                    @Override
                    public boolean onReceive(WTFSocketSession session, WTFSocketMsg msg) {

                        JSONObject body = msg.getBody();

                        if (body.containsKey("cmd")) {
                            session.replyMsg(mockHardware.parseCmd(body), msg);
                        } else {
                            mockHardware.parseErrCode(body);
                        }

                        return true;
                    }
                });
        WTFSocketLogUtils.info(String.format("create MockHardware => {ip => %s, port => %s, name => %s}", ip, port, name));
        
    }

    private static void register() {

        JSONObject register = new JSONObject();
        register.put("cmd", 64);
        register.put("version", "1.0");
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();
        param.put("deviceType", "MockDevice");
        params.add(param);
        register.put("params", params);

        // 向服务器注册模拟硬件
        WTFSocketMsg registerMsg = new WTFSocketMsg().setBody(register);
        WTFSocketSessionFactory.SERVER.sendMsg(registerMsg, new WTFSocketHandler() {
            @Override
            public boolean onReceive(WTFSocketSession session, WTFSocketMsg msg) {

                JSONObject body = msg.getBody();

                if (body.containsKey("flag") && body.getIntValue("flag") != 1) {
                    WTFSocketLogUtils.err("register self to server failure!");
                } else {
                    WTFSocketLogUtils.info("register self to server success!");
                }

                return true;
            }

        }, 5_000);
    }

    private static void testCheckTimeout(final int id) {
        WTFSocketSessionFactory.getSession("MockPhone")
                .sendMsg(new WTFSocketMsg().setBody(new JSONObject()), new WTFSocketHandler() {

                    @Override
                    public boolean onReceive(WTFSocketSession session, WTFSocketMsg msg) {
                        WTFSocketLogUtils.err("test check timeout <" + id + "> failure:\nmsg => " + msg);
                        return true;
                    }

                    @Override
                    public boolean onException(WTFSocketSession session, WTFSocketMsg msg, WTFSocketException e) {
                        WTFSocketLogUtils.info("test check timeout <" + id + "> success:\n" + e.getMessage());
                        return true;
                    }
                }, 10_000);
    }
}
