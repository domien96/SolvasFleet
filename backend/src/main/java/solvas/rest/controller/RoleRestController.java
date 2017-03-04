package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Role;
import solvas.persistence.Role.RoleDao;

@RestController
public class RoleRestController extends AbstractRestController<Role> {


    @Autowired
    public RoleRestController(RoleDao dao) {
        super(dao);
    }

    @Override
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    ResponseEntity<?> get() {
        return super.get();
    }

    @Override
    @RequestMapping(value = "/roles/{s_id}",method = RequestMethod.GET)
    ResponseEntity<?> getId(@PathVariable String s_id) {
        return super.getId(s_id);
    }

    @Override
    @RequestMapping(value = "/roles",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Role input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/roles/{s_id}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteId(@RequestBody String s_id) {
        return super.deleteId(s_id);
    }

    @Override
    @RequestMapping(value = "/roles",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Role input) {
        return super.put(input);
    }
}
