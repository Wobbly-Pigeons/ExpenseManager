package wobbly.pigeons.expensemanager.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Expense {


    public Expense(byte[] receipt, ExpenseCategory category,
                   String localCurrency, LocalDate dateOfPurchase,
                   Double amount, boolean companyCC, ReceiptStatuses currentStatus,
                   String itemName, String itemDescription, String comment, boolean hasViolated, User user) {


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
        this.comment = comment;
        this.hasViolated = hasViolated;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "expense_generator")
    @SequenceGenerator(name = "expense_generator", sequenceName = "expense_generator", allocationSize = 1)
    private Long id;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] receipt;
    //This will contain the logical path to the file in some external storage.
    private LocalDate dateOfSubmission = LocalDate.now();
    private LocalDateTime dateOfStatusChange;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfPurchase;
    private LocalDateTime dateModified;
    private ReceiptStatuses currentStatus;
    private ExpenseCategory category;
    private CurrenciesAllowed localCurrency;
    private Double amount;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;
    private String comment;
    private Boolean hasViolated;
    private Long departmentPolicyBudget;
    private Long individualPolicyBudget;

    @ManyToOne(fetch = EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private User user;

    public Expense(byte[] receipt, Double amount, User user) {
        this.receipt = receipt;
        this.amount = amount;
        this.user = user;
//        this.dateOfPurchase = purchaseDate;
        this.dateOfSubmission = LocalDate.now();
    }

    public Expense(Double amount, ReceiptStatuses status) {
        this.amount = amount;
        this.currentStatus = status;
        this.dateOfSubmission = LocalDate.now();

    }

    public Expense(Double amount, User user) {
        this.amount = amount;
        this.user = user;
        this.dateOfSubmission = LocalDate.now();
    }


    public Expense(Double amount, Employee employee) {

        this.user = employee;
        this.amount = amount;
        this.dateOfSubmission = LocalDate.now();
    }


    public Expense(byte[] bytes, long amount, Employee employee) {
    }



}
