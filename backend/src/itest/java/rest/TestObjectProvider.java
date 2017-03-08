package rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import solvas.models.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Tijdelijke klasse, in de toekomst testcases verkrijgen aan de hand van json bestanden*/
 class TestObjectProvider {
    private List<Company> companies;
    private List<Vehicle> vehicles;
    private List<User> users;
    private List<Role> roles;
     TestObjectProvider()
    {
        companies=Arrays.asList(
                new Company("Fortis","0130120EOA","04818283883","Fortislaan 1","fortis.be"),
                new Company("Vracht bvba","019991209OA","044583883","vrachtlaan 1","vracht.be")
                );


        users=Arrays.asList(
                new User("John","Doe","john@doe.com","batteryhorse","john.com"),
                new User("Jane","Doe","jane@doe.com","admin","jane.com")

                );

        roles=Arrays.asList(
                new Role(companies.get(0),"whatever",users.get(0),null,null,"role@role.com"),

                new Role(companies.get(1),"god",users.get(1),null,null,"role@role.com")
        );
        vehicles=new ArrayList<>();
        try {
            roles=new ObjectMapper().readValue(new File("/resources/roles.json"), new TypeReference<List<Role>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     Company getCompany()
    {
        return companies.get(0);
    }

     List<Company> getCompanies()
    {
        return companies;
    }

     Vehicle getVehicle()
    {
        return vehicles.get(0);
    }

     List<Vehicle> getVehicles()
    {
        return vehicles;
    }

     User getUser()
    {
        return users.get(0);
    }

     List<User> getUsers()
    {
        return users;
    }

    Role getRole()
    {
        return roles.get(0);
    }
    List<Role> getRoles()
    {
        return roles;
    }

}
