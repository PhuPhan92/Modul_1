
import { createStore } from 'vuex';
import { weather } from './modules/weather/state';

export const store = createStore({
    modules: {
        weather,
    }
});