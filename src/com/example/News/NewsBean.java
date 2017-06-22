package com.example.News;

import android.widget.ImageView;
import android.widget.TextView;

public class NewsBean {
	public String imageViewURL;
	public String Title,content;
	public void setImageView(String imageView) {
		this.imageViewURL = imageView;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String toString() {
		return "NewsBean [newsIconUrl=" + imageViewURL + ", newsTitle=" + Title + ", newsContent=" + content
				+ "]";
	}
}
