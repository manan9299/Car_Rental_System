import React, { Component } from 'react'
import Register from './Register';
import UserHome from './UserHome';
import MembershipForm from './MembershipForm';
export class UserForm extends Component {
    state={
        step:1
    }
    nextStep = () => {
        const {step} = this.state;
        this.setState({
            step : step + 1
        });
    }
    // Go back to previous step
    previousStep = () => {
        const {step} = this.state;
        this.setState({
            step : step - 1
        });
    }

    render() {
        const {step} = this.state;
        
        switch(step){
            case 1:
                return (
                    <Register
                    nextStep={this.nextStep}
                    />
                )
            case 2:
                return <MembershipForm
                nextStep={this.nextStep}
                />
            case 3:
                return <UserHome
                    nextStep={this.nextStep}
                    />
                    
        }
        
    }
}

export default UserForm
