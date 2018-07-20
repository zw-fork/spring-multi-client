/*
 * ............................................. 
 * 
 * 				    _ooOoo_ 
 * 		  	       o8888888o 
 * 	  	  	       88" . "88 
 *                 (| -_- |) 
 *                  O\ = /O 
 *              ____/`---*\____ 
 *               . * \\| |// `. 
 *             / \\||| : |||// \ 
 *           / _||||| -:- |||||- \ 
 *             | | \\\ - /// | | 
 *            | \_| **\---/** | | 
 *           \  .-\__ `-` ___/-. / 
 *            ___`. .* /--.--\ `. . __ 
 *        ."" *< `.___\_<|>_/___.* >*"". 
 *      | | : `- \`.;`\ _ /`;.`/ - ` : | | 
 *         \ \ `-. \_ __\ /__ _/ .-` / / 
 *======`-.____`-.___\_____/___.-`____.-*====== 
 * 
 * ............................................. 
 *              佛祖保佑 永无BUG 
 *
 * 佛曰: 
 * 写字楼里写字间，写字间里程序员； 
 * 程序人员写程序，又拿程序换酒钱。 
 * 酒醒只在网上坐，酒醉还来网下眠； 
 * 酒醉酒醒日复日，网上网下年复年。 
 * 但愿老死电脑间，不愿鞠躬老板前； 
 * 奔驰宝马贵者趣，公交自行程序员。 
 * 别人笑我忒疯癫，我笑自己命太贱； 
 * 不见满街漂亮妹，哪个归得程序员？
 *
 * 北纬30.√  154518484@qq.com
 */
package com.github.model;

