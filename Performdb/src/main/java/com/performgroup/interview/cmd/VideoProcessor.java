package com.performgroup.interview.cmd;

import java.io.Console;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoTag;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.jaxb.dto.VideoDto;
import com.performgroup.interview.service.VideoReportingService;
import com.performgroup.interview.service.VideoService;

public class VideoProcessor {

	private transient VideoService videoService;

	private transient String videoIngestFolder;

	private transient VideoReportingService videoReportingService;

	private Map<Integer, String> videoFileList = new HashMap<Integer, String>();

	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	public void setVideoIngestFolder(String videoIngestFolder) {
		this.videoIngestFolder = videoIngestFolder;
	}

	@Resource
	public void setVideoReportingService(VideoReportingService videoReportingService) {
		this.videoReportingService = videoReportingService;
	}

	/**
	 * Outputs video details to the specified logger
	 * 
	 * @param logger
	 */
	public void listVideos(Logger logger, String option) {
		if ("exit".equalsIgnoreCase(option)) {
			System.exit(0);
		}

		try {
			Integer reportOption = new Integer(option);
			if (reportOption < 1 || reportOption > 3) {
				throw new NumberFormatException("Wrong option!");
			}

			switch (reportOption) {
			case 1:
				Collection<Video> videos = videoService.listVideos();
				for (Video video : videos) {
					String videoData = String.format(" [%d] -	Title: %s \n	Type: %s \n	Video path: %s", video.getId(), video.getTitle(),
							video.getVideoType(), video.getVideoPath());
					logger.info(videoData);
					for (VideoTag videoTag : video.getVideoTag()) {
						String videoTagData = String.format("	 Tags: [%d] - %s", videoTag.getId(), videoTag.getKeyword());
						logger.info(videoTagData);
					}// end for
					logger.info("\n");
				}// end for
				break;
			case 2:
				Map<String, List<Video>> videoMap = videoService.listVideosByTags();
				
				for(String tag : videoMap.keySet()){
					System.out.println("Tag: "+tag+" -> ");
					for(Video video : videoMap.get(tag)){
						String videoData = String.format(" [%d] -	Title: %s \n	Type: %s \n	Video path: %s\n", video.getId(), video.getTitle(),
								video.getVideoType(), video.getVideoPath());
						logger.info(videoData);
					}
				}
				
				break;
			case 3:
				Collection<VideoTag> videoTagList = videoService.listVideoTag();
				for (VideoTag videoTag : videoTagList) {
					String videoData = String.format(" [%d] -	Tag: %s \n", videoTag.getId(), videoTag.getKeyword());
					logger.info(videoData);
				}// end for
				break;
			}

		} catch (NumberFormatException e) {
			System.out.println("Wrong option! Please choose option one of [1,2,3]");
			System.out.print(">");
			reportsVideos(logger, System.console().readLine());
		}
	}

	/**
	 * Processes a video by ingesting data from XML
	 * 
	 * @param logger
	 * @param videoDataFileIndex
	 */
	public void ingestVideo(Logger logger, String videoDataFileIndex) {
		if ("exit".equalsIgnoreCase(videoDataFileIndex)) {
			System.exit(0);
		}

		String videoFile = this.videoFileList.get(convertVideoDataFileIndex(videoDataFileIndex));

		if (videoFile == null) {
			logger.error("Wrong file index! Please choose option one of " + getVideoFileIndexesInfo());
			System.out.print(">");
			Console c = System.console();
			String optionValue = c.readLine();
			ingestVideo(logger, optionValue);
			return;
		}

		String path = videoIngestFolder + videoFile;

		InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);

