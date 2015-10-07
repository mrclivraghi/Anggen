
package it.polimi.service;

import java.util.List;
import it.polimi.model.Photo;
import it.polimi.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhotoServiceImpl
    implements PhotoService
{

    @Autowired
    public PhotoRepository photoRepository;

    @Override
    public List<Photo> findById(Long photoId) {
        return photoRepository.findByPhotoId(photoId);
    }

    @Override
    public List<Photo> find(Photo photo) {
        return photoRepository.findByPhotoIdAndUrlAndSocialAndDateAndStatusAndSocialIdAndRelatedPostAndSeedQuery(photo.getPhotoId(),photo.getUrl(),photo.getSocial(),it.polimi.utils.Utility.formatDate(photo.getDate()),photo.getStatus(),photo.getSocialId(),photo.getRelatedPost(),photo.getSeedQuery());
    }

    @Override
    public void deleteById(Long photoId) {
        photoRepository.delete(photoId);
        return;
    }

    @Override
    public Photo insert(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    @Transactional
    public Photo update(Photo photo) {
        Photo returnedPhoto=photoRepository.save(photo);
        if (photo.getSeedQuery()!=null)
        {
        List<Photo> photoList = photoRepository.findBySeedQuery( photo.getSeedQuery());
        if (!photoList.contains(returnedPhoto))
        photoList.add(returnedPhoto);
        returnedPhoto.getSeedQuery().setPhotoList(photoList);
        }
         return returnedPhoto;
    }

}
