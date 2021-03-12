import React from 'react';
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';
import {Alert,Form,FormGroup,Input,Label,Button} from 'reactstrap';

import { Redirect } from 'react-router-dom';
import axios from 'axios';
import config from '../constants/config';
import UserNavigation from './UserNavigation';
import AppNavbar from '../AppNavbar'
export default class PaymentFormForBooking extends React.Component {

  constructor(props)
    {
        super(props);
        this.state={
            nextPage:null,
            cvc: '',
            expiry: '',
            focus: '',
            name: '',
            number: '',
            error:false,
            errorMsg:''
        }
    }

  handleInputFocus = (e) => {
    this.setState({ focus: e.target.name });
  }
  
  handleInputChange = (e) => {
    const { name, value } = e.target;
    
    this.setState({ [name]: value });
  }
  submit = e => {
    e.preventDefault();
    
    if(this.state.cvc === "" || this.state.expiry === "" || this.state.name === "" || this.state.number === "") {
      this.setState({
        errorMsg : <Alert color="danger">
        Invalid Card Details. Please check again
      </Alert>,
      error:false
      })
      return false;
    }
    else {
      console.log("From Payment Form")
      this.props.onSubmit();
      return true
    }

  } 
  render() { 
    var errorMsg = null;
    if(this.state.error)
    {
      errorMsg = this.state.errorMsg
    }
    if(this.state.nextPage) {
      return (<Redirect to='/userHome'/>);
     }
    return (
      <div id="payment-form-booking" style={{width : '100%'}}>
        <div className='row'>
        <div className='col-md-6 col-sm-6'>
                <Cards
                    cvc={this.state.cvc}
                    expiry={this.state.expiry}
                    focused={this.state.focus}
                    name={this.state.name}
                    number={this.state.number}
                    />
            </div>
          <div className='col-md-6 col-sm-6'>
                
                {/* <Form> */}
                    <FormGroup>
                        <Input type="tel" name="number" required placeholder="Card Number" onChange={this.handleInputChange} onFocus={this.handleInputFocus}/>
                    </FormGroup>
                    <FormGroup>
                        <Input type="text" name="name" placeholder="Name on Card" onChange={this.handleInputChange} onFocus={this.handleInputFocus}/>
                    </FormGroup>
                    <FormGroup>
                        <Input type="month" name="expiry" placeholder="Expriry Date" onChange={this.handleInputChange} onFocus={this.handleInputFocus}/>
                    </FormGroup>
                    <FormGroup>
                        <Input type="password" name="cvc" placeholder="Enter Your CVC" onChange={this.handleInputChange} onFocus={this.handleInputFocus}/>
                    </FormGroup>
                    <Button type='primary' onClick={this.reset}>Reset</Button>
                    <Button type='primary' onClick={this.submit}>Submit</Button>
                {/* </Form> */}
                {errorMsg}
            </div>

        </div>
        </div>

    );
  }
}