import React, { Component } from 'react';
import AppNavbar from '../AppNavbar';
// import UserProfile from './UserProfile';
import axios from 'axios';
import config from '../constants/config';
import cookie from 'react-cookies';
import '../css/login.css';
import { Form, FormGroup, Label, Input,Button } from 'reactstrap';
import { Redirect } from 'react-router-dom';
class Login extends Component {
    state={
        email:"",
        name:"",
        password:"",
        auth:false,
        redirectAfterLogin : null
    }
    componentDidMount(){       
        this.setState({
            auth:false
        })  
    }

    handleChange=input=>e=>{ 
        this.setState({
            [input] : e.target.value
        });
    }

    validateFields= () => {
        var validatePassword = new Promise((resolve,reject)=>{
            if (this.state.password === "")
            {
                  
                reject(false);
            }
            else{
                resolve(true);
            }
        });

        var validateEmail = new Promise((resolve,reject)=>{

            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if(re.test(this.state.email.toLowerCase()))
            {
                resolve(true);
            }
            else if (this.state.email === "")
            {
               
                reject(false);
            }
            else{

               
                reject(false);
            }
            
        });
        var res = false;
        Promise.all([validateEmail,validatePassword]).then(
            (results) => 
            {
                res = results;
            }
        ).catch(
            (errors)=>
            {
                res = errors;
            }
        )

        return res;        
    }
    onSubmit=()=>{
        if(this.validateFields){
            // send the axios request 
            axios.defaults.withCredentials = true;
            axios.get(config.serverURI+"/login", {
                auth : {
                    username : this.state.email,
                    password:this.state.password
                }
            })
            .then(async response => {
                // Any successful response code
                if(response.status <= 299 && response.status >= 200){
                    console.log(response)
                    // Very important
                    // NOTE: To save and important information which can be useful on next page 
                    // use await keyword to make sure that page won't proceed further before getting the response
                    // For instance here we don't want to move ahead befo   re setting the correct values 
                    const data = await response.data
                    console.log("========login_resp============")
                    let userRole = data.role
                    let redirectPage = null
                    if(userRole == "admin"){
                        redirectPage = <Redirect to="/adminHome" />
                    } else {
                        redirectPage = <Redirect to="/userHome" />
                    }

                    this.setState({
                        redirectAfterLogin : redirectPage
                    });
                    

                    console.log(data)
                    localStorage.setItem('email', data.email);
                    localStorage.setItem('name', data.name);
                    localStorage.setItem('user_id',data.id);
                    if(data.membership_expiry_date)
                    {
                        localStorage.setItem('membership',data.membership_expiry_date)
                    }
                    this.setState({
                        email:data.email,
                        name:data.name,
                        auth:true,
                        isAuthenticated:true
                    });
                }
                else{
                    console.error(response);    
                }
            }).catch(err=>{
                //let fullError = err.response.data.errors.join(",");
                this.setState({
                    isAuthenticated:false
                })
                console.error(err);
            });
        }
        else{
            this.setState({
                isAuthenticated:false
            })
        }
    }

    render() {
        var content = null;
        var error = null;
        if (cookie.load(config.sessionCookie) && this.state.auth)
        {

            content = this.state.redirectAfterLogin
        }
        else{

            error = <p className="text-danger">Invalid Credentials!</p>; 
            content = (
                <div>
                    <AppNavbar/>
                    <h1>Login Page</h1>
                    <div className="container">
                        <Form>
                            <FormGroup>
                                <Label for="email">Email</Label>
                                <Input  type="email" name="email" id="email" onChange={this.handleChange('email')}/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password">Password</Label>
                                <Input  type="password" name="password" id="password" onChange={this.handleChange('password')}/>
                            </FormGroup>
                            {/* {error} */}
                            <Button color="primary" onClick={this.onSubmit}>Login</Button>
                        </Form>
                    </div>
                </div>
            )
        }
        
        return content;
    }
}

export default Login
