package com.zg.action.shop;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Member;
import com.zg.entity.Product;
import com.zg.service.MemberService;
import com.zg.service.ProductService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 收藏夹
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX061C8723F97757E959E12F6FB73D83BF
 * ============================================================================
 */

@ParentPackage("member")
@Results( {
	@Result(name = "memberCenterIndexAction", location = "member_center!index.action", type = "redirect") 
	}
)
public class FavoriteAction extends BaseShopAction {

	private static final long serialVersionUID = 6297956848773319710L;
	
	private static final Integer pageSize = 10;// 商品收藏每页显示数
	
	private Product product;

	@Resource
	private MemberService memberService;
	@Resource
	private ProductService productService;

	// 商品收藏列表
	public String list() {
		Member loginMember = getLoginMember();
		if (pager == null) {
			pager = new Pager();
		}
		pager.setPageSize(pageSize);
		pager = productService.getFavoriteProductPager(loginMember, pager);
		return LIST;
	}

	// 添加收藏商品
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String ajaxAdd() {
		product = productService.load(id);
		if (!product.getIsMarketable()) {
			return ajaxJsonErrorMessage("此商品已下架!");
		}
		Member loginMember = getLoginMember();
		Set<Product> favoriteProductSet = (loginMember.getFavoriteProductSet() == null ? new HashSet<Product>() : loginMember.getFavoriteProductSet());
		if (favoriteProductSet.contains(product)) {
			return ajaxJsonWarnMessage("您已经收藏过此商品!");
		} else {
			favoriteProductSet.add(product);
			memberService.update(loginMember);
			return ajaxJsonSuccessMessage("商品收藏成功!");
		}
	}

	// 删除收藏商品
	public String delete () {
		product = productService.load(id);
		Member loginMember = getLoginMember();
		Set<Product> favoriteProductSet = loginMember.getFavoriteProductSet();
		if (!favoriteProductSet.contains(product)) {
			addActionError("参数错误!");
			return ERROR;
		}
		favoriteProductSet.remove(product);
		memberService.update(loginMember);
		redirectionUrl = "favorite!list.action";
		return SUCCESS;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}