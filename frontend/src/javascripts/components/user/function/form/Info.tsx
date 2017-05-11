import React from 'react';

import RoleInputfield from '../../../auth/role/RoleInputfield.tsx';
import CompanyInputfield from '../../../client/CompanyInputfield.tsx';
import Card from '../../../app/Card.tsx';

interface Props {
  handleChange: (field : string, e: any) => void;
  hasError: (e: any) => boolean;
  Sfunctions: SFunctionData[];
}

const Info: React.StatelessComponent<Props> = props => {
  const handleChange = (field: SFunction.Field) => {
    return (e: any) => {
      props.handleChange(field, e);
    };
  };

  const { Sfunctions } = props;
  const selectedRole = Sfunctions[0].role;
  const selectedCompanies: number[] = Sfunctions.map((Sfunction: SFunctionData) => {
    return Sfunction.company;
  });

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-content'>
          <RoleInputfield 
            value={ [selectedRole] } 
            placeholder='function.role' 
            callback={ handleChange('role') } 
            hasError={ false } />
          <CompanyInputfield 
            value={ selectedCompanies } 
            placeholder='function.company' 
            callback={ handleChange('company') } 
            hasError={ false } 
            multiple={ true } />
        </div>
      </Card>
    </div>
  );
};


export default Info;
