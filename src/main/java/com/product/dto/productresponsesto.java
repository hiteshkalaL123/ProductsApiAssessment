package com.product.dto;

import java.time.LocalDateTime;

public class productresponsesto {
	 private Long id;

	    private String productName;
	    private String createdBy;
	    private LocalDateTime createdOn;
	    private String modifiedBy;
	    private LocalDateTime modifiedOn;
	    
	    public Long getId() {
			return id;
		}

		public void setId(Long long1) {
			this.id = long1;
		}

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

		public LocalDateTime getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(LocalDateTime createdOn) {
			this.createdOn = createdOn;
		}

		public String getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

		public LocalDateTime getModifiedOn() {
			return modifiedOn;
		}

		public void setModifiedOn(LocalDateTime modifiedOn) {
			this.modifiedOn = modifiedOn;
		}

		

}
