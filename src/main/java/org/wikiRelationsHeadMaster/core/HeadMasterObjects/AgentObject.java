package org.wikiRelationsHeadMaster.core.HeadMasterObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgentObject {
    public String url;
    public String communicationUrl;
    @JsonProperty("type")
    public String type;
    @JsonProperty("port")
    public String port;
    public Integer requestCount;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public void decrementRequestCount(Integer num) {
        this.requestCount -= num;
    }
    public void incrementRequestCount(Integer num) {
        this.requestCount += num;
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
        setCommunicationUrl("http://" + this.url+"/" + this.type);
    }
}
