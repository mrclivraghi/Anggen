
package it.polimi.service.mountain;

import java.util.List;
import it.polimi.model.mountain.SeedQuery;
import it.polimi.searchbean.mountain.SeedQuerySearchBean;

public interface SeedQueryService {


    public List<SeedQuery> findById(Long seedQueryId);

    public List<SeedQuery> find(SeedQuerySearchBean seedQuery);

    public void deleteById(Long seedQueryId);

    public SeedQuery insert(SeedQuery seedQuery);

    public SeedQuery update(SeedQuery seedQuery);

}
