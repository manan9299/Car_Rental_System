import React, { Component } from 'react'
import AccountBoxIcon from '@material-ui/icons/AccountBox';
import TimeToLeaveIcon from '@material-ui/icons/TimeToLeave';
import EventIcon from '@material-ui/icons/Event';
import PaymentIcon from '@material-ui/icons/Payment';
import HomeIcon from '@material-ui/icons/Home';

export class UserNavigation extends Component {
    render() {
        return (
            <nav id="user-nav" className="nav nav-tabs flex-column" style={{textAlign:"left"}}>
                <a className="nav-link" href="/userHome"><HomeIcon/>Dashboard</a>
                <a className="nav-link" href="/userProfile"><AccountBoxIcon/>Profile</a>
                <a className="nav-link" href="/bookings"><EventIcon/>Manage Booking</a>
                <a className="nav-link" href="/membershipForm"><PaymentIcon/>Memebership</a>
                <a className="nav-link" href="/renting"><TimeToLeaveIcon/>Renting</a>
                
            </nav>
        )
    }
}

export default UserNavigation
