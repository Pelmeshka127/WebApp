package com.pelmeshka.music.controllers;

import com.pelmeshka.music.models.Song;
import com.pelmeshka.music.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import org.springframework.core.io.InputStreamResource;

import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "main";
    }

    @GetMapping("/songinfo/{id}")
    public String getSongInfo(@PathVariable Long id, Model model) {
        model.addAttribute("song", songService.getSongById(id));
        return "songinfo";
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        return "upload";
    }

    @PostMapping("/upload")
    public String addMusic(@RequestParam(name = "file") MultipartFile file,
                           @RequestParam(name = "artist") String artist,
                           @RequestParam(name = "title") String title) throws IOException {
        songService.addSong(file, artist, title);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteMusic(@PathVariable Long id) {
        songService.deleteSongById(id);
        return "redirect:/";
    }

    @GetMapping("/song/{id}")
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
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(song.getContentType()))
                .contentLength(song.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(song.getBytes())));
    }
}