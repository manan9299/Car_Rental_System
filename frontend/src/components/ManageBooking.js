import React, { Component } from 'react';
// import {Table} from 'reactstrap';
import AppNavbar from '../AppNavbar';
import MaterialTable from 'material-table';
import UserNavigation from './UserNavigation';
import axios from 'axios';
import config from '../constants/config';
import Search from '@material-ui/icons/Search'
import ViewColumn from '@material-ui/icons/ViewColumn'
import SaveAlt from '@material-ui/icons/SaveAlt'
import ChevronLeft from '@material-ui/icons/ChevronLeft'
import ChevronRight from '@material-ui/icons/ChevronRight'
import FirstPage from '@material-ui/icons/FirstPage'
import LastPage from '@material-ui/icons/LastPage'
import Add from '@material-ui/icons/Add'
import Check from '@material-ui/icons/Check'
import FilterList from '@material-ui/icons/FilterList'
import Remove from '@material-ui/icons/Remove'
import Edit from '@material-ui/icons/Edit';
import ResetSearch from '@material-ui/icons/Clear'
import AssignmentReturnIcon from '@material-ui/icons/AssignmentReturn';
// import Popup from "reactjs-popup";



export class ManageBooking extends Component {
    
    constructor(){
        super();
        this.state = {
            columns : [
              { title: 'Vehicle Number', field: 'vehicle.vehicleNumber' },
              { title: 'Vehicle Make', field: 'vehicle.make' },
              { title: 'Vehicle Model', field: 'vehicle.model' },
              { title: 'Vehicle Type', field: 'vehicle.vehicleType' },
              { title: 'Vehicle Condition', field: 'vehicle.condition' },
              { title: 'Start time', field: 'bookingStartTime' },
              { title: 'End time', field: 'bookingEndTime'},
              { title: 'Status', field: 'status' },
            ],
            columnsPrevious: [
              { title: 'Vehicle Number', field: 'vehicle.vehicleNumber' },
              { title: 'Vehicle Make', field: 'vehicle.make' },
              { title: 'Vehicle Model', field: 'vehicle.model' },
              { title: 'Vehicle Type', field: 'vehicle.vehicleType' },
              { title: 'Vehicle Condition', field: 'vehicle.condition' },
              { title: 'Start time', field: 'bookingStartTime' },
              { title: 'End time', field: 'bookingEndTime'},
              { title: 'Status', field: 'status' },
              { title: 'Charges', field: 'charges'}
            ],
            previous : [],
            upcoming : []
        }
        
    }

    componentDidMount()
    {
        this.getBookings();
    }

    getReservations(){
        axios.defaults.withCredentials = true;

            axios.get(config.serverURI+"/reservation/user/" + parseInt(localStorage.getItem('user_id'))/*this.state.userId*/, {
            })
            .then(async response => {
                const reservations = await response.data;
                this.setState({
                    previous: reservations.previous,
                    upcoming: reservations.upcoming
                });
            }).catch(err=>{
                console.error(err);
            });
    }

    getBookings(){

        axios.defaults.withCredentials = true;
            axios.get(config.serverURI+"/currentuser")
            .then(async response => {
                const userData = await response.data;
        
                this.setState({
                    userId: userData.id
                }, this.getReservations);
            }).catch(error=> {
                console.log(error);
                console.log("User fetch failed");
                return;
            })
    }

    returnVehicle(rowData) {
      console.log(rowData);
      var data = {
        orderId: rowData.orderId
      }
      console.log(data);

      var currentDate = new Date().toISOString();
      var startTime = new Date(rowData.bookingStartTime).toISOString();
      
      if (startTime > currentDate){
        console.log("Can not return ahead of time");
        console.log("Please cancel the booking");
      } else {

        axios.defaults.withCredentials = true;                      
        axios.post(config.serverURI+"/reservation/return", data)
        .then(async response => {
          const responseData = await response.data;
          console.log(responseData);
          this.componentDidMount()
        })
        .catch(error=>{
          console.error(error);      
        });
      }
      
    }

    render() {
        return (
            <div className='container-fluid'>
                <AppNavbar/>
                <div className='row'>
                  <div className='col-md-2'>
                    <UserNavigation />
                  </div>
                  <div className='col-md-10'>
                  <MaterialTable
                      title="Previous Bookings"
                      icons={{ 
                        Check: Check,
                        DetailPanel: ChevronRight,
                        Edit: Edit,
                        Export: SaveAlt,
                        Filter: FilterList,
                        FirstPage: FirstPage,
                        LastPage: LastPage,
                        NextPage: ChevronRight,
                        PreviousPage: ChevronLeft,
                        ResetSearch: ResetSearch,
                        Search: Search,
                        ThirdStateCheck: Remove,
                      }}
                      columns={this.state.columnsPrevious}
                      data={this.state.previous}
                    />     
                    </div>
                
                <div className='col-md-2'>    
                </div>

                <div className='col-md-10'>
                  
                  <MaterialTable
                      title="Upcoming Bookings"
                      localization={{
                        
                        header: {
                            actions: 'Booking'
                        }
                    }}
                      icons={{ 
                        Check: Check,
                        DetailPanel: ChevronRight,
                        Edit: Edit,
                        Export: SaveAlt,
                        Filter: FilterList,
                        FirstPage: FirstPage,
                        LastPage: LastPage,
                        NextPage: ChevronRight,
                        PreviousPage: ChevronLeft,
                        ResetSearch: ResetSearch,
                        Search: Search,
                        ThirdStateCheck: Remove,
                      }}
                      columns={this.state.columns}
                      data={this.state.upcoming}
                      actions={[
                        {
                            icon: Remove,
                            tooltip: 'Cancel Booking',
                            onClick: (event, rowData) => {
                              // Do save operation
                              console.log(event);
                              console.log(rowData);
                              axios.defaults.withCredentials = true;
                              
                              axios.put(config.serverURI+"/reservation/" + rowData.orderId + "/cancel")
                              .then(async response => {
                                const data = await response.data;
                                this.componentDidMount()
                              })
                              .catch(error=>{
                                console.error(error);      
                              });
                              // console.log("Updating");
                              // this.forceUpdate();
                            }
                        },
                        {
                          icon: AssignmentReturnIcon,
                          tooltip: 'Return Vehicle',
                          onClick: (event, rowData) => {
                            this.returnVehicle(rowData);
                          }
                        }
                      ]}
                    
                        />     
                    </div>
                  </div>
                  
                </div>
        )
    }
}

export default ManageBooking
