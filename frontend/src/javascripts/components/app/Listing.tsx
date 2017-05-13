import React from 'react';
import { Link } from 'react-router';
import { th } from '../../utils/utils.ts';
import Card from '../app/Card.tsx';
import Pagination from '../pagination/Pagination.tsx';
import InfoTable from '../tables/InfoTable.tsx';
import T from 'i18n-react';

interface Props {
  onSelect: any;
  addNewRoute: string;
  fetchModels: any;
  modelName: string;
  columns: string[];
  response: ListResponse;
}

const Listing: React.StatelessComponent<Props>  = props =>  {

  const tablehead = props.columns.map(c => {
    return th(c, `${props.modelName}.${c}`);
  });

  return (
    <div className='row'>
      <div className='col-xs-12'>
        <Card>
          <div className='card-content'>
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

export default Listing;
