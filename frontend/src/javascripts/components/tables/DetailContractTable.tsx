import React from 'react';
import T from 'i18n-react';
import { redirect_to } from '../../routes/router.tsx';

interface Props {
  insuranceCompanyData: Table.Head.Data;
  vehicleData: Table.Head.Data;
  data: Table.Head.Data[];
}

const DetailContractTable: React.StatelessComponent<Props> = props => {
  const tableRows = props.data.map((c: Table.Head.Data, i: number) => (
    <tr key={ i } className='table-row-left'>
      <td className='first-column'>{ T.translate(c.key) }</td>
      <td className='second-column'>{ c.label }</td>
    </tr>
  ));

  const { insuranceCompanyData, vehicleData } = props;

  return (
    <div className='table-wrap table-responsive'>
      <table className='table table-hover'>
        <tbody>
          <tr onClick={ () => { redirect_to(`/clients/${insuranceCompanyData.label.split(':')[0]}`); } } className='table-row-left'>
            <td className='first-column'>{ T.translate(insuranceCompanyData.key) }</td>
            <td className='second-column'>{ insuranceCompanyData.label }</td>
          </tr>
          <tr onClick={ () => { redirect_to(`/vehicles/${vehicleData.label}`); } } className='table-row-left'>
            <td className='first-column'>{ T.translate(vehicleData.key) }</td>
            <td className='second-column'>{ vehicleData.label }</td>
          </tr>
          </tbody>
      </table>
      <table className='table'>
        <tbody>
          { tableRows }
        </tbody>
      </table>
    </div>
  );
};

export default DetailContractTable;
