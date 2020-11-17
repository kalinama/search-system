package bsuir.ai.nli.controller;

import bsuir.ai.nli.model.document.service.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
public class SearcherController {

    @Resource(name = "defaultDocumentService")
    private DocumentService documentService;

    @GetMapping("/")
    public ModelAndView mainPage() {
        return new ModelAndView("searchSystem");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String query) {
        long a1 = System.currentTimeMillis();
        ModelAndView modelAndView = new ModelAndView("searchSystem");
        modelAndView.addObject("documents", documentService.search(query));
        long a2 = System.currentTimeMillis();
        modelAndView.addObject("searchTime", ((double)a2-a1)/1000);
        return modelAndView;
    }
}
