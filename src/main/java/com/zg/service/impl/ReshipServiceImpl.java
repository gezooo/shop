package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.ReshipDao;
import com.zg.entity.Reship;
import com.zg.service.ReshipService;

/**
 * Service实现类 - 退货
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX14093C23520A786815DFA8C679BCDD5F
 * ============================================================================
 */

@Service
public class ReshipServiceImpl extends BaseServiceImpl<Reship, String> implements ReshipService {
	
	@Resource
	private ReshipDao reshipDao;

	@Resource
	public void setBaseDao(ReshipDao reshipDao) {
		super.setBaseDao(reshipDao);
	}
	
	public String getLastReshipSn() {
		return reshipDao.getLastReshipSn();
	}

	// 重写对象，保存时自动设置退货编号
	@Override
	public String save(Reship reship) {
		reship.setReshipSn(SerialNumberUtils.buildReshipSn());
		return super.save(reship);
	}

}