import React, {Component} from 'react';
import { Form } from 'react-bootstrap';

class VehicleTypeDropdown extends Component{
    
    getDropdownItems = () => {
        let items = this.props.items;
        items = items.map((item) => {
            return (
                <option key={item}>{item}</option>
            );
        });

        return items;
    }
    
    
    render() {
        let itemsList = this.getDropdownItems();

        return (
            <Form.Control as="select" onChange={e => this.props.onClick(e.target.value)}>
                {itemsList}
            </Form.Control>
                        
        );
    }
}

export default VehicleTypeDropdown;