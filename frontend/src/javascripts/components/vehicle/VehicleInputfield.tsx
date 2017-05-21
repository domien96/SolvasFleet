import React from 'react';
import T from 'i18n-react';
import { Typeahead } from 'react-bootstrap-typeahead';
import { fetchVehicles } from '../../actions/vehicle_actions.ts';
import classNames from 'classnames';

interface Props {
  value: number;
  callback: (e: any) => void;
  placeholder: string;
  hasError: boolean;
}

interface State {
  vehicles: VehicleData[];
}

class VehicleInputfield extends React.Component<Props, State> {
  constructor(props: any) {
    super(props);
    this.state = { vehicles: [] };
    this.fetchVehicles = this.fetchVehicles.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount() {
    this.fetchVehicles();
  }

  componentWillReceiveProps(nextProps: any) {
    if (nextProps.value !== this.props.value) {
      this.fetchVehicles();
    }
  }

  handleChange(selectedVehicles: string[]) {
    if (selectedVehicles) {
      if (selectedVehicles[0]) {
        this.props.callback({ target: { value: parseInt(selectedVehicles[0].split(':')[0], 10) } });
      } 
    }
  }

  fetchVehicles() {
    fetchVehicles((data: any) => {
      this.setState({ vehicles: data.data })
    });
  }

  render() {
    let optionList: string[] = [];
    let selected: string[] = [];
    if (this.state.vehicles) {
      optionList = this.state.vehicles.map((c: VehicleData) => {
        let option = `${c.id.toString()}: ${c.licensePlate} - ${c.vin}`;
        if (c.id === this.props.value) {
          selected.push(option);
        }
        return option;
      });
    }

    const label = T.translate(this.props.placeholder);
    const wrapperClasses = classNames('form-group', { 'has-error': this.props.hasError });

    return(
      <div className={ wrapperClasses }>
        <label>{ label }</label>
        <Typeahead onChange={ this.handleChange } options={ optionList } selected={ selected }/>
      </div>
    );
  }
}

export default VehicleInputfield;
