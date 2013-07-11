package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.CartItemDao;
import com.zg.entity.CartItem;
import com.zg.service.CartItemService;

/*
* @author gez
* @version 0.1
*/

@Service
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, String> implements CartItemService {

	@Resource
	public void setBaseDao(CartItemDao cartItemDao) {
		super.setBaseDao(cartItemDao);
	}

}