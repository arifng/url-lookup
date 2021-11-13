package com.arifng.urllookup.service.db;

import com.arifng.urllookup.model.MappedUrl;
import com.arifng.urllookup.repository.MappedUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Rana on 02/07/2021.
 */
@Service
@Transactional
public class MappedUrlServiceImpl implements MappedUrlService {
    private MappedUrlRepository mappedUrlRepository;

    @Autowired
    public void setMappedUrlRepository(MappedUrlRepository mappedUrlRepository) {
        this.mappedUrlRepository = mappedUrlRepository;
    }

    @Override
    public List<MappedUrl> getAll() {
        return mappedUrlRepository.findAll();
    }
}
