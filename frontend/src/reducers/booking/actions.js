import actionConstants from '../../constants/actionConstants'
import axios from 'axios'
export function updateBookingForm(data){
    return dispatch => {
        
        console.log(dispatch(setBookingForm(data)));
    }
} 

export function setBookingForm(data){
    console.log(data)    
    return {
        type:actionConstants.UPDATE_BOOKING_FORM,
        data
    }
} 