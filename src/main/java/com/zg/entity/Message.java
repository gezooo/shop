package com.zg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/*
* @author gez
* @version 0.1
*/

@Entity
public class Message extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4625046193312707824L;
	
	public static final int DEFAULT_MESSAGE_LIST_PAGE_SIZE = 12;
	
	public enum DeleteStatus {
		NON_DELETE, FROM_DELETE, TO_DELETE
	}
	
	private String title;
	
	private String content;
	
	private DeleteStatus deleteStatus;
	
	private Boolean isRead;
	
	private Boolean isSaveDraftbox;
	
	private Member fromMember; //if null, default is admin
	
	private Member toMember; //if null, default is admin

	
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(nullable = false, length = 10000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Enumerated
	@Column(nullable = false)
	public DeleteStatus getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(DeleteStatus deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	@Column(nullable = false)
	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@Column(nullable = false)
	public Boolean getIsSaveDraftbox() {
		return isSaveDraftbox;
	}

	public void setIsSaveDraftbox(Boolean isSaveDraftbox) {
		this.isSaveDraftbox = isSaveDraftbox;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getFromMember() {
		return fromMember;
	}

	public void setFromMember(Member fromMember) {
		this.fromMember = fromMember;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getToMember() {
		return toMember;
	}

	public void setToMember(Member toMember) {
		this.toMember = toMember;
	}
	
	
	

}
