package sky.pro.shelterbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.shelterbot.model.Knowledge;
import sky.pro.shelterbot.service.KnowledgeService;

import java.util.Collection;

@RestController
@RequestMapping("knowledge")
public class KnowledgeController {

    private final KnowledgeService service;

    public KnowledgeController(KnowledgeService service) {
        this.service = service;
    }

    @GetMapping("{idRead}")
    public ResponseEntity<Knowledge> getKnowledgeById(@PathVariable Long idRead) {
        Knowledge knowledgeGet = service.findKnowledgeById(idRead);
        if (knowledgeGet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(knowledgeGet);
    }

    @GetMapping("all")
    public Collection<Knowledge> getAllKnowledge() {
        return service.findAllKnowledge();
    }

}
