package wobbly.pigeons.expensemanager.models;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Expense {

    @Id
    private long id;

    private Byte[] receipt;

    public Expense(Byte[] receipt, ExpenseCategory category,
                   String localCurrency, LocalDateTime dateOfPurchase,
                   long amount, boolean companyCC, RecieptStatuses currentStatus,
                   String itemName, String itemDescription, UserModel user) {
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
    private RecieptStatuses currentStatus;
    @ManyToOne
    private ExpenseCategory category;
    private String localCurrency;
    private long amount;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;

    @ManyToOne
    private UserModel user;



}
