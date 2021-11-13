package com.arifng.urllookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Rana on 02/07/2021.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 512851335455726837L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Version
    @Column(name = "version")
    protected Long version = 0L;
}
