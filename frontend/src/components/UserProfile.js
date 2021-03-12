import React, { Component } from 'react'
import AppNavbar from '../AppNavbar'
import "../css/UserProfile.css";
import {Table,Input,Button,Label} from 'reactstrap';
import cookie from 'react-cookies';
import config from '../constants/config';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import UserNavigation from './UserNavigation';
import {TextField, responsiveFontSizes} from '@material-ui/core';
import {KeyboardDatePicker} from '@material-ui/pickers';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import { makeStyles } from '@material-ui/core/styles';
import DateFnsUtils from '@date-io/date-fns';
const useStyles = makeStyles((theme) => ({
    button: {
      margin: theme.spacing(1),
    },
  }));
export class UserProfile extends Component {
    constructor(props){
        super();
        
        this.state={
            id:"",
            name : "",
            email: "",
            address: "",
            dob:"",
            license_number : "",
            license_date : "",
            phone:"",
            edit_name : "",
            edit_email:"",
            edit_address:"",
            edit_dob:"",
            edit_license_number : "",
            edit_license_date : "",
            edit_phone:"",
            isAuthenticated:true,
            error:null,
            userUpdated:false
        }

    }    
    componentDidMount(){

        if(!cookie.load(config.sessionCookie))
        {
            // Go to home Page 
            this.setState({
                isAuthenticated:false
            })
        }
        else{
            axios.defaults.withCredentials = true;
            axios.get(config.serverURI+'/currentuser',{
                params:{
                    email: localStorage.getItem('email')
                }
            }).then(async response =>{
                console.log(response)
                if(response.status>=200 && response.status<=299)
                {
                    const data = await response.data
                    console.log(data);

                    this.setState({
                        id:data.id,
                        name:data.name,
                        email:data.email,
                        address:data.address,
                        dob:data.dob,
                        license_number:data.license_number,
                        license_date:data.license_date,
                        phone:data.phone,
                        edit_name:data.name,
                        edit_email:data.email,
                        edit_address:data.address,
                        edit_dob:data.dob,
                        edit_license_number:data.license_number,
                        edit_license_date:data.license_date,
                        edit_phone:data.phone
                    })

                }
            })
        }
    }

    handleChange=input=>e=>{ 
        this.setState({
            [input] : e.target.value
        });
    }

    handleDateChange=input=>e=> {
        if (e.toString() !== "Invalid Date") {
            console.log(e);
            this.setState({
                [input] : e
            })
        } 
    }

