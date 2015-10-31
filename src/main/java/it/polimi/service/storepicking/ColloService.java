
package it.polimi.service.storepicking;

import java.util.List;
import it.polimi.model.storepicking.Collo;
import it.polimi.searchbean.storepicking.ColloSearchBean;

public interface ColloService {


    public List<Collo> findById(Integer colloId);

    public List<Collo> find(ColloSearchBean collo);

    public void deleteById(Integer colloId);

    public Collo insert(Collo collo);

    public Collo update(Collo collo);

}
