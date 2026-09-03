package com.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class productrequestdto {
	
	   @NotBlank(message = "Product name is required")
	    @Size(max = 255, message = "Product name must not exceed 255 characters")
	    private String productName;

	    @NotBlank(message = "Created by is required")
	    @Size(max = 100, message = "Created by must not exceed 100 characters")
	    private String createdBy;

	    @Size(max = 100, message = "Modified by must not exceed 100 characters")
	    private String modifiedBy;

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
	}

