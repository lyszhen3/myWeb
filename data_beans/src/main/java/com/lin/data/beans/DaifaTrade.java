package com.lin.data.beans;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *@author 自动生成
 *@see DaifaTrade
 *@docRoot com.opentae.data.daifa.beans.DaifaTrade
 *@remark 自动生成
 *生成日期：2016-06-23 02:49:25
 *完成日期：
 *内容摘要：
 *
 */
public class DaifaTrade {

	/***/
	@Id
@GeneratedValue(generator="JDBC")
	private Long dfTradeId;
	/**分销商ID*/
	private Long buyerId;
	/**分销商名称*/
	private String buyerName;
	/**分销商电话*/
	private String buyerTelephone;
	/**分销商QQ*/
	private String buyerQq;
	/**分销商旺旺*/
	private String buyerWw;
	/**分销商微信*/
	private String buyerWx;
	/**代发ID*/
	private Long sellerId;
	/**代发名称*/
	private String sellerName;
	/**商品数*/
	private Long goodsNum;
	/**0.未付款，1、交易关闭2、已付款待接手，3、拿货中，4、检货中，5、已发货，6、交易完成，7、协商件，8、协商件交易结束、9、到货退款中*/
	private Integer tradeStatus;
	/**交易费用*/
	private String tradeFee;
	/**快递费用*/
	private String postFee;
	/**代发费*/
	private String daifaFee;
	/**体积*/
	private String volume;
	/**重量*/
	private String weight;
	/**最后修改时间*/
	@Transient
	private java.util.Date lastDoTime;
	/**创建时间*/
	private java.util.Date createTime;
	/**支付时间*/
	private java.util.Date payTime;
	/**发货时间*/
	private java.util.Date sendTime;
	/**确认时间*/
	private java.util.Date sureTime;
	/**结束时间*/
	private java.util.Date finishTime;
	/**修改时间*/
	private java.util.Date modifyTime;
	/**快递ID*/
	private Long postId;
	/**快递名*/
	private String postName;
	/**支付编号（未用）*/
	private String payNo;
	/**支付方式（手机、电脑之类的）*/
	private String payWay;
	/**总钱数（包含快递，代发费）＝total_fee-trade_discount_fee*/
	private String money;
	/**是否使用代金券@只有在使用星座宝支付的时候才可能使用代金券1不使用2已使用*/
	private Integer useVoucher;
	/**使用了多少代金券@所有子单的代金券之和*/
	private String useVoucherAmount;
	/**实付钱数＝money- use_voucher_amount*/
	private String realPayMoney;
	/**收件人姓名*/
	private String receiverName;
	/**收件人省*/
	private String receiverState;
	/**收件人详细地址*/
	private String receiverAddress;
	/**收件人邮编*/
	private String receiverZip;
	/**收件人手机*/
	private String receiverMobile;
	/**收件人电话*/
	private String receiverPhone;
	/**快递单号*/
	private String postCode;
	/**买家留言*/
	private String buyerRemark;
	/**过时*/
	private String postFeeBu;
	/**付款状态@ 1未付款2已付款*/
	private Integer payStatus;
	/**店铺商品匹配状态@1未匹配2已匹配*/
	private Integer atchingStatus;
	/**接单状态@1待接单2已接单3拒绝*/
	private Integer orderReceivingStatus;
	/**识别码状态@1未生成2已生成*/
	private Integer cdkeyStatus;
	/**打印拿货单状态@1未打印2已打印*/
	private Integer printGgoodsStatus;
	/**打印快递单@1未打印2已打印*/
	private Integer printExpressStatus;
	/**缺货状态@1有货2 部分缺货3全部缺货*/
	private Integer stockoutStatus;
	/**缺货周期状态@1星缺货2星缺货*/
	private Integer stockoutCycleStatus;
	/**退货状态@1待退货2待部分退货3已退货4已部分退货*/
	private Integer returnGoodsStatus;
	/**退回档口状态@1待退回2已退回*/
	private Integer returnStatus;
	/**淘宝反向写入@1待标注2已标注*/
	private Integer tbWriteLabelStatus;
	/**发货状态@1检货中2 打包成功 3已经出库发货*/
	private Integer sendStatus;
	/**客户标记@0普通客户1大客户默认为0*/
	private Integer bigCustFlag;
	/**订单内容二维码*/
	private String orderQrcode;
	/**0初始状态 退款状态1待退款2待部分退款3已退款4已部分退款*/
	private Integer refundStatus;
	/**退货的退回快递单号*/
	private String returnPostCode;
	/**代发类型@1表格下单2在线下单3淘宝同步订单4excel下单*/
	private Integer daifaType;
	/**过时*/
	private Integer postFeeBuFlag;
	/**过时*/
	private String tuiFee;
	/**过时*/
	private String tuiFeeWorker;
	/**过时*/
	private java.util.Date tuiFeeTime;
	/**过时*/
	private String tuiFeeReason;
	/**过时*/
	private String zdPostFeeBuWorder;
	/**过时*/
	private java.util.Date zdPostFeeBuTime;
	/**是否同意档口服务状态@0不同意1同意*/
	private Integer agreeStoreService;
	/**1拿到部分发货0全部退货*/
	private String remark1;
	/***/
	private String remark2;
	/**买家留言*/
	private String remark3;
	/**表格拿货匹配上null 未匹配上0*/
	private String remark4;
	/***/
	private String remark5;
	/***/
	private String remark6;
	/***/
	private String remark7;
	/***/
	private String remark8;
	/***/
	private String remark9;
	/***/
	private String remark10;

