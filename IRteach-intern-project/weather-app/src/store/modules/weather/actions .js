export default {
	setWeather(context, weather) {
		context.commit("setWeather", weather)
	},
	setLocation(context, location) {
		context.commit("setLocation", location)
	},
	setForecastday(context, forecastday) {
		context.commit("setForecastday", forecastday)
	},
}
