import React from 'react';
import T from 'i18n-react';
import Header from '../app/Header.tsx';
import { th } from '../../utils/utils.ts';
import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';

interface Props {
  entry: LogEntryData;
  getUser: (id: number) => string;
}

const Layout: React.StatelessComponent<Props> = props => {

  const { entry } = props;

  const entityTypeSplit = entry.entityType.split('.');
  const newEntityType = entityTypeSplit[entityTypeSplit.length - 1];
  const logDateSplit = entry.logDate.split('T');
  const newLogDate = `${logDateSplit[0]} ${logDateSplit[1]}`;

  const entryInfo = [
    th('log.logDate', newLogDate),
    th('log.entityType', newEntityType),
    th('log.method', entry.method),
    th('log.user', props.getUser(entry.user)),
    th('log.entity', entry.entity)
  ];

  const entity = JSON.parse(entry.payload);
  var entityInfo: any = [];

  for (var key in entity) {
    console.log(key);
    entityInfo.push(th(key, entity[key]));
  }

  return (
    <div>
      <Header>
        <h2>{ T.translate('log.entry') } { entry.id }</h2>
      </Header>
      <Card>
        <div className='card-content'>
          <DetailTable data={ entryInfo }/>
        </div>
        <div className='card-title'>
          <h3>Current status entity</h3>
        </div>
        <div className='card-content'>
          <DetailTable data={ entityInfo }/>
        </div>
      </Card>
    </div>
  );
};

export default Layout;
