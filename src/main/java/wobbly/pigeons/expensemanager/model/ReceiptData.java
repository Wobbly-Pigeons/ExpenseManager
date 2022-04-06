package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.*;

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
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "receipt")
public class ReceiptData {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private Long Id;
    private String id;
    private String name;
    private String type;

    @Lob
    //LOB is datatype for storing large object data.
    private byte[] data;


   // public ReceiptData(String fileName, String contentType, byte[] bytes) {


    public ReceiptData(String id, String name, String type, byte[] data) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public ReceiptData() {

    }
}

