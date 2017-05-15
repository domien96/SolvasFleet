import React from 'react';
import { Link } from 'react-router';
import { th } from '../../utils/utils.ts';
import Card from '../app/Card.tsx';
import Pagination from '../pagination/Pagination.tsx';
import InfoTable from '../tables/InfoTable.tsx';
import T from 'i18n-react';
import Errors from '../app/Errors.tsx'

interface UProps {
  errors: Form.Error[];
  handleChange: (e: any) => void;
  addNewRoute: string;
  modelName: string;
}

const VehicleUpload: React.StatelessComponent<UProps>  = props =>  {
  return (
    <form method='post' encType='multipart/form-data'>
      <div className='wrapper'>
        <Errors errors={ props.errors } />
          <div className='pull-right'>
            <span className='glyphicon glyphicon-upload' aria-hidden='true'></span>
            Upload Vehicles (CSV)
            <input onChange={ props.handleChange } type='file' name='File Upload' id='csvFileUpload' accept='.csv' className='btn btn-default' />
            <Link to={props.addNewRoute} className='btn btn-default lab-margin pull-right'>
              <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
              { T.translate(props.modelName + '.addNew') }
            </Link>
          </div>
      </div>
    </form>
  );
};

interface Props {
  onSelect: any;
  addNewRoute: string;
  fetchModels: any;
  modelName: string;
  columns: string[];
  response: ListResponse;
  errors: Form.Error[];
  handleChange: (e: any) => void;
}

const VehicleListing: React.StatelessComponent<Props>  = props =>  {
  const tablehead = props.columns.map(c => {
    return th(c, `${props.modelName}.${c}`);
  });

  return (
    <div className='row'>
      <div className='col-xs-12'>
        <Card>
          <div className='card-content'>
            <VehicleUpload 
              errors={ props.errors } 
              handleChange={ props.handleChange } 
              modelName={ props.modelName }
              addNewRoute={ props.addNewRoute } />
            <InfoTable head={ tablehead } data={ props.response.data } onClick={ props.onSelect } />
            <Pagination onClick={ props.fetchModels } response={ props.response }/>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default VehicleListing;