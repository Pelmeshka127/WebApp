package com.pelmeshka.music.services;

import com.pelmeshka.music.models.Song;
import com.pelmeshka.music.repositories.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public Song uploadSong(MultipartFile file, String title, String artist) throws IOException {
        Song music = new Song();
        music.setTitle(title);
        music.setArtist(artist);
        music.setContentType(file.getContentType());
        music.setSize(file.getSize());
        music.setOriginalFileName(file.getOriginalFilename());
        music.setBytes(file.getBytes());
        return songRepository.save(music);
    }

    public Song getSong(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
}
