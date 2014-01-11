package com.performgroup.interview.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class VideoReportingPOJO {

	@Column(name = "film_number")
	private Integer count;

	@Column(name = "description")
	private String description;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
