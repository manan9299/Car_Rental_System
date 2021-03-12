import React, {Component} from 'react';
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';
import config from '../constants/config';
import Select from 'react-select';
import AdminNavBar from './AdminNavBar';

class ManageVehicleLocation extends Component {

	constructor(props){
		super(props);

		this.state = {
            message : "",
            vehicleOptions : [],
            locationOptions : [],
            selectedVehicleNumber : "",
            selectedLocation : ""
		}
    }
    componentDidMount() {
		
		axios.defaults.withCredentials = true;
        axios.get(config.serverURI + '/admin/vehicle/all')
        .then(response => {
            if(response.status == 200){
                let vehicles = response.data;
                let vehOpts = vehicles.map( vehicle => {
                    return { 
                        label: vehicle.vehicleNumber,
                        value: vehicle.vehicleNumber
                    }
                });
                
                this.setState({
                    vehicleOptions : vehOpts
                });
            }
        });

        axios.get(config.serverURI + '/admin/locations')
        .then(response => {
            if(response.status == 200){
                let locations = response.data;
                let locOpts = locations.map( location => {
                    return { 
                        label: location.address,
                        value: location.location_id
                    }
                });

                this.setState({
                    locationOptions : locOpts
                });
            }
        });

    };
    

	updateVehicleLocation = (event) => {
		event.preventDefault();
		let reqData = {
            vehicle_number : this.state.selectedVehicleNumber,
            location_id : this.state.selectedLocation
        }
		
		// set withCredentials to true in order to send cookies with request
		// axios.defaults.withCredentials = true;

        console.log("===============payload==================")
        console.log(reqData);

		axios.defaults.withCredentials = true;
		axios.post(config.serverURI + "/admin/vehicleLocation", reqData)
			.then(response => {

                console.log("==============response===================")
                console.log(response)
                
				let status = response.status;

				if (status == 200){
					this.setState({
						message : "Vehicle Location Updated Successfully"
					});
					
				} else {
					this.setState({
						message : "Error while updating vehicle Location"
					})
				}
			})
	}

	vehicleSelectionHandler = (vehicleNumber) => {
        console.log("=========vehicleNumber============");
        console.log(vehicleNumber);

		this.setState({
			selectedVehicleNumber : vehicleNumber.label
		});
	}

	locationSelectionHandler = (location) => {
        console.log("=========location============");
        console.log(location);

		this.setState({
			selectedLocation : location.value
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
						Update Vehicle Location
						</Form.Text>
						<Form.Group >
							<Form.Label>Select Vehicle</Form.Label>
							<Select 
								onChange={this.vehicleSelectionHandler} 
								options={this.state.vehicleOptions}
							/>
						</Form.Group>
						<Form.Group >
							<Form.Label>Select Location</Form.Label>
							<Select
							onChange={this.locationSelectionHandler}
							options={this.state.locationOptions}
							/>
						</Form.Group>
						<Form.Group>
							<Button onClick={this.updateVehicleLocation} variant="primary" type="submit" block>
								Update Location
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

export default ManageVehicleLocation;
