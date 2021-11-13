package com.arifng.urllookup.form;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Rana on 02/07/2021.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlLookupForm {
    @NotNull
    @Size(min = 1)
    private List<@NotBlank @Size(min = 1) String> inputUrls;
}
