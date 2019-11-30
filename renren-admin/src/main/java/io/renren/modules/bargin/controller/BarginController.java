package io.renren.modules.bargin.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import io.renren.common.utils.QRCodeUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.activity.entity.ActivityEntity;
import io.renren.modules.activity.service.ActivityService;
import io.renren.modules.bargin.entity.BarginEntity;

import io.renren.modules.distribution.entity.Distribution;
import io.renren.modules.distribution.service.DistributionService;
import io.renren.modules.gather.entity.PrizeEntity;
import io.renren.modules.order.model.Order;
import io.renren.modules.order.model.OrderInfo;
import io.renren.modules.order.service.OrderService;
import io.renren.modules.sys.entity.ReturnCodeEnum;
import io.renren.modules.sys.entity.ReturnResult;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.renren.modules.bargin.service.BarginService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-25 15:35:24
 */
@RestController
@RequestMapping("/bargin")
public class BarginController {
    @Autowired
    private BarginService barginService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private ActivityService  activityService;

    @Value("${qr.bargin}")
    String qrBarginUrl;
    @Value("${qr.barginImgPath}")
    String qrBarginImgUrl;
    @Value("${qr.httpbarginurl}")
    String httpbarginurl;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = barginService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/queryAll")
    //@RequiresPermissions("sys:distribution:list")
    public ReturnResult queryAll(@RequestBody BarginEntity params) {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        List<Map<String, Object>> activityLst = barginService.queryList(params.getCreateUser());
        Map<String, Object> map = new HashedMap();
        map.put("data", activityLst);
        result.setResult(map);
        return result;
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ReturnResult info(@RequestBody BarginEntity bargin) {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> map = new HashedMap();
        ActivityEntity act = new ActivityEntity();
        BarginEntity barginEntity = barginService.queryById(bargin.getId());
        List<Map<String, Object>> orders = orderService.queryByActivtyId(bargin.getId());
        if("info".equals(bargin.getType())){
            act.setId(bargin.getId());
            act.setViewNum(1);
            activityService.updateActivityState(act);
        }
        map.put("bargin",barginEntity);
        map.put("order",orders);
        result.setResult(map);
        return result;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional
    public ReturnResult save(@RequestBody BarginEntity bargin) throws Exception {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> map = new HashedMap();
        ActivityEntity act = new ActivityEntity();
        if ("".equals(bargin.getId()) || bargin.getId() == null) {
            bargin.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            bargin.setQrImg(httpbarginurl + bargin.getId() + ".jpg");
            bargin.setPrizeLeft(bargin.getPrizeNum());
            bargin.setCreateTime(new Date());
            bargin.setTemplateId(bargin.getId());
            barginService.insertBarginEntity(bargin);
            distributionService.insertActivity(bargin);
            if((!"".equals(bargin.getTemplateId())) && bargin.getTemplateId() != null){
                act.setId(bargin.getTemplateId());
                act.setUseNum(1);
                activityService.updateActivityState(act);
            }
            String text = qrBarginUrl.replace("id=", "id=" + bargin.getId());
            QRCodeUtils.encode(text, null, qrBarginImgUrl, bargin.getId(), true);
        } else {
            bargin.setUpdateTime(new Date());
            barginService.updateBarginEntity(bargin);//全部更新
            distributionService.updateActivity(bargin);
        }
        map.put("bargin",bargin);
        result.setResult(map);
        return result;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    //@RequiresPermissions("sys:distribution:save")
    @Transactional
    @ResponseBody
    public ReturnResult copy(@RequestBody BarginEntity bargin) throws Exception {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> map = new HashedMap();
        ActivityEntity act = new ActivityEntity();
        BarginEntity barginEntity = barginService.queryById(bargin.getId());
        barginEntity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        barginEntity.setQrImg(httpbarginurl + barginEntity.getId() + ".jpg");
        barginEntity.setCreateTime(new Date());
        barginService.insertAllColumn(barginEntity);
        distributionService.insertActivity(barginEntity);
        if(bargin.getId().equals(bargin.getTemplateId())){
            act.setId(bargin.getTemplateId());
            act.setUseNum(1);
            activityService.updateActivityState(act);
        }
        String text = qrBarginUrl.replace("id=", "id=" + barginEntity.getId());
        QRCodeUtils.encode(text, null, qrBarginImgUrl, barginEntity.getId(), true);
        map.put("bargin",barginEntity);
        result.setResult(map);
        return result;
    }

    @RequestMapping(value = "/bargin", method = RequestMethod.POST)
    @Transactional
    //@RequiresPermissions("sys:distribution:delete")
    public ReturnResult bargin(@RequestBody Order order) {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> map = new HashedMap();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DecimalFormat dfn = new DecimalFormat("0.00");
        LocalDateTime toDate = LocalDateTime.now();
        BarginEntity ba = barginService.queryById(order.getActivityId());
        List<Map<String, Object>> mp = barginService.queryBarginLog(order.getOrderId());
        if (mp == null || (mp.size() < ba.getBarginNum())) {//是否超过砍价人数
            Map<String, Object> resMap = barginService.queryMaxTime(order);
            long minutes = 0;
            Long restrictTime = Long.parseLong(ba.getRestrictTime().toString());
            LocalDateTime create_time = resMap == null ? toDate : LocalDateTime.parse(resMap.get("create_time").toString().replace(".0", ""), df);
            minutes = ChronoUnit.MINUTES.between(create_time, toDate);
            if (restrictTime < (minutes/60) || mp.size() == 0||resMap==null) {//是否超过投票间隔时间
                Double reduct = Math.random() * (ba.getMaxReduction().subtract(ba.getMinReduction()).doubleValue()) + ba.getMinReduction().doubleValue();
                Double price_left = Double.valueOf(order.getTotal_price()) - reduct;
                if (Double.valueOf(order.getTotal_price()) == (ba.getFloorPrice().doubleValue())) {
                    result.setCode(ReturnCodeEnum.INVOKE_VENDOR_DF_ERROR.getCode());
                    result.setMsg("您已砍至低价！");
                    result.setResult(map);
                } else {
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setOrder_id(order.getOrderId());
                    if (price_left <= ba.getFloorPrice().doubleValue()) {
                        orderInfo.setTotal_price(dfn.format(ba.getFloorPrice().doubleValue()));
                    } else {
                        orderInfo.setTotal_price(dfn.format(price_left));
                    }
                    orderService.edit(orderInfo);//modify price
                    orderInfo.setUser_id(order.getUser_id());
                    order.setTotal_price(dfn.format(reduct));
                    barginService.insertBarginLog(order);
                    map.put("data", order);
                    result.setResult(map);
                }
            } else {
                result.setCode(ReturnCodeEnum.INVOKE_VENDOR_DF_ERROR.getCode());
                result.setMsg("请您休息" + (restrictTime - minutes/60) + "小时"+ (restrictTime*60-minutes)%60 +"分钟后再次砍价");
                result.setResult(map);
            }
        } else {
            result.setCode(ReturnCodeEnum.INVOKE_VENDOR_DF_ERROR.getCode());
            result.setMsg("超出砍价人数");
            result.setResult(map);
        }

        return result;
    }

    @RequestMapping(value = "/queryBarginLog", method = RequestMethod.POST)
    //@RequiresPermissions("sys:distribution:delete")
    public ReturnResult queryBarginLog(@RequestBody Order order) {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> map = new HashedMap();
        List<Map<String, Object>> mp = barginService.queryBarginLog(order.getOrderId());
        map.put("status", "success");
        map.put("msg", "send ok");
        map.put("data", mp);
        result.setResult(map);
        return result;
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BarginEntity bargin) {
        ValidatorUtils.validateEntity(bargin);
        barginService.updateAllColumnById(bargin);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value ="/delete", method = RequestMethod.POST)
    @Transactional
    public ReturnResult delete(@RequestBody String[] ids) {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        barginService.deleteBatchIds(Arrays.asList(ids));
        distributionService.deleteActivity(Arrays.asList(ids));
        return result;
    }

}
