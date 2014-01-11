package com.performgroup.interview.service.impl;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.performgroup.interview.dao.VideoDAO;
import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoTag;
import com.performgroup.interview.jaxb.dto.VideoDto;
import com.performgroup.interview.jaxb.utils.JaxbUtils;
import com.performgroup.interview.service.VideoService;

@SuppressWarnings("restriction")
public class VideoServiceImpl implements VideoService {
	
	private VideoDAO videoDAO;
	
	public VideoDAO getVideoDAO() {
		return videoDAO;
	}
	
	@Resource
	public void setVideoDAO(VideoDAO videoDAO) {
		this.videoDAO = videoDAO;
	}

	public void addVideo(Video video) {
		getVideoDAO().save(video);
	}

	public void deleteVideo(Video video) {
		getVideoDAO().delete(video);
	}

	public Video getVideo(Integer videoId) {
		return getVideoDAO().findById(videoId);
	}

	public Collection<Video> listVideos() {
		return getVideoDAO().findAll();
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.service.VideoService#readVideoFile(java.io.InputStream)
	 */
	public VideoDto readVideoFile(InputStream inputStream) throws JAXBException {
		Unmarshaller u = JaxbUtils.getUnmarshaller(VideoDto.class.getPackage().getName());
		VideoDto videoDto = (VideoDto)  u.unmarshal(new StreamSource(inputStream));
		return videoDto;
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.service.VideoService#listVideosByTags()
	 */
	public Map<String, List<Video>> listVideosByTags() {
		Map<String, List<Video>> videoMap = new HashMap<String, List<Video>>();
		
		List<VideoTag> videoTagList = (List<VideoTag>) videoDAO.findAllVideoTag();
		
		for(VideoTag videoTag : videoTagList){
			List<Video> videoList = (List<Video>) videoDAO.findAllByTags(videoTag);
			videoMap.put(videoTag.getKeyword(), videoList);
		}
		
		return videoMap;
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.service.VideoService#listVideoTag()
	 */
	public Collection<VideoTag> listVideoTag() {
		return videoDAO.findAllVideoTag();
	}

}
