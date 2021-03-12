import React, { Component } from 'react'
import MaterialTable from 'material-table';
import AppNavbar from '../AppNavbar';
import UserNavigation from './UserNavigation';
import Search from '@material-ui/icons/Search'
import ViewColumn from '@material-ui/icons/ViewColumn'
import SaveAlt from '@material-ui/icons/SaveAlt'
import ResetSearch from '@material-ui/icons/Clear'
import ChevronLeft from '@material-ui/icons/ChevronLeft'
import ChevronRight from '@material-ui/icons/ChevronRight'
import FirstPage from '@material-ui/icons/FirstPage'
import LastPage from '@material-ui/icons/LastPage'
import Add from '@material-ui/icons/Add'
import Check from '@material-ui/icons/Check'
import FilterList from '@material-ui/icons/FilterList'
import Remove from '@material-ui/icons/Remove'
import Edit from '@material-ui/icons/Edit';
import SearchIcon from '@material-ui/icons/Search';
import {TextField} from '@material-ui/core';
import axios from 'axios';
import {KeyboardDatePicker} from '@material-ui/pickers';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import { makeStyles } from '@material-ui/core/styles';
import DateFnsUtils from '@date-io/date-fns';
import config from '../constants/config';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import {updateBookingForm} from '../reducers/booking/actions'
import store from '../store'
import OrderReviewPage from './OrderReviewPage';
import { Redirect } from 'react-router-dom';
const useStyles = makeStyles((theme) => ({
  button: {
    margin: theme.spacing(1),
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    marginLeft: theme.spacing(1),
    marginRight: theme.spacing(1),
    width: 200,
  },
}));
export class RentingForm extends Component {
    state = {
      selectRow: null,
      modal:false,
      nextPage:null,
      vehicleDetails: {
        make:null,
        model:null,
        vehicle_number:null
      }, 
      columns : [
        { title: 'Company', field: 'make' },
        { title: 'Model', field: 'model' },
        { title: 'Registration Number', field: 'vehicle_number'},
        { title: 'Type', field:'type'},
        { title: 'Price', field:'price'},
        { title: 'Location', field:'address'}
      ],
      data : [],
      userDetails:null,
      content:null
    }

    componentDidMount(){
      axios.defaults.withCredentials = true;
      axios.get(config.serverURI+"/getVehicles")
        .then(response => response.data)
        .then(data => {
          console.log(data);
          this.setState({
            data: data
          })
        }).catch(error =>{
            console.log(error)
        })
      
    }

    toggleModal = () => {
      this.setState({
        modal: !this.state.modal
      })
    }
    onRowClick=(row) => {
      this.toggleModal()
      console.log(row)
      this.setState({
        vehicleDetails:{
          make:row.make,
          model:row.model,
          vehicle_number:row.vehicle_number,
          address:row.address,
          type:row.type,
          price:row.price
        }
      })
    }
    orderReviewPage=()=>{
        store.dispatch(updateBookingForm(this.state.vehicleDetails))
        this.setState({
          nextPage:true
        })
        localStorage.setItem('make',this.state.vehicleDetails.make);
        localStorage.setItem('model',this.state.vehicleDetails.model);
        localStorage.setItem('vehicle_number',this.state.vehicleDetails.vehicle_number);
        localStorage.setItem('address',this.state.vehicleDetails.address);
        localStorage.setItem('type',this.state.vehicleDetails.type);
        localStorage.setItem('price',this.state.vehicleDetails.price);
        
    }
    render() {
           
      var content =null;
      if(localStorage.getItem("membership"))
      {
        content = (
          <div className='constain-fluid'>
            <AppNavbar/>
              <div className='row'>
                <div className='col-md-2'>
                  <UserNavigation />
                </div>
                <div className='col-md-9'>
                <MaterialTable
                    title="Search your ride"
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
                    data={this.state.data}
                    onRowClick={(event,rowData) =>this.onRowClick(rowData)}
                    options={{
                      search: true
                    }}
                  /> 
                  <Modal isOpen={this.state.modal} toggle={()=>this.toggleModal()} >
                    <ModalHeader toggle={()=>this.toggleModal()}>Select Date and Time</ModalHeader>
                    <ModalBody>
                    <MuiPickersUtilsProvider className={useStyles.root} utils={DateFnsUtils}>
                      <div id='booking-modal'> 
                      <table class="table table-striped">
                          <tbody>
                            <tr>
                              <td>Company</td>
                              <td>{this.state.vehicleDetails.make}</td>
                            </tr>
                            <tr>
                              <td>Model</td>
                              <td>{this.state.vehicleDetails.model}</td>
                            </tr>                              
                            <tr>
                              <td>Registration Number</td>
                              <td>{this.state.vehicleDetails.vehicle_number}</td>
                            </tr>
                            <tr>
                              <td>Address</td>
                              <td>{this.state.vehicleDetails.address}</td>
                            </tr>
                            <tr>
                              <td>Price</td>
                              <td>{this.state.vehicleDetails.price}</td>
                            </tr>
                            <tr>
                              <td>Type</td>
                              <td>{this.state.vehicleDetails.type}</td>
                            </tr>
                              
                          </tbody>
                        </table>
                      </div>
                    </MuiPickersUtilsProvider>
                    </ModalBody>
                    <ModalFooter>
                      <Button color="secondary" onClick={()=>this.orderReviewPage()}>Confirm and Go to Payment</Button>
                    </ModalFooter>
                  </Modal>    
                  </div>
                </div>
              </div>
        );
       
      }
      else{
        content =<div className='constain-fluid'>
                    <AppNavbar/>
                    <div className='row'>
                    <div className='col-md-2'>
                    <UserNavigation />
                  </div>
                  <div className='col-md-9'>
                 <h2>Please purchase a membership to start booking.</h2>
                 </div>
                 </div>
                 </div>
      }
      if(this.state.nextPage)
      {
        content = <Redirect to='/orderReview' />
      }
      
      return content;
    }
}

export default RentingForm
