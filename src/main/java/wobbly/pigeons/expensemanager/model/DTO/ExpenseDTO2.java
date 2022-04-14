package wobbly.pigeons.expensemanager.model.DTO;

import lombok.Data;



import org.springframework.web.multipart.MultipartFile;
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;

@Data
public class ExpenseDTO2 {

    private long user_id;
    private MultipartFile receipt;
    //    private LocalDate dateOfSubmission;
//    private LocalDateTime dateOfStatusChange;
//    private LocalDateTime dateOfPurchase;
//    private LocalDateTime dateModified;
    private ReceiptStatuses currentStatus;
    private ExpenseCategory category;

//    private CurrenciesAllowed localCurrency;
    private long amount;
    private long convertedAmount;

    private CurrenciesAllowed localCurrency;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;
    private String comment;
    private boolean hasViolated;
}