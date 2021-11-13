package com.arifng.urllookup.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Rana on 03/07/2021.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlLookupResponse {
    private List<String> results;
}
