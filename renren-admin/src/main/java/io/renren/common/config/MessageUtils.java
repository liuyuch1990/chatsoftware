package io.renren.common.config;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsResultBase;
import com.github.qcloudsms.SmsSingleSender;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

public class MessageUtils {
    /**
     * 按模板发送短信 支持单发和群发
     * @param isSingle 是否单发 true: 单发，false: 群发
     * @param phoneNumbers 需要发送的短信内容及收信人手机号
     * @param params 短信配置
     */
    public static void sendMessage(Boolean isSingle, String[] params,String[] phoneNumbers  ) throws com.github.qcloudsms.httpclient.HTTPException {
        String regex = ";";
        SmsResultBase result;

        try {
            // 是否单发
            if (isSingle) {
                SmsSingleSender ssender = new SmsSingleSender(Constants.SMSAPPID, Constants.SMSKEY);
                result = ssender.sendWithParam("86", phoneNumbers[0], Constants.SMSTEMPLATEID, params, Constants.SMSSIGN, "", "");
            } else {
                SmsMultiSender msender = new SmsMultiSender(Constants.SMSAPPID, Constants.SMSKEY);
                result =  msender.sendWithParam("86", phoneNumbers, Constants.SMSTEMPLATEID, params, Constants.SMSSIGN, "", "");
            }
            System.out.println(result);
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
