
package it.polimi.service.mountain;

import java.util.List;
import it.polimi.model.mountain.Mountain;

public interface MountainService {


    public List<Mountain> findById(Long mountainId);

    public List<Mountain> find(Mountain mountain);

    public void deleteById(Long mountainId);

    public Mountain insert(Mountain mountain);

    public Mountain update(Mountain mountain);

}
