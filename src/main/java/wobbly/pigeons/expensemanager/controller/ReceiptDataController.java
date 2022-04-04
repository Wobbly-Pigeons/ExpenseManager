package wobbly.pigeons.expensemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wobbly.pigeons.expensemanager.model.ReceiptData;
import wobbly.pigeons.expensemanager.service.ReceiptDataService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/receipt")
public class ReceiptDataController {

    @Autowired
    private ReceiptDataService receiptDataService;

    @PostMapping("/upload")
    public ReceiptData uploadReceipt(@RequestParam ("receipt") MultipartFile receipt) throws IOException {
        return receiptDataService.store(receipt);
    }

    @GetMapping("/{id}")
    public Optional<ReceiptData> getReceipt(@PathVariable String id){
        return receiptDataService.getReceiptById(id);

    }

    @GetMapping("/list")
    public List<ReceiptData> getAllReceipt(){
        return receiptDataService.getAllReceipts();

    }
}
