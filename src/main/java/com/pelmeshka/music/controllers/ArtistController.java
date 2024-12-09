package com.pelmeshka.music.controllers;

import com.pelmeshka.music.models.Artist;
import com.pelmeshka.music.services.ArtistService;
import com.pelmeshka.music.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping("/create/artist")
    public String addArtistPage() {
        return "addartist";
    }

    @PostMapping("/create/artist")
    public String addArtist(@RequestParam(name = "name") String name) {
        artistService.addArtist(name);
        return "redirect:/";
    }

    @GetMapping("/artistinfo/{id}")
    public String getArtistInfo(@PathVariable Long id, Model model) {
        model.addAttribute("artist", artistService.getArtistById(id));
        model.addAttribute("songs", artistService.getArtistById(id).getSongs());
        return "artistinfo";
    }

    @PostMapping("/upload/song/{id}")
    public String addMusic( @PathVariable Long id,
                            @RequestParam(name = "file") MultipartFile file,
                            @RequestParam(name = "title") String title)
    throws IOException {
        artistService.addSongToArtist(file, title, id);
        return "redirect:/artistinfo/{id}";
    }

    @PostMapping("/delete/artist")
    public String deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return "redirect:/";
    }
}
