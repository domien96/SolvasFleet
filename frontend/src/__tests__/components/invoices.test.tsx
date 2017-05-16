import React from 'react';
import { mount, shallow } from 'enzyme';

import Invoice from '../../javascripts/components/invoices/Invoice.tsx';

jest.mock('../../javascripts/actions/fleet_actions.ts', () => ({
  fetchInvoice: jest.fn().mockImplementation((id, fleetId, success, fail, type) =>
    success({ id: 1, fleet: 1, paid: false, type: 'billing', totalAmount: 100.3, startDate: '10-01-2012', endDate: '10-01-2016' })),
  fetchInvoicePdf: jest.fn(),
  fetchInvoices: jest.fn().mockImplementation((fleetid, success, fail, type) =>
    success({ data: [ { id: 1, fleet: 1, paid: false, type: 'billing', totalAmount: 100.3, startDate: '10-01-2012', endDate: '10-01-2016' } ] }))
}));

const inv = { id: 1, fleet: 1, paid: false, type: 'billing', totalAmount: 100.3, startDate: '10-01-2012', endDate: '10-01-2016' };

test('Invoice renders correctly', () => {
  var invoice = mount(<Invoice params={ { fleetId: 1, invoiceId: 1 } }/>);
  expect(invoice.find('InvoiceView').prop('invoice')).toEqual(inv);
});

import InvoiceView from '../../javascripts/components/invoices/InvoiceView.tsx';

test('InvoiceView renders correctly', () => {
  const onDownload = jest.fn();
  var invoiceView = mount(<InvoiceView invoice={ inv } onDownload={ onDownload }/>);
  expect(invoiceView.find('DetailTable').prop('data')).toEqual(
    [
      { key: "invoice.id", label: "1" },
      { key: "invoice.fleet", label: "1" },
      { key: "invoice.paid", label: "false" },
      { key: "invoice.totalAmount", label: "100.3" },
      { key: "invoice.type", label: inv.type },
      { key: "invoice.startDate", label: inv.startDate },
      { key: "invoice.endDate", label: inv.endDate }
    ]
  );
  expect(invoiceView.find('DownloadButton').prop('onDownload')).toEqual(onDownload);
});

import Invoices from '../../javascripts/components/invoices/Invoices.tsx';

test('Invoices renders correctly', () => {
  var invoices = mount(<Invoices params={ { fleetId: 1 } }/>);
  expect(invoices.find('InvoicesView').prop('invoices')).toEqual([ inv ]);
});

import InvoicesView from '../../javascripts/components/invoices/InvoicesView.tsx';

test('InvoicesView renders correctly', () => {
  var invoicesView = mount(<InvoicesView invoices={ [ inv ] }/>);

  expect(invoicesView.find('InfoTable').prop('head')).toEqual(
    [
      { key: "id", label: "invoice.id"},
      { key: "startDate", label: "invoice.startDate"},
      { key: "endDate", label: "invoice.endDate"},
      { key: "paid", label: "invoice.paid"}
    ]
  );
  expect(invoicesView.find('InfoTable').prop('data')).toEqual([ inv ]);
});
