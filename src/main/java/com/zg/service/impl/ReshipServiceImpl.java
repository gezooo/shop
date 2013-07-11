package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.ReshipDao;
import com.zg.entity.Reship;
import com.zg.service.ReshipService;

/*
* @author gez
* @version 0.1
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