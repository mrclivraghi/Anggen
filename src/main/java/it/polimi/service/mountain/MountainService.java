
package it.polimi.service.mountain;

import java.util.List;
import it.polimi.model.mountain.Mountain;
import it.polimi.searchbean.mountain.MountainSearchBean;

public interface MountainService {


    public List<Mountain> findById(Long mountainId);

    public List<Mountain> find(MountainSearchBean mountain);

    public void deleteById(Long mountainId);

    public Mountain insert(Mountain mountain);

    public Mountain update(Mountain mountain);

}
