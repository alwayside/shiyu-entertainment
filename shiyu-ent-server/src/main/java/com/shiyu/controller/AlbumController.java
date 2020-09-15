package com.shiyu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shiyu.authority.AuthorityCenter;
import com.shiyu.entity.model.AlbumDto;
import com.shiyu.entity.repository.AlbumDo;
import com.shiyu.service.AlbumService;
import com.shiyu.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * media-manager
 * 2020/9/4 16:32
 *
 * @since
 **/
@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AuthorityCenter authorityCenter;

    @Autowired
    private PhotoService photoService;

    @GetMapping("/{albumId}")
    public ResponseEntity<?> getAlbumById(@PathVariable("albumId") Long albumId) {
        AlbumDo result = albumService.getAlbumById(albumId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "albumName", required = false) String albumName,
                                    @RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        PageInfo<AlbumDto> results = albumService.searchAlbum(albumName, new Page(pageNum, pageSize));
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/search/{userId}")
    public ResponseEntity<?> searchByUser(@RequestParam(value = "albumName", required = false) String albumName,
                                          @PathVariable("userId") Long userId,
                                          @RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize) {
        PageInfo<AlbumDto> results = albumService.searchAlbumByUser(albumName, userId, new Page(pageNum, pageSize));
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> addAlbum(@RequestBody AlbumDo albumDo,
                                      HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        albumService.saveAlbum(albumDo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{albumId}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("albumId") Long albumId,
                                         HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        albumService.deleteAlbumById(albumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cover/{albumId}")
    public ResponseEntity<?> setCover(@PathVariable("albumId") Long albumId,
                                      MultipartFile file,
                                      HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        if (!photoService.checkFileForSpringUpload(file)) {
            throw new RuntimeException("上传" + file.getOriginalFilename());
        }
        albumService.setAlbumCover(albumId, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{albumId}/cover/{photoId}")
    public ResponseEntity<?> setCover(@PathVariable("albumId") Long albumId,
                                      @PathVariable("photoId") Long photoId,
                                      MultipartFile file,
                                      HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        albumService.setAlbumCoverPhoto(albumId, photoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/photo/{albumId}")
    public ResponseEntity<?> addPhotoToAlbum(@PathVariable("albumId") Long albumId,
                                             MultipartFile file,
                                             HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        if (!photoService.checkFileForSpringUpload(file)) {
            throw new RuntimeException("上传" + file.getOriginalFilename());
        }
        albumService.addPhotoToAlbum(albumId, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{albumId}")
    public ResponseEntity<?> updateAlbum(@PathVariable("albumId") Long albumId,
                                         @RequestBody AlbumDo albumDo,
                                         HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        albumDo.setAlbumId(albumId);
        albumService.updateAlbum(albumDo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{albumId}/{photoId}")
    public ResponseEntity<?> deleteAlbumPhoto(@PathVariable("albumId") Long albumId,
                                              @PathVariable("photoId") Long photoId,
                                              HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        albumService.deleteAlbumPhoto(albumId, photoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
