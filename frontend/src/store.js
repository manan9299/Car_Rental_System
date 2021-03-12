// Index file for Redux Store
  
import { createStore,applyMiddleware, combineReducers } from "redux";
import thunk from 'redux-thunk';
import reducer from './reducers/index';
import {composeWithDevTools} from 'redux-devtools-extension';
const initialState = {};


const middleware = [thunk];
const store = createStore(reducer,initialState,composeWithDevTools(applyMiddleware(...middleware)));
export default store; 