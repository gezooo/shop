package com.zg.search;

import java.util.ArrayList;
import java.util.List;

/*
* @author gez
* @version 0.1
*/

public class SearchCriterial {
	
	private List<Criterial> critials = new ArrayList<Criterial>();;
	
	
	public static class Criterial {
		
		private String field;
		private String keywords;
		
		public Criterial(String field, String keywords) {
			this.field = field;
			this.keywords = keywords;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}	
		
	}
	
	public List<Criterial> getCriterials() {
		return this.critials;
	}
	
	public void add(String field, String keywords){
		this.critials.add(new Criterial(field, keywords));
	}

}
