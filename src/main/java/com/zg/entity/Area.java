package com.zg.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "parent_id"})})
public class Area extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7277608456210967248L;
	
	public static final String PATH_SEPARATOR = ",";
	
	private String name;
	
	private String path;
	
	private Area parent;
	
	private Set<Area> children;

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = true, length = 10000)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("name asc")
	public Set<Area> getChildren() {
		return children;
	}

	public void setChildren(Set<Area> children) {
		this.children = children;
	}
	
	
	/**
	 * Get the level of the area, the top level is 0
	 * @return the level of the area
	 */
	@Transient
	public Integer getLevel() {
		return this.path.split(PATH_SEPARATOR).length -1;
	}
	
	
	
	

}
