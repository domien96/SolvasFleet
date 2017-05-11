import React from 'react';
import classNames from 'classnames';
import T from 'i18n-react';
import DatePicker from 'react-bootstrap-date-picker';

interface Props {
  label: string;
  hasError: boolean;
  callback: (e: any) => void;
  value: any;
}

const DateForm: React.StatelessComponent<Props> = props => {
  const label = T.translate(props.label);
  const wrapperClasses = classNames('form-group', { 'has-error': props.hasError });
  return (
    <div className={ wrapperClasses }>
      <label>{ label }</label>
      <DatePicker
        className='form-control'
        onChange= { props.callback }
        value={ props.value || new Date().toISOString() }
      />
    </div>
  );
};

export default DateForm;
