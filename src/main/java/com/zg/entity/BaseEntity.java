package com.zg.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/*
* @author gez
* @version 0.1
*/

@MappedSuperclass
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8487110493140329200L;
	
	private String id;

	private Date createDate;
	
	private Date modifyDate;
	
	
	
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public int hashCode()
	{
		int hash = this.id == null ? System.identityHashCode(this) : this.id.hashCode();
		hash += (hash << 15) ^ 0xffffcd7d;
		hash ^= (hash >>> 10);
		hash += (hash << 3);
		hash ^= (hash >>> 6);
		hash += (hash << 2) + (hash << 14);
		hash ^= (hash >>> 16);
		return hash;
	}
	
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
		final BaseEntity other = (BaseEntity) obj;
		if(id == null) {
			if(other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
		
	}
	

}
