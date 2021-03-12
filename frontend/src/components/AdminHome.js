import React, {Component} from 'react';
import { Form, Button, Card } from 'react-bootstrap';
import AdminNavBar from './AdminNavBar';
import {Redirect} from 'react-router';
import cookie from 'react-cookies';
import config from '../constants/config';

class AdminHome extends Component {

  	
	render() {

        let redirectVar = null;
        if(!cookie.load(config.sessionCookie)){
            redirectVar = <Redirect to='/login'/>
        }

        return(
            <div>
                {redirectVar}
                <div>
                    <AdminNavBar />
                </div>
                <div className="offset-sm-3 col-sm-8">
                    
                    {redirectVar}
                    <Form inline>
                        <Card style={{ width: '18rem', margin: '2rem', color: 'black'}} >
                            <Card.Body >
                                <Card.Title >Update Vehicle Location</Card.Title>
                                <Card.Text>
                                    Manage the vehicle location here
                                </Card.Text>
                                <Button variant="primary" href="manageVehicleLocation" block>Update</Button>
                            </Card.Body>
                        </Card>
                        <Card style={{ width: '18rem', margin: '2rem', color: 'black'}} >
                            <Card.Body >
                                <Card.Title >Add new Vehicles</Card.Title>
                                <Card.Text>
                                    Add new vehicles here
                                </Card.Text>
                                <Button variant="primary" href="/vehicle" block>Add Vehicle</Button>
                            </Card.Body>
                        </Card>
                        <Card style={{ width: '18rem', margin: '2rem', color: 'black'}} >
                            <Card.Body >
                                <Card.Title >Add new Vehicle Type</Card.Title>
                                <Card.Text>
                                    Add a new Vehicle and its pricing
                                </Card.Text>
                                <Button variant="primary" href="/vehicleType" block>Add Type</Button>
                            </Card.Body>
                        </Card>
                        <Card style={{ width: '18rem', margin: '2rem', color: 'black'}} >
                            <Card.Body >
                                <Card.Title >Add new Location</Card.Title>
                                <Card.Text>
                                    Add a new location and it's capacity
                                </Card.Text>
                                <Button variant="primary" href="/addLocation" block>Add Location</Button>
                            </Card.Body>
                        </Card>
                        <Card style={{ width: '18rem', margin: '2rem', color: 'black'}} >
                            <Card.Body >
                                <Card.Title >Update Vehicle</Card.Title>
                                <Card.Text>
                                    Change Properties of the vehicle
                                </Card.Text>
                                <Button variant="primary" href="/addLocation" block>Update vehicle</Button>
                            </Card.Body>
                        </Card>
                        <Card style={{ width: '18rem', margin: '2rem', color: 'black'}} >
                            <Card.Body >
                                <Card.Title >Manage Pricing</Card.Title>
                                <Card.Text>
                                    Change Membership fee and Late return fee
                                </Card.Text>
                                <Button variant="primary" href="/managePricing" block>Manage</Button>
                            </Card.Body>
                        </Card>
                        <Card style={{ width: '18rem', margin: '2rem', color: 'black'}} >
                            <Card.Body >
                                <Card.Title >Terminate membership</Card.Title>
                                <Card.Text>
                                    Terminate a user's membership for serious violations
                                </Card.Text>
                                <Button variant="light" href="/removeUser" block>Terminate</Button>
                            </Card.Body>
                        </Card>
                        
                    </Form>
                    
                </div>
            </div>
            
        );
        
	}
}

export default AdminHome;
