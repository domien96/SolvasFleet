import React from 'react';

class FleetForm extends React.Component<any, any> {
  constructor(props : any) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onSubmit(e : any) : void {
    e.preventDefault();
  }

  render() {
    return (
      <form method='post' onSubmit={ this.props.onSubmit } className='fleet-form'>
        <h3>Name:</h3>
        <span>
          <input type='text' />
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
