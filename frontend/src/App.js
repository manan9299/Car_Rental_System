import React from 'react';
import {Provider} from 'react-redux';
import store from './store';
import {
  BrowserRouter
} from 'react-router-dom';
import CssBaseline from '@material-ui/core/CssBaseline';import './css/App.css';
import Routes from './Routes';


function App() {
  return (
    <Provider store={store}>
    <div className="App">
      <BrowserRouter> 
        <Routes/>
      </BrowserRouter>
    </div>
    </Provider>
    
  );
}

export default App;
