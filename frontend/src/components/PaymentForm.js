import React from 'react';
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';
import {Form,FormGroup,Input,Label,Button} from 'reactstrap';
import '../css/PaymentForm.css'
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import config from '../constants/config';
import UserNavigation from './UserNavigation';
import AppNavbar from '../AppNavbar'
export default class PaymentForm extends React.Component {

  constructor(props)
    {
        super(props);
        this.state={
            nextPage:null,
            cvc: '',
            expiry: '',
            focus: '',
            name: '',
            number: ''
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
    
    if(this.state.cvc === "" || this.state.expiry === "" || this.state.name === "" || this.state.number === "") {
      return;
    } else {
      
      axios.defaults.withCredentials = true;
      axios.get(config.serverURI+"/currentuser")
      .then(async response => {
          const userData = await response.data;
          
          var todayDate = new Date();
          
          var expiryDate = new Date(userData.membership_expiry_date);

          if (todayDate > expiryDate) {
            expiryDate = todayDate;
          }

          expiryDate.setMonth(expiryDate.getMonth() + 6);
          var newExpiryDate = expiryDate.toISOString().slice(0,19);
          
          var data = {
            id: userData.id,
            membership_expiry_date: newExpiryDate

          }

          axios.defaults.withCredentials = true;
          axios.put(config.serverURI+"/updateUser",data)
          .then(async response => {
              const data = await response.data;
              
              this.setState({
                  membershipExpiryDate: data.membership_expiry_date,
                  nextPage: true
              })

          })
          .catch(error=>{
              console.error(error);
              
          });
          
      }).catch(error=> {
          console.log(error);
          console.log("User fetch failed");
          return;
      })
      
    }

  } 
  render() { 
    if(this.state.nextPage) {
      return (<Redirect to='/userHome'/>);
   }
    return (
      <div id="payment-form" style={{width : '100%'}}>
          <AppNavbar/>
          <h1>Extend membership</h1>
          <div className='row'>
          <div className="col-md-2">
              <UserNavigation/>
          </div>
          <div className='col-md-3'>
            <div className='col-md-6 col-sm-6'>
                <Cards
                    cvc={this.state.cvc}
                    expiry={this.state.expiry}
                    focused={this.state.focus}
                    name={this.state.name}
                    number={this.state.number}
                    />
            </div>
          </div>
          <div className='col-md-6'>
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
                    

            </div>
            </div>
          </div>
      </div>
    );
  }
}