import org.apache.commons.lang.builder.*;
import org.apache.commons.lang.time.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {
	
	//alias
	public static final String TABLE_ALIAS = "User";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_AVATAR = "头像url";
	public static final String ALIAS_NICK_NAME = "昵称";
	public static final String ALIAS_REAL_NAME = "真实姓名";
	public static final String ALIAS_PHONE = "手机号码";
	public static final String ALIAS_SALT = "盐";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_MONEY = "账户余额";
	public static final String ALIAS_PROFIT = "累计收益";
	public static final String ALIAS_TYPE = "1管理员 2股东 3报单中心(高级股东)";
	public static final String ALIAS_LEVEL = "1无等级 2市场总监 3市场经理";
	public static final String ALIAS_AREA_CODE = "用户所属市级的区域代码";
	public static final String ALIAS_BANK_ACCOUNT = "银行账户名";
	public static final String ALIAS_BANK_NAME = "开户行名称";
	public static final String ALIAS_BANK_BRANCH = "支行名称";
	public static final String ALIAS_BANK_NUMBER = "银行卡号";
	public static final String ALIAS_PARENT_PHONE = "父级用户电话号码";
	public static final String ALIAS_SEX = "性别: 0未知 1男  2女";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_ADDRESS = "联系地址";
	public static final String ALIAS_LAST_LOGIN_TIME = "最后登录时间";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_PROFIT_START = "分红开始日期";
	public static final String ALIAS_PROFIT_EXPIRE = "分红结束日期";
	public static final String ALIAS_STATUS = "状态 1可用，0禁用";
	private static final long serialVersionUID = -142078627746093032L;


	//columns START
	/** id   db_column: id */ 	
	private Integer id;
	/** 头像url   db_column: avatar */ 	
	private String avatar;
	/** 昵称   db_column: nick_name */ 	
	private String nickName;
	/** 真实姓名   db_column: real_name */ 	
	private String realName;
	/** 手机号码   db_column: phone */ 	
	private String phone;
	/** 盐   db_column: salt */ 	
	private String salt;
	/** 密码   db_column: password */ 	
	private String password;
	/** 账户余额   db_column: money */ 	
	private BigDecimal money;
	/** 累计收益   db_column: profit */ 	
	private BigDecimal profit;
	/** 1管理员 2股东 3报单中心(高级股东)   db_column: type */ 	
	private Integer type;
	/** 1无等级 2市场总监 3市场经理   db_column: level */ 	
	private Integer level;
	/** 用户所属市级的区域代码   db_column: area_code */ 	
	private Integer areaCode;
	/** 银行账户名   db_column: bank_account */ 	
	private String bankAccount;
	/** 开户行名称   db_column: bank_name */ 	
	private String bankName;
	/** 支行名称   db_column: bank_branch */ 	
	private String bankBranch;
	/** 银行卡号   db_column: bank_number */ 	
	private String bankNumber;
	/** 父级用户电话号码   db_column: parent_phone */ 	
	private String parentPhone;
	/** 性别: 0未知 1男  2女   db_column: sex */ 	
	private Integer sex;
	/** 生日   db_column: birthday */ 	
	private java.util.Date birthday;
	/** 联系地址   db_column: address */ 	
	private String address;
	/** 最后登录时间   db_column: last_login_time */ 	
	private java.util.Date lastLoginTime;
	/** 创建时间   db_column: create_time */ 	
	private java.util.Date createTime;
	/** 分红开始日期   db_column: profit_start */ 	
	private java.util.Date profitStart;
	/** 分红结束日期   db_column: profit_expire */ 	
	private java.util.Date profitExpire;
	/** 状态 1可用，0禁用   db_column: status */ 	
	private Integer status;
	//columns END

	public static final int TYPE_ADMIN = 1;
	public static final int TYPE_PRIMARY = 2;
	public static final int TYPE_SENIOR = 3;

	public static final int LEVEL_DIRECTOR = 2;
	public static final int LEVEL_MANAGER = 3;

	public User(){
	}

	public User(Integer id){
		this.id = id;
	}

	public User(String realName, String phone, int type, Integer areaCode, String bankAccount, String bankName, String bankBranch, String bankNumber, String parentPhone) {
		this.realName = realName;
		this.phone = phone;
		this.money = new BigDecimal(0);
		this.profit = new BigDecimal(0);
		this.type = type;
		this.level = 1;
		this.areaCode = areaCode;
		this.bankAccount = bankAccount;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.bankNumber = bankNumber;
		this.parentPhone = parentPhone;
		this.lastLoginTime = new Date();
		this.createTime = new Date();
		this.profitStart = DateUtils.addDays(new Date(), 1);
		switch (type) {
			case TYPE_PRIMARY:
				this.profitExpire = DateUtils.addYears(new Date(), 1);
				break;
			case TYPE_SENIOR:
				this.profitExpire = DateUtils.addYears(new Date(), 2);
				break;
			default:
				throw new IllegalArgumentException();
		}
		this.status = 1;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public Integer getId() {
		return this.id;
	}
	public void setAvatar(String value) {
		this.avatar = value;
	}
	public String getAvatar() {
		return this.avatar;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	public String getRealName() {
		return this.realName;
	}
	public void setPhone(String value) {
		this.phone = value;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setSalt(String value) {
		this.salt = value;
	}
	public String getSalt() {
		return this.salt;
	}
	public void setPassword(String value) {
		this.password = value;
	}
	public String getPassword() {
		return this.password;
	}
	public void setMoney(BigDecimal value) {
		this.money = value;
	}
	public BigDecimal getMoney() {
		return this.money;
	}
	public void setProfit(BigDecimal value) {
		this.profit = value;
	}
	public BigDecimal getProfit() {
		return this.profit;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	public Integer getType() {
		return this.type;
	}
	public void setLevel(Integer value) {
		this.level = value;
	}
	public Integer getLevel() {
		return this.level;
	}
	public void setAreaCode(Integer value) {
		this.areaCode = value;
	}
	public Integer getAreaCode() {
		return this.areaCode;
	}
	public void setBankAccount(String value) {
		this.bankAccount = value;
	}
	public String getBankAccount() {
		return this.bankAccount;
	}
	public void setBankName(String value) {
		this.bankName = value;
	}
	public String getBankName() {
		return this.bankName;
	}
	public void setBankBranch(String value) {
		this.bankBranch = value;
	}
	public String getBankBranch() {
		return this.bankBranch;
	}
	public void setBankNumber(String value) {
		this.bankNumber = value;
	}
	public String getBankNumber() {
		return this.bankNumber;
	}
	public void setParentPhone(String value) {
		this.parentPhone = value;
	}
	public String getParentPhone() {
		return this.parentPhone;
	}
	public void setSex(Integer value) {
		this.sex = value;
	}
	public Integer getSex() {
		return this.sex;
	}
	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	public void setAddress(String value) {
		this.address = value;
	}
	public String getAddress() {
		return this.address;
	}
	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setProfitStart(java.util.Date value) {
		this.profitStart = value;
	}
	public java.util.Date getProfitStart() {
		return this.profitStart;
	}
	public void setProfitExpire(java.util.Date value) {
		this.profitExpire = value;
	}
	public java.util.Date getProfitExpire() {
		return this.profitExpire;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

