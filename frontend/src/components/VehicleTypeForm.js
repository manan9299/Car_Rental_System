import React, {Component} from 'react';
import { Form, Button } from 'react-bootstrap'
import axios from 'axios';
import config from '../constants/config'
import AdminNavBar from './AdminNavBar'
import {Redirect} from 'react-router';
import cookie from 'react-cookies';

class VehicleTypeForm extends Component {

	constructor(props){
		super(props);

		this.state = {
			typeName : "",
			fromHours : "",
			toHours : "",
			price : "",
			message : ""
		}
		
		this.typeNameChangeHandler = this.typeNameChangeHandler.bind(this);
		this.fromHoursChangeHandler = this.fromHoursChangeHandler.bind(this);
		this.toHoursChangeHandler = this.toHoursChangeHandler.bind(this);
		this.priceChangeHandler = this.priceChangeHandler.bind(this);
		this.saveVehicleType = this.saveVehicleType.bind(this);
	}

	saveVehicleType = (event) => {
		event.preventDefault();
		let reqData = {
			vehicle_type : this.state.typeName,
			range_start : this.state.fromHours,
			range_end : this.state.toHours,
			price : this.state.price
		}
		
		// set withCredentials to true in order to send cookies with request
		axios.defaults.withCredentials = true;

		axios.post(config.serverURI + "/admin/vehicleType/add", reqData)
			.then(response => {
				let status = response.status;

				if (status == 200){
					this.setState({
						message : "Vehicle Type Added Successfully"
					});
					
				} else {
					this.setState({
						message : "Error while adding vehicle type"
					})
				}
			})
	}

	fromHoursChangeHandler = (event) => {
		this.setState({
			fromHours : event.target.value
		});
	}

	toHoursChangeHandler = (event) => {
		this.setState({
			toHours : event.target.value
		});
	}

	priceChangeHandler = (event) => {
		this.setState({
			price : event.target.value
		});
	}

	typeNameChangeHandler = (event) => {
		this.setState({
			typeName : event.target.value
		});
	}


	render() {
		let redirectVar = null;
        if(!cookie.load(config.sessionCookie)){
            redirectVar = <Redirect to='/login'/>
        }
		
		return (
			<div>
				{redirectVar}
				<div>
                    <AdminNavBar />
                </div>
				<div className="offset-sm-4 col-sm-3">
					{/* {redirectVar} */}
					<Form style={{color: 'black'}}>
						<Form.Text>
						New Vehicle Type Info
						</Form.Text>
						<Form.Group >
							<Form.Label>Type of Vehicle</Form.Label>
							<Form.Control onChange={this.typeNameChangeHandler} className='form-group' type="text" />
						</Form.Group>
						<Form.Group >
							<Form.Label>From Hours</Form.Label>
							<Form.Control onChange={this.fromHoursChangeHandler} className='form-group' />
						</Form.Group>
						<Form.Group >
							<Form.Label>To Hours</Form.Label>
							<Form.Control onChange={this.toHoursChangeHandler} className='form-group' />
						</Form.Group>
						<Form.Group >
							<Form.Label>Price</Form.Label>
							<Form.Control onChange={this.priceChangeHandler} className='form-group' />
						</Form.Group>
						<Form.Group>
							<Button onClick={this.saveVehicleType} variant="primary" type="submit" block>
								Add new type
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

export default VehicleTypeForm;
