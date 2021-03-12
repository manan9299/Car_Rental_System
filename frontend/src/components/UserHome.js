import React, { Component } from 'react'
import AppNavbar from '../AppNavbar'
import axios from 'axios';
import '../css/UserHome.css';
import cookie from 'react-cookies';
import config from '../constants/config';
import { Redirect } from 'react-router-dom';
import Button from '@material-ui/core/Button';

class Success extends Component {
    
    constructor(props)
    {
        super(props);
        this.state={
            next:"",
            name:localStorage.getItem('name'),
            email:localStorage.getItem('email'),
            isAuthenticated:true,
            nextPage:null,
            userDetails:null
        }
    }
    componentDidMount(){

        axios.defaults.withCredentials=true;
        axios.get(config.serverURI+'/getUser',{
            params:{
                email:localStorage.getItem('email')
            }
        }).then(async res =>{
            const data = await res.data;
            
            if(res.status === 200)
            {
                this.setState({
                    userDetials:data
                })
                console.log(data.membership_expiry_date)
                
                if(new Date(data.membership_expiry_date) > new Date())
                {
                    console.log(data.membership_expiry_date)
                    localStorage.setItem('membership',data.membership_expiry_date)
                }
            }
        }).catch(error=>{
            console.log(error)
        })

        if (!localStorage.getItem('email'))
        {
            // go to login screen
            this.setState({
                email:"",
                name:"",
                isAuthenticated:false
            })
        }
        else{
            this.setState({
                email:localStorage.getItem('email'),
                name:localStorage.getItem('name'),
                isAuthenticated:true
            })
            axios.defaults.withCredentials = true;
            axios.get(config.serverURI+'/getUser',{
                params:{
                    email:this.state.email
                }
            }).then(async response =>{
                if(response.status <= 299 && response>=200)
                {
                    console.log(response);
                } 
            })

        }
    }
    goToPage = (pageName) => {
        console.log(pageName)
        this.setState({
            nextPage : pageName
        })
    }
    render() {
        var name = this.state.name;
        const {styles} = this.props;
        var content =  (
            <div id='user-home'>
                <AppNavbar/>
                <h1>User Home Page</h1>
                <h3>Welcome {name}</h3>
                <div id='user-options' className='container-md'>
                    <div className='row'>
                        <div className='col-md-4'>
                        <div id="card" class="card" >
                            <img class="card-img-top" src="images/mybookings.jpg" alt="Card image cap"/>
                            <div class="card-body">
                                {/* <p class="card-text">Manage Bookings</p> */}
                                <Button size="small" class="btn btn-secondary btn-small" onClick={()=>this.goToPage("bookings")}>Manage bookings</Button>
                            </div>
                        </div>
                        </div>
                        <div className='col-md-4'>
                            <div id="card" class="card" >
                                <img class="card-img-top" src="images/newbooking.png" alt="Card image cap"/>
                                <div class="card-body">
                                    {/* <p class="card-text">Start Booking</p> */}
                                    <Button size="small" class="btn btn-secondary btn-small" onClick={()=>this.goToPage("rent")}>Start Booking</Button>
                                </div>
                            </div>
                        </div>
                        <div className='col-md-4'>
                            <div id="card" class="card" >
                                <img class="card-img-top" src="images/membership.png" alt="Card image cap"/>
                                <div class="card-body">
                                    <p class="card-text">Manage Membership</p>
                                    <Button size="small" onClick={()=>this.goToPage("membership")}>Click here to extend membership</Button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            );
            
            if(!cookie.load(config.sessionCookie))
            {
                content = (<Redirect to='/'/>);
            }
            else{
                switch(this.state.nextPage){
                    case "bookings":
                        content = (<Redirect to='/bookings'/>);
                        break;
                    case "rent":
                        console.log("RENT")
                        content = (<Redirect to='/renting'/>);
                        break;
                    case "membership":
                        content = (<Redirect to='/membershipForm'/>);
                        break;
                }
            }
            return content;
        }
}
export default Success;