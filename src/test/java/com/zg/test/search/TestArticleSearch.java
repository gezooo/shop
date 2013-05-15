package com.zg.test.search;



import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	
	private String articleid;
	
	@Before
	public void saveArticle(){
		Article article = new Article();
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setCreateDate(new Date());
		articleCategory.setMetaDescription("articleCategory MetaDescription");
		articleCategory.setMetaKeywords("metaKeywords");
		articleCategory.setName("name");
		articleCategory.setOrderList(1);
		articleCategory.setPath("root");
		article.setArticleCategory(articleCategory);
		article.setAuthor("author");
		article.setContent("content");
		article.setCreateDate(new Date());
		article.setHits(0);
		article.setHtmlFilePath("file");
		article.setMetaDescription("metaDescription");
		article.setPageCount(1);
		article.setPublication(true);
		article.setRecommend(true);
		article.setTitle("title");
		article.setTop(true);
		articleid = articleService.save(article);
	}
	
	@Test
	public void testSearchArticle(){
		
		org.springframework.mock.web.MockServletContext c;
		
	}
	
	//@After
	public void deleteArticle() {
		Article article = articleService.get(articleid);
		articleCategoryService.delete(article.getArticleCategory());
		articleService.delete(articleid);
	}

}
