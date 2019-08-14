package utils;

import cn.hutool.core.net.NetUtil;

import javax.swing.*;

public class RabbitMQUtil {

    public static void checkServer() {
        if (NetUtil.isUsableLocalPort(15672)) {
            JOptionPane.showMessageDialog(null, " RabbitMQ server is not running .");
            System.exit(1);
        }
    }
}
