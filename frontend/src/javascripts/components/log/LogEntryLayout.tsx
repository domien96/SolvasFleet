import React from 'react';
import T from 'i18n-react';
import Header from '../app/Header.tsx';
import { th } from '../../utils/utils.ts';
import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';

interface Props {
  entry: LogEntryData;
  oldEntry: LogEntryData;
  getUser: (id: number) => string;
}

const getCompany = (entity: any) => {
  const company: CompanyData = entity;
  const data = [
    th('company.id', company.id),
    th('company.name', company.name),
    th('company.vatNumber', company.vatNumber),
    th('company.phoneNumber', company.phoneNumber),
    th('company.types', T.translate(`company.type.${company.type}`).toString()),
    th('company.address.street', company.address.street),
    th('company.address.houseNumber', company.address.houseNumber),
    th('company.address.postalCode', company.address.postalCode),
    th('company.address.city', company.address.city),
    th('company.address.country', company.address.country)
  ];
  return data;
};

const getFunction = (entity: any) => {
  const Sfunction: SFunctionData = entity;
  const data = [
    th('function.id', Sfunction.id),
    th('function.role', Sfunction.role),
    th('function.company', Sfunction.company),
    th('function.user', Sfunction.user)
  ];
  return data;
};

const getVehicle = (entity: any) => {
  const vehicle: VehicleData = entity;
  const data = [
    th('vehicle.id', vehicle.id),
    th('vehicle.vin', vehicle.vin),
    th('vehicle.licensePlate', vehicle.licensePlate),
    th('vehicle.brand', vehicle.brand),
    th('vehicle.model', vehicle.model),
    th('vehicle.type', vehicle.type),
    th('vehicle.mileage', vehicle.mileage),
    th('vehicle.fleet', vehicle.fleet),
    th('vehicle.leasingCompany', vehicle.leasingCompany),
    th('vehicle.value', vehicle.value),
    th('vehicle.year', vehicle.year.split('-')[0].toString())
  ];
  return data;
};

const getRole = (entity: any) => {
  const role: RoleData = entity;
  const data = [
    th('role.id', role.id),
    th('role.name', role.name)
  ];
  role.permissions.map((permission: string) => {
    data.push(th('role.permission', permission));
  })
  return data;
};

const getUser = (entity: any) => {
  const user: UserData = entity;
  const data = [
    th('user.id', user.id),
    th('user.firstName', user.firstName),
    th('user.lastName', user.lastName),
    th('user.email', user.email)
  ];
  return data;
};

const getContract = (entity: any) => {
  const contract: ContractData = entity;
  const data = [
    th('contract.id', contract.id),
    th('contract.startDate', contract.startDate),
    th('contract.endDate', contract.endDate),
    th('contract.premium', contract.premium),
    th('contract.franchise', contract.franchise),
    th('contract.insuranceCompany', contract.insuranceCompany),
    th('contract.type', contract.type),
    th('contract.vehicle', contract.vehicle)
  ];
  return data;
};

const getInvoice = (entity: any) => {
  const invoice: InvoiceData = entity;
  const data = [
    th('invoice.id', invoice.id),
    th('invoice.startDate', invoice.startDate),
    th('invoice.endDate', invoice.endDate),
    th('invoice.fleet', invoice.fleet),
    th('invoice.paid', invoice.paid),
    th('invoice.totalAmount', invoice.totalAmount),
    th('invoice.type', invoice.type)
  ];
  return data;
};

const Layout: React.StatelessComponent<Props> = props => {

  const { entry, oldEntry } = props;

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
  let entityInfo: any;

  let oldEntity;
  let oldEntityInfo: any;
  if(oldEntry) {
    oldEntity = JSON.parse(oldEntry.payload);
  }

  if (newEntityType == "Company") {
    entityInfo = getCompany(entity);
    if(oldEntry) oldEntityInfo = getCompany(oldEntity);
  }
  if (newEntityType == "Function") {
    entityInfo = getFunction(entity);
    if(oldEntry) oldEntityInfo = getFunction(oldEntity);
  }
  if (newEntityType == "Vehicle") {
    entityInfo = getVehicle(entity);
    if(oldEntry) oldEntityInfo = getVehicle(oldEntity);
  }
  if (newEntityType == "Role") {
    entityInfo = getRole(entity);
    if(oldEntry) oldEntityInfo = getRole(oldEntity);
  }
  if (newEntityType == "User") {
    entityInfo = getUser(entity);
    if(oldEntry) oldEntityInfo = getUser(oldEntity);
  }
  if (newEntityType == "Contract") {
    entityInfo = getContract(entity);
    if(oldEntry) oldEntityInfo = getContract(oldEntity);
  }
  if (newEntityType == "Invoice") {
    entityInfo = getInvoice(entity);
    if(oldEntry) oldEntityInfo = getInvoice(oldEntity);
  }

  let oldEntityDisplay = (<div></div>);
  if(oldEntry) {
    oldEntityDisplay = (
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h3>Before {entry.method}</h3>
          </div>
          <div className='card-content'>
            <DetailTable data={ oldEntityInfo }/>
          </div>
        </Card>
      </div>
    );
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
      </Card>
      { oldEntityDisplay }
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h3>After {entry.method}</h3>
          </div>
          <div className='card-content'>
            <DetailTable data={ entityInfo }/>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default Layout;