	/**交易编号（未用）*/
	private String tradeCode;
	/**收货人QQ*/
	private String receiverQq;
	/**接手时间*/
	private java.util.Date orderReceivingTime;
	/**拿货时间*/
	private java.util.Date ggoodsTime;
	/**检货时间*/
	private java.util.Date checkTime;
	/**淘宝订单号tid*/
	private Long taobaoTid;
	/**下拉订单的淘宝账户的昵称*/
	private String taobaoUserNick;
	/**是否已经导出过excel1为是0为否*/
	private Integer isExport;
	/**退款原因*/
	private String refundReason;
	/**退货原因*/
	private String returnGoodsReason;
	/**申请退款时间*/
	private java.util.Date reqRefundTime;
	/**多订单ID*/
	private String moreTradeId;
	/**结算状态：0结算错误可再次发起1已发起结算2已结算*/
	private Long settingAccountsStatus;
	/***/
	private String dfTradeRemark1;
	/**减免前总费用（包含快递，代发费）＝trade_fee+post_fee+daifa_fee*/
	private String totalFee;
	/**vip要减面的费用=所有子单里的order_discount_fee*/
	private String tradeDiscountFee;
	/**缺货时间*/
	private java.util.Date outOfStockTime;

	private Integer changeStatus;
	private Integer notPayPost;
	private String payPostId;
	private String excelUrl;
	private Integer isTbSend;
	private Integer isUserStore;
	private String barCodeKey;
	private Integer agreeSplit;
	public Integer getAgreeSplit() {
		return agreeSplit;
	}

	public void setAgreeSplit(Integer agreeSplit) {
		this.agreeSplit = agreeSplit;
	}

	public String getBarCodeKey() {
		return barCodeKey;
	}

	public void setBarCodeKey(String barCodeKey) {
		this.barCodeKey = barCodeKey;
	}

	public Integer getIsUserStore() {
		return isUserStore;
	}

	public void setIsUserStore(Integer isUserStore) {
		this.isUserStore = isUserStore;
	}

	public Integer getIsTbSend() {
		return isTbSend;
	}

	public void setIsTbSend(Integer isTbSend) {
		this.isTbSend = isTbSend;
	}

	public Integer getNotPayPost() {
		return notPayPost;
	}

	public void setNotPayPost(Integer notPayPost) {
		this.notPayPost = notPayPost;
	}

	public String getPayPostId() {
		return payPostId;
	}

	public void setPayPostId(String payPostId) {
		this.payPostId = payPostId;
	}

	public String getExcelUrl() {
		return excelUrl;
	}

	public void setExcelUrl(String excelUrl) {
		this.excelUrl = excelUrl;
	}

