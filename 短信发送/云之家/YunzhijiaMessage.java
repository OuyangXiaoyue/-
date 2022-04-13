package wens.breeding.standardizedwork.task.warning.set;

import kd.bd.gmc.common.util.UserUtil;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.bos.servicehelper.workflow.MessageCenterServiceHelper;
import kd.bos.workflow.engine.msg.info.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * 云之家消息发送
 **/
public class YunzhijiaMessage {
    private static final Logger logger = LoggerFactory.getLogger(YunzhijiaMessage.class);
    public static void sendThMsg(String title, String message, List<Long> receiverIds) throws UnsupportedEncodingException {
        if (receiverIds.isEmpty()){
            logger.error("XpOrderThNotifyUtil_sendThMsg_接收订单用户集合为空");
            return;
        }
        // 发送金蝶消息给业务员通知下单成功

        logger.error("XpOrderThNotifyUtil_sendThMsg_准备开始给业务员发送订单消息");
        MessageInfo messageInfo = new MessageInfo();

        messageInfo.setTitle(title);
        String url = "";
//        url = "http://mbos.kingdee.com/mbos/page/loadPage?appid=10036&eid=12001905&path=wsmall_b2b&name=SPForm.editui&billID="+ URLEncoder.encode(easOrderId, "UTF-8");
        logger.error("XpOrderThNotifyUtil_sendThMsg_生成的超链接:" + url);
        logger.error("XpOrderThNotifyUtil_sendThMsg_生成的消息内容:" + message);

        //云之家消息日志同步
        DynamicObject log = BusinessDataServiceHelper.newDynamicObject("mdr_message_log");
        log.set("user", UserUtil.getUserID());
        String salerIdsTemp = null;
        StringBuffer buf = new StringBuffer();
        for (Object salerOrderId : receiverIds) {
            salerIdsTemp = salerOrderId.toString();
            buf.append("[" + salerIdsTemp + "]");
        }
        String salerId = buf.toString();
        if (salerId.length() > 99) {
            salerId = salerId.substring(0, 99);
        }
        log.set("phonelist", salerId);
        log.set("message", message);
        log.set("createtime", new Date());
        log.set("status", "A");
        try {
            if (receiverIds.size() > 0) {
                //给渠道业务员发送云之家消息
                logger.error("XpOrderThNotifyUtil_sendThMsg_业务员id列表长度:" + receiverIds.size());
                messageInfo.setUserIds(receiverIds);
                messageInfo.setContent(message);
                messageInfo.setMobContentUrl(url);
                messageInfo.setType(MessageInfo.TYPE_MESSAGE);
                messageInfo.setEntityNumber("wens_fresh_order");
                messageInfo.setPubaccNumber("systempubacc");// 金蝶云消息助手
                logger.info("message order: content:" + messageInfo.getContent() + " pubaccNumber:" + messageInfo.getPubaccNumber() + "");
                MessageCenterServiceHelper.sendMessage(messageInfo);
                log.set("status", "B");
                log.set("remark", "message order: content:" + messageInfo.getContent() + " pubaccNumber:" + messageInfo.getPubaccNumber() + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.set("message", message + "发送异常:" + e.getMessage());
            logger.error("XpOrderThNotifyUtil_sendThMsg_发送错误:" + message + "发送异常:" + e.getMessage());
        } finally {
            SaveServiceHelper.save(new DynamicObject[]{log});
        }
    }
}
