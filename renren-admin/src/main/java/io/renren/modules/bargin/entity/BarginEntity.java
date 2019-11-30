package io.renren.modules.bargin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-25 15:35:24
 */
@TableName("tb_bargin")
public class BarginEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;

	private String address;

	private String type;

	private String templateId;
	/**
	 * 砍价次数
	 */
	private Integer barginNum;
	/**
	 * 活动名称 顶部
	 */
	private String activityName;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 机构图片
	 */
	private String thumbnail;
	/**
	 * 原价
	 */
	private BigDecimal originalPrice;
	/**
	 * 底价
	 */
	private BigDecimal floorPrice;
	/**
	 * 最少砍价
	 */
	private BigDecimal minReduction;
	/**
	 * 最多砍价
	 */
	private BigDecimal maxReduction;
	/**
	 * 砍价间隔时间
	 */
	private Integer restrictTime;
	/**
	 * 奖品描述
	 */
	private String prizeDescription;
	/**
	 * 活动规则
	 */
	private String activityRule;
	/**
	 * 领奖信息
	 */
	private String prizeInfo;
	/**
	 * 机构介绍
	 */
	private String companyDescription;
	/**
	 * 店内优惠
	 */
	private String discount;
	/**
	 * 二维码
	 */
	private String qrImg;
	/**
	 * 
	 */
	private Date createTime;

	private String  createUser;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 顶部图片
	 */
	private String headImage;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 机构名称
	 */
	private String companyName;
	/**
	 * 主办方电话
	 */
	private String phone;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 底部图片
	 */
	private String footImage;
	/**
	 * 背景图片
	 */
	private String bgImage;
	/**
	 * 奖品图片
	 */
	private String gift;

	/**
	 * 奖品图片
	 */
	private String giftName;

	/**
	 * 数据采集问题1
	 */
	private String question1;

	/**
	 * 数据采集问题2
	 */
	private String question2;

	/**
	 * 数据采集问题5
	 */
	private String question5;

	/**
	 * 数据采集问题3
	 */
	private String question3;

	/**
	 * 数据采集问题4
	 */
	private String question4;

	/**
	 *
	 */
	private Integer prizeLeft;
	/**
	 * 奖品数量
	 */
	private Integer prizeNum;

	private int likeNum;//点赞
	private int shareNum;//分享
	private int useNum;//使用
	private int viewNum;//查看

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	/**
	 * 获取：
	 */
	public String getActivityName() {
		return activityName;
	}
	/**
	 * 设置：
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置：
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置：
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	/**
	 * 获取：
	 */
	public String getThumbnail() {
		return thumbnail;
	}
	/**
	 * 设置：
	 */
	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	/**
	 * 设置：
	 */
	public void setFloorPrice(BigDecimal floorPrice) {
		this.floorPrice = floorPrice;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getFloorPrice() {
		return floorPrice;
	}
	/**
	 * 设置：
	 */
	public void setMinReduction(BigDecimal minReduction) {
		this.minReduction = minReduction;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getMinReduction() {
		return minReduction;
	}
	/**
	 * 设置：
	 */
	public void setMaxReduction(BigDecimal maxReduction) {
		this.maxReduction = maxReduction;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getMaxReduction() {
		return maxReduction;
	}
	/**
	 * 设置：
	 */
	public void setRestrictTime(Integer restrictTime) {
		this.restrictTime = restrictTime;
	}
	/**
	 * 获取：
	 */
	public Integer getRestrictTime() {
		return restrictTime;
	}
	/**
	 * 设置：
	 */
	public void setPrizeDescription(String prizeDescription) {
		this.prizeDescription = prizeDescription;
	}
	/**
	 * 获取：
	 */
	public String getPrizeDescription() {
		return prizeDescription;
	}
	/**
	 * 设置：
	 */
	public void setActivityRule(String activityRule) {
		this.activityRule = activityRule;
	}
	/**
	 * 获取：
	 */
	public String getActivityRule() {
		return activityRule;
	}
	/**
	 * 设置：
	 */
	public void setPrizeInfo(String prizeInfo) {
		this.prizeInfo = prizeInfo;
	}
	/**
	 * 获取：
	 */
	public String getPrizeInfo() {
		return prizeInfo;
	}
	/**
	 * 设置：
	 */
	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}
	/**
	 * 获取：
	 */
	public String getCompanyDescription() {
		return companyDescription;
	}
	/**
	 * 设置：
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	/**
	 * 获取：
	 */
	public String getDiscount() {
		return discount;
	}
	/**
	 * 设置：
	 */
	public void setQrImg(String qrImg) {
		this.qrImg = qrImg;
	}
	/**
	 * 获取：
	 */
	public String getQrImg() {
		return qrImg;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	/**
	 * 获取：
	 */
	public String getHeadImage() {
		return headImage;
	}
	/**
	 * 设置：
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取：
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 设置：
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取：
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置：
	 */
	public void setFootImage(String footImage) {
		this.footImage = footImage;
	}
	/**
	 * 获取：
	 */
	public String getFootImage() {
		return footImage;
	}
	/**
	 * 设置：
	 */
	public void setBgImage(String bgImage) {
		this.bgImage = bgImage;
	}
	/**
	 * 获取：
	 */
	public String getBgImage() {
		return bgImage;
	}
	/**
	 * 设置：
	 */
	public void setGift(String gift) {
		this.gift = gift;
	}
	/**
	 * 获取：
	 */
	public String getGift() {
		return gift;
	}
	/**
	 * 设置：
	 */
	public void setPrizeLeft(Integer prizeLeft) {
		this.prizeLeft = prizeLeft;
	}
	/**
	 * 获取：
	 */
	public Integer getPrizeLeft() {
		return prizeLeft;
	}
	/**
	 * 设置：
	 */
	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}
	/**
	 * 获取：
	 */
	public Integer getPrizeNum() {
		return prizeNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getBarginNum() {
		return barginNum;
	}

	public void setBarginNum(Integer barginNum) {
		this.barginNum = barginNum;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getQuestion5() {
		return question5;
	}

	public void setQuestion5(String question5) {
		this.question5 = question5;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getQuestion4() {
		return question4;
	}

	public void setQuestion4(String question4) {
		this.question4 = question4;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	public int getShareNum() {
		return shareNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

	public int getViewNum() {
		return viewNum;
	}

	public void setViewNum(int viewNum) {
		this.viewNum = viewNum;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}
