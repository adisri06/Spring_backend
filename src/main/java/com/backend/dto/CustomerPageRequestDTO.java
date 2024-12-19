package com.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Data transfer object for pagination")
public class CustomerPageRequestDTO {
    @Schema(description = "Page number to fetch", example = "1", required = true)
    @NotNull(message = "Page number cannot be null / Rectify the page parameter as 'page'")
    @Min(value = 0, message = "Page number must be 0 or greater")
    private Integer page;

    @Schema(description = "Number of records per page", example = "10", required = true)
    @NotNull(message = "Page size cannot be null")
    @Min(value = 1, message = "Page size must be 1 or greater")
    private Integer size;

    @Schema(description = "Optional plan ID to filter customers", example = "1", required = true)
    @NotBlank(message = "Plan ID cannot be blank")
    private String planId;

    // Getters and Setters
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}