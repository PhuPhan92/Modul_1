import getters from "./getters"
import actions from "./actions "
import mutations from "./mutations"
const state = {
	weather: {},
	location: {},
	forecastday: [],
}
export const weather = {
	state,
	getters,
	actions,
	mutations,
}

// export const weather = {
// 	state: {
// 		weather: {},
// 		location: {},
// 		forecastday: [],
// 	},
// 	getters: {
// 		weather: (state) => state.weather,
// 		location: (state) => state.location,
// 		forecastday: (state) => state.forecastday,
// 	},
// 	mutations: {
// 		setWeather(state, weather) {
// 			state.weather = weather
// 		},
// 		setLocation(state, location) {
// 			state.location = location
// 		},
// 		setForecastday(state, forecastday) {
// 			state.forecastday = forecastday
// 		},
// 	},
// 	actions: {
// 		setWeather(context, weather) {
// 			context.commit("setWeather", weather)
// 		},
// 		setLocation(context, location) {
// 			context.commit("setLocation", location)
// 		},
// 		setForecastday(context, forecastday) {
// 			context.commit("setForecastday", forecastday)
// 		},
// 	},
// }
