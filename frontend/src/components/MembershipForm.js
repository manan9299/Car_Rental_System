import React, { Component } from 'react'
// import PaymentForm from './PaymentForm'
import AppNavbar from '../AppNavbar'
import { Form, FormGroup, Label, CheckBox ,Button, Alert } from 'reactstrap';
import UserNavigation from './UserNavigation';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import { makeStyles } from '@material-ui/core/styles';
import DateFnsUtils from '@date-io/date-fns';

import {Table,Input} from 'reactstrap';
import cookie from 'react-cookies';
import config from '../constants/config';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import {TextField, responsiveFontSizes} from '@material-ui/core';
import {KeyboardDatePicker} from '@material-ui/pickers';
const useStyles = makeStyles((theme) => ({
    button: {
      margin: theme.spacing(1),
    },
  }));

export class MembershipForm extends Component {
    state={
        price:0,
        startDate:"",
        endDate:"",
        paymentStatus:null,
        errorMsg:"",
        nextPage:null
    }
    
    componentDidMount(){
        axios.defaults.withCredentials = true;
        axios.get(config.serverURI+"/currentuser")
        .then(async response => {
            const userData = await response.data;
            
            var temp = userData.membership_expiry_date;
            
            if(temp === null) {
                console.log("not a member");
                userData.membership_expiry_date ="Not a member"
            }
            this.setState({
                userId: userData.id,
                membershipExpiryDate: userData.membership_expiry_date
            });
            
        }).catch(error=> {
            console.log(error);
            console.log("User fetch failed");
            return;
        })
    }

    terminateMembership=()=>{
        
        var todayDate = new Date().toISOString().slice(0,19);
        if(localStorage.getItem('membership'))
        {
            localStorage.removeItem('membership')
        }
        if (todayDate>=this.state.membershipExpiryDate) {
            return;
        }
        console.log(this.state.membershipExpiryDate)
        if(this.state.membershipExpiryDate === "Not a member") {
            console.log("Return");
            return;
        }
        
        var data = {
            id: this.state.userId,
            membership_expiry_date: todayDate
        }

        axios.defaults.withCredentials = true;
        axios.put(config.serverURI+"/updateUser",data)
        .then(async response => {
            const data = await response.data;
            
            this.setState({
                membershipExpiryDate: data.membership_expiry_date
                
            })

            
        })
        .catch(error=>{
            console.error(error);
        });
        

    }

    extendMembership=()=>{
        this.setState({
            nextPage:true
        })
    }

    render() {
        
        if(this.state.nextPage) {
            return (<Redirect to='/paymentForm'/>);
        }

        var name = this.state.name;
        const {styles} = this.props;

        var content =  (
            <div id='membership-home'>
                <AppNavbar/>
                <div className="row">
                <div className="col-md-2">
                    <UserNavigation/>
                </div>
                <div className="col-md-6">
                    <h1>Membership Page</h1>
                    {/* <h3>Welcome {name}</h3> */}
                    <MuiPickersUtilsProvider className={useStyles.root} utils={DateFnsUtils}>
                            <div id="user-form" className="container-md">
                                <div className="form-group">
                                <Table borderless>
                                    <tbody>
                                        
                                        <tr>
                                            <th scope="row"><Label for="name">Membership Expiry Date</Label> </th>
                                            <td><TextField required id="standard-required" label="" value={this.state.membershipExpiryDate} /></td>
                                        </tr>
                                    </tbody>
                                </Table>
                                </div>
                                    
                                
                            </div>
                            </MuiPickersUtilsProvider>
                            
                    <Button size="small" onClick={()=>this.terminateMembership()} >Terminate Membership</Button>
                    <Button size="small" onClick={()=>this.extendMembership()} >Extend membership</Button>
                
                </div>
                </div>
                </div>
            );
        return content;
    }
}

export default MembershipForm
