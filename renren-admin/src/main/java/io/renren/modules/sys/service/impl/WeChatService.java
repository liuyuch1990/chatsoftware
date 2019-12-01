package io.renren.modules.sys.service.impl;

import io.renren.common.config.Constants;
import io.renren.common.utils.ReadExcelUtil;
import io.renren.common.utils.WxUtil;
import io.renren.modules.distribution.dao.DistributionDao;
import io.renren.modules.sys.entity.TextEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class WeChatService {
    @Autowired
    private DistributionDao distributionDao;

    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = ReadExcelUtil.parseXml(request);
            // 消息类型
            String msgType = (String) requestMap.get(Constants.MsgType);
            String mes = null;
            // 文本消息
            if (msgType.equals(Constants.REQ_MESSAGE_TYPE_TEXT)) {
                List<TextEntity> textEntityList = distributionDao.queryTextByName((String) requestMap.get(Constants.Content));
                for(TextEntity text:textEntityList){
                    respXml = WxUtil.sendTextMsg(requestMap, text.getText());
                }
            }
            // 图片消息
            else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                respXml = WxUtil.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
                respXml = WxUtil.sendTextMsg(requestMap, respContent);
            }
            // 视频消息
            else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                respXml = WxUtil.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                respXml = WxUtil.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                respXml = WxUtil.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = (String) requestMap.get(Constants.Event);
                // 关注
                if (eventType.equals(Constants.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                    respXml = WxUtil.sendTextMsg(requestMap, respContent);
                }
                // 取消关注
                else if (eventType.equals(Constants.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(Constants.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(Constants.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(Constants.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            mes = mes == null ? "不知道你在干嘛" : mes;
            if(respXml == null)
                respXml = WxUtil.sendTextMsg(requestMap, mes);
            System.out.println(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}
