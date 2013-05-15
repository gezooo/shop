package com.zg.action.admin;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;



import com.zg.entity.Admin;
import com.zg.entity.Role;
import com.zg.service.AdminService;
import com.zg.service.ArticleService;
import com.zg.service.MemberService;
import com.zg.service.MessageService;
import com.zg.service.OrderService;
import com.zg.service.ProductService;
import com.zg.service.RoleService;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

@ParentPackage("admin")
public class AdminAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5708268950310517011L;
	
	public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";// Spring security 最后登录异常Session名称

	private String loginUsername;

	private Admin admin;

	private List<Role> allRole;

	private List<Role> roleList;

	@Resource
	private AdminService adminService;
	@Resource
	private RoleService roleService;
	@Resource
	private OrderService orderService;
	@Resource
	private MessageService messageService;
	@Resource
	private ProductService productService;
	@Resource
	private MemberService memberService;
	@Resource
	private ArticleService articleService;
	@Resource
	private ServletContext servletContext;
	
	public String login() {
		String error = getParameter("error");
		if (StringUtils.endsWithIgnoreCase(error, "captcha")) {
			//Locale loc = getLocale();
			//getText("key");
			addActionError("验证码错误,请重新输入!");
			return "login";
		}
		
		Exception springSecurityLastException = (Exception) getSession(SPRING_SECURITY_LAST_EXCEPTION);
		if(springSecurityLastException !=  null) {
			if(springSecurityLastException instanceof BadCredentialsException) {
				loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
				Admin admin = adminService.get("username", loginUsername);
				if (admin != null) {
					int loginFailureLockCount = getSystemConfig().getLoginFailureLockCount();
					int loginFailureCount = admin.getLoginFailureCount();
					if (getSystemConfig().isLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
						addActionError("若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
					} else {
						addActionError("您的用户名或密码错误!");
					}
				} else {
					addActionError("您的用户名或密码错误!");
				}

			} else if (springSecurityLastException instanceof DisabledException) {
				addActionError("您的账号已被禁用,无法登录!");
			} else if (springSecurityLastException instanceof LockedException) {
				addActionError("您的账号已被锁定,无法登录!");
			} else if (springSecurityLastException instanceof AccountExpiredException) {
				addActionError("您的账号已过期,无法登录!");
			} else {
				addActionError("出现未知错误,无法登录!");
			}
			getSession().remove(SPRING_SECURITY_LAST_EXCEPTION);

		}
		
		//TODO change or remove later
		String k = (String) servletContext.getAttribute("SHOPXX" + "_" + "KEY");
		if (!StringUtils.containsIgnoreCase(k, "shopxx")) {
			throw new ExceptionInInitializerError();
		}
		return "login";
	}
	
	// 后台主页面
		public String main() {
			return "main";
		}
		
		// 后台Header
		public String header() {
			return "header";
		}
		
		// 后台菜单
		public String menu() {
			return "menu";
		}
		
		// 后台中间(显示/隐藏菜单)
		public String middle() {
			return "middle";
		}
		
		// 后台首页
		public String index() {
			return "index";
		}
		
		// 是否已存在 ajax验证
		public String checkUsername() {
			String username = admin.getUsername();
			if (adminService.isExistByUsername(username)) {
				return ajaxText("false");
			} else {
				return ajaxText("true");
			}
		}
		
		// 添加
		public String add() {
			return INPUT;
		}

		// 编辑
		public String edit() {
			admin = adminService.load(id);
			return INPUT;
		}

		// 列表
		public String list() {
			pager = adminService.findByPager(pager);
			return LIST;
		}

		// 删除
		public String delete() {
			adminService.delete(ids);
			return ajaxJsonSuccessMessage("删除成功！");
		}
		
		// 保存
		@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
				@RequiredStringValidator(fieldName = "admin.password", message = "密码不允许为空!"),
				@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "admin.isAccountEnabled", message = "是否启用不允许为空!")
			},
			stringLengthFields = {
				@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
				@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!")
			},
			emails = {
				@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
			},
			regexFields = {
				@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
			}
		)
		@InputConfig(resultName = "error")
		public String save() {
			if (roleList == null || roleList.size() == 0) {
				return ERROR;
			}
			admin.setUsername(admin.getUsername().toLowerCase());
			admin.setLoginFailureCount(0);
			admin.setAccountLocked(false);
			admin.setAccountExpired(false);
			admin.setCredentialsExpired(false);
			admin.setRoleSet(new HashSet<Role>(roleList));
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			admin.setPassword(passwordMd5);
			adminService.save(admin);
			redirectionUrl = "admin!list.action";
			return SUCCESS;
		}
		
		// 更新
		@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
				@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "admin.isAccountEnabled", message = "是否启用不允许为空!")
			},
			stringLengthFields = {
				@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
				@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") },
			emails = {
				@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
			},
			regexFields = {
				@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
			}
		)
		@InputConfig(resultName = "error")
		public String update() {
			Admin persistent = adminService.load(id);
			if (roleList == null && roleList.size() == 0) {
				addActionError("请至少选择一个角色!");
				return ERROR;
			}
			admin.setRoleSet(new HashSet<Role>(roleList));
			if (StringUtils.isNotEmpty(admin.getPassword())) {
				String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
				persistent.setPassword(passwordMd5);
			}
			BeanUtils.copyProperties(admin, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp", "authorities"});
			adminService.update(persistent);
			redirectionUrl = "admin!list.action";
			return SUCCESS;
		}
		
		// 获取未处理订单数
		public Long getUnprocessedOrderCount() {
			return orderService.getUnprocessedOrderCount();
		}
		
		// 获取已支付未发货订单数
		public Long getPaidUnshippedOrderCount() {
			return orderService.getPaidUnshippedOrderCount();
		}
		
		// 获取未读消息数
		public Long getUnreadMessageCount() {
			return messageService.getUnreadMessageCount();
		}
		
		// 获取商品库存报警数
		public Long getStoreAlertCount() {
			return productService.getStoreAlertCount();
		}
		
		// 获取已上架商品数
		public Long getMarketableProductCount() {
			return productService.getMarketableProductCount();
		}
		
		// 获取已下架商品数
		public Long getUnMarketableProductCount() {
			return productService.getUnMarketableProductCount();
		}
		
		// 获取会员总数
		public Long getMemberTotalCount() {
			return memberService.getTotalCount();
		}
		
		// 获取文章总数
		public Long getArticleTotalCount() {
			return articleService.getTotalCount();
		}
		
		// freemarker静态方法调用
		public TemplateHashModel getStatics() {
			return BeansWrapper.getDefaultInstance().getStaticModels();
		}
		
		public String getLoginUsername() {
			return loginUsername;
		}

		public void setLoginUsername(String loginUsername) {
			this.loginUsername = loginUsername;
		}

		public Admin getAdmin() {
			return admin;
		}

		public void setAdmin(Admin admin) {
			this.admin = admin;
		}

		public List<Role> getAllRole() {
			allRole = roleService.getAll();
			return allRole;
		}

		public void setAllRole(List<Role> allRole) {
			this.allRole = allRole;
		}

		public List<Role> getRoleList() {
			return roleList;
		}

		public void setRoleList(List<Role> roleList) {
			this.roleList = roleList;
		}

}
