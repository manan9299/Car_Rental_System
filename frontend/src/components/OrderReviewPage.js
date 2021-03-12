import React, { Component } from 'react'
import store from '../store'
import AppNavbar from '../AppNavbar'
import UserNavigation from './UserNavigation'
import {TextField} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import PaymentFormForBooking from './PaymentFormForBooking';
import axios from 'axios';
import config from '../constants/config';
import { Button,Alert, Modal, ModalHeader, ModalBody, ModalFooter, Label } from 'reactstrap';
import { Redirect } from 'react-router-dom';
const useStyles = makeStyles((theme) => ({
    button: {
      margin: theme.spacing(1),
    },
    container: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    textField: {
      marginLeft: theme.spacing(1),
      marginRight: theme.spacing(1),
      width: 200,
    },
  }));
export default class OrderReviewPage extends Component {
    
    state= {
        make:localStorage.getItem('make'),
        model:localStorage.getItem('model'),
        vehicle_number:localStorage.getItem('vehicle_number'),
        address:localStorage.getItem('address'),
        price:localStorage.getItem('price'),
        totalPrice:null,
        type:localStorage.getItem('type'),
        start_time:new Date().toISOString().substring(0,16),
        end_time:new Date().toISOString().substring(0,16),
        error:false,
        errorMsg: null,
        goBack:false, 
        modal:false
    }
    
    handleChange=input=>e=>{
        // Validate 
        this.setState({
            [input] : e.target.value
        });
    }
    calculateCost = () => {

        var start = new Date(this.state.start_time)
        var end = new Date(this.state.end_time)
        console.log(start)

        console.log(new Date())
        if(start < new Date() || end < new Date())
        {
            this.setState({
                errorMsg:<p>You cannot select dates in past.</p>,
                error:true
            })
            return 
        }
        if(start > end)
        {
            this.setState({
                errorMsg:<p>Start date must be before End Date</p>,
                error:true
            })
            return 
        }
        var diff = Math.abs( end-start) / 36e5

        console.log(diff)
        if(diff >= 72)
        {
            this.setState({
                error:true,
                errorMsg : <p>Maximum time period alllowed is 3 days (72 hours).</p>
            })
            return
            
        }
        this.setState({
            totalPrice:diff*parseInt(this.state.price)
        })
        
        this.setState({
            errorMsg:null,
            error:true
        })

    }
    onSubmit = () =>
    {
        
        const data = {
            vehicle : this.state.vehicle_number,
            user: parseInt(localStorage.getItem('user_id')),
            bookingStartTime:new Date(this.state.start_time).toISOString().substring(0,16).replace('T',' ')+":00",
            bookingEndTime:new Date(this.state.end_time).toISOString().substring(0,16).replace('T',' ')+":00"
        }
        console.log(data.bookingStartTime)

        console.log(data)
        axios.defaults.withCredentials = true;
        axios.post(config.serverURI+'/reservation',data)
        .then(async res =>{
            if(res.status===200 )
            {
                console.log("BOOKING SUCCESS")
                this.setState({
                    modal:true
                })
            }
            else{
                console.log()
            }
        }).catch(error=>{
            console.log(error)
        })
        this.toggle()
    }
    toggle=()=>{
        this.setState({
            modal:!this.state.modal
        })
    }
    goback = () =>{
        this.setState({
            goBack:true
        })

    }
   
    render() {
        var errorMsg = null;
        if(this.state.error)
        {
            errorMsg = this.state.errorMsg;
        }
        var content = (
            <div id='order-review-page'>
                <AppNavbar/>
                <div className='row'>
                    <div className='col-md-2'>
                        <UserNavigation/>
                    </div>
                    <div className='col-md-10'>
                        <h1>Booking Review </h1>
                        <div className="review-page">
                        <table class="table table-striped">
                            <tbody>
                              <tr>
                                <td>Company</td>
                                <td>{this.state.make}</td>
                              </tr>
                              <tr>
                                <td>Model</td>
                                <td>{this.state.model}</td>
                              </tr>                              
                              <tr>
                                <td>Registration Number</td>
                                <td>{this.state.vehicle_number}</td>
                              </tr>
                              <tr>
                                <td>Address</td>
                                <td>{this.state.address}</td>
                              </tr>
                              <tr>
                                <td>Price</td>
                                <td>{this.state.price}</td>
                              </tr>
                              <tr>
                                <td>Type</td>
                                <td>{this.state.type}</td>
                              </tr>
                                
                            </tbody>
                          </table>
                            <div className='row'>
                            <div className='col-md-6'>          
                                <TextField
                                id="start-time"
                                onChange={this.handleChange('start_time')}
                                label="Start Time"
                                type="datetime-local"
                                defaultValue={this.state.start_time}//"2017-05-24T10:30"
                                className={useStyles.textField}
                                InputLabelProps={{
                                  shrink: true,
                                }}
                              />
                            </div>
                            <div className='col-md-6'>
                                <TextField
                                    id="end-time"
                                    label="End Time"
                                    type="datetime-local"
                                    onChange={this.handleChange('end_time')}
                                    defaultValue={this.state.end_time}//"2017-05-24T10:30"
                                    className={useStyles.textField}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    />      
                            </div>
                            
                            </div>
                                <Label>Total Cost: {this.state.totalPrice}</Label>
                                <Button color="success" onClick={()=>this.calculateCost()}>Ok</Button>
                            {errorMsg}

                            <div id='payment-portal'>
                                <PaymentFormForBooking onSubmit={this.onSubmit}/>
                            </div>
                            <Modal isOpen={this.state.modal} toggle={()=>this.toggle()} >
                                <ModalHeader toggle={()=>this.toggle()}>Modal title</ModalHeader>
                                <ModalBody>
                                <Alert color="primary">
                                    Booking Confirmed
                                </Alert>
                                </ModalBody>
                                <ModalFooter>
                                <Button color="secondary" onClick={()=>this.goback()}>Ok</Button>
                                </ModalFooter>
                            </Modal>
                        </div>  
                        </div>
                    </div>
                </div>)
        if(this.state.goBack)
        {
            content = <Redirect to='/userHome'/>
        }
        return content;
    }
}

