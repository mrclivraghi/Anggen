
package it.polimi.service;

import java.util.List;
import it.polimi.model.Collo;

public interface ColloService {


    public List<Collo> findById(Integer colloId);

    public List<Collo> find(Collo collo);

    public void deleteById(Integer colloId);

    public Collo insert(Collo collo);

    public Collo update(Collo collo);

}
