package com.zg.search;

import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.zg.entity.Article;

public class HibernateSearchTemplate {
	
	@Resource
	private SessionFactory sessionFactory;
	
	private FullTextSession fullTextSession;

	
	public HibernateSearchTemplate() {
		
	}
	
	
	public HibernateSearchTemplate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Set the Hibernate SessionFactory that should be used to create
	 * Hibernate Sessions.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Return the Hibernate SessionFactory that should be used to create
	 * Hibernate Sessions.
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	public FullTextSession getFullTextSession() {
		if(this.fullTextSession == null) {
			Session session = sessionFactory.openSession();
			this.fullTextSession = Search.getFullTextSession(session); 
		}
		return this.fullTextSession;
	}
	
	public QueryBuilder getQueryBuilder(Class<?> clazz){
	
		QueryBuilder b = this.getFullTextSession().getSearchFactory()
			    .buildQueryBuilder().forEntity(clazz ).get();
		return b;
	}
	
	public FullTextQuery getFullTextQuery(Query luceneQuery) {
		return this.getFullTextSession().createFullTextQuery( luceneQuery );
	}
	
	public <T> List<T> search(Query query, SearchCallback<T> searchCallBack) {
		
		Transaction tx = this.getFullTextSession().beginTransaction();
		// wrap Lucene query in a org.hibernate.Query
		FullTextQuery hibQuery = 
		    fullTextSession.createFullTextQuery(query, Article.class);
		tx.commit();
		fullTextSession.close();
		// execute search
		@SuppressWarnings("unchecked")
		List<T> results = hibQuery.list();
		searchCallBack.onFinishSearch(results, hibQuery.getResultSize());
		return results;
		
	}
	
	public <T> List<T> search(Query query, List<Sort> sorts, int firstResult, int maxResult, SearchCallback<T> searchCallBack) {
		
		Transaction tx = this.getFullTextSession().beginTransaction();
		// wrap Lucene query in a org.hibernate.Query
		FullTextQuery hibQuery = 
		    fullTextSession.createFullTextQuery(query, Article.class);
		
		if(sorts != null) {
			for(Sort sort : sorts) {
				hibQuery.setSort(sort);
			}
		}
		// execute search
		@SuppressWarnings("unchecked")
		List<T> results = hibQuery.setFirstResult(firstResult).setMaxResults(maxResult).list();
		tx.commit();
		fullTextSession.close();
		searchCallBack.onFinishSearch(results, hibQuery.getResultSize());
		
		return results;
		
	}
	
	

}
