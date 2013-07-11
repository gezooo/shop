package com.zg.dao.impl;

import com.zg.dao.FooterDao;
import com.zg.entity.Footer;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class FooterDaoImpl extends BaseDaoImpl<Footer, String> implements FooterDao {

	public Footer getFooter() {
		return load(Footer.FOOTER_ID);
	}

}
