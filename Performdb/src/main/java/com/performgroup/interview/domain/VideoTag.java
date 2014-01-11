package com.performgroup.interview.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author Marcin
 *
 */
@Entity
@SequenceGenerator(name = "VIDEO_TAG_SEQ_GEN", sequenceName = "VIDEOTAGSEQ", allocationSize = 1)
public class VideoTag implements Serializable {

	private static final long serialVersionUID = 9159043646102340749L;

	private Long id;
	private String keyword;
	
	public VideoTag() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIDEO_TAG_SEQ_GEN")
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "keyword", unique = false, nullable = false)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