		if (in == null) {
			logger.info("Cannot find the file");
		} else {
			System.out.println("Selected file: " + path);
			try {
				VideoDto videoDto = this.videoService.readVideoFile(in);
				this.videoService.addVideo(this.convertVidoeDtoToVideo(videoDto));

				System.out.println("Path: " + videoDto.getPath());
				System.out.println("Title: " + videoDto.getTitle());
				System.out.println("Type: " + videoDto.getType());
			} catch (JAXBException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * Displays a list of xml files in classpath
	 * 
	 * @param logger
	 * @param folder
	 */
	public void listFilesForFolder(Logger logger, File folder) {

		if (folder == null) {
			CodeSource src = VideoProcessor.class.getProtectionDomain().getCodeSource();
			if (src != null) {
				URL jarPath = src.getLocation();
				folder = new File(jarPath.getPath() + "/" + videoIngestFolder);

				Integer index = 1;
				for (File fileEntry : folder.listFiles()) {
					if (fileEntry.isDirectory()) {
						listFilesForFolder(logger, fileEntry);
					} else {
						System.out.println(index + ". " + fileEntry.getName());
						this.videoFileList.put(index, fileEntry.getName());
						index++;
					}
				}// end for

			} else {
				logger.error("Cannot read class path");
			}// end if
		}
	}

	/**
	 * Return the index of video file
	 * 
	 * @return
	 */
	public String getVideoFileIndexesInfo() {
		String indexVideoValue = "[";
		Integer indexCount = this.videoFileList.keySet().size();
		for (Integer index : this.videoFileList.keySet()) {
			indexVideoValue = indexVideoValue + index;
			if (index < indexCount) {
				indexVideoValue = indexVideoValue + ",";
			}
		}
		indexVideoValue = indexVideoValue + "]";
		return indexVideoValue;
	}

	/**
	 * Valideate input index
	 * 
	 * @param videoDataFileIndex
	 * @return
	 */
	private Integer convertVideoDataFileIndex(String videoDataFileIndex) {
		try {
			return new Integer(videoDataFileIndex);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Convert VideoDto object (XML) to Video (Entity) - We can use reflection
	 * 
	 * @param videoDto
	 * @return
	 */
	private Video convertVidoeDtoToVideo(VideoDto videoDto) {
		Video video = new Video();
		video.setTitle(videoDto.getTitle());
		video.setVideoPath(videoDto.getPath());
		video.setVideoType(VideoType.valueOf(videoDto.getType()));
		video.setCreationDate(new Date());

		List<VideoTag> videoTagList = new ArrayList<VideoTag>();
		for (String keyword : videoDto.getKeywordSet().getKeyword()) {
			VideoTag videoTag = new VideoTag();
			videoTag.setKeyword(keyword);
			videoTagList.add(videoTag);
		}

		video.setVideoTag(videoTagList);
		return video;
	}

	/**
	 * Created video reports statistics - using JDBC connection
	 * 
	 * @param logger
	 * @param option
	 */
	public void reportsVideos(Logger logger, String option) {
		if ("exit".equalsIgnoreCase(option)) {
			System.exit(0);
		}

		try {
			Integer reportOption = new Integer(option);
			if (reportOption < 1 || reportOption > 3) {
				throw new NumberFormatException("Wrong option!");
			}

			switch (reportOption) {
			case 1:
				List<VideoReportingBean> vrBeanList = (List<VideoReportingBean>) videoReportingService
						.getVideoReportCountByDay();
				for (VideoReportingBean vrBean : vrBeanList) {
					System.out.println("- " + vrBean.getDescription() + ": " + vrBean.getCount());
				}
				break;
			case 2:
				List<VideoReportingBean> vrBeanTypeList = (List<VideoReportingBean>) videoReportingService
						.getVideoReportCountByVideoType();
				for (VideoReportingBean vrBean : vrBeanTypeList) {
					System.out.println("- " + vrBean.getDescription() + ": " + vrBean.getCount());
				}
				break;
			case 3:

				System.out.println("Enter the type of video: ");
				System.out.print(">");
				Console c = System.console();
				String videoTypeInput = c.readLine();
				VideoType videoType = null;

				try {
					videoType = VideoType.valueOf(videoTypeInput);
				} catch (Exception e) {
					System.out.println("Wrong video type!");
					break;
				}

				VideoReportingBean videoReportingBean = videoReportingService.countForVideoType(videoType);
				if (videoReportingBean == null) {
					System.out.println("No videos typeL " + VideoType.FRUIT_MATCH.toString());
				} else {
					System.out.println("- " + videoReportingBean.getDescription() + ": "
							+ videoReportingBean.getCount());
				}
				break;
			}

		} catch (NumberFormatException e) {
			System.out.println("Wrong option! Please choose option one of [1,2,3]");
			System.out.print(">");
			reportsVideos(logger, System.console().readLine());
		}
	}

	/**
	 * Reporting options
	 * 
	 * @return
	 */
	public String getReportingOption() {
		StringBuilder sb = new StringBuilder();
		sb.append("Select the report:\n");
		sb.append("1. Number of videos created on a day\n");
		sb.append("2. Number of videos by Type\n");
		sb.append("3. Number of videos by selected type\n");
		sb.append("Please choose option one of [1,2,3]");
		return sb.toString();
	}

	/**
	 * Listing options
	 * 
	 * @return
	 */
	public String getListingOption() {
		StringBuilder sb = new StringBuilder();
		sb.append("Select the report:\n");
		sb.append("1. List videos in the system with tags\n");
		sb.append("2. List videos in the system grouping by tags\n");
		sb.append("3. View a list of tags in the system\n");
		sb.append("Please choose option one of [1,2,3]");
		return sb.toString();
	}

}
