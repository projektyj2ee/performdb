package com.performgroup.interview.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoTag;
import com.performgroup.interview.jaxb.dto.VideoDto;

/**
 * A service layer to retrieve video information
 */
public interface VideoService {

	Video getVideo(Integer videoId);

	Collection<Video> listVideos();

	void addVideo(Video video);

	void deleteVideo(Video video);

	/**
	 * Method which creates VideoDto objects from xml files
	 * 
	 * @param inputStream
	 * @return
	 * @throws JAXBException
	 */
	VideoDto readVideoFile(InputStream inputStream) throws JAXBException;
	
	/**
	 * Method which create vidoe list grouping by tags
	 * @return
	 */
	Map<String, List<Video>> listVideosByTags();
	
	/**
	 * Method which create video tag list 
	 * @return
	 */
	Collection<VideoTag> listVideoTag();
}
