package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Function;

@Repository
public interface FunctionDao extends Dao<Function> {
}
