package solvas.rest;

/**
 * Class that contains the test fixtures for restcontrollers
 */
public class RestTestFixtures {


    final static String VEHICLE_AT_FLEET_ROOT_URL = "/companies/1/fleets/1/vehicles";
    final static String VEHICLE_AT_FLEET_ID_URL = "/companies/1/fleets/1/vehicles/1";
    final static String VEHICLE_TYPES_URL = "/vehicles/types";
    final static String VEHICLE_GREENCARD_URL = "/vehicles/1/greencard.pdf";

    final static String ROLE_PERMISSION_URL = "/auth/roles/1/permissions";
    final static String CONTRACT_TYPES = "/contracts/types";
    final static String CONTRACT_BY_COMPANY_FLEET_VEHICLE = "/companies/1/fleets/1/vehicles/1/contracts";
    final static String CONTRACT_BY_COMPANY_ID = "/companies/1/contracts";

    final static String INVOICE_CURRENT_URL = "/fleets/1/invoices/current";
    final static String INVOICE_PDF_URL = "/fleets/1/invoices/1.pdf";
    final static String INVOICE_CURRENT_PDF = "/fleets/1/invoices/current.pdf";


    public final static String USER_ROOT_URL = "/users";
    public final static String USER_ID_URL = "/users/1";
    public final static String FLEET_ROOT_URL = "/companies/1/fleets";
    public final static String FLEET_ID_URL = "/companies/1/fleets/1";
    public final static String VEHICLE_ROOT_URL = "/vehicles";
    public final static String VEHICLE_ID_URL = "/vehicles/1";
    public final static String ROLE_ROOT_URL = "/auth/roles";
    public final static String ROLE_ID_URL = "/auth/roles/1";
    public final static String COMPANY_ROOT_URL = "/companies";
    public final static String COMPANY_ID_URL = "/companies/1";
    public final static String INVOICE_ROOT_URL = "/fleets/1/invoices";
    public final static String INVOICE_ID_URL = "/fleets/1/invoices/1";
    public final static String TAX_BASE_URL = "/vehicles/types/PersonalVehicle/taxes/LegalAid";
    public final static String CONTRACT_ROOT_URL = "/contracts";
    public final static String CONTRACT_ID_URL = "/contracts/1";
    public final static String FUNCTION_ROOT_URL = "/users/1/functions";
    public final static String FUNCTION_ID_URL = "/users/1/functions/1";
    public final static String PERMISSION_ROOT_URL = "/auth/roles/1/permissions";

    public final static String VEHICLES_FROM_FLEET_URL = "/companies/1/fleets/1/vehicles";


}
