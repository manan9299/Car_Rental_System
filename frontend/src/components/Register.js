import React, { Component } from 'react';
import { Col, Row, Button, Form, FormGroup, Label, Input } from 'reactstrap';
import '../css/Register.css';
import AppNavbar from '../AppNavbar';
import { ThemeProvider as MuiThemeProvider } from '@material-ui/core/styles';

import axios from 'axios';
import config from '../constants/config';
import UserProfile from './UserProfile';
import { Redirect } from 'react-router-dom';

export class Register extends Component {
    
    state={
        step:1,
        name:"",
        email:"",
        password:"",
        confirmpassword:"",
        dob:"",
        address:"",
        license_number:"",
        license_date:"",
        phone:"",
        error:"",
        moveToNextPage:false,
        redirect:false
    }

    continue = e =>{
        e.preventDefault();
        if(this.validateFields()){
            this.props.nextStep();
            this.setState({
                error:"",
                moveToNextPage:true
            })        
        }
    
        var {name,email,password,address,dob,license_number,license_date,phone} = this.state;
        const data = {
            email:email,
            name:name,
            password:password,
            address:address,
            dob:dob,
            license_number:license_number,
            license_date:license_date,
            phone:phone
        }

        axios.post(config.serverURI+'/registration',data)
        .then(async response => {
            
            // if(response.status === 201){
            //     this.props.nextStep();                 
            // }
            // else{
                console.log(response);    
                this.setState({
                    redirect:true
                })
            // 
            // }
        }).catch(err=>{
            console.log(err.message);
            console.error(err);
        });
        
    
    }
     // This method will handle all the field changes
    // this method takes 'input' which the field to be changed 
    handleChange=input=>e=>{
        // Validate 
        this.setState({
            [input] : e.target.value
        });
    }
    validateFields= () => {
        var validatePassword = new Promise((resolve,reject)=>{
            if (this.state.password !== this.state.confirmpassword)
            {
                this.setState({
                 error:this.state.error + "\nPasswords are not matching."  
                })
                 
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
            else{

                this.setState({
                    error :this.state.error+"\nThe email format is incorrect"
                });
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

   
    render() {
        if(this.state.redirect) {
            return (<Redirect to='/login'/>);
        }
        var error = null;
        var content = <div class="container-fluid" id="register-body">
                        <AppNavbar isAuthenticated="false"/>
                        <div id="register-form" class = "container-md" >        
                            <MuiThemeProvider>
                                <React.Fragment>
                                    <h2>Enter Driver Detials</h2>
                                    <Form>
                                        <FormGroup>
                                            <Label for="name">Name</Label>
                                            <Input  type="text" name="name" id="name" onChange={this.handleChange('name')}/>
                                        </FormGroup>

                                        <FormGroup>
                                            <Label for="email">Email</Label>
                                            <Input  type="email" name="email" id="email" onChange={this.handleChange('email')}/>
                                        </FormGroup>
                                        <Row form>
                                            <Col md={6}>
                                            <FormGroup>
                                                <Label for="password">Password</Label>
                                                <Input type="password" placeholdername="password" id="password" onChange={this.handleChange('password')}/>
                                            </FormGroup>
                                            </Col>
                                            <Col md={6}>
                                            <FormGroup>
                                                <Label for="confirmpassword">Confirm Password</Label>
                                                <Input type="password" name="confirmpassword" id="confirmpassword" onChange={this.handleChange('confirmpassword')}/>
                                            </FormGroup>
                                            </Col>
                                        </Row>
                                        <FormGroup>
                                            <Label for="address">Address</Label>
                                            <Input type="text" name="address" id="Address" onChange={this.handleChange('address')}/>
                                        </FormGroup>
                                        
                                        <FormGroup>
                                            <Label for="dob">Date of Birth</Label>
                                            <Input type="date" name="dob" id="dob" onChange={this.handleChange('dob')}/>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="license_number">License Number</Label>
                                            <Input type="text" name="license_number" id="license_number" onChange={this.handleChange('license_number')}/>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="license_date">License Date</Label>
                                            <Input type="date" name="license_date" id="dob" onChange={this.handleChange('license_date')}/>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="phone">Contact number</Label>
                                            <Input type="text" name="phone" id="phone" onChange={this.handleChange('phone')}/>
                                        </FormGroup>
                                        {error}
                                        <Button onClick={this.continue}>Register</Button>
                                    </Form>
                                </React.Fragment>
                            </MuiThemeProvider>
                        </div>
                    </div>;
        if(this.state.moveToNextPage){
            content=<UserProfile email={this.state.email}/>
        }
        
        // Return the content to be rendered
        return content;
    }
}

export default Register
