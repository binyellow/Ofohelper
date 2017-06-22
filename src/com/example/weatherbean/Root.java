package com.example.weatherbean;

import java.util.List;

public class Root {
	private List<Results> results ;

	public void setResults(List<Results> results){
	this.results = results;
	}
	public List<Results> getResults(){
	return this.results;
	}
}
