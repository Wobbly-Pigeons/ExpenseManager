package wobbly.pigeons.expensemanager.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import wobbly.pigeons.expensemanager.model.ReceiptData;
import wobbly.pigeons.expensemanager.repository.ReceiptDataRepository;

import java.io.IOException;
import java.util.stream.Stream;


/**
 store(file): receives MultipartFile object, transform to ReceiptData object and save it to Database
 getFile(id): returns a ReceiptData object by provided Id
 getAllFiles(): returns all stored files as list of code>Receipt objects
 **/

@Service
public class ReceiptDataService {

    @Autowired
    private ReceiptDataRepository receiptDataRepository;
    public ReceiptData store(@NotNull MultipartFile file) throws IOException {

        //getting automatically original name of the file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ReceiptData receiptData = new ReceiptData(fileName, file.getContentType(), file.getBytes());
        return receiptDataRepository.save(receiptData);
    }
    public ReceiptData getReceipt(String id) {
        return receiptDataRepository.findById(Long.valueOf(id)).get();
    }

    public Stream<ReceiptData> getAllReceipts() {
        return receiptDataRepository.findAll().stream();
    }
}

}
