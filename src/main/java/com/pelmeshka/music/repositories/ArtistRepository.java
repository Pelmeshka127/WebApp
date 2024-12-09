package com.pelmeshka.music.repositories;

import com.pelmeshka.music.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
