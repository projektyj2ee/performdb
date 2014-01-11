package com.performgroup.interview.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoTag;

public class VideoHibernateDAO extends HibernateDaoSupport implements VideoDAO {

	@SuppressWarnings("unchecked")
	public List<Video> findAll() {
		return getHibernateTemplate().loadAll(Video.class);
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.dao.VideoDAO#findById(java.lang.Integer)
	 */
	public Video findById(Integer videoId) {
		return (Video) getHibernateTemplate().load(Video.class, videoId);
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.dao.VideoDAO#delete(com.performgroup.interview.domain.Video)
	 */
	public void delete(Video video) {
		getHibernateTemplate().delete(video);
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.dao.VideoDAO#save(com.performgroup.interview.domain.Video)
	 */
	public void save(Video video) {
		getHibernateTemplate().save(video);
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.dao.VideoDAO#findAllVideoTag()
	 */
	public Collection<VideoTag> findAllVideoTag() {
		return getHibernateTemplate().loadAll(VideoTag.class);
	}

	/* (non-Javadoc)
	 * @see com.performgroup.interview.dao.VideoDAO#findAllByTags(com.performgroup.interview.domain.VideoTag)
	 */
	public Collection<Video> findAllByTags(VideoTag videoTag) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Video.class); 
		DetachedCriteria videoTagCriteria = criteria.createCriteria("videoTag");
		videoTagCriteria.add(Restrictions.eq("keyword", videoTag.getKeyword()));  
		videoTagCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  
		 
		return getHibernateTemplate().findByCriteria(criteria);
	}
}