//package wobbly.pigeons.expensemanager.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import wobbly.pigeons.expensemanager.model.ReceiptData;
//import wobbly.pigeons.expensemanager.repository.ReceiptDataRepository;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Stream;
//
//
///**
// store(file): receives MultipartFile object, transform to ReceiptData object and save it to Database
// getReceipt(id): returns a ReceiptData object by provided Id
// getAllReceipts(): returns all stored files as list of code>Receipt objects
// **/
//
//@Service
//@RequiredArgsConstructor
//public class ReceiptDataService {
//
//    private final ReceiptDataRepository receiptDataRepository;
//
//    public ReceiptData store( MultipartFile file) throws IOException {
//
//        //getting automatically original name of the file
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        ReceiptData receiptData = new ReceiptData(UUID.randomUUID().toString(), fileName, file.getContentType(), file.getBytes());
//        return receiptDataRepository.save(receiptData);
//    }
//    public Optional<ReceiptData> getReceiptById(String id) {
//        return receiptDataRepository.findById(id);
//
////        Optional<ReceiptData> optionalReceipt = receiptDataRepository.findById(id);
////        if (optionalReceipt.isPresent()) {
////            return optionalReceipt.get();
////        } else {
////            System.out.println("There is no receipt with given ID");
////            return null;
////        }
//
//    }
//    //should I make a List instead of stream?
//
//    public List<ReceiptData> getAllReceipts() {
//        return receiptDataRepository.findAll();
//    }
//}

