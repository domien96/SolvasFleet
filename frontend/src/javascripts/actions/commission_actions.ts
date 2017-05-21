import { COMMISSION_URL } from '../constants/constants.ts';
import { callback, GET, PUT } from './fetch_json.ts';

function getCommissionQuery(vehicleType: string, insuranceType: string, vehicleId: number, fleetId: number, clientId: number) {
  return {
    vehicleType: vehicleType,
    insuranceType: insuranceType,
    vehicle: vehicleId,
    fleet: fleetId,
    company: clientId
  };
}

export function fetchGlobalCommission(vehicleType: string, insuranceType: string, success?: callback, fail?: callback) {
  GET( COMMISSION_URL, success, fail, getCommissionQuery(vehicleType, insuranceType, -1, -1, -1) );
}

export function fetchCommissionOfClient(vehicleType: string, insuranceType: string, clientId: number, success?: callback, fail?: callback) {
  GET( COMMISSION_URL, success, fail, getCommissionQuery(vehicleType, insuranceType, -1, -1, clientId) );
}

export function fetchCommissionOfFleet(vehicleType: string, insuranceType: string, fleetId: number, clientId: number, success?: callback, fail?: callback) {
  GET( COMMISSION_URL, success, fail, getCommissionQuery(vehicleType, insuranceType, -1, fleetId, clientId) );
}

export function fetchCommissionOfVehicle(vehicleType: string, insuranceType: string, vehicleId: number, companyId: number, fleetId: number, success?: callback, fail?: callback) {
  GET( COMMISSION_URL, success, fail, getCommissionQuery(vehicleType, insuranceType, vehicleId, fleetId, companyId) );
}

export function putCommission(body: CommissionData, success?: callback, fail?: callback) {
  PUT( COMMISSION_URL, body , success, fail );
}
