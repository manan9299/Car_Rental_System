import React, {Component} from 'react';
import { Form, Button } from 'react-bootstrap'
import axios from 'axios';
import config from '../constants/config'
import AdminNavBar from './AdminNavBar';

class ManagePricing extends Component {

	constructor(props){
		super(props);

		this.state = {
			address : "",
            locationCapacity : "",
            message : ""
		}
	}

	saveLocation = (event) => {
		event.preventDefault();
		let reqData = {
            address : this.state.address,
            location_capacity : parseInt(this.state.locationCapacity)
        }
		
		// set withCredentials to true in order to send cookies with request
		axios.defaults.withCredentials = true;
        console.log("===============payload==================")
        console.log(reqData);

		axios.defaults.withCredentials = true;
		axios.post(config.serverURI + "/admin/location/add", reqData)
			.then(response => {

                console.log("==============response===================")
                console.log(response)
                
				let status = response.status;

				if (status == 200){
					this.setState({
						message : "Pricing updated Successfully"
					});
					
				} else {
					this.setState({
						message : "Failed to update pricing"
					})
				}
			})
	}

	capacityChangeHandler = (event) => {
		this.setState({
			locationCapacity : event.target.value
		});
	}

	addressChangeHandler = (event) => {
		this.setState({
			address : event.target.value
		});
	}


	render() {
		// let redirectVar = null;
		// if(cookie.load('grubhubcookie')){
		// 	redirectVar = <Redirect to= "/buyerhome"/>
		// }
		// let {isEmailValid, isPasswordValid} = this.state;
		// let emailErrorMessage = isEmailValid ? "" : "Email is Invalid";
		// let passwordErrorMessage = isPasswordValid ? "" : "Password is Invalid";

		return (
			<div>
				<div>
                    <AdminNavBar />
                </div>
				<div className="offset-sm-4 col-sm-3">
					{/* {redirectVar} */}
					<Form style={{color: 'black'}}>
						<Form.Text>
						    Manage Membership
						</Form.Text>
						<Form.Group >
							<Form.Label>Six month membership fee</Form.Label>
							<Form.Control onChange={this.addressChangeHandler} className='form-group' type="text" />
						</Form.Group>
						<Form.Group >
							<Form.Label>Late return fee</Form.Label>
							<Form.Control onChange={this.capacityChangeHandler} className='form-group' />
						</Form.Group>
						<Form.Group>
							<Button onClick={this.saveLocation} variant="primary" type="submit" block>
								Update Pricing
							</Button>
							<br/>
							{this.state.message}
						</Form.Group>
					</Form>
				</div>
			</div>
                
		);
	}
}

export default ManagePricing;
