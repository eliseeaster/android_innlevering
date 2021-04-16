package no.kristiania.android_innlevering

class HomeFeed(val data: List<Data>)

class Data(val id: String, val name: String, val symbol: String, val priceUsd: Double, val changePercent24Hr: Double)

//name, symbol, recent value in dollars and negative or positive percentage change in the last 24 hours.
