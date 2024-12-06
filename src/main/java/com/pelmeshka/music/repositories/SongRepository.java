package com.pelmeshka.music.repositories;

import com.pelmeshka.music.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
