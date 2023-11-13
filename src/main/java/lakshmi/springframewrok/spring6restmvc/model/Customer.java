package lakshmi.springframewrok.spring6restmvc.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
	Long id;
	Long version;
	LocalDateTime createdDate;
	LocalDateTime lastModifiedDate;
}