	public Integer getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(Integer changeStatus) {
		this.changeStatus = changeStatus;
	}

	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public Long getDfTradeId() {
        return dfTradeId;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setDfTradeId(Long dfTradeId) {
        this.dfTradeId = dfTradeId;
    }
	/**
  	*
	*get方法
  	*分销商ID
 	*自动生成
  	*/
    public Long getBuyerId() {
        return buyerId;
    }

    /**
  	*
  	*set方法
	*分销商ID
 	*自动生成
  	*/
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
	/**
  	*
	*get方法
  	*分销商名称
 	*自动生成
  	*/
    public String getBuyerName() {
        return buyerName;
    }

    /**
  	*
  	*set方法
	*分销商名称
 	*自动生成
  	*/
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
	/**
  	*
	*get方法
  	*分销商电话
 	*自动生成
  	*/
    public String getBuyerTelephone() {
        return buyerTelephone;
    }

    /**
  	*
  	*set方法
	*分销商电话
 	*自动生成
  	*/
    public void setBuyerTelephone(String buyerTelephone) {
        this.buyerTelephone = buyerTelephone;
    }
	/**
  	*
	*get方法
  	*分销商QQ
 	*自动生成
  	*/
    public String getBuyerQq() {
        return buyerQq;
    }

    /**
  	*
  	*set方法
	*分销商QQ
 	*自动生成
  	*/
    public void setBuyerQq(String buyerQq) {
        this.buyerQq = buyerQq;
    }
	/**
  	*
	*get方法
  	*分销商旺旺
 	*自动生成
  	*/
    public String getBuyerWw() {
        return buyerWw;
    }

    /**
  	*
  	*set方法
	*分销商旺旺
 	*自动生成
  	*/
    public void setBuyerWw(String buyerWw) {
        this.buyerWw = buyerWw;
    }
	/**
  	*
	*get方法
  	*分销商微信
 	*自动生成
  	*/
    public String getBuyerWx() {
        return buyerWx;
    }

    /**
  	*
  	*set方法
	*分销商微信
 	*自动生成
  	*/
    public void setBuyerWx(String buyerWx) {
        this.buyerWx = buyerWx;
    }
	/**
  	*
	*get方法
  	*代发ID
 	*自动生成
  	*/
    public Long getSellerId() {
        return sellerId;
    }

    /**
  	*
  	*set方法
	*代发ID
 	*自动生成
  	*/
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
	/**
  	*
	*get方法
  	*代发名称
 	*自动生成
  	*/
    public String getSellerName() {
        return sellerName;
    }

    /**
  	*
  	*set方法
	*代发名称
 	*自动生成
  	*/
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
	/**
  	*
	*get方法
  	*商品数
 	*自动生成
  	*/
    public Long getGoodsNum() {
        return goodsNum;
    }

    /**
  	*
  	*set方法
	*商品数
 	*自动生成
  	*/
    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }
	/**
  	*
	*get方法
  	*0.未付款，1、交易关闭2、已付款待接手，3、拿货中，4、检货中，5、已发货，6、交易完成，7、协商件，8、协商件交易结束、9、到货退款中
 	*自动生成
  	*/
    public Integer getTradeStatus() {
        return tradeStatus;
    }

    /**
  	*
  	*set方法
	*0.未付款，1、交易关闭2、已付款待接手，3、拿货中，4、检货中，5、已发货，6、交易完成，7、协商件，8、协商件交易结束、9、到货退款中
 	*自动生成
  	*/
    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
	/**
  	*
	*get方法
  	*付款状态@ 1未付款2已付款
 	*自动生成
  	*/
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
  	*
  	*set方法
	*付款状态@ 1未付款2已付款
 	*自动生成
  	*/
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
	/**
  	*
	*get方法
  	*店铺商品匹配状态@1未匹配2已匹配
 	*自动生成
  	*/
    public Integer getAtchingStatus() {
        return atchingStatus;
    }

    /**
  	*
  	*set方法
	*店铺商品匹配状态@1未匹配2已匹配
 	*自动生成
  	*/
    public void setAtchingStatus(Integer atchingStatus) {
        this.atchingStatus = atchingStatus;
    }
	/**
  	*
	*get方法
  	*接单状态@1待接单2已接单3拒绝
 	*自动生成
  	*/
    public Integer getOrderReceivingStatus() {
        return orderReceivingStatus;
    }

    /**
  	*
  	*set方法
	*接单状态@1待接单2已接单3拒绝
 	*自动生成
  	*/
    public void setOrderReceivingStatus(Integer orderReceivingStatus) {
        this.orderReceivingStatus = orderReceivingStatus;
    }
	/**
  	*
	*get方法
  	*识别码状态@1未生成2已生成
 	*自动生成
  	*/
    public Integer getCdkeyStatus() {
        return cdkeyStatus;
    }

    /**
  	*
  	*set方法
	*识别码状态@1未生成2已生成
 	*自动生成
  	*/
    public void setCdkeyStatus(Integer cdkeyStatus) {
        this.cdkeyStatus = cdkeyStatus;
    }
	/**
  	*
	*get方法
  	*打印拿货单状态@1未打印2已打印
 	*自动生成
  	*/
    public Integer getPrintGgoodsStatus() {
        return printGgoodsStatus;
    }

    /**
  	*
  	*set方法
	*打印拿货单状态@1未打印2已打印
 	*自动生成
  	*/
    public void setPrintGgoodsStatus(Integer printGgoodsStatus) {
        this.printGgoodsStatus = printGgoodsStatus;
    }
	/**
  	*
	*get方法
  	*缺货状态@1有货2 部分缺货3全部缺货
 	*自动生成
  	*/
    public Integer getStockoutStatus() {
        return stockoutStatus;
    }

