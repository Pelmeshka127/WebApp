package com.pelmeshka.music.controllers;

import com.pelmeshka.music.models.Artist;
import com.pelmeshka.music.models.Song;
import com.pelmeshka.music.services.ArtistService;
import com.pelmeshka.music.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;
    private final ArtistService artistService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        model.addAttribute("artists", artistService.getArtists());
        return "main";
    }

    @GetMapping("/songslist")
    public String getSongsList(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "/songslist";
    }

    @GetMapping("/songinfo/{id}")
    public String getSongInfo(@PathVariable Long id, Model model) {
        model.addAttribute("song", songService.getSongById(id));
        return "songinfo";
    }

    @GetMapping("/song/upload")
    public String uploadSongPage() {
        return "songupload";
    }

//    @PostMapping("/song/upload")
//    public String uploadSong(@RequestParam(name = "file") MultipartFile file,
//                             @RequestParam(name = "title") String title)
//    throws IOException {
//        return "redirect:/";
//    }

    @PostMapping("/song/delete/{artistId}/{songId}")
    public String deleteMusic(@PathVariable Long artistId, @PathVariable Long songId) {
        Artist artist = artistService.getArtistById(artistId);
        artist.getSongs().remove(songService.getSongById(songId));
        songService.deleteSongById(songId);
        return "redirect:/artistinfo/{artistId}";
    }

    @GetMapping("/song/play/{id}")
    public ResponseEntity<?> playSong(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        if (song != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, song.getContentType())
                    .body(song.getBytes());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/song/download/{id}")
    public ResponseEntity<?> downloadSong(@PathVariable Long id) throws IOException {
        Song song = songService.getSongById(id);
        if (song != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(song.getContentType()))
                    .contentLength(song.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(song.getBytes())));
        }
        return ResponseEntity.notFound().build();
    }
}