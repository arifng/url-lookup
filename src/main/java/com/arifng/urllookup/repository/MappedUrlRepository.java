package com.arifng.urllookup.repository;

import com.arifng.urllookup.model.MappedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Rana on 02/07/2021.
 */
@Repository
public interface MappedUrlRepository extends JpaRepository<MappedUrl, Long> {
}
