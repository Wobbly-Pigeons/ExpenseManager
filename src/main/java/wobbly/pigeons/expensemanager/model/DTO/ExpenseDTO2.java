package wobbly.pigeons.expensemanager.model.DTO;

import lombok.Data;

import wobbly.pigeons.expensemanager.model.ExpenseCategory;

@Data
public class ExpenseDTO2 {

    private long user_id;
    private byte[] receipt;
    //    private LocalDate dateOfSubmission;
//    private LocalDateTime dateOfStatusChange;
//    private LocalDateTime dateOfPurchase;
//    private LocalDateTime dateModified;
//    private ReceiptStatuses currentStatus;
    private ExpenseCategory category;
//    private CurrenciesAllowed localCurrency;
    private long amount;
    private long convertedAmount;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;
    private String comment;
    private boolean hasViolated;




}