package com.arifng.urllookup.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Rana on 02/07/2021.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder", toBuilder = true)
@Entity
@Table(name = "mapped_urls")
public class MappedUrl extends BaseEntity {
    // Assumed that, maximum size of the url would be 2048 characters
    // as IE support this as maximum limit.
    @Column(name = "parameterized_url", length = 2048, nullable = false)
    private String parameterizedUrl;

    @Column(name = "pretty_url", length = 2048, nullable = false)
    private String prettyUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MappedUrl)) return false;

        MappedUrl mappedUrl = (MappedUrl) o;
        return getId().equals(mappedUrl.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
