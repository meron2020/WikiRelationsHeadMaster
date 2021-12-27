package org.wikiRelationsHeadMaster.core.HeadMasterObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgentObject {
    public String url;
    public String communicationUrl;
    @JsonProperty("type")
    public String type;
    public Integer requestCount;

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommunicationUrl() {
        return communicationUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setCommunicationUrl(String communicationUrl) {
        this.communicationUrl = communicationUrl;
    }

    public void setUrl(String url) {
        this.url = url;
        setCommunicationUrl(this.url+"/communication/");
    }
}
