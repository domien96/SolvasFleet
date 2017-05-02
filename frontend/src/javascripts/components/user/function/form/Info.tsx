import React from 'react';

import FormChoice from '../../../forms/FormChoice.tsx';
import Card from '../../../app/Card.tsx';

interface Props {
  handleChange: (field: string, e: any) => void;
  hasError: (e: any) => boolean;
  Sfunction: SFunctionData;
  roles: RoleData[];
  companies: CompanyData[];
}

const Info: React.StatelessComponent<Props> = props => {
  const handleChange = (field: SFunction.Field) => {
    return (e: any) => {
      props.handleChange(field, e);
    };
  };

  const { roles, companies, Sfunction } = props;
  const { company, role } = Sfunction;

  const roleChoices: Choice.Data[] = roles.map((r: RoleData) => {
    return { key: r.id.toString(), label: r.name };
  });

  const companyChoices: Choice.Data[] = companies.map((c: CompanyData) => {
    return { key: c.id.toString(), label: c.name };
  });

  const allCompanies: Choice.Data = { key: '-1', label: 'All companies' };
  companyChoices.splice(0, 0, allCompanies);

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-content'>
          <FormChoice
            value={ role }
            placeholder='function.role'
            choices={ roleChoices }
            callback={ handleChange('role') } />
          <FormChoice
            value={ company }
            placeholder='function.company'
            choices={ companyChoices }
            callback={ handleChange('company') } />
        </div>
      </Card>
    </div>
  );
};

export default Info;
