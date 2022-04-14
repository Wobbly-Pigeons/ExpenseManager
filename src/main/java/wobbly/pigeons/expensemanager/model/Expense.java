package wobbly.pigeons.expensemanager.model;

import antlr.collections.List;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
                String localCurrency, LocalDateTime dateOfPurchase,
        Long amount, boolean companyCC, ReceiptStatuses currentStatus,
                String itemName, String itemDescription,  String comment, boolean hasViolated, User user) {

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
    private byte[] receipt;
    private LocalDate dateOfSubmission;
    private LocalDateTime dateOfStatusChange;
    private LocalDateTime dateOfPurchase;
    private LocalDateTime dateModified;
    private ReceiptStatuses currentStatus;
    private ExpenseCategory category;
    private CurrenciesAllowed localCurrency;
    private Long amount;
    private boolean companyCC;
    private String itemName;
    private String itemDescription;
    private String comment;
    private Boolean hasViolated;


    private Long departmentPolicyBudget;
    private Long individualPolicyBudget;


    @ManyToOne(fetch = EAGER)
    @Cascade(value= org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn (name = "user_id")
    private User user;

    public Expense(byte[] receipt, Long amount, User user) {
        this.receipt = receipt;
        this.amount = amount;
        this.user = user;
    }

    public Expense(Long amount, ReceiptStatuses status) {
        this.amount = amount;
        this.currentStatus = status;

    }

    public Expense(Long amount, User user) {
        this.amount = amount;
        this.user = user;
        this.dateOfSubmission = LocalDate.now();
    }

    public Expense(long amount, Employee employee) {

        this.user = employee;
        this.amount = amount;
        this.dateOfSubmission = LocalDate.now();
    }

//    public Expense(Byte[] receiptByte,
//                   ExpenseCategory food,
//                   String usd,
//                   LocalDateTime of,
//                   long l,
//                   boolean b,
//                   ReceiptStatuses currentStatus,
//                   String american_lunch,
//                   String some_description_here,
//                   Employee newEmployee) {
//    }
}
