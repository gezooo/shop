package com.zg.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.DefinitionList;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

import com.zg.util.CommonUtils;

@Entity
@Indexed
public class Article extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4332784274659680773L;
	
	public static final int MAX_RECOMMEND_ARTICLE_LIST_COUT = 20;
	
	public static final int MAX_HOT_ARTICLE_LIST_COUT = 20;
	
	public static final int MAX_NEW_ARTICLE_LIST_COUT = 20;
	
	public static final int MAX_PAGE_CONTENT_COUNT = 2000;
	
	public static final int DEFAULT_ARTICLE_LIST_PAGE_SIZE = 2000;
	
	private String title;
	
	private String author;
	
	private String content;
	
	private String metaKeywords;
	
	private String metaDescription;
	
	private boolean isPublication;
	
	private boolean isTop;
	
	private boolean isRecommend;
	
	private Integer pageCount;
	
	private String htmlFilePath;
	
	private Integer hits;
	
	private ArticleCategory articleCategory;

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(length = 10000, nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 5000)
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(length = 5000)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	public boolean getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(boolean isPublication) {
		this.isPublication = isPublication;
	}

	
	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(nullable = false)
	public boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(boolean isTop) {
		this.isTop = isTop;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(nullable = false)
	public boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	@Column(nullable = false)
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(nullable = false, updatable = false)
	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}
	
	@Column(nullable = false)
	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(nullable = false)
	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}
	
	@Transient
	public List<String> getHtmlFilePathList() {
		ArrayList<String> htmlFilePathList = new ArrayList<String>();
		String perfix = StringUtils.substringBeforeLast(this.htmlFilePath, ".");
		String extension = StringUtils.substringAfterLast(this.htmlFilePath, ".");
		htmlFilePathList.add(this.htmlFilePath);
		for(int i = 2; i <= this.pageCount; i++) {
			htmlFilePathList.add(perfix + "_" + i + "." + extension);
		}
		return htmlFilePathList;
	}
	
	@Transient
	public String getContentText() {
		String result = "";
		try {
			Parser parser = Parser.createParser(this.content, "UTF-8");
			TextExtractingVisitor textExtractingVisitor = new TextExtractingVisitor();
			parser.visitAllNodesWith(textExtractingVisitor);
			result = textExtractingVisitor.getExtractedText();
			
		}
		catch (ParserException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Transient
	public List<String> getPageContentList() {
		List<String> pageContentList = new ArrayList<String>();
		if(this.content.length() <= Article.MAX_PAGE_CONTENT_COUNT) {
			pageContentList.add(content);
			return pageContentList;
		}
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class); //TABLE
		NodeFilter divFilter = new NodeClassFilter(Div.class); //DIV
		NodeFilter paragraphFilter = new NodeClassFilter(ParagraphTag.class); // P
		NodeFilter bulletListFilter = new NodeClassFilter(BulletList.class); //UL
		NodeFilter bulletFilter = new NodeClassFilter(Bullet.class); //lI
		NodeFilter definitionListFilter = new NodeClassFilter(DefinitionList.class); //DL
		NodeFilter definitionListBulletFilter = new NodeClassFilter(DefinitionListBullet.class); //DD
		OrFilter orFilter = new OrFilter();
		orFilter.setPredicates(new NodeFilter[] { paragraphFilter, 
				divFilter, tableFilter, bulletListFilter, bulletFilter, definitionListFilter,
				definitionListBulletFilter});
		List<Integer> indexList = new ArrayList<Integer>();
		List<String> contentList = CommonUtils.splitString(content, Article.MAX_PAGE_CONTENT_COUNT);
		for(int i = 0; i < contentList.size(); i++) {
			String splitedContent = contentList.get(i);
			Parser htmlParser = Parser.createParser(splitedContent, "UTF-8");
			try{
				NodeList nodeList = htmlParser.parse(orFilter);
				if(nodeList.size() > 0) {
					Node node = nodeList.elementAt(nodeList.size() -1);
					indexList.add(node.getStartPosition());
				} else {
					String regex = "\\.|\\!|\\?";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(splitedContent);
					int endIndex = 0;
					if (matcher.find()){
						while (matcher.find()) {
							endIndex = matcher.end();
						}
						indexList.add(i * Article.MAX_PAGE_CONTENT_COUNT + endIndex);
					} else {
						indexList.add((i + 1) * Article.MAX_PAGE_CONTENT_COUNT);
					}
				} 
			}
			catch (ParserException ex) {
				
			} 
		}
		for(int i = 0; i <= indexList.size(); i++) {
			String pageContent = "";
			if(i == 0) {
				pageContent = content.substring(0, indexList.get(0));
			} else if (i == indexList.size()) {
				pageContent = content.substring(indexList.get(i - 1));
			} else {
				pageContent = content.substring(indexList.get(i - 1), indexList.get(i));
			}
			try {
				Parser parser = Parser.createParser(pageContent, "UTF-8");
				NodeList nodeList = parser.parse(orFilter);
				String contentResult = nodeList.toHtml();
				if (StringUtils.isEmpty(contentResult)) {
					contentResult = pageContent;
				}
				pageContentList.add(contentResult);
			} catch (ParserException ex) {
				
			}
		}
		return pageContentList;
		
		
	}
	
	
	

}
