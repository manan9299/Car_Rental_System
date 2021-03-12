import Home from './components/Home';
import Login from './components/Login';

import React, { Component } from 'react';

import { Switch,Route } from 'react-router-dom';
import UserProfile from './components/UserProfile';
import UserHome from './components/UserHome';
import MembershipForm from './components/MembershipForm';
import PaymentForm from './components/PaymentForm';
import ManageBooking from './components/ManageBooking';
import AdminHome from './components/AdminHome';
import VehicleTypeForm from './components/VehicleTypeForm'
import VehicleForm from './components/VehicleForm'

import RentingForm from './components/RentingForm';
import UserForm from './components/UserForm';
import OrderReviewPage from './components/OrderReviewPage';
import LocationForm from './components/LocationForm';
import ManageVehicleLocation from './components/ManageVehicleLocation';
import RemoveUser from './components/RemoveUser';
import ManagePricing from './components/ManagePricing';

export class Routes extends Component {
    render() {
        return (
            <div>   
                <Switch>              
                    <Route exact path="/login" component={Login}/>
                    <Route exact path="/membershipForm" component={MembershipForm} /> 
                    <Route exact path="/bookings" component={ManageBooking} />  
                    <Route exact path="/vehicleType" component={VehicleTypeForm}/>
                    <Route exact path="/vehicle" component={VehicleForm}/>
                    <Route exact path="/addLocation" component={LocationForm}/>
                    <Route exact path="/manageVehicleLocation" component={ManageVehicleLocation}/>
                    <Route exact path="/managePricing" component={ManagePricing}/>
                    <Route exact path="/removeUser" component={RemoveUser}/>
                    <Route exact path="/register" component={UserForm}/>
                    <Route exact path="/renting" component={RentingForm}/>
                    <Route exact path="/userProfile" component={UserProfile} />
                    <Route exact path="/userHome" component={UserHome} /> 
                    <Route exact path="/home" component={Home}/>
                    <Route exact path="/adminHome" component={AdminHome}/>
                    <Route exact path="/orderReview" component={OrderReviewPage}/>
                    <Route exact path="/" component={Home}/>
                    <Route exact path="/membershipForm" component={MembershipForm} />
                    <Route exact path="/paymentForm" component={PaymentForm} />

                </Switch>
            </div>
        );
    }
}

export default Routes
