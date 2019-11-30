package io.renren.modules.gather.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-11 10:39:02
 */
@TableName("tb_gather")
public class GatherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;

	private String address;
	/**
	 * 
	 */
	private String activityName;
	/**
	 * 
	 */
	private String startTime;
	/**
	 * 
	 */
	private String endTime;
	/**
	 * 
	 */
	private Integer priceNum;
	/**
	 * 
	 */
	private Integer targetNum;
	/**
	 * 
	 */
	private Integer restrictTime;
	/**
	 * 
	 */
	private String priceDescription;
	/**
	 * 
	 */
	private String activityRule;
	/**
	 * 
	 */
	private String priceInfo;
	/**
	 * 
	 */
	private String companyDescription;
	/**
	 * 
	 */
	private String thumbnail;
	/**
	 * 
	 */
	private String discount;
	/**
	 * 
	 */
	private String updateUser;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private String qrImg;
	/**
	 * 
	 */
	private String headImage;
	/**
	 * 
	 */
	private String phone;

	private String gift;

	private String longitude;//经度

	private String latitude;//纬度

	private Integer prizeLeft;

	private String footImage;

	private String bgImage;

	private String  createUser;

	private int likeNum;//点赞
	private int shareNum;//分享
	private int useNum;//使用
	private int viewNum;//查看

	private String type;

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
	public void setPriceNum(Integer priceNum) {
		this.priceNum = priceNum;
	}
	/**
	 * 获取：
	 */
	public Integer getPriceNum() {
		return priceNum;
	}
	/**
	 * 设置：
	 */
	public void setTargetNum(Integer targetNum) {
		this.targetNum = targetNum;
	}
	/**
	 * 获取：
	 */
	public Integer getTargetNum() {
		return targetNum;
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
	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}
	/**
	 * 获取：
	 */
	public String getPriceDescription() {
		return priceDescription;
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
	public void setPriceInfo(String priceInfo) {
		this.priceInfo = priceInfo;
	}
	/**
	 * 获取：
	 */
	public String getPriceInfo() {
		return priceInfo;
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
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取：
	 */
	public String getUpdateUser() {
		return updateUser;
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
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：
	 */
	public String getPhone() {
		return phone;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getPrizeLeft() {
		return prizeLeft;
	}

	public void setPrizeLeft(Integer prizeLeft) {
		this.prizeLeft = prizeLeft;
	}

	public String getFootImage() {
		return footImage;
	}

	public void setFootImage(String footImage) {
		this.footImage = footImage;
	}

	public String getBgImage() {
		return bgImage;
	}

	public void setBgImage(String bgImage) {
		this.bgImage = bgImage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
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
}
