import React, {Component} from 'react';
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';
import config from '../constants/config';
import Select from 'react-select';
import AdminNavBar from './AdminNavBar';

class RemoveUser extends Component {

	constructor(props){
		super(props);

		this.state = {
            message : "",
            usersOptions : [],
            selectedUserId : ""
		}
    }
    
    
    componentDidMount() {
		
		axios.defaults.withCredentials = true;
        axios.get(config.serverURI + '/admin/allUsers')
        .then(response => {
            if(response.status == 200){
                let users = response.data;
                let userOpts = users.map( user => {
                    return { 
                        label: user.email,
                        value: user.id
                    }
                });
                
                this.setState({
                    usersOptions : userOpts
                });
            }
        });

    };
    

	terminateMembership = (event) => {
        event.preventDefault();
        let yesterdayDate = new Date();
        yesterdayDate.setDate(yesterdayDate.getDate() - 2);
        yesterdayDate = yesterdayDate.toISOString().substr(0,19);
        // let formattedDate = yesterdayDate.getFullYear() + '-' + 
        // ((yesterdayDate.getMonth() > 8) ? (yesterdayDate.getMonth() + 1) : ('0' + (yesterdayDate.getMonth() + 1))) + '-' + 
        // ((yesterdayDate.getDate() > 9) ? yesterdayDate.getDate() : ('0' + yesterdayDate.getDate()))
		let reqData = {
            id : this.state.selectedUserId,
            membership_expiry_date : yesterdayDate
        }
		
		// set withCredentials to true in order to send cookies with request
		// axios.defaults.withCredentials = true;

        console.log("===============payload==================")
        console.log(reqData);

		axios.defaults.withCredentials = true;
		axios.put(config.serverURI + "/updateUser", reqData)
			.then(response => {

                console.log("==============response===================")
                console.log(response)
                
				let status = response.status;

				if (status == 200){
					this.setState({
						message : "Membership terminated Successfully"
					});
					
				} else {
					this.setState({
						message : "Failed to terminate membership"
					})
				}
            })
            .catch(error => {
                this.setState({
                    message : "Failed to terminate membership"
                })
            })
	}

	userSelectionHandler = (user) => {
        console.log("=========user============");
        console.log(user);

		this.setState({
			selectedUserId : user.value
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
						Terminate User Membership
						</Form.Text>
						<Form.Group >
							<Form.Label>Select User</Form.Label>
							<Select 
								onChange={this.userSelectionHandler} 
								options={this.state.usersOptions}
							/>
						</Form.Group>
						<Form.Group>
							<Button onClick={this.terminateMembership} variant="primary" type="submit" block>
								Terminate Membership
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

export default RemoveUser;
