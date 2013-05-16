package com.zg.test.search;



import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zg.beans.Pager;
import com.zg.dao.ArticleCategoryDao;
import com.zg.dao.ArticleDao;
import com.zg.dao.impl.BaseDaoImpl;
import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.service.ArticleCategoryService;
import com.zg.service.ArticleService;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestArticleSearch {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	@Autowired
	private ArticleDao articleDao;

	
	
	@Before
	public void saveArticle(){
		
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setCreateDate(new Date());
		articleCategory.setModifyDate(new Date());
		articleCategory.setMetaDescription("articleCategory MetaDescription");
		articleCategory.setMetaKeywords("metaKeywords");
		articleCategory.setName("name");
		articleCategory.setOrderList(1);
		articleCategory.setPath("root");
		articleCategory.setParent(null);
		articleCategoryService.save(articleCategory);
		
		articleCategoryService.flush();
		Article article = new Article();
		article.setArticleCategory(articleCategory);
		article.setAuthor("author");
		article.setContent("content");
		article.setCreateDate(new Date());
		article.setHits(0);
		article.setHtmlFilePath("file");
		article.setMetaDescription("metaDescription");
		article.setPageCount(1);
		article.setIsPublication(true);
		article.setIsRecommend(true);
		article.setTitle("title");
		article.setIsTop(true);
		articleService.save(article);
		
	}
	
	@Test
	public void testSearchArticle(){
		
		Pager<Article> pager = new Pager<Article>();
		pager.setKeywords("title");
		pager.setPageSize(10);
		pager.setPageNumber(1);
		pager = articleService.search(pager);
		List<Article> articles = pager.getDataList();
		
		for(Article a : articles) {
			System.out.println("author: " + a.getAuthor());
		}
		
		org.junit.Assert.assertEquals(1, articles.size());
		
	}
	
	@After
	public void deleteArticle() {
		
		List<Article> articles =  articleService.getAll();
		for(Article article : articles) {
			
			articleDao.delete(article);
		}
		
		List<ArticleCategory> articleCategorys =  articleCategoryService.getAll();
		for(ArticleCategory articleCategory : articleCategorys) {
			articleCategoryService.delete(articleCategory);
		}
		
	}

}
