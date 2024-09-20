import { combineReducers, createStore } from "redux";
import { themeReducer } from "./redux/ThemeActions";

const rootReducer = combineReducers({
    theme: themeReducer,

})
const store = createStore(rootReducer);

export default store;