package com.esfe.Asistencia.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.data.domain.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import jakarta.validation.Valid;

import com.esfe.Asistencia.Modelos.Estudiante;
import com.esfe.Asistencia.Servicios.Interfaces.IEstudianteService;


@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private IEstudianteService estudianteService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        
        int currentPage = page.orElse(1) - 1; // PageRequest usa índice base 0
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        
        Page<Estudiante> estudiantes = estudianteService.buscarTodos(pageable);
        model.addAttribute("estudiantes", estudiantes);

        int totalPages = estudiantes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "estudiante/index";
    }

    /// CREAR
@GetMapping("/create")
public String create(Model model) {
    model.addAttribute("estudiante", new Estudiante());
    model.addAttribute("action", "create");
    return "estudiante/mant";
}

/// EDITAR
@GetMapping("/edit/{id}")
public String edit(@PathVariable Integer id, Model model) {
    Estudiante estudiante = estudianteService.buscarPorId(id).orElseThrow();
    model.addAttribute("estudiante", estudiante);
    model.addAttribute("action", "edit");
    return "docente/mant";
}

// VER
@GetMapping("/view/{id}")
public String view(@PathVariable Integer id, Model model) {
    Estudiante estudiante = estudianteService.buscarPorId(id).orElseThrow();
    model.addAttribute("estudiante", estudiante);
    model.addAttribute("action", "view");
    return "estudiante/view";
}

// ELIMINAR Confirmación
@GetMapping("/delete/{id}")
public String deleteConfirm(@PathVariable Integer id, Model model) {
    Estudiante estudiante = estudianteService.buscarPorId(id).orElseThrow();
    model.addAttribute("estudiante", estudiante);
    model.addAttribute("action", "delete");
    return "estudiante/mant";
}

/// PROCESAR POST según action
@PostMapping("/create")
public String saveNuevo(@Valid @ModelAttribute Estudiante estudiante,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
    if (result.hasErrors()) {
        model.addAttribute("action", "create");
        return "estudiante/mant";
    }
    estudianteService.crearOeditar(estudiante);
    redirectAttributes.addFlashAttribute("msg", "Estudiante creado exitosamente.");
    return "redirect:/estudiantes";
}

@PostMapping("/edit")
public String saveEditado(@Valid @ModelAttribute Estudiante estudiante,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
    if (result.hasErrors()) {
        model.addAttribute("action", "edit");
        return "estudiante/mant";
    }
    estudianteService.crearOeditar(estudiante);
    redirectAttributes.addFlashAttribute("msg", "Estudiante actualizado exitosamente.");
    return "redirect:/estudiantes";
}

@PostMapping("/delete")
public String deleteEstudiante(@ModelAttribute Estudiante estudiante,
                            RedirectAttributes redirectAttributes) {
    estudianteService.eliminarPorId(estudiante.getId());
    redirectAttributes.addFlashAttribute("msg", "Estudiante eliminado exitosamente.");
    return "redirect:/estudiantes";
}

}

    

