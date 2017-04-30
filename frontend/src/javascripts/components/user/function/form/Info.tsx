import React from 'react';

import FormChoice from '../../../forms/FormChoice.tsx';

import Card from '../../../app/Card.tsx';

interface Props {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  Sfunction : SFunctionData;
  roles  : RoleData[];
  companies : CompanyData[];
}

const Info : React.StatelessComponent<Props> = props => {
  let handleChange = (field : SFunction.Field) => {
    return (e : any) => {
      props.handleChange(field, e);
    }
  }

  var { roles, companies, Sfunction } = props;
  var { company, role } = Sfunction;

  const roleChoices : Choice.Data[] = roles.map((r : RoleData) => {
    return { key: r.id.toString(), label: r.name };
  });

  const companyChoices : Choice.Data[] = companies.map((c : CompanyData) => {
    return { key: c.id.toString(), label: c.name };
  });

  let allCompanies : Choice.Data = { key: "-1", label: "All companies"};
  companyChoices.splice(0,0,allCompanies);
    
  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>Role form</h5>
        </div>
        <div className='card-content'>
          <FormChoice value={ role }      placeholder='function.role'      choices={ roleChoices }     callback={ handleChange('role') } />
          <FormChoice value={ company }   placeholder='function.company'   choices={ companyChoices }  callback={ handleChange('company') } />
        </div>
      </Card>
    </div>
  );
}

export default Info;
