package station404.projetFinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import station404.projetFinal.model.Evaluation;
import station404.projetFinal.model.Incident;
import station404.projetFinal.service.EvaluationService;

import java.util.List;

@Controller
@RequestMapping("/evaluations")
public class EvaluationController {
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

}
