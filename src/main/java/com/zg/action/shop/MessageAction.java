package com.zg.action.shop;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.zg.entity.Member;
import com.zg.entity.Message;
import com.zg.entity.Message.DeleteStatus;
import com.zg.service.MemberService;
import com.zg.service.MessageService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 消息
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX5FA278C609EB7D244458FEEED904803E
 * ============================================================================
 */

@ParentPackage("member")
public class MessageAction extends BaseShopAction {

	private static final long serialVersionUID = 3248218706961305882L;

	private Message message;
	private String toMemberUsername;

	@Resource
	private MessageService messageService;
	@Resource
	private MemberService memberService;
	
	// 检查用户名是否存在
	public String checkUsername() {
		String value = getParameter("toMemberUsername");
		if (memberService.isExist("username", value)) {
			ajaxText("true");
		} else {
			ajaxText("false");
		}
		return null;
	}

	// 发送消息
	public String send() {
		if (StringUtils.isNotEmpty(id)) {
			message = messageService.load(id);
			if (message.getIsSaveDraftbox() == false || message.getFromMember() != getLoginMember()) {
				addActionError("参数错误!");
				return ERROR;
			}
		}
		return "send";
	}
	
	// 回复
	public String reply() {
		message = messageService.load(id);
		if (message.getToMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		return "reply";
	}
	
	// 保存消息
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "message.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "message.content", message = "消息内容不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "message.content", maxLength = "10000", message = "消息内容长度超出限制！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "message.isSaveDraftbox", message = "是否保存草稿箱不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(toMemberUsername)) {
			Member toMember = memberService.getMemberByUsername(toMemberUsername);
			if (toMember == null) {
				addActionError("收件人不存在!");
				return ERROR;
			}
			if (toMember == getLoginMember()) {
				addActionError("收件人不允许为自己!");
				return ERROR;
			}
			message.setToMember(toMember);
		} else {
			message.setToMember(null);
		}
		message.setFromMember(getLoginMember());
		message.setDeleteStatus(DeleteStatus.NON_DELETE);
		message.setIsRead(false);
		
		if (StringUtils.isNotEmpty(id)) {
			Message persistent = messageService.load(id);
			if (persistent.getIsSaveDraftbox() == false || persistent.getFromMember() != getLoginMember()) {
				addActionError("参数错误!");
				return ERROR;
			}
			BeanUtils.copyProperties(message, persistent, new String[] {"id", "createDate", "modifyDate"});
			messageService.update(persistent);
		} else {
			messageService.save(message);
		}
		if (message.getIsSaveDraftbox()) {
			redirectionUrl = "message!draftbox.action";
		} else {
			redirectionUrl = "message!outbox.action";
		}
		return SUCCESS;
	}
	
	// 收件箱
	public String inbox() {
		pager = messageService.getMemberInboxPager(getLoginMember(), pager);
		return "inbox";
	}

	// 发件箱
	public String outbox() {
		pager = messageService.getMemberOutboxPager(getLoginMember(), pager);
		return "outbox";
	}

	// 草稿箱
	public String draftbox() {
		pager = messageService.getMemberDraftboxPager(getLoginMember(), pager);
		return "draftbox";
	}

	// 删除
	public String delete() {
		Message message = messageService.load(id);
		if (message.getIsSaveDraftbox()) {
			if (message.getFromMember() == getLoginMember()) {
				messageService.delete(message);
				redirectionUrl = "message!draftbox.action";
			}
		} else {
			if (message.getToMember() != null && message.getToMember() == getLoginMember()) {
				if (message.getDeleteStatus() == DeleteStatus.NON_DELETE) {
					message.setDeleteStatus(DeleteStatus.TO_DELETE);
					messageService.update(message);
				} else if (message.getDeleteStatus() == DeleteStatus.FROM_DELETE) {
					messageService.delete(message);
				}
				redirectionUrl = "message!inbox.action";
			} else if (message.getFromMember() != null && message.getFromMember() == getLoginMember()) {
				if (message.getDeleteStatus() == DeleteStatus.NON_DELETE) {
					message.setDeleteStatus(DeleteStatus.FROM_DELETE);
					messageService.update(message);
				} else if (message.getDeleteStatus() == DeleteStatus.TO_DELETE) {
					messageService.delete(message);
				}
				redirectionUrl = "message!outbox.action";
			}
		}
		return SUCCESS;
	}

	// AJAX获取消息内容
	public String ajaxMessageContent() {
		Message message = messageService.load(id);
		if (message.getToMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		if (!message.getIsRead()) {
			message.setIsRead(true);
			messageService.update(message);
		}
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(CONTENT, message.getContent());
		return ajaxJson(jsonMap);
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getToMemberUsername() {
		return toMemberUsername;
	}

	public void setToMemberUsername(String toMemberUsername) {
		this.toMemberUsername = toMemberUsername;
	}

}