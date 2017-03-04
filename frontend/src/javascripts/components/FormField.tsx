import React      from 'react';
import classNames from 'classnames';

export default class FormField extends React.Component<LoginField, {}> {
  public errors() : string {
    return classNames(
      { 'has-error': this.props.hasError }, 
      'form-control'
    );
  }

  render() {
    return (
      <div className='form-group'>
        <input 
          type={ this.props.type } 
          placeholder={ this.props.field } 
          className={ this.errors() } 
          onChange= { this.props.callback } />
      </div>
    )
  }
}
