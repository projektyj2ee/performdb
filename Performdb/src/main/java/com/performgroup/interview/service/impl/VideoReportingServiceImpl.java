package com.performgroup.interview.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.performgroup.interview.dao.VideoReportingDAO;
import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.service.VideoReportingService;

public class VideoReportingServiceImpl implements VideoReportingService {

	private VideoReportingDAO videoReportingDAO;

	@Resource
	public void setVideoReportingDAO(VideoReportingDAO videoReportingDAO) {
		this.videoReportingDAO = videoReportingDAO;
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.service.VideoReportingService#getVideoReportCountByDay()
	 */
	public Collection<VideoReportingBean> getVideoReportCountByDay() {
		return videoReportingDAO.countByDay();
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.service.VideoReportingService#getVideoReportCountByVideoType()
	 */
	public Collection<VideoReportingBean> getVideoReportCountByVideoType() {
		return videoReportingDAO.countByVideoType();
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.service.VideoReportingService#countForVideoType(com.performgroup.interview.domain.VideoType)
	 */
	public VideoReportingBean countForVideoType(VideoType videoType) {
		return videoReportingDAO.countForVideoType(videoType);
	}

}
