import React, { Component } from 'react';
import "../css/home.css";
import AppNavbar from '../AppNavbar';
import { Redirect } from 'react-router-dom';
import cookie from 'react-cookies';
import config from '../constants/config';


class Home extends Component {
    
    render() {
        var content = (
            <div>
                <AppNavbar/>
                <div id="jumbotron" class = "Jumbotron">
                    
                    <div id="jumbotron-text" class="container">
                        <h1>Welcome to Just Rent It</h1>
                        <p>Enjoy your ride at the best rate in the market</p>
                    </div>
                </div>

            </div>
        )
        if (cookie.load(config.sessionCookie))
        {
            content = (<Redirect to='/userHome'/>)
        }
        return content;
    }
}

export default Home
