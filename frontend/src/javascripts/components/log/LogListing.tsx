import React from 'react';
import { th } from '../../utils/utils.ts';
import Card from '../app/Card.tsx';
import Pagination from '../pagination/Pagination.tsx';
import InfoTable from '../tables/InfoTable.tsx';

interface Props {
  onSelect: any;
  fetchModels: any;
  modelName: string;
  columns: string[];
  response: ListResponse;
}

const LogListing: React.StatelessComponent<Props>  = props =>  {

  const tablehead = props.columns.map(c => {
    return th(c, `${props.modelName}.${c}`);
  });

  return (
    <div className='row'>
      <div className='col-xs-12'>
        <Card>
          <div className='card-content'>
            <InfoTable head={ tablehead } data={ props.response.data } onClick={ props.onSelect } />
            <Pagination onClick={props.fetchModels} response={props.response}/>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default LogListing;
