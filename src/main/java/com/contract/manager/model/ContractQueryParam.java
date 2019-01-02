package com.contract.manager.model;

import lombok.Data;

@Data
public class ContractQueryParam {
    private Contract contract;
    private Page page;
}
