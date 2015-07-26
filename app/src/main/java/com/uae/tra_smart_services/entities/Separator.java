package com.uae.tra_smart_services.entities;

import android.content.Context;

/**
 * Created by andrey on 7/26/15.
 */
public class Separator {

	private Context context;
	private int width;
	private int height;
	private int color;

	Separator(Context context){
		this.context = context;
	}

	public Separator(Context contex, int width, int height, int color) {
		this(contex);
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public int getColor() {
		return context.getResources().getColor(color);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
