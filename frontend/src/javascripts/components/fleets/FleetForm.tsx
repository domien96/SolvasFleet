import React from 'react';

class FleetForm extends React.Component<any, any> {
  constructor(props : any) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  onSubmit(e : any) : void {
    e.preventDefault();
  }

  handleChange(field : Fleet.Field) : any {
    return (e : any) => {
      this.props.handleChange(field, e);
    }
  }

  render() {
    return (
      <form method='post' onSubmit={ this.props.onSubmit } className='fleet-form'>
        <h3><label htmlFor='name'>Name:</label></h3>
        <span>
          <input type='text' id='name' onChange={ this.handleChange('name') }/>
        </span>
        <div className='actions pull-right'>
          <h3>
            <span className='glyphicon glyphicon-plus' />
          </h3>
        </div>
      </form>
    )
  }
}

export default FleetForm;
