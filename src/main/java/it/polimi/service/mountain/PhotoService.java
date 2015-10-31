
package it.polimi.service.mountain;

import java.util.List;
import it.polimi.model.mountain.Photo;
import it.polimi.searchbean.mountain.PhotoSearchBean;

public interface PhotoService {


    public List<Photo> findById(Long photoId);

    public List<Photo> find(PhotoSearchBean photo);

    public void deleteById(Long photoId);

    public Photo insert(Photo photo);

    public Photo update(Photo photo);

}
