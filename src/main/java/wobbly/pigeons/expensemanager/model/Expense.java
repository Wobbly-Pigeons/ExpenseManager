package wobbly.pigeons.expensemanager.model;

import antlr.collections.List;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Expense {

    public Expense(Byte[] receipt, ExpenseCategory category,
                   String localCurrency, LocalDateTime dateOfPurchase,
                   long amount, Double convertedAmount, boolean companyCC, ReceiptStatuses currentStatus,
                   String itemName, String itemDescription, User user) {
        this.receipt = receipt;
        this.dateOfSubmission = LocalDate.now();
        this.dateOfStatusChange = LocalDateTime.now();
        this.dateOfPurchase = dateOfPurchase;
        this.dateModified = LocalDateTime.now();
        this.currentStatus = currentStatus;
        this.category = category;
        this.localCurrency = CurrenciesAllowed.valueOf(localCurrency);
        this.amount = amount;
        this.companyCC = companyCC;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.user = user;
        this.convertedAmount = convertedAmount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "expense_generator")
    @SequenceGenerator(name = "expense_generator", sequenceName = "expense_generator", allocationSize = 1)
    private Long id;
    private Byte[] receipt;
    private LocalDate dateOfSubmission;
    private LocalDateTime dateOfStatusChange;
    private LocalDateTime dateOfPurchase;
    private LocalDateTime dateModified;
    private ReceiptStatuses currentStatus;
    private ExpenseCategory category;
    private CurrenciesAllowed localCurrency;
    private long amount;
    private Double convertedAmount;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;
    private String comment;
    private Boolean hasViolated;


    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;


    public Expense(long amount, User user) {
        this.amount = amount;
        this.user = user;
    }
}
