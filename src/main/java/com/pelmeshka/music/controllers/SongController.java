package com.pelmeshka.music.controllers;

import com.pelmeshka.music.models.Song;
import com.pelmeshka.music.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadSong(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("artist") String artist) throws IOException {
        songService.uploadSong(file, title, artist);
        return "redirect:/";
    }

    @GetMapping("/song/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> playSong(@PathVariable Long id) {
        Song song = songService.getSong(id);
        if (song != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                    .body(song.getBytes());
        }
        return ResponseEntity.notFound().build();
    }
}