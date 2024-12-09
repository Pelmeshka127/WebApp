package com.pelmeshka.music.services;

import com.pelmeshka.music.models.Artist;
import com.pelmeshka.music.models.Song;
import com.pelmeshka.music.repositories.ArtistRepository;
import com.pelmeshka.music.repositories.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public void addArtist(String name) {
        Artist artist = new Artist();
        artist.setName(name);
        artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    public void addSongToArtist(MultipartFile file, String title, Long id) throws IOException {
        Song song = new Song();
        song.setTitle(title);
        song.setOriginalFileName(file.getOriginalFilename());
        song.setContentType(file.getContentType());
        song.setSize(file.getSize());
        song.setBytes(file.getBytes());
        songRepository.save(song);
        Artist artist = artistRepository.findById(id).orElse(null);
        song.setArtist(artist);
        artist.addSong(song);
        artistRepository.save(artist);
    }

    public void deleteSongFromArtist(Artist artist, Long songId) {
        for (int i = 0; i < artist.getSongs().size(); i++) {
            if (songId.equals(artist.getSongs().get(i).getId())) {
                artist.getSongs().remove(artist.getSongs().get(i));
                return;
            }
        }
    }
}