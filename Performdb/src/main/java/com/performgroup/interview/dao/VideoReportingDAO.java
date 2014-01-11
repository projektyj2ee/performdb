package com.performgroup.interview.dao;

import java.util.Collection;

import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;

/**
 * A DAO to provide reporting information for videos.
 * 
 *  Should cover the following types of reporting:
 *  - Number of videos in the system for a specific VideoType
 *  - Number of videos created per day
 */
public interface VideoReportingDAO {
	
	/**
	 * Returns a <code>VideoReportingBean</code> indicating the number of videos
	 * for the type passed in parameter, and the description of that type.
	 *  
	 * @param videoType
	 * @return a VideoReportingBean representing the count and description
	 */
	VideoReportingBean countForVideoType(VideoType videoType);
	
	/**
	 * Returns a breakdown of number of videos for each type.
	 * 
	 * @return a collection of VideoReportingBean representing the count and description for each type
	 */
	Collection<VideoReportingBean> countByVideoType();
	
	/**
	 * Returns a breakdown of number of videos for each day
	 * 
	 * @return a collection of VideoReportingBean representing the count and description
	 * (dd/mm/yyyy) for each day we have videos available for
	 */
	Collection<VideoReportingBean> countByDay();
	
}
