import React from 'react';

import Actions from '../forms/Actions.tsx';
import T from 'i18n-react';
import FormField from '../forms/FormField.tsx';
import Card from '../app/Header.tsx';
import { Collapse }  from 'react-bootstrap';
import classNames from 'classnames';


interface Props {
  vehicleType: string;
  commission: CommissionGroupData;
  handleChange:  (s: string) => ((e: any) => void);
  showForm: boolean;
  toggleForm: void;
}

interface ComFormProps {
  label: string;
  onChange:  (e: any) => void;
  value: number;
  className?: string;
}

const COM: React.StatelessComponent<ComFormProps> = props => {

  const label = T.translate(props.label);
  const classes = classNames('form-group', 'col-md-12', 'commission-input', 'fleet', props.className);

  return (<div className={ classes }>
    <div className='col-md-9'><label className='ver'>{label}</label></div><div className='col-md-3'>
    <T.text
      tag='input'
      type='number'
      placeholder={ label }
      className='form-control'
      onChange= { props.onChange }
      value={ props.value || '' }/></div></div>
  );
}

class CommissionGroupForm extends React.Component<Props, {}> {

  constructor(props: Props) {
    super(props);
  }

  render() {
    return (
      <div className='col-md-12'>
        <Card>
          <div className='card-content fleets' onClick={this.props.toggleForm}>
            <div className='fleet'>
              <h3>{ this.props.vehicleType }</h3>
              <div className='actions pull-right'>
                <h3>
                  <span className='glyphicon glyphicon-menu-right' />
                </h3>
              </div>
            </div>
          </div>
        </Card>

        <Collapse in = { this.props.showForm }>
          <div className='fleets commission-wrapper'>
                      <COM className='border-bottom' value ={ this.props.commission.burgerlijkeAansprakelijkheid.value } label='Burgerlijke aansprakelijkheid' onChange={ this.props.handleChange('burgerlijkeAansprakelijkheid') }/>
                      <COM className='border-bottom' value ={ this.props.commission.burgerlijkeAansprakelijkheid.value } label='Omnium' onChange={ this.props.handleChange('burgerlijkeAansprakelijkheid') }/>
                      <COM className='border-bottom' value ={ this.props.commission.burgerlijkeAansprakelijkheid.value } label='Rechtsbijstand' onChange={ this.props.handleChange('burgerlijkeAansprakelijkheid') }/>
                      <COM className='border-bottom' value ={ this.props.commission.burgerlijkeAansprakelijkheid.value } label='Veiligheid' onChange={ this.props.handleChange('burgerlijkeAansprakelijkheid') }/>
                      <COM value ={ this.props.commission.burgerlijkeAansprakelijkheid.value } label='Reisbijstand' onChange={ this.props.handleChange('burgerlijkeAansprakelijkheid') }/>
          </div>
        </Collapse>
      </div>
    );
  }
}

export default CommissionGroupForm;
