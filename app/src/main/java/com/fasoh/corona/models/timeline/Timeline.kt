package com.fasoh.corona.models.timeline

import com.google.gson.annotations.SerializedName

data class Timeline(

	@SerializedName("date")
	var date: String? = null,

	@SerializedName("data")
	var data: List<TimelineDataItem>
)