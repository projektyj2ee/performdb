package com.performgroup.interview.service;

import java.util.Collection;

import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;

public interface VideoReportingService {

	/**
	 * Returns a <code>VideoReportingBean</code> indicating the number of videos
	 * for the type passed in parameter, and the description of that type.
	 *  
	 * @param videoType
	 * @return a VideoReportingBean representing the count and description
	 */
	Collection<VideoReportingBean> getVideoReportCountByDay();
	
	/**
	 * Returns a breakdown of number of videos for each type.
	 * 
	 * @return a collection of VideoReportingBean representing the count and description for each type
	 */
	Collection<VideoReportingBean> getVideoReportCountByVideoType();
	
	/**
	 * Returns a breakdown of number of videos for type
	 * 
	 * @param videoType
	 * @return
	 */
	VideoReportingBean countForVideoType(VideoType videoType);
}
