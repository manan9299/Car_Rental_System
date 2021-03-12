import React, { Component } from 'react'
import '../css/AppNavbar.css';
import config from '../constants/config';
import axios from 'axios';
import cookie from 'react-cookies';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import HomeIcon from '@material-ui/icons/Home';

export class AppNavbar extends Component {
  
  // constructor(props){
  //      super(props);
  // }

  logout(){
    axios.defaults.withCredentials = true;
     axios({
      method:'get',
      url:config.serverURI+'/logout',
      headers: {
          "Content-Type": "application/json"
        }})
        .then(() => {
          
        }).catch(err => {        
          console.error(err);
        });
        cookie.remove(config.sessionCookie)
        localStorage.removeItem('email');
        localStorage.removeItem('name');
  }
  render() {
    var tabs = null;
    if (!cookie.load(config.sessionCookie))
    {
      tabs = (<ul className="navbar-nav navbar-right">
                <li className="nav-item">
                  <a href="/login"> Login </a>
                </li>
                <li className="nav-item">
                  <a href="/register"> Sign Up </a>
                </li>
              </ul>);

    }
    else{

      tabs =  (<ul className="navbar-nav navbar-right">
                <li className="nav-item">
                  <a href='/adminHome' ><HomeIcon/>Home</a>
                  <a href='/login' onClick={this.logout}><ExitToAppIcon/>Log Out</a>
                </li>
              </ul>);
    }
    return (
      <div id='appnavbar'>
        
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
          <a className="navbar-brand" href="/">Just Rent It</a>
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
              {tabs}
          </div>
        </nav>
      </div>
     
    )
  }
}

export default AppNavbar
