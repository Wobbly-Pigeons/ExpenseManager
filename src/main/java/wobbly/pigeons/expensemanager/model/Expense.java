package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Expense {

    @Id
    private Long id;

    private Byte[] receipt;

    public Expense(Byte[] receipt, ExpenseCategory category,
                   String localCurrency, LocalDateTime dateOfPurchase,
                   long amount, boolean companyCC, ReceiptStatuses currentStatus,
                   String itemName, String itemDescription, User user) {
        this.receipt = receipt;
        this.dateOfSubmission = LocalDateTime.now();
        this.dateOfStatusChange = LocalDateTime.now();
        this.dateOfPurchase = dateOfPurchase;
        this.dateModified = LocalDateTime.now();
        this.currentStatus = currentStatus;
        this.category = category;
        this.localCurrency = localCurrency;
        this.amount = amount;
        this.companyCC = companyCC;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.user = user;
    }

    private LocalDateTime dateOfSubmission;
    private LocalDateTime dateOfStatusChange;
    private LocalDateTime dateOfPurchase;
    private LocalDateTime dateModified;
    private ReceiptStatuses currentStatus;
    @ManyToOne
    private ExpenseCategory category;
    private String localCurrency;
    private long amount;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;


    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;


}
