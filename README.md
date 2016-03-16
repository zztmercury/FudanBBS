# 商户端用户管理接口

## 推送接口说明：
### 场景：用户扫商户码
**推送内容**:
```javascript
{
	"type": "user_info",
	"weChatUserId": USER_ID //String 微信扫码用户Id
}
```

## 主动拉取接口
### 登录
**URL** /merchantAction/login.action

**参数**

| 参数名 | 类型 | 备注 |
| :--- | :--- | :--- |
| account | String | 商户账号 |
| password | String | 商户密码 |

**返回**
```javascript
{
	"success":SUCCESS, //bool 操作是否成功
	"message":MESSAGE, //String 提示消息
	"param":{
		"stores":[ //Array 门店列表
			{
				"storeName":STORE_NAME, //String 门店名称
				"storeId":STORE_ID //String 门店ID
			},
			...
		],
		"merchantId":MERCHANTID //String 商户ID
	}
}
```

### 绑定门店
**URL** /merchantAction/bindDeviceToStore.action

**参数**

| 参数名 | 类型 | 备注 |
| :--- | :--- | :--- |
| push_did | String | 设备推送服务唯一标识|
| store_id | String | 门店Id |


### 拉取客户列表
**URL** /merchantAction/guestList.action

**参数**

| 参数名 | 类型 | 备注 |
| :--- | :--- | :--- |
| type | int | 0:所有客户 <br/> 1:常客 |
| merchant_id | String | store_id为空时必需 |
| store_id | String | `store_id==1`时必需 |

**返回**
```javascript
{
	"success":SUCCESS, //bool 操作是否成功
	"message":MESSAGE, //String 提示消息
	"param":{
		"guests":[ //Array 客户列表
			{
				"weChatUserId":WE_CHAT_USER_ID //int 微信用户Id
				"headImg":HEAD_IMG, //String 微信头像图片地址
				"nickname":NICKNAME, //String 微信昵称
				"remarks":REMARKS //String 客户备注
			},
			...
		]
	}
}
```

### 客户详情
**URL** /merchantAction/guestDetail.action

**参数**

| 参数名 | 类型 | 备注 |
| :--- | :--- | :--- |
| we_chat_user_id | int | 微信扫码用户Id |
| merchant_id | String | 商户Id |

**返回**
```javascript
{
	"success":SUCCESS, //bool 操作是否成功
	"message":MESSAGE, //String 提示消息
	"param":{ //Object 客户详情
		"headImg":HEAD_IMG, //String 微信头像图片地址
		"nickname":NICKNAME, //String 微信昵称
		"phoneNum":PHONE_NUM, //String 客户手机号
		"level":LEVEL, //String 会员等级
		"totalPoint":TOTAL_POINT, //int 累计积点数
		"currentPoint":CURRENT_POINT, //int 当前积点数
		"registerDate":REGISTER_DATE, //long 注册时间，单位：秒
		"balance":BALANCE //int 账户余额，单位：分
	}
}
```

### 积点
**URL** /merchantAction/givePoint.action

**参数**

| 参数名 | 类型 | 备注 |
| :--- | :--- | :--- |
| we_chat_user_id | int | 微信扫码用户Id |
| store_id | String | 门店Id |

**返回**
```javascript
{
	"success":SUCCESS, //bool 操作是否成功
	"message":MESSAGE //String 提示消息
}
```

### 拉取客户名下所有卡券信息
**URL** /merchantAction/getGuestCards.action

**参数**

| 参数名 | 类型 | 备注 |
| :--- | :--- | :--- |
| we_chat_user_id | int | 微信扫码用户Id |
| merchant_id | String | 商户Id |

**返回**
```javascript
{
	"success":SUCCESS, //boolean 操作是否成功
	"message":MESSAGE, //String 提示消息
	"param":{
		"cards":[ //卡券列表
			{
				"cardId":CARD_ID, //String 卡券ID
				"type":TYPE, //int 0——积点卡，1——兑换券，2——签到卡
				"cardName":CARD_NAME, //String 卡券名
				"convertName":CONVERT_NAME, //String 兑换物品名
				"period":PERIOD, //long 卡券有效期
				//以下参数仅在type==0或2时必需
				"currentPoint":CURRENT_POINT, //int 当前积点数
				"convertPoint":CONVERT_POINT //int 兑换需要积点数
			}
		]
	}
}
```

### 兑换
**URL** /merchantAction/convert.action

**参数**

| 参数名 | 类型 | 备注 |
| :--- | :--- | :--- |
| we_chat_user_id | int | 微信扫码用户Id |
| store_id | String | 门店ID |
| card_id | String | 卡券ID |

**返回**
```javascript
{
	"success":SUCCESS, //bool 操作是否成功
	"message":MESSAGE //String 提示消息
}
```
