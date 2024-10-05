package com.pelmeshka.Cakes.repositories;

import com.pelmeshka.Cakes.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
