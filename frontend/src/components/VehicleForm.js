import React, {Component} from 'react';
import { Form, Button, Row, Col } from 'react-bootstrap'
import DatePicker from 'react-date-picker';
import axios from 'axios';
import styles from '../css/Form.css';
import config from '../constants/config';
import VehicleTypeDropdown from './VehicleTypeDropdown';
import AdminNavBar from './AdminNavBar';

class VehicleForm extends Component {

	constructor(props){
		super(props);

		this.state = {
			vehicleNumber : "",
			make : "",
			model : "",
            year : "",
            registration : "",
            mileage : "",
            lastServiced : new Date(),
            condition : "",
            message : "",
            selectedVehicleType : "",
            vehicleTypes : [],
            status : true,
            message : ""
        }
    }
    
    componentDidMount() {
        
        axios.defaults.withCredentials = true;
        axios.get(config.serverURI + '/admin/vehicleType/all')
        .then(response => {
            if(response.status == 200){
                this.setState({
                    vehicleTypes : response.data,
                    selectedVehicleType : response.data[0]
                });
            }
        });

    };

	saveVehicle = (event) => {
        event.preventDefault();
        let date = this.state.lastServiced;
        let formattedDate = date.getFullYear() + '/' + ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate()))

        console.log("lastServiced ")
        console.log(formattedDate.toString())

        let reqData = {
            vehicle_number : this.state.vehicleNumber,
            make : this.state.make,
            model : this.state.model,
            year : this.state.year,
            registration_number : this.state.registration,
            mileage : this.state.mileage,
            last_serviced : formattedDate,
            condition : this.state.condition,
            vehicle_type : this.state.selectedVehicleType
        }

        console.log("payload")
        console.log(reqData);

        axios.defaults.withCredentials = true;
        axios.post(config.serverURI + '/admin/vehicle', reqData)
        .then(response => {
            console.log("==============response===================")
            console.log(response)
            if(response.status == 200){
                this.setState({
                    status : true,
                    message : "Saved Vehicle Successfully"
                });
            } else {
                this.setState({
                    status : false,
                    message : "Could not Save Vehicle"
                })
            }

        })
        .catch( error => {
            this.setState({
                status : false,
                message : "Could not Save Vehicle"
            })
        })
	}

    
    lastServicedDateChange = (date) => {
        this.setState({
            lastServiced: date
        });
    };

    setVehicleType = (vehicleType) => {
        this.setState({
            selectedVehicleType : vehicleType
        });
    }

    setVehicleNumber = (event) => {
        this.setState({
            vehicleNumber : event.target.value
        })
    }

    setMake = (event) => {
        this.setState({
            make : event.target.value
        })
    }

    setModel = (event) => {
        this.setState({
            model : event.target.value
        })
    }

    setYear = (event) => {
        this.setState({
            year : event.target.value
        })
    }

    setRegistration = (event) => {
        this.setState({
            registration : event.target.value
        })
    }

    setVehicleNumber = (event) => {
        this.setState({
            vehicleNumber : event.target.value
        })
    }

    setMileage = (event) => {
        this.setState({
            mileage : event.target.value
        })
    }

    setCondition = (event) => {
        this.setState({
            condition : event.target.value
        })
    }

	render() {

		return (
            <div>
                <div>
                    <AdminNavBar />
                </div>
                <div className="offset-sm-2 col-sm-8">
                    <Form style={{color: 'black'}}>
                        <Form.Row>
                            <Col>
                                <Form.Text>
                                New Vehicle Info
                                </Form.Text>
                            </Col>
                        </Form.Row>
                        <Form.Row >
                            <Col>
                            <Form.Label>Vehicle Type</Form.Label>
                            </Col>
                            <Col style={{color: 'black', margin: '5px'}}>
                            <VehicleTypeDropdown onClick={this.setVehicleType} items={this.state.vehicleTypes} />
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Vehicle Number</Form.Label>
                            </Col>
                            <Col>
                            <Form.Control className='form-group' onChange={this.setVehicleNumber} type="text" required/>
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Make</Form.Label>
                            </Col>
                            <Col>
                            <Form.Control className='form-group' onChange={this.setMake} type="text" />
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Model</Form.Label>
                            </Col>
                            <Col>
                            <Form.Control className='form-group' onChange={this.setModel} type="text" />
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Year</Form.Label>
                            </Col>
                            <Col>
                            <Form.Control className='form-group' onChange={this.setYear} type="text" />
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Registration Number</Form.Label>
                            </Col>
                            <Col>
                            <Form.Control className='form-group' onChange={this.setRegistration} type="text" />
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Mileage</Form.Label>
                            </Col>
                            <Col>
                            <Form.Control className='form-group' onChange={this.setMileage} type="text" />
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Last Serviced</Form.Label>
                            </Col>
                            <Col>
                            <div style={{color: 'black', marginBottom: '14px'}}>
                            <DatePicker onChange={this.lastServicedDateChange} value={this.state.lastServiced} />
                            </div>
                            
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            <Form.Label>Condition</Form.Label>
                            </Col>
                            <Col>
                            <Form.Control className='form-group' onChange={this.setCondition} type="text" />
                            </Col>
                        </Form.Row>
                        <Form.Row>
                            <Col>
                            </Col>
                            <Col>
                            <Button onClick={this.saveVehicle} variant="primary" type="submit" block>
                                Add Vehicle
                            </Button>
                            </Col>
                        </Form.Row>
                        <br/>
                        {this.state.message}
                    </Form>
                </div>
            </div>
                
		);
	}
}

export default VehicleForm;
