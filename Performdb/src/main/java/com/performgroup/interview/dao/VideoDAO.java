package com.performgroup.interview.dao;

import java.util.Collection;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoTag;

/**
 * Data Access Object to access video information 
 */

public interface VideoDAO {
	
	/**
	 * Find all videos in the system
	 * 
	 * @return a collection of <code>Video</code>
	 */
	Collection<Video> findAll();
	
	/**
	 * Find all video tag in the system
	 * 
	 * @return
	 */
	Collection<VideoTag> findAllVideoTag();

	/**
	 * Returns a specific video
	 * 
	 * @param videoId
	 * @return a <code>Video</code> identified by the videoId
	 */
	Video findById(Integer videoId);
	
	/**
	 * Persists a <code>Video</code> object into the system
	 * 
	 * @param video the <code>Video</code> object to persist
	 */
	void save(Video video);
	
	/**
	 * Deletes a <code>Video</code> object from the system
	 * 
	 * @param video the <code>Video</code> object to delete
	 */
	void delete(Video video);
	
	
	Collection<Video> findAllByTags(VideoTag videoTag);
}