package com.zg.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.beans.TenpayConfig;
import com.zg.beans.TenpayConfig.TenpayType;
import com.zg.common.util.SystemConfigUtils;
import com.zg.dao.PaymentConfigDao;
import com.zg.entity.PaymentConfig;
import com.zg.service.PaymentConfigService;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@Service
public class PaymentConfigServiceImpl extends BaseServiceImpl<PaymentConfig, String> implements PaymentConfigService {

	@Resource
	PaymentConfigDao paymentConfigDao;
	
	@Resource
	public void setBaseDao(PaymentConfigDao PaymentConfigDao) {
		super.setBaseDao(PaymentConfigDao);
	}
	
	@Cacheable(value = "caching")
	public List<PaymentConfig> getNonDepositPaymentConfigList() {
		return paymentConfigDao.getNonDepositPaymentConfigList();
	}
	
	@Cacheable(value = "caching")
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
		return paymentConfigDao.getNonDepositOfflinePaymentConfigList();
	}
	
	// 根据商户号、支付编号生成财付通交易号
	public String buildTenpayTransactionId (String bargainorId, String paymentSn) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateString = simpleDateFormat.format(new Date());
		int count = 10 - paymentSn.length();
		if (count > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < count; i ++) {
				stringBuffer.append("0");
			}
			stringBuffer.append(paymentSn);
			paymentSn = stringBuffer.toString();
		} else {
			paymentSn = StringUtils.substring(paymentSn, count);
		}
		return bargainorId + dateString + paymentSn;
	}
	
	public String buildParameterString(Map<String, String> parameterMap) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String key : parameterMap.keySet()) {
			String value = parameterMap.get(key);
			if (StringUtils.isNotEmpty(value)) {
				stringBuffer.append("&" + key + "=" + value);
			}
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}
	
	public String buildTenpayDirectPaymentUrl(PaymentConfig paymentConfig, String paymentSn, BigDecimal totalAmount, String description, String ip) {
		TenpayConfig tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
		String transactionId = buildTenpayTransactionId(tenpayConfig.getBargainorId(), paymentSn);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateString = simpleDateFormat.format(new Date());
		String totalAmountString = totalAmount.multiply(new BigDecimal("100")).setScale(0, RoundingMode.UP).toString();
		String data = null;
		try {
			if (StringUtils.isNotEmpty(description)) {
				description = URLEncoder.encode(description, "GB2312");
				BASE64Decoder bASE64Decoder = new BASE64Decoder();
				data = new String(bASE64Decoder.decodeBuffer("c2hvcC16Zw=="));
			} else {
				description = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String cmdno = "1";// 业务代码（1：即时交易支付）
		String date = dateString;// 支付日期
		String bank_type = "0";// 银行类型（0：财付通）
		String desc = description;// 交易描述
		String purchaser_id = "";// 客户财付通帐户
		String bargainor_id = tenpayConfig.getBargainorId();// 商户号
		String transaction_id = transactionId;// 交易号
		String sp_billno = paymentSn;// 支付编号
		String total_fee = totalAmountString;// 总金额（单位：分）
		String fee_type = "1";// 支付币种（1：人民币）
		String return_url = SystemConfigUtils.getSystemConfig().getShopUrl() + TenpayConfig.RETURN_URL;// 结果处理URL
		String attach = data;// 商户数据
		String spbill_create_ip = ip;// 客户IP
		String key = tenpayConfig.getKey();// 密钥
		
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("cmdno", cmdno);
		parameterMap.put("date", date);
		parameterMap.put("bargainor_id", bargainor_id);
		parameterMap.put("transaction_id", transaction_id);
		parameterMap.put("sp_billno", sp_billno);
		parameterMap.put("total_fee", total_fee);
		parameterMap.put("fee_type", fee_type);
		parameterMap.put("return_url", return_url);
		parameterMap.put("attach", attach);
		parameterMap.put("spbill_create_ip", spbill_create_ip);
		parameterMap.put("key", key);

		// 生成签名
		String sign = DigestUtils.md5Hex(buildParameterString(parameterMap)).toUpperCase();
		
		parameterMap.put("bank_type", bank_type);
		parameterMap.put("desc", desc);
		parameterMap.put("purchaser_id", purchaser_id);
		parameterMap.put("sign", sign);
		parameterMap.remove("key");
		
		// 生成参数字符串
		String parameterString = buildParameterString(parameterMap);
		
		return TenpayConfig.PAYMENT_URL + "?" + parameterString;
	}
	
	public String buildTenpayDirectQueryUrl(PaymentConfig paymentConfig, String transactionId, String paymentSn) {
		TenpayConfig tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateString = simpleDateFormat.format(new Date());
		
		String data = null;
		try {
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
			data = new String(bASE64Decoder.decodeBuffer("c2hvcC16Zw=="));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String cmdno = "2";// 业务代码（2：即时交易查询）
		String date = dateString;// 日期
		String bargainor_id = tenpayConfig.getBargainorId();// 商户号
		String transaction_id = transactionId;// 交易号
		String sp_billno = paymentSn;// 支付编号
		String attach = data;// 商户数据
		String output_xml = "1";// 输出类型
		String charset = "UTF-8";// 编码
		String key = tenpayConfig.getKey();// 密钥
		
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("attach", attach);
		parameterMap.put("bargainor_id", bargainor_id);
		parameterMap.put("charset", charset);
		parameterMap.put("cmdno", cmdno);
		parameterMap.put("date", date);
		parameterMap.put("output_xml", output_xml);
		parameterMap.put("sp_billno", sp_billno);
		parameterMap.put("transaction_id", transaction_id);
		parameterMap.put("key", key);
		
		// 生成签名
		String sign = DigestUtils.md5Hex(buildParameterString(parameterMap)).toUpperCase();
		
		parameterMap.put("sign", sign);
		parameterMap.remove("key");
		
		// 生成参数字符串
		String parameterString = buildParameterString(parameterMap);
		
		return TenpayConfig.QUERY_URL + "?" + parameterString;
	}
	
	public String buildTenpayPartnerPaymentUrl(PaymentConfig paymentConfig, String paymentSn, BigDecimal totalAmount, String description) {
		TenpayConfig tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
		String totalAmountString = totalAmount.multiply(new BigDecimal("100")).setScale(0, RoundingMode.UP).toString();
		String mchType = null;
		if (tenpayConfig.getTenpayType() != TenpayType.partnerMaterial) {
			mchType = "1";
		} else {
			mchType = "2";
		}
		String data = null;
		try {
			if (StringUtils.isNotEmpty(description)) {
				description = URLEncoder.encode(description, "GB2312");
				BASE64Decoder bASE64Decoder = new BASE64Decoder();
				data = new String(bASE64Decoder.decodeBuffer("c2hvcC16Zw=="));
			} else {
				description = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String version = "2";// 版本号
		String cmdno = "12";// 业务代码（12：担保交易支付）
		String encode_type = "2";// 编码方式（1：GB2312编码；2：UTF-8编码）
		String chnid = tenpayConfig.getBargainorId();// 收款方财付通账号
		String seller = tenpayConfig.getBargainorId();// 收款方财付通账号
		String mch_name = "";// 商品名称
		String mch_price = totalAmountString;// 商品价格（单位：分）
		String transport_desc = "";// 物流公司或物流方式描述
		String transport_fee = "0";// 物流费用（单位：分）
		String mch_desc = description;// 交易描述
		String need_buyerinfo = "2";// 是否需要填写物流信息（1：需要；2：不需要）
		String mch_type = mchType;// 交易类型（1、实物交易；2、虚拟交易）
		String mch_vno = paymentSn;// 支付编号
		String mch_returl = SystemConfigUtils.getSystemConfig().getShopUrl() + TenpayConfig.RETURN_URL;// 结果处理URL
		String show_url = SystemConfigUtils.getSystemConfig().getShopUrl() + TenpayConfig.RETURN_URL;// 结果展示URL
		String attach = data;// 商户数据
		String key = tenpayConfig.getKey();// 密钥
		
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("attach", attach);
		parameterMap.put("chnid", chnid);
		parameterMap.put("cmdno", cmdno);
		parameterMap.put("encode_type", encode_type);
		parameterMap.put("key", key);
		parameterMap.put("mch_desc", mch_desc);
		parameterMap.put("mch_name", mch_name);
		parameterMap.put("mch_price", mch_price);
		parameterMap.put("mch_returl", mch_returl);
		parameterMap.put("mch_type", mch_type);
		parameterMap.put("mch_vno", mch_vno);
		parameterMap.put("need_buyerinfo", need_buyerinfo);
		parameterMap.put("seller", seller);
		parameterMap.put("show_url", show_url);
		parameterMap.put("transport_desc", transport_desc);
		parameterMap.put("transport_fee", transport_fee);
		parameterMap.put("version", version);
		
		// 生成签名
		String sign = DigestUtils.md5Hex(buildParameterString(parameterMap)).toUpperCase();
		
		parameterMap.put("sign", sign);
		parameterMap.remove("key");
		
		// 生成参数字符串
		String parameterString = buildParameterString(parameterMap);
		
		return TenpayConfig.PAYMENT_URL + "?" + parameterString;
	}
	
	@Override
	@Cacheable(value = "caching")
	public List<PaymentConfig> getAll() {
		return paymentConfigDao.getAll();
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(PaymentConfig paymentConfig) {
		paymentConfigDao.delete(paymentConfig);
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(String id) {
		paymentConfigDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(String[] ids) {
		paymentConfigDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching")
	public String save(PaymentConfig paymentConfig) {
		return paymentConfigDao.save(paymentConfig);
	}

	@Override
	@CacheEvict(value = "caching")
	public void update(PaymentConfig paymentConfig) {
		paymentConfigDao.update(paymentConfig);
	}
}
