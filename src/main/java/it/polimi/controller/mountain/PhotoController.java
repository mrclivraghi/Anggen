
package it.polimi.controller.mountain;

import java.util.List;
import it.polimi.model.mountain.Photo;
import it.polimi.searchbean.mountain.PhotoSearchBean;
import it.polimi.service.mountain.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    public PhotoService photoService;
    private final static Logger log = LoggerFactory.getLogger(Photo.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "photo";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        PhotoSearchBean photo) {
        List<Photo> photoList;
        if (photo.getPhotoId()!=null)
         log.info("Searching photo like {}", photo.getPhotoId()+' '+ photo.getUrl());
        photoList=photoService.find(photo);
        getRightMapping(photoList);
         log.info("Search: returning {} photo.",photoList.size());
        return ResponseEntity.ok().body(photoList);
    }

    @ResponseBody
    @RequestMapping(value = "/{photoId}", method = RequestMethod.GET)
    public ResponseEntity getphotoById(
        @PathVariable
        String photoId) {
        log.info("Searching photo with id {}",photoId);
        List<Photo> photoList=photoService.findById(java.lang.Long.valueOf(photoId));
        getRightMapping(photoList);
         log.info("Search: returning {} photo.",photoList.size());
        return ResponseEntity.ok().body(photoList);
    }

    @ResponseBody
    @RequestMapping(value = "/{photoId}", method = RequestMethod.DELETE)
    public ResponseEntity deletephotoById(
        @PathVariable
        String photoId) {
        log.info("Deleting photo with id {}",photoId);
        photoService.deleteById(java.lang.Long.valueOf(photoId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertphoto(
        @org.springframework.web.bind.annotation.RequestBody
        Photo photo) {
        log.info("Inserting photo like {}", photo.getPhotoId()+' '+ photo.getUrl());
        Photo insertedphoto=photoService.insert(photo);
        getRightMapping(insertedphoto);
        log.info("Inserted photo with id {}",insertedphoto.getPhotoId());
        return ResponseEntity.ok().body(insertedphoto);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatephoto(
        @org.springframework.web.bind.annotation.RequestBody
        Photo photo) {
        log.info("Updating photo with id {}",photo.getPhotoId());
        Photo updatedphoto=photoService.update(photo);
        getRightMapping(updatedphoto);
        return ResponseEntity.ok().body(updatedphoto);
    }

    private List<Photo> getRightMapping(List<Photo> photoList) {
        for (Photo photo: photoList)
        {
        getRightMapping(photo);
        }
        return photoList;
    }

    private void getRightMapping(Photo photo) {
        if (photo.getSeedQuery()!=null)
        {
        photo.getSeedQuery().setMountain(null);
        photo.getSeedQuery().setPhotoList(null);
        }
    }

}
