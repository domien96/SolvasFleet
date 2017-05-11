import React from 'react';
import { Link } from 'react-router';
import { th } from '../../utils/utils.ts';
import Card from '../app/Card.tsx';
import Pagination from '../pagination/Pagination.tsx';
import InfoTable from '../tables/InfoTable.tsx';
import T from 'i18n-react';
import Errors from '../app/Errors.tsx'

interface UProps {
  onSubmit: (e: any) => void;
  errors: Form.Error[];
  handleChange: (e: any) => void;
}

const VehicleUpload: React.StatelessComponent<UProps>  = props =>  {
  return (
    <form method='post' onSubmit={ props.onSubmit } >
      <div className='wrapper'>
        <Errors errors={ props.errors } />
        <div className=''>
          <div className=''>
            <input onChange={ props.handleChange } type="file" name="File Upload" id="csvFileUpload" accept=".csv" /> 
          </div>
          <div className=''>
            <button onClick={ props.onSubmit } className="btn btn-default pull-right">
              <span className='glyphicon glyphicon-upload' aria-hidden='true'></span>
              Upload Vehicles (CSV)
            </button>
          </div>
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
  onSubmit: (e: any) => void;
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
            <VehicleUpload onSubmit={ props.onSubmit } errors={ props.errors } handleChange={ props.handleChange } />
            <Link to={props.addNewRoute} className='btn btn-default pull-right'>
              <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
              { T.translate(props.modelName + '.addNew') }
            </Link>
            <InfoTable head={ tablehead } data={ props.response.data } onClick={ props.onSelect } />
            <Pagination onClick={props.fetchModels} response={props.response}/>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default VehicleListing;