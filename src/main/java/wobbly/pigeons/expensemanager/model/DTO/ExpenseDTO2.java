package wobbly.pigeons.expensemanager.model.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;
import java.time.LocalDate;

@Data
public class ExpenseDTO2 {

    private long user_id;
    private MultipartFile receipt;
    private LocalDate dateOfSubmission = LocalDate.now();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfPurchase;
    private ReceiptStatuses currentStatus;
    private ExpenseCategory category;
    private Double amount;
    private CurrenciesAllowed localCurrency;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;
    private String comment;
    private boolean hasViolated;
}