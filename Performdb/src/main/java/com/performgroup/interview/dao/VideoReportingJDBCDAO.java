package com.performgroup.interview.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.performgroup.interview.dao.utils.ResultSetMapper;
import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.pojo.VideoReportingPOJO;

/**
 * The JDBC implementation of the reporting DAO. Needs to remain JDBC driven for
 * the purpose of this task (as opposed to Hibernate).
 */
public class VideoReportingJDBCDAO<T> implements VideoReportingDAO {

	private static transient final Logger LOGGER = Logger.getLogger(VideoReportingJDBCDAO.class);

	@Autowired
	private SingleConnectionDataSource singleConnectionDataSource; // use to get
																	// database
																	// credentials

	private String dbUrl;
	private String dbUser;
	private String dbPass;

	@PostConstruct
	public void init() {
		this.dbUrl = singleConnectionDataSource.getUrl();
		this.dbUser = singleConnectionDataSource.getUsername();
		this.dbPass = singleConnectionDataSource.getPassword();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.performgroup.interview.dao.VideoReportingDAO#countByDay()
	 */
	public Collection<VideoReportingBean> countByDay() {
		List<VideoReportingBean> videoReportingBean = new ArrayList<VideoReportingBean>();
		List<VideoReportingPOJO> resultList = (List<VideoReportingPOJO>) getDataFromDB(
				"SELECT count(id) as film_number, 'Number of videos created on ' || creation_date as description FROM video GROUP BY creation_date",
				VideoReportingPOJO.class);

		if (resultList != null) {
			for (VideoReportingPOJO vrBean : resultList) {
				videoReportingBean.add(new VideoReportingBean(vrBean.getCount(), vrBean.getDescription()));
			}
		}

		return videoReportingBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.performgroup.interview.dao.VideoReportingDAO#countByVideoType()
	 */
	public Collection<VideoReportingBean> countByVideoType() {
		List<VideoReportingBean> videoReportingBean = new ArrayList<VideoReportingBean>();
		List<VideoReportingPOJO> resultList = (List<VideoReportingPOJO>) getDataFromDB(
				"SELECT count(id) as film_number, 'Number of type film ' || video_type as description FROM video GROUP BY video_type",
				VideoReportingPOJO.class);

		if (resultList != null) {
			for (VideoReportingPOJO vrBean : resultList) {
				videoReportingBean.add(new VideoReportingBean(vrBean.getCount(), vrBean.getDescription()));
			}
		}

		return videoReportingBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.performgroup.interview.dao.VideoReportingDAO#countForVideoType(com
	 * .performgroup.interview.domain.VideoType)
	 */
	public VideoReportingBean countForVideoType(VideoType videoType) {
		VideoReportingBean videoReportingBean = null;
		List<VideoReportingPOJO> resultList = (List<VideoReportingPOJO>) getDataFromDB(
				"SELECT count(id) as film_number, 'Number of type film ' || video_type as description FROM video WHERE video_type = '"
						+ videoType.toString() + "' GROUP BY video_type", VideoReportingPOJO.class);

		if (resultList != null) {
			videoReportingBean = new VideoReportingBean(resultList.get(0).getCount(), resultList.get(0).getDescription());
		}

		return videoReportingBean;
	}

	/**
	 * Get object list from database
	 * 
	 * @param sql
	 * @param outputClass
	 *            - return object class
	 * @return
	 */
	private List<T> getDataFromDB(String sql, Class outputClass) {
		Connection conn = null;
		Statement stmt = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
		List<T> pojoList = new ArrayList<T>();

		try {
			Class.forName("org.hsqldb.jdbcDriver");

			conn = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			pojoList = (List<T>) resultSetMapper.mapRersultSetToObject(rs, outputClass);

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} catch (Exception e) { // Handle errors for Class.forName
			LOGGER.error(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}// end finally try
		}// end try

		return pojoList;
	}
}
