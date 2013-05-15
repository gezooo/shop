package com.zg.common;


import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.InitializingBean;

public class HibernateSearchIndexBuilder implements InitializingBean {

	private int lazyTime = 30;// 索引操作线程延时，单位:秒
	private SessionFactory sessionFactory;

	private Thread indexThread = new Thread() {
		@Override
		public void run() {
			try {
				Thread.sleep(lazyTime * 1000);
				FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
				fullTextSession.createIndexer().startAndWait();
				sessionFactory.getCurrentSession().close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void afterPropertiesSet() throws Exception {
		indexThread.setDaemon(true);
		indexThread.setName("Hibernate Search Indexer");
		indexThread.start();		
	}
	
	

	public void setLazyTime(int lazyTime) {
		this.lazyTime = lazyTime;
	}

	/**
	 * Set the SessionFactory that this instance should manage transactions for.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Return the SessionFactory that this instance should manage transactions for.
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

}