    /**
  	*
  	*set方法
	*缺货状态@1有货2 部分缺货3全部缺货
 	*自动生成
  	*/
    public void setStockoutStatus(Integer stockoutStatus) {
        this.stockoutStatus = stockoutStatus;
    }
	/**
  	*
	*get方法
  	*缺货周期状态@1星缺货2星缺货
 	*自动生成
  	*/
    public Integer getStockoutCycleStatus() {
        return stockoutCycleStatus;
    }

    /**
  	*
  	*set方法
	*缺货周期状态@1星缺货2星缺货
 	*自动生成
  	*/
    public void setStockoutCycleStatus(Integer stockoutCycleStatus) {
        this.stockoutCycleStatus = stockoutCycleStatus;
    }
	/**
  	*
	*get方法
  	*打印快递单@1未打印2已打印
 	*自动生成
  	*/
    public Integer getPrintExpressStatus() {
        return printExpressStatus;
    }

    /**
  	*
  	*set方法
	*打印快递单@1未打印2已打印
 	*自动生成
  	*/
    public void setPrintExpressStatus(Integer printExpressStatus) {
        this.printExpressStatus = printExpressStatus;
    }
	/**
  	*
	*get方法
  	*发货状态@1检货中2 打包成功 3已经出库发货
 	*自动生成
  	*/
    public Integer getSendStatus() {
        return sendStatus;
    }

    /**
  	*
  	*set方法
	*发货状态@1检货中2 打包成功 3已经出库发货
 	*自动生成
  	*/
    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }
	/**
  	*
	*get方法
  	*0初始状态 退款状态1待退款2待部分退款3已退款4已部分退款
 	*自动生成
  	*/
    public Integer getRefundStatus() {
        return refundStatus;
    }

    /**
  	*
  	*set方法
	*0初始状态 退款状态1待退款2待部分退款3已退款4已部分退款
 	*自动生成
  	*/
    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }
	/**
  	*
	*get方法
  	*退货状态@1待退货2待部分退货3已退货4已部分退货
 	*自动生成
  	*/
    public Integer getReturnGoodsStatus() {
        return returnGoodsStatus;
    }

    /**
  	*
  	*set方法
	*退货状态@1待退货2待部分退货3已退货4已部分退货
 	*自动生成
  	*/
    public void setReturnGoodsStatus(Integer returnGoodsStatus) {
        this.returnGoodsStatus = returnGoodsStatus;
    }
	/**
  	*
	*get方法
  	*退回档口状态@1待退回2已退回
 	*自动生成
  	*/
    public Integer getReturnStatus() {
        return returnStatus;
    }

    /**
  	*
  	*set方法
	*退回档口状态@1待退回2已退回
 	*自动生成
  	*/
    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }
	/**
  	*
	*get方法
  	*淘宝反向写入@1待标注2已标注
 	*自动生成
  	*/
    public Integer getTbWriteLabelStatus() {
        return tbWriteLabelStatus;
    }

    /**
  	*
  	*set方法
	*淘宝反向写入@1待标注2已标注
 	*自动生成
  	*/
    public void setTbWriteLabelStatus(Integer tbWriteLabelStatus) {
        this.tbWriteLabelStatus = tbWriteLabelStatus;
    }
	/**
  	*
	*get方法
  	*最后修改时间
 	*自动生成
  	*/
    public java.util.Date getLastDoTime() {
        return lastDoTime;
    }

    /**
  	*
  	*set方法
	*最后修改时间
 	*自动生成
  	*/
    public void setLastDoTime(java.util.Date lastDoTime) {
        this.lastDoTime = lastDoTime;
    }
	/**
  	*
	*get方法
  	*创建时间
 	*自动生成
  	*/
    public java.util.Date getCreateTime() {
        return createTime;
    }

    /**
  	*
  	*set方法
	*创建时间
 	*自动生成
  	*/
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
	/**
  	*
	*get方法
  	*支付时间
 	*自动生成
  	*/
    public java.util.Date getPayTime() {
        return payTime;
    }

    /**
  	*
  	*set方法
	*支付时间
 	*自动生成
  	*/
    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }
	/**
  	*
	*get方法
  	*接手时间
 	*自动生成
  	*/
    public java.util.Date getOrderReceivingTime() {
        return orderReceivingTime;
    }

    /**
  	*
  	*set方法
	*接手时间
 	*自动生成
  	*/
    public void setOrderReceivingTime(java.util.Date orderReceivingTime) {
        this.orderReceivingTime = orderReceivingTime;
    }
	/**
  	*
	*get方法
  	*拿货时间
 	*自动生成
  	*/
    public java.util.Date getGgoodsTime() {
        return ggoodsTime;
    }

    /**
  	*
  	*set方法
	*拿货时间
 	*自动生成
  	*/
    public void setGgoodsTime(java.util.Date ggoodsTime) {
        this.ggoodsTime = ggoodsTime;
    }
	/**
  	*
	*get方法
  	*检货时间
 	*自动生成
  	*/
    public java.util.Date getCheckTime() {
        return checkTime;
    }

    /**
  	*
  	*set方法
	*检货时间
 	*自动生成
  	*/
    public void setCheckTime(java.util.Date checkTime) {
        this.checkTime = checkTime;
    }
	/**
  	*
	*get方法
  	*发货时间
 	*自动生成
  	*/
    public java.util.Date getSendTime() {
        return sendTime;
    }

    /**
  	*
  	*set方法
	*发货时间
 	*自动生成
  	*/
    public void setSendTime(java.util.Date sendTime) {
        this.sendTime = sendTime;
    }
	/**
  	*
	*get方法
  	*确认时间
 	*自动生成
  	*/
    public java.util.Date getSureTime() {
        return sureTime;
    }

    /**
  	*
  	*set方法
	*确认时间
 	*自动生成
  	*/
    public void setSureTime(java.util.Date sureTime) {
        this.sureTime = sureTime;
    }
	/**
  	*
	*get方法
  	*结束时间
 	*自动生成
  	*/
    public java.util.Date getFinishTime() {
        return finishTime;
    }

    /**
  	*
  	*set方法
	*结束时间
 	*自动生成
  	*/
    public void setFinishTime(java.util.Date finishTime) {
        this.finishTime = finishTime;
    }
	/**
  	*
	*get方法
  	*修改时间
 	*自动生成
  	*/
    public java.util.Date getModifyTime() {
        return modifyTime;
    }

    /**
  	*
  	*set方法
	*修改时间
 	*自动生成
  	*/
    public void setModifyTime(java.util.Date modifyTime) {
        this.modifyTime = modifyTime;
    }
	/**
  	*
	*get方法
  	*申请退款时间
 	*自动生成
  	*/
    public java.util.Date getReqRefundTime() {
        return reqRefundTime;
    }

    /**
  	*
  	*set方法
	*申请退款时间
 	*自动生成
  	*/
    public void setReqRefundTime(java.util.Date reqRefundTime) {
        this.reqRefundTime = reqRefundTime;
    }
	/**
  	*
	*get方法
  	*快递ID
 	*自动生成
  	*/
    public Long getPostId() {
        return postId;
    }

    /**
  	*
  	*set方法
	*快递ID
 	*自动生成
  	*/
    public void setPostId(Long postId) {
        this.postId = postId;
    }
	/**
  	*
	*get方法
  	*快递名
 	*自动生成
  	*/
    public String getPostName() {
        return postName;
    }

    /**
  	*
  	*set方法
	*快递名
 	*自动生成
  	*/
    public void setPostName(String postName) {
        this.postName = postName;
    }
	/**
  	*
	*get方法
  	*支付编号（未用）
 	*自动生成
  	*/
    public String getPayNo() {
        return payNo;
    }

    /**
  	*
  	*set方法
	*支付编号（未用）
 	*自动生成
  	*/
    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }
	/**
  	*
	*get方法
  	*支付方式（手机、电脑之类的）
 	*自动生成
  	*/
    public String getPayWay() {
        return payWay;
    }

    /**
  	*
  	*set方法
	*支付方式（手机、电脑之类的）
 	*自动生成
  	*/
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
	/**
  	*
	*get方法
  	*是否使用代金券@只有在使用星座宝支付的时候才可能使用代金券1不使用2已使用
 	*自动生成
  	*/
    public Integer getUseVoucher() {
        return useVoucher;
    }

    /**
  	*
  	*set方法
	*是否使用代金券@只有在使用星座宝支付的时候才可能使用代金券1不使用2已使用
 	*自动生成
  	*/
    public void setUseVoucher(Integer useVoucher) {
        this.useVoucher = useVoucher;
    }
	/**
  	*
	*get方法
  	*使用了多少代金券@所有子单的代金券之和
 	*自动生成
  	*/
    public String getUseVoucherAmount() {
        return useVoucherAmount;
    }

    /**
  	*
  	*set方法
	*使用了多少代金券@所有子单的代金券之和
 	*自动生成
  	*/
    public void setUseVoucherAmount(String useVoucherAmount) {
        this.useVoucherAmount = useVoucherAmount;
    }
	/**
  	*
	*get方法
  	*交易费用
 	*自动生成
  	*/
    public String getTradeFee() {
        return tradeFee;
    }

    /**
  	*
  	*set方法
	*交易费用
 	*自动生成
  	*/
    public void setTradeFee(String tradeFee) {
        this.tradeFee = tradeFee;
    }
	/**
  	*
	*get方法
  	*快递费用
 	*自动生成
  	*/
    public String getPostFee() {
        return postFee;
    }

    /**
  	*
  	*set方法
	*快递费用
 	*自动生成
  	*/
    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }
	/**
  	*
	*get方法
  	*代发费
 	*自动生成
  	*/
    public String getDaifaFee() {
        return daifaFee;
    }

    /**
  	*
  	*set方法
	*代发费
 	*自动生成
  	*/
    public void setDaifaFee(String daifaFee) {
        this.daifaFee = daifaFee;
    }
	/**
  	*
	*get方法
  	*减免前总费用（包含快递，代发费）＝trade_fee+post_fee+daifa_fee
 	*自动生成
  	*/
    public String getTotalFee() {
        return totalFee;
    }

    /**
  	*
  	*set方法
	*减免前总费用（包含快递，代发费）＝trade_fee+post_fee+daifa_fee
 	*自动生成
  	*/
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
	/**
  	*
	*get方法
  	*vip要减面的费用=所有子单里的order_discount_fee
 	*自动生成
  	*/
    public String getTradeDiscountFee() {
        return tradeDiscountFee;
    }

    /**
  	*
  	*set方法
	*vip要减面的费用=所有子单里的order_discount_fee
 	*自动生成
  	*/
    public void setTradeDiscountFee(String tradeDiscountFee) {
        this.tradeDiscountFee = tradeDiscountFee;
    }
	/**
  	*
	*get方法
  	*总钱数（包含快递，代发费）＝total_fee-trade_discount_fee
 	*自动生成
  	*/
    public String getMoney() {
        return money;
    }

    /**
  	*
  	*set方法
	*总钱数（包含快递，代发费）＝total_fee-trade_discount_fee
 	*自动生成
  	*/
    public void setMoney(String money) {
        this.money = money;
    }
	/**
  	*
	*get方法
  	*实付钱数＝money- use_voucher_amount
 	*自动生成
  	*/
    public String getRealPayMoney() {
        return realPayMoney;
    }

    /**
  	*
  	*set方法
	*实付钱数＝money- use_voucher_amount
 	*自动生成
  	*/
    public void setRealPayMoney(String realPayMoney) {
        this.realPayMoney = realPayMoney;
    }
	/**
  	*
	*get方法
  	*收件人姓名
 	*自动生成
  	*/
    public String getReceiverName() {
        return receiverName;
    }

    /**
  	*
  	*set方法
	*收件人姓名
 	*自动生成
  	*/
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
	/**
  	*
	*get方法
  	*收件人省
 	*自动生成
  	*/
    public String getReceiverState() {
        return receiverState;
    }

    /**
  	*
  	*set方法
	*收件人省
 	*自动生成
  	*/
    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }
	/**
  	*
	*get方法
  	*收件人详细地址
 	*自动生成
  	*/
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
  	*
  	*set方法
	*收件人详细地址
 	*自动生成
  	*/
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
	/**
  	*
	*get方法
  	*收件人邮编
 	*自动生成
  	*/
    public String getReceiverZip() {
        return receiverZip;
    }

    /**
  	*
  	*set方法
	*收件人邮编
 	*自动生成
  	*/
    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }
	/**
  	*
	*get方法
  	*收件人手机
 	*自动生成
  	*/
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
  	*
  	*set方法
	*收件人手机
 	*自动生成
  	*/
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }
	/**
  	*
	*get方法
  	*收件人电话
 	*自动生成
  	*/
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
  	*
  	*set方法
	*收件人电话
 	*自动生成
  	*/
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
	/**
  	*
	*get方法
  	*快递单号
 	*自动生成
  	*/
    public String getPostCode() {
        return postCode;
    }

    /**
  	*
  	*set方法
	*快递单号
 	*自动生成
  	*/
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
	/**
  	*
	*get方法
  	*买家留言
 	*自动生成
  	*/
    public String getBuyerRemark() {
        return buyerRemark;
    }

    /**
  	*
  	*set方法
	*买家留言
 	*自动生成
  	*/
    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }
	/**
  	*
	*get方法
  	*重量
 	*自动生成
  	*/
    public String getWeight() {
        return weight;
    }

    /**
  	*
  	*set方法
	*重量
 	*自动生成
  	*/
    public void setWeight(String weight) {
        this.weight = weight;
    }
	/**
  	*
	*get方法
  	*体积
 	*自动生成
  	*/
    public String getVolume() {
        return volume;
    }

    /**
  	*
  	*set方法
	*体积
 	*自动生成
  	*/
    public void setVolume(String volume) {
        this.volume = volume;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public String getPostFeeBu() {
        return postFeeBu;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setPostFeeBu(String postFeeBu) {
        this.postFeeBu = postFeeBu;
    }
	/**
  	*
	*get方法
  	*客户标记@0普通客户1大客户默认为0
 	*自动生成
  	*/
    public Integer getBigCustFlag() {
        return bigCustFlag;
    }

    /**
  	*
  	*set方法
	*客户标记@0普通客户1大客户默认为0
 	*自动生成
  	*/
    public void setBigCustFlag(Integer bigCustFlag) {
        this.bigCustFlag = bigCustFlag;
    }
	/**
  	*
	*get方法
  	*订单内容二维码
 	*自动生成
  	*/
    public String getOrderQrcode() {
        return orderQrcode;
    }

    /**
  	*
  	*set方法
	*订单内容二维码
 	*自动生成
  	*/
    public void setOrderQrcode(String orderQrcode) {
        this.orderQrcode = orderQrcode;
    }
	/**
  	*
	*get方法
  	*退货的退回快递单号
 	*自动生成
  	*/
    public String getReturnPostCode() {
        return returnPostCode;
    }

    /**
  	*
  	*set方法
	*退货的退回快递单号
 	*自动生成
  	*/
    public void setReturnPostCode(String returnPostCode) {
        this.returnPostCode = returnPostCode;
    }
	/**
  	*
	*get方法
  	*代发类型@1表格下单2在线下单3淘宝同步订单4excel下单
 	*自动生成
  	*/
    public Integer getDaifaType() {
        return daifaType;
    }

    /**
  	*
  	*set方法
	*代发类型@1表格下单2在线下单3淘宝同步订单4excel下单
 	*自动生成
  	*/
    public void setDaifaType(Integer daifaType) {
        this.daifaType = daifaType;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public Integer getPostFeeBuFlag() {
        return postFeeBuFlag;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setPostFeeBuFlag(Integer postFeeBuFlag) {
        this.postFeeBuFlag = postFeeBuFlag;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public String getTuiFee() {
        return tuiFee;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setTuiFee(String tuiFee) {
        this.tuiFee = tuiFee;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public String getTuiFeeWorker() {
        return tuiFeeWorker;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setTuiFeeWorker(String tuiFeeWorker) {
        this.tuiFeeWorker = tuiFeeWorker;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public java.util.Date getTuiFeeTime() {
        return tuiFeeTime;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setTuiFeeTime(java.util.Date tuiFeeTime) {
        this.tuiFeeTime = tuiFeeTime;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public String getTuiFeeReason() {
        return tuiFeeReason;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setTuiFeeReason(String tuiFeeReason) {
        this.tuiFeeReason = tuiFeeReason;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public String getZdPostFeeBuWorder() {
        return zdPostFeeBuWorder;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setZdPostFeeBuWorder(String zdPostFeeBuWorder) {
        this.zdPostFeeBuWorder = zdPostFeeBuWorder;
    }
	/**
  	*
	*get方法
  	*过时
 	*自动生成
  	*/
    public java.util.Date getZdPostFeeBuTime() {
        return zdPostFeeBuTime;
    }

    /**
  	*
  	*set方法
	*过时
 	*自动生成
  	*/
    public void setZdPostFeeBuTime(java.util.Date zdPostFeeBuTime) {
        this.zdPostFeeBuTime = zdPostFeeBuTime;
    }
	/**
  	*
	*get方法
  	*是否同意档口服务状态@0不同意1同意
 	*自动生成
  	*/
    public Integer getAgreeStoreService() {
        return agreeStoreService;
    }

    /**
  	*
  	*set方法
	*是否同意档口服务状态@0不同意1同意
 	*自动生成
  	*/
    public void setAgreeStoreService(Integer agreeStoreService) {
        this.agreeStoreService = agreeStoreService;
    }
	/**
  	*
	*get方法
  	*交易编号（未用）
 	*自动生成
  	*/
    public String getTradeCode() {
        return tradeCode;
    }

    /**
  	*
  	*set方法
	*交易编号（未用）
 	*自动生成
  	*/
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }
	/**
  	*
	*get方法
  	*收货人QQ
 	*自动生成
  	*/
    public String getReceiverQq() {
        return receiverQq;
    }

    /**
  	*
  	*set方法
	*收货人QQ
 	*自动生成
  	*/
    public void setReceiverQq(String receiverQq) {
        this.receiverQq = receiverQq;
    }
	/**
  	*
	*get方法
  	*淘宝订单号tid
 	*自动生成
  	*/
    public Long getTaobaoTid() {
        return taobaoTid;
    }

    /**
  	*
  	*set方法
	*淘宝订单号tid
 	*自动生成
  	*/
    public void setTaobaoTid(Long taobaoTid) {
        this.taobaoTid = taobaoTid;
    }
	/**
  	*
	*get方法
  	*下拉订单的淘宝账户的昵称
 	*自动生成
  	*/
    public String getTaobaoUserNick() {
        return taobaoUserNick;
    }

    /**
  	*
  	*set方法
	*下拉订单的淘宝账户的昵称
 	*自动生成
  	*/
    public void setTaobaoUserNick(String taobaoUserNick) {
        this.taobaoUserNick = taobaoUserNick;
    }
	/**
  	*
	*get方法
  	*是否已经导出过excel1为是0为否
 	*自动生成
  	*/
    public Integer getIsExport() {
        return isExport;
    }

    /**
  	*
  	*set方法
	*是否已经导出过excel1为是0为否
 	*自动生成
  	*/
    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }
	/**
  	*
	*get方法
  	*退款原因
 	*自动生成
  	*/
    public String getRefundReason() {
        return refundReason;
    }

    /**
  	*
  	*set方法
	*退款原因
 	*自动生成
  	*/
    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
	/**
  	*
	*get方法
  	*退货原因
 	*自动生成
  	*/
    public String getReturnGoodsReason() {
        return returnGoodsReason;
    }

    /**
  	*
  	*set方法
	*退货原因
 	*自动生成
  	*/
    public void setReturnGoodsReason(String returnGoodsReason) {
        this.returnGoodsReason = returnGoodsReason;
    }
	/**
  	*
	*get方法
  	*1拿到部分发货0全部退货
 	*自动生成
  	*/
    public String getRemark1() {
        return remark1;
    }

    /**
  	*
  	*set方法
	*1拿到部分发货0全部退货
 	*自动生成
  	*/
    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getRemark2() {
        return remark2;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
	/**
  	*
	*get方法
  	*买家留言
 	*自动生成
  	*/
    public String getRemark3() {
        return remark3;
    }

    /**
  	*
  	*set方法
	*买家留言
 	*自动生成
  	*/
    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
	/**
  	*
	*get方法
  	*表格拿货匹配上null 未匹配上0
 	*自动生成
  	*/
    public String getRemark4() {
        return remark4;
    }

    /**
  	*
  	*set方法
	*表格拿货匹配上null 未匹配上0
 	*自动生成
  	*/
    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getRemark5() {
        return remark5;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setRemark5(String remark5) {
        this.remark5 = remark5;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getRemark6() {
        return remark6;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setRemark6(String remark6) {
        this.remark6 = remark6;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getRemark7() {
        return remark7;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setRemark7(String remark7) {
        this.remark7 = remark7;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getRemark8() {
        return remark8;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setRemark8(String remark8) {
        this.remark8 = remark8;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getRemark9() {
        return remark9;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setRemark9(String remark9) {
        this.remark9 = remark9;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getRemark10() {
        return remark10;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setRemark10(String remark10) {
        this.remark10 = remark10;
    }
	/**
  	*
	*get方法
  	*多订单ID
 	*自动生成
  	*/
    public String getMoreTradeId() {
        return moreTradeId;
    }

    /**
  	*
  	*set方法
	*多订单ID
 	*自动生成
  	*/
    public void setMoreTradeId(String moreTradeId) {
        this.moreTradeId = moreTradeId;
    }
	/**
  	*
	*get方法
  	*结算状态：0结算错误可再次发起1已发起结算2已结算
 	*自动生成
  	*/
    public Long getSettingAccountsStatus() {
        return settingAccountsStatus;
    }

    /**
  	*
  	*set方法
	*结算状态：0结算错误可再次发起1已发起结算2已结算
 	*自动生成
  	*/
    public void setSettingAccountsStatus(Long settingAccountsStatus) {
        this.settingAccountsStatus = settingAccountsStatus;
    }
	/**
  	*
	*get方法
  	*
 	*自动生成
  	*/
    public String getDfTradeRemark1() {
        return dfTradeRemark1;
    }

    /**
  	*
  	*set方法
	*
 	*自动生成
  	*/
    public void setDfTradeRemark1(String dfTradeRemark1) {
        this.dfTradeRemark1 = dfTradeRemark1;
    }
	/**
  	* 
	*get方法
  	*缺货时间
 	*自动生成
  	*/
    public java.util.Date getOutOfStockTime() {
        return outOfStockTime;
    }

    /**
  	* 
  	*set方法
	*缺货时间
 	*自动生成
  	*/
    public void setOutOfStockTime(java.util.Date outOfStockTime) {
        this.outOfStockTime = outOfStockTime;
    }


}
