package com.esfe.Asistencia.controladores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esfe.Asistencia.Modelos.Docente;
import com.esfe.Asistencia.Servicios.Interfaces.IDocenteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/docentes") // Define la dirección URL del controller
public class DocenteController {

    @Autowired
    private IDocenteService docenteService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        
        int currentPage = page.orElse(1) - 1; // PageRequest usa índice base 0
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        
        Page<Docente> docentes = docenteService.buscarTodos(pageable);
        model.addAttribute("docentes", docentes);

        int totalPages = docentes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "docente/index";
    }

    /// CREAR
@GetMapping("/create")
public String create(Model model) {
    model.addAttribute("docente", new Docente());
    model.addAttribute("action", "create");
    return "docente/mant";
}

/// EDITAR
@GetMapping("/edit/{id}")
public String edit(@PathVariable Integer id, Model model) {
    Docente docente = docenteService.buscarPorId(id).orElseThrow();
    model.addAttribute("docente", docente);
    model.addAttribute("action", "edit");
    return "docente/mant";
}

// VER
@GetMapping("/view/{id}")
public String view(@PathVariable Integer id, Model model) {
    Docente docente = docenteService.buscarPorId(id).orElseThrow();
    model.addAttribute("docente", docente);
    model.addAttribute("action", "view");
    return "docente/view";
}

// ELIMINAR Confirmación
@GetMapping("/delete/{id}")
public String deleteConfirm(@PathVariable Integer id, Model model) {
    Docente docente = docenteService.buscarPorId(id).orElseThrow();
    model.addAttribute("docente", docente);
    model.addAttribute("action", "delete");
    return "docente/mant";
}

/// PROCESAR POST según action
@PostMapping("/create")
public String saveNuevo(@Valid @ModelAttribute Docente docente,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
    if (result.hasErrors()) {
        model.addAttribute("action", "create");
        return "docente/mant";
    }
    docenteService.crearOeditar(docente);
    redirectAttributes.addFlashAttribute("msg", "Docente creado exitosamente.");
    return "redirect:/docentes";
}

@PostMapping("/edit")
public String saveEditado(@Valid @ModelAttribute Docente docente,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
    if (result.hasErrors()) {
        model.addAttribute("action", "edit");
        return "docente/mant";
    }
    docenteService.crearOeditar(docente);
    redirectAttributes.addFlashAttribute("msg", "Docente actualizado exitosamente.");
    return "redirect:/docentes";
}

@PostMapping("/delete")
public String deleteDocente(@ModelAttribute Docente docente,
                            RedirectAttributes redirectAttributes) {
    docenteService.eliminarPorId(docente.getId());
    redirectAttributes.addFlashAttribute("msg", "Docente eliminado exitosamente.");
    return "redirect:/docentes";
}

}
