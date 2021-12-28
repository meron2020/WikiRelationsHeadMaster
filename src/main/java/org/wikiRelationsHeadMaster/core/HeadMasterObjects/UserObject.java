package org.wikiRelationsHeadMaster.core.HeadMasterObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserObject {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("query")
    String query;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
