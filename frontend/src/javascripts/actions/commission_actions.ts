import { COMMISSION_URL } from '../constants/constants.ts';
import { callback, DELETE, GET, POST, PUT } from './fetch_json.ts';

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

export function fetchCommissionOfFleet(vehicleType: string, insuranceType: string, fleetId: number, success?: callback, fail?: callback) {
  GET( COMMISSION_URL, success, fail, getCommissionQuery(vehicleType, insuranceType, -1, fleetId, -1) );
}

export function fetchCommissionOfVehicle(vehicleType: string, insuranceType: string, vehicleId: number, success?: callback, fail?: callback) {
  GET( COMMISSION_URL, success, fail, getCommissionQuery(vehicleType, insuranceType, vehicleId, -1, -1) );
}
