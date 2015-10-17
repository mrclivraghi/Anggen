
package it.polimi.service.mountain;

import java.util.List;
import it.polimi.model.mountain.SeedQuery;

public interface SeedQueryService {


    public List<SeedQuery> findById(Long seedQueryId);

    public List<SeedQuery> find(SeedQuery seedQuery);

    public void deleteById(Long seedQueryId);

    public SeedQuery insert(SeedQuery seedQuery);

    public SeedQuery update(SeedQuery seedQuery);

}
