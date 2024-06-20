
package com.hp.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;


//@JsonPropertyOrder({
//    "appliedFacets",
//    "limit",
//    "offset",
//    "searchText"
//})
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSearch {


   // @Valid
    private AppliedFacets appliedFacets;
    private Integer limit;
    private Integer offset;
    private String searchText;

}
