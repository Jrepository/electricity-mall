package com.indi.electricity.mall.model.vo;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ServerInfo {

    private String serverName;

    private String port;

    private Map<String, Object> otherInfo;

}
