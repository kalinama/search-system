package bsuir.ai.nli.controller;

import bsuir.ai.nli.model.document.entity.Document;
import bsuir.ai.nli.model.document.service.DocumentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Resource(name = "defaultDocumentService")
    private DocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView uploadDocument(@RequestParam("documents") MultipartFile[] files) throws IOException {
        List<Document> documents= documentService.createDocument(files);
        documentService.addAll(documents);
        return new ModelAndView("searchSystem");
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getDocument(@PathVariable("id") long id) {
        Document document = documentService.getById(id);
        ModelAndView modelAndView = new ModelAndView("documentDetails");
        modelAndView.addObject("document", document);
        return modelAndView;
    }
}