    onBlur = e =>{

        e.target.type = "text"
    }
    onFocus = e =>{
        e.target.type = "date"
    }
    onCancel=()=>{
        this.setState({
            edit_name:this.state.name,
            edit_email:this.state.email,
            edit_address:this.state.address,
            edit_dob:this.state.dob,
            edit_license_number:this.state.license_number,
            edit_license_date:this.state.license_date,
            edit_phone:this.state.phone
        });
        console.log(this.state.edit_name)
        
    }
    validateFields = async () => {
        var error = null;
        var validateEmail = new Promise((resolve,reject)=>{
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if(re.test(this.state.email.toLowerCase()))
            {
                console.log(this.state.email);
                resolve(true);
            }
            else{
                
                this.setState({
                    error:"The Email format is invalid\n"
                });
                reject(false);
            }
            
        });
        var res = false;
        await Promise.all([validateEmail]).then(
            (results) => 
            {  
                console.log(results);
                res = results;
            }
        ).catch(
            (errors)=>
            {
                console.log(error)
                res = error;
            }
        )
        console.log(res)
        return res;        
    }
    onSave=()=>{
        var results = this.validateFields();
        
        if(results)
        {
            this.setState({
                name: this.state.edit_name,
                email: this.state.edit_email,
                password: this.state.edit_password,
                address: this.state.edit_address,
                dob: this.state.edit_dob,
                license_number: this.state.edit_license_number,
                license_date: this.state.edit_license_date,
                phone: this.state.edit_phone
            });

            axios.defaults.withCredentials = true;
            axios.get(config.serverURI+"/currentuser")
            .then(async response => {
                const currentUserData = await response.data;
                console.log(typeof this.state.dob);
                console.log(new Date(this.state.dob));
                console.log("----");
                // moment.tz
                // Send a put request
                const data = {
                    id:currentUserData.id,
                    email:this.state.email,
                    name:this.state.name,
                    address:this.state.address,
                    dob:new Date(this.state.dob),
                    license_number:this.state.license_number,
                    license_date:new Date(this.state.license_date),
                    phone:this.state.phone
                }
                
                axios.defaults.withCredentials = true;
                axios.put(config.serverURI+"/updateUser",data)
                .then(async response => {
                    const data = await response.data;
                    console.log(data);
                    //if (response.status == 200){
                        this.setState({
                            userUpdated:true
                        })
                   // }
                })
                .catch(error=>{
                    console.error(error);
                    
                });
            })
            .catch(error=> {
                console.log(error);
                console.log("User update failed");
            })
            
            
        }
        else{
            console.log("Validation Error");
        }
    }
    render() {
        var status = null;
        if (this.state.error){
            status = (<div className="alert alert-danger" role="alert" style={{margin:"auto"}}>
                        {this.state.error}
                    </div>);
        }
        else if(this.state.userUpdated)
        {
            status = (<div className="alert alert-success" role="alert" style={{margin:"auto"}}> 
                        Details Updated Successufully!
                        </div>);
        }

        var content =  (
            <div id="page-body">
                <AppNavbar />
                <div className="row">
                    <div className="col-md-2">
                        <UserNavigation/>
                    </div>
                    <div className="col-md-10">

                    <h1>Welcome {this.state.name}</h1>
                        <MuiPickersUtilsProvider className={useStyles.root} utils={DateFnsUtils}>
                        <div id="user-form" className="container-md">
                            <div className="form-group">
                            <Table borderless>
                                <tbody>
                                    <tr>
                                        {status}
                                    </tr>
                                    <tr>
                                        <th scope="row"><Label for="name">Name</Label> </th>
                                        <td><TextField required id="standard-required" label="required"  onChange={this.handleChange('edit_name')} value={this.state.edit_name} /></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Email</th>
                                       
                                        <td><Input required type="email" name="email" onChange={this.handleChange('edit_email')} defaultValue = {this.state.edit_email} /></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Address</th>
                                        <td><Input  type="text" name="address" onChange={this.handleChange('edit_address')} defaultValue = {this.state.edit_address} /></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Date of Birth</th>
                                        <td><KeyboardDatePicker disableToolbar variant="inline" format="MM/dd/yyyy"    
                                                margin="normal"
                                                id="date-picker-inline"
                                                value={this.state.edit_dob}
                                                defaultValue={this.state.edit_dob}
                                                onChange={this.handleDateChange('edit_dob')}
                                                KeyboardButtonProps={{
                                                    'aria-label': 'change date',
                                                }}/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">License Number</th>
                                        <td><Input  type="text" name="license_number" onChange={this.handleChange('edit_license_number')} defaultValue = {this.state.edit_license_number} /></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">License Date</th>
                                        <td> <KeyboardDatePicker
                                disableToolbar
                                variant="inline"
                                format="MM/dd/yyyy"
                                margin="normal"
                                id="date-picker-inline"
                                value={this.state.edit_license_date}
                                defaultValue={this.state.edit_license_date}
                                onChange={this.handleDateChange('edit_license_date')}
                                KeyboardButtonProps={{
                                    'aria-label': 'change date',
                                }}/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Phone Number</th>
                                        <td><Input  type="text" name="contact" onChange={this.handleChange('edit_phone')} defaultValue = {this.state.edit_phone} /></td>
                                    </tr>
                                    <tr>
                                        <td><Button onClick={this.onCancel}>Cancel</Button>
                                        <Button onClick={this.onSave}>Save</Button></td>
                                    </tr>

                                </tbody>
                            </Table>
                            </div>
                                
                            
                        </div>
                        </MuiPickersUtilsProvider>
                        
                    </div>
                    
                </div>
                
            </div>
        );
        if(!this.state.isAuthenticated)
        {
            content = <Redirect to='/login'/>   
        }
        return content;
    }
}

export default UserProfile
