package solvas.rest.api.models;

import javax.validation.constraints.NotNull;

/**
 * Schema for a contract as defined in the Permission
 * @author Steven Bastiaens
 */
public class ApiPermission extends ApiModel {

    @NotNull
    private String action;

    @NotNull
    private String resource;




}
