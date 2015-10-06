
package it.polimi.service;

import java.util.List;
import it.polimi.model.Photo;

public interface PhotoService {


    public List<Photo> findById(Long photoId);

    public List<Photo> find(Photo photo);

    public void deleteById(Long photoId);

    public Photo insert(Photo photo);

    public Photo update(Photo photo);

}
