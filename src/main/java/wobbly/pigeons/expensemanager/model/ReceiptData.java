package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * id: automatically generated as UUID
 name: name of the file
 type: mime type
 data: array of bytes, map to a BLOB
 *
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receipt")
public class ReceiptData {

    @Id
    private Long Id;
    private String name;
    private String type;

    @Lob
    //LOB is datatype for storing large object data.
    private byte[] data;


    public ReceiptData(String fileName, String contentType, byte[] bytes) {


    }
}
