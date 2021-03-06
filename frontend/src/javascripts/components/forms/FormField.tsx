import React from 'react';
import classNames from 'classnames';
import T from 'i18n-react';

interface Props {
  placeholder: string;
  type: string;
  hasError?: boolean;
  callback: (e: any) => void;
  value: any;
  name?: string
}

const FormField: React.StatelessComponent<Props> = props => {
  const label = T.translate(props.placeholder);
  const wrapperClasses = classNames('form-group', { 'has-error': props.hasError || false });
  return (
    <div className={ wrapperClasses }>
      <label>{ label }</label>
      <T.text
        tag='input'
        type={ props.type}
        placeholder={ label }
        className='form-control'
        onChange= { props.callback }
        name={props.name ||  ''}
        value={ props.value || '' }/>
    </div>
  );
};

export default FormField;
