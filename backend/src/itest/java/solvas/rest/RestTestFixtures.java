package solvas.rest;

/**
 * Class that contains the test fixtures for restcontrollers
 */
class RestTestFixtures {
    final static String USER_ROOT_URL = "/users";
    final static String USER_ID_URL = "/users/14";

    final static String FLEET_ROOT_URL = "/companies/1/fleets";
    final static String FLEET_ID_URL = "/companies/1/fleets/1";

    final static String VEHICLE_ROOT_URL = "/vehicles";
    final static String VEHICLE_ID_URL = "/vehicles/9";
    final static String VEHICLE_AT_FLEET_ROOT_URL = "/companies/1/fleets/1/vehicles";
    final static String VEHICLE_AT_FLEET_ID_URL = "/companies/1/fleets/1/vehicles/1";
    final static String VEHICLE_TYPES_URL = "/vehicles/types";
    final static String VEHICLE_GREENCARD_URL = "/vehicles/1/greencard.pdf";

    final static String ROLE_ROOT_URL = "/auth/roles";
    final static String ROLE_ID_URL = "/auth/roles/13";
    final static String ROLE_PERMISSION_URL = "/auth/roles/1/permissions";

    final static String PERMISSION_ROOT_URL = "/auth/permissions";

    final static String COMPANY_ROOT_URL = "/companies";
    final static String COMPANY_ID_URL = "/companies/13";

    final static String TAX_BASE_URL = "/vehicles/types/3/taxes/4";
    final static String CONTRACT_ROOT_URL = "/contracts";
    final static String CONTRACT_ID_URL = "/contracts/1";

    final static String CONTRACT_TYPES = "/contracts/types";
    final static String CONTRACT_BY_COMPANY_FLEET_VEHICLE = "/companies/1/fleets/1/vehicles/1/contracts";
    final static String CONTRACT_BY_COMPANY_ID = "/companies/1/contracts";

    final static String INVOICE_ROOT_URL = "/fleets/1/invoices";
    final static String INVOICE_ID_URL = "/fleets/1/invoices/1";
    final static String INVOICE_CURRENT_URL = "/fleets/1/invoices/current";
    final static String INVOICE_PDF_URL = "/fleets/1/invoices/1.pdf";
    final static String INVOICE_CURRENT_PDF = "/fleets/1/invoices/current.pdf";

}
