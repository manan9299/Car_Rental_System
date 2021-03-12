import actionContants  from '../../constants/actionConstants';
const initialState = [];

export default (state = initialState, action)=>{
    switch(action.type){
        case actionContants.UPDATE_BOOKING_FORM:
            return action.data
        default:
            return state
    }
}