package wobbly.pigeons.expensemanager.models;

import lombok.*;
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
    private LocalDateTime dateOfSubmission;
    private LocalDateTime dateOfStatusChange;
    private LocalDateTime dateOfPurchase;
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
