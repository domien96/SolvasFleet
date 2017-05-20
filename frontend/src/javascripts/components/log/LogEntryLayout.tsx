import React from 'react';
import T from 'i18n-react';
import Header from '../app/Header.tsx';
import { th } from '../../utils/utils.ts';
import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import LogEntryTable from '../tables/LogEntryTable.tsx';

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

const getFleet = (entity: any) => {
  const fleet: FleetData = entity;
  const data = [
    th('fleet.id', fleet.id),
    th('fleet.name', fleet.name),
    th('fleet.company', fleet.company),
    th('fleet.paymentPeriod', `${fleet.paymentPeriod} month(s)`),
    th('fleet.facturationPeriod', `${fleet.facturationPeriod} month(s)`)
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

const getChangedValues = (oldEntity: any, newEntity: any) => {
  const changed: number[] = [];
  for (let i = 0; i < oldEntity.length; i++) {
    if (oldEntity[i].label !== newEntity[i].label) {
      changed.push(i);
    }
  }
  return changed;
}

const Layout: React.StatelessComponent<Props> = props => {

  const { entry, oldEntry } = props;
  const logDateSplit = entry.logDate.split('T');
  const newLogDate = `${logDateSplit[0]} ${logDateSplit[1]}`;

  const entryInfo = [
    th('log.logDate', newLogDate),
    th('log.entityType', entry.entityType),
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

  if (entry.entityType == "Company") {
    entityInfo = getCompany(entity);
    if (oldEntry) oldEntityInfo = getCompany(oldEntity);
  }
  if (entry.entityType == "Function") {
    entityInfo = getFunction(entity);
    if (oldEntry) oldEntityInfo = getFunction(oldEntity);
  }
  if (entry.entityType == "Vehicle") {
    entityInfo = getVehicle(entity);
    if (oldEntry) oldEntityInfo = getVehicle(oldEntity);
  }
  if (entry.entityType == "Role") {
    entityInfo = getRole(entity);
    if (oldEntry) oldEntityInfo = getRole(oldEntity);
  }
  if (entry.entityType == "User") {
    entityInfo = getUser(entity);
    if (oldEntry) oldEntityInfo = getUser(oldEntity);
  }
  if (entry.entityType == "Contract") {
    entityInfo = getContract(entity);
    if (oldEntry) oldEntityInfo = getContract(oldEntity);
  }
  if (entry.entityType == "Invoice") {
    entityInfo = getInvoice(entity);
    if (oldEntry) oldEntityInfo = getInvoice(oldEntity);
  }
  if (entry.entityType == "Fleet") {
    entityInfo = getFleet(entity);
    if (oldEntry) oldEntityInfo = getFleet(oldEntity);
  }

  let entityDisplay = null;
  if (oldEntry) {
    entityDisplay = (
      <div>
        <div className='col-sm-6'>
          <Card>
            <div className='card-title'>
              <h3>Before {entry.method}</h3>
            </div>
            <div className='card-content'>
              <LogEntryTable 
                data={ oldEntityInfo } 
                changed={ getChangedValues(oldEntityInfo, entityInfo) } />
            </div>
          </Card>
        </div>
        <div className='col-sm-6'>
          <Card>
            <div className='card-title'>
              <h3>After {entry.method}</h3>
            </div>
            <div className='card-content'>
              <LogEntryTable 
                data={ entityInfo }
                changed={ getChangedValues(oldEntityInfo, entityInfo) } />
            </div>
          </Card>
        </div>
      </div>
    );
  } else {
    entityDisplay = (
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h3>After {entry.method}</h3>
          </div>
          <div className='card-content'>
            <DetailTable data={ entityInfo } />
          </div>
        </Card>
      </div>
    );
  }

  if (entry.method === "archive") {
    if (entity.archived) {
      entityDisplay = (
        <div className='col-sm-6'>
          <Card>
            <div className='card-title'>
              <h3>Archived</h3>
            </div>
            <div className='card-content'>
              <DetailTable data={ entityInfo } />
            </div>
          </Card>
        </div>
      );
    } else {
      entityDisplay = (
        <div className='col-sm-6'>
          <Card>
            <div className='card-title'>
              <h3>Restored</h3>
            </div>
            <div className='card-content'>
              <DetailTable data={ entityInfo } />
            </div>
          </Card>
        </div>
      );
    }
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
      { entityDisplay }
    </div>
  );
};

export default Layout;